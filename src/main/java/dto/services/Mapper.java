package dto.services;

import domain.Comment;
import domain.Product;
import domain.ProductCategory;
import dto.CommentDto;
import dto.ProductCategoryDto;
import dto.ProductDto;

public class Mapper {

	
	
	public ProductCategoryDto mapPC (ProductCategory pC){
		ProductCategoryDto pCD = new ProductCategoryDto();
		pCD.setId(pC.getId());
		pCD.setCategoryName(pC.getCategoryName());
		return pCD;
	}
	
	public ProductDto mapP (Product p){
		ProductDto pD = new ProductDto();
		pD.setId(p.getId());
		pD.setName(p.getName());
		pD.setPrice(p.getPrice());
		return pD;
		
	}
	
	public CommentDto mapC (Comment c){
		CommentDto cD = new CommentDto();
		cD.setId(c.getId());
		cD.setName(c.getName());
		cD.setDate(c.getDate());
		cD.setContents(c.getContents());
		return cD;
		
	}
}
