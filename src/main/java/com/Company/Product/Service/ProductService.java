package com.Company.Product.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Company.Product.Entity.Product;
import com.Company.Product.Entity.ProductRequest;
import com.Company.Product.ExceptionHandler.ProductNotFoundException;
import com.Company.Product.Repository.ProductRepository;
@Component
public class ProductService {

	@Autowired
	private ProductRepository Productrepo;

	public Product AddProducts(ProductRequest productrequest) {
		Product product = Product.build(0, productrequest.getProductname(), productrequest.getEmail(),
				productrequest.getPhone()

				, productrequest.getCity(), productrequest.getState(), productrequest.getCountry());

		return Productrepo.save(product);
	}

	
	 public List<Iterable<Product>> getALlProducts() {
	        return Arrays.asList(Productrepo.findAll());
	    }
	 
	 public Product getProduct(Integer id) throws ProductNotFoundException {

		 Product product=Productrepo.findById(id).orElse(null);
		 
		 if(product!=null) {
			 return product;
		 }else {
			 throw new  ProductNotFoundException("Product not found with id : "+id);
			 
		 }
	 }

}