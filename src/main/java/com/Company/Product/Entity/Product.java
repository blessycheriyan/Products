package com.Company.Product.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="products_Category")
@AllArgsConstructor(staticName = "build")
@Getter
@Setter
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Integer id;
	private String productname;
	private String email;
	private String phone;
	private String city;
	private String state;
	private String country;
	
	
	public  Product() {}


	public Product orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
