package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Product;
import domain.ProductCategory;
import dto.CommentDto;
import dto.ProductCategoryDto;
import dto.ProductDto;
import dto.services.Mapper;

@Path("/pCats")
@Stateless
public class CategoryResources {
	@PersistenceContext
	EntityManager em;
	Mapper mapEntitiesToDto = new Mapper();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProductCategory(@QueryParam("cName") String categoryName,
			@QueryParam("name") String productName, @QueryParam("priceL") double priceL,
			@QueryParam("priceH") double priceH) {
		if (categoryName != null) {
			List<Product> result = em.createNamedQuery("product.byPCatName", Product.class)
					.setParameter("categoryName", "%"+categoryName+"%").getResultList();
			if (result == null)
				return Response.status(404).build();
			List<ProductDto> productList = new ArrayList<ProductDto>();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				productList.add(mapEntitiesToDto.mapP(result.get(i)));
			}
			return Response.ok(productList).build();
		}
		if (productName != null) {
			List<Product> result = em.createNamedQuery("product.byPName", Product.class)
					.setParameter("productName", "%"+productName+"%").getResultList();
			if (result == null)
				return Response.status(404).build();
			List<ProductDto> productList = new ArrayList<ProductDto>();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				productList.add(mapEntitiesToDto.mapP(result.get(i)));
			}
			return Response.ok(productList).build();
		}
		if (priceL != 0 || priceH != 0) {
			List<Product> result = em.createNamedQuery("product.byPPrice", Product.class).setParameter("priceL", priceL)
					.setParameter("priceH", priceH).getResultList();
			if (result == null)
				return Response.status(404).build();
			List<ProductDto> productList = new ArrayList<ProductDto>();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				productList.add(mapEntitiesToDto.mapP(result.get(i)));
			}
			return Response.ok(productList).build();
		} else {
			List<ProductCategory> result = em.createNamedQuery("productCategory.all", ProductCategory.class)
					.getResultList();
			if (result == null) {
				return Response.status(404).build();
			}
			List<ProductCategoryDto> productCategoryList = new ArrayList<ProductCategoryDto>();
			int size = result.size();
			for (int i = 0; i < size; i++) {
				productCategoryList.add(mapEntitiesToDto.mapPC(result.get(i)));
			}
			return Response.ok(productCategoryList).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddProductCategory(ProductCategory pCategory) {
		em.persist(pCategory);
		return Response.ok(pCategory.getId()).build();
	}

	@GET
	@Path("/{productCategoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductCategory(@PathParam("productCategoryId") int productId) {
		ProductCategory result = em.createNamedQuery("productCategory.id", ProductCategory.class)
				.setParameter("productCategoryId", productId).getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		ProductCategoryDto resultDto = mapEntitiesToDto.mapPC(result);
		return Response.ok(resultDto).build();
	}

	@PUT
	@Path("/{productCategoryId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProductCategory(@PathParam("productCategoryId") int productId, ProductCategory p) {
		ProductCategory result = em.createNamedQuery("productCategory.id", ProductCategory.class)
				.setParameter("productCategoryId", productId).getSingleResult();
		if (result == null)
			return Response.status(404).build();
		result.setCategoryName(p.getCategoryName());
		result.setProductList(p.getProductList());
		em.persist(result);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{productCategoryId}")
	public Response deleteProductCategory(@PathParam("productCategoryId") int productCategoryId) {
		ProductCategory result = em.createNamedQuery("productCategory.id", ProductCategory.class)
				.setParameter("productCategoryId", productCategoryId).getSingleResult();
		if (result == null)
			return Response.status(404).build();
		em.remove(result);
		return Response.ok().build();
	}

	@GET
	@Path("/{productCategoryId}/products")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@PathParam("productCategoryId") int productCategoryId) {
		ProductCategory result = em.createNamedQuery("productCategory.id", ProductCategory.class)
				.setParameter("productCategoryId", productCategoryId).getSingleResult();
		if (result == null)
			return Response.status(404).build();
		List<ProductDto> productList = new ArrayList<ProductDto>();
		int size = result.getProductList().size();
		for (int i = 0; i < size; i++) {
			productList.add(mapEntitiesToDto.mapP(result.getProductList().get(i)));
		}
		return Response.ok(productList).build();
	}

	@POST
	@Path("/{productCategoryId}/products")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@PathParam("productCategoryId") int productCategoryId, Product product) {
		ProductCategory result = em.createNamedQuery("productCategory.id", ProductCategory.class)
				.setParameter("productCategoryId", productCategoryId).getSingleResult();
		if (result == null)
			return Response.status(404).build();
		result.getProductList().add(product);
		product.setCategory(result);
		em.persist(product);
		return Response.ok().build();
	}

	@GET
	@Path("/{productCategoryId}/products/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("productCategoryId") int productCategoryId,
			@PathParam("productId") int productId) {
		Product result = em.createNamedQuery("product.byPCatAndPId", Product.class)
				.setParameter("productCategoryId", productCategoryId).setParameter("productId", productId)
				.getSingleResult();
		if (result == null)
			return Response.status(404).build();
		ProductDto resultDto = mapEntitiesToDto.mapP(result);
		return Response.ok(resultDto).build();
	}

	@PUT
	@Path("/{productCategoryId}/products/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduct(@PathParam("productCategoryId") int productCategoryId,
			@PathParam("productId") int productId, Product p) {
		Product result = em.createNamedQuery("product.byPCatAndPId", Product.class)
				.setParameter("productCategoryId", productCategoryId).setParameter("productId", productId)
				.getSingleResult();
		if (result == null)
			return Response.status(404).build();
		result.setName(p.getName());
		result.setPrice(p.getPrice());
		result.setCategory(p.getCategory());
		result.setCommentList(p.getCommentList());
		em.persist(result);
		return Response.ok().build();
	}

	@GET
	@Path("/{productCategoryId}/products/{productId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComments(@PathParam("productCategoryId") int productCategoryId,
			@PathParam("productId") int productId) {
		Product result = em.createNamedQuery("product.byPCatAndPId", Product.class)
				.setParameter("productCategoryId", productCategoryId).setParameter("productId", productId)
				.getSingleResult();
		if (result == null)
			return null;
		int size = result.getCommentList().size();
		List<CommentDto> commentList = new ArrayList<CommentDto>();
		for (int i = 0; i < size; i++) {
			commentList.add(mapEntitiesToDto.mapC(result.getCommentList().get(i)));
		}
		return Response.ok(commentList).build();
	}

	@POST
	@Path("/{productCategoryId}/products/{productId}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("productCategoryId") int productCategoryId,
			@PathParam("productId") int productId, Comment comment) {
		Product result = em.createNamedQuery("product.byPCatAndPId", Product.class)
				.setParameter("productCategoryId", productCategoryId).setParameter("productId", productId)
				.getSingleResult();
		if (result == null)
			return Response.status(404).build();
		result.getCommentList().add(comment);
		comment.setProduct(result);
		em.persist(comment);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{productCategoryId}/products/{productId}/comments/{commentId}")
	public Response deleteComment(@PathParam("productCategoryId") int productCategoryId,
			@PathParam("productId") int productId, @PathParam("commentId") int commentId) {
		Comment result = em.createNamedQuery("comment.product.id", Comment.class)
				.setParameter("productCategoryId", productCategoryId).setParameter("productId", productId)
				.setParameter("commentId", commentId).getSingleResult();
		if (result == null)
			return Response.status(404).build();
		em.remove(result);
		return Response.ok().build();
	}

}
