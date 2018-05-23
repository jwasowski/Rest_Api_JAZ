package rest;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Product;
import domain.ProductCategory;

@Path("/pCat")
@Stateless
public class CategoryResources {
	@PersistenceContext
	EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductCategory> getAllProductCategory() {
		return em.createNamedQuery("productCategory.all", ProductCategory.class).getResultList();
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
		return Response.ok(result).build();
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
	public List<Product> getProducts(@PathParam("productCategoryId") int productCategoryId) {
		return em.createNamedQuery("product.byPCat", Product.class).setParameter("productCategoryId", productCategoryId)
				.getResultList();
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
	@Path("/{productCategoryId}/products/{productId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("productCategoryId") int productCategoryId,
			@PathParam("productId") int productId) {
		Product result = em.createNamedQuery("product.byPCatAndPId", Product.class)
				.setParameter("productCategoryId", productCategoryId).setParameter("productId", productId)
				.getSingleResult();
		if (result == null)
			return null;
		return result.getCommentList();
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
}
