package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity(name = "Product")
@Table(name = "product")
@NamedQueries({ @NamedQuery(name = "product.all", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "product.id", query = "SELECT p FROM Product p WHERE p.id=:productId"),
		@NamedQuery(name = "product.byPCat", query = "SELECT p FROM Product p WHERE p.category.id=:productCategoryId"),
		@NamedQuery(name = "product.byPCatAndPId", query = "SELECT p FROM Product p WHERE p.category.id=:productCategoryId AND p.id=:productId"),
		@NamedQuery(name = "product.byPCatName", query = "SELECT p FROM Product p JOIN ProductCategory k ON p.category.id=k.id WHERE k.categoryName=:categoryName"),
		@NamedQuery(name = "product.byPName", query = "SELECT p FROM Product p WHERE p.name LIKE :productName"),
		@NamedQuery(name = "product.byPPrice", query = "SELECT p FROM Product p WHERE p.price >= :price AND p.price <= :priceTwo") })
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productCategory_id")
	private ProductCategory category;
	@OneToMany(mappedBy = "product", orphanRemoval = true)
	@JoinColumn(name = "product")
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
	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

}
