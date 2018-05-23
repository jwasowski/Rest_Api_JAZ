package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@NamedQueries({ @NamedQuery(name = "product.all", query = "Select p from Product p"),
		@NamedQuery(name = "product.id", query = "Select p from Product p where p.id=:productId"),
		@NamedQuery(name = "product.byPCat", query = "Select p from Product p where p.category.id=:productCategoryId"),
		@NamedQuery(name = "product.byPCatAndPId", query = "Select p from Product p where p.category.id=:productCategoryId and p.id=:productId") })
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductCategory category;
	private List<Comment> commentList = new ArrayList<Comment>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	@XmlTransient
	@OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
}
