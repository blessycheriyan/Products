package com.Company.Product.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products_Category")
@AllArgsConstructor(staticName = "build")
@Getter
@Setter
public class ProductRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;

	@NotNull(message = "Product Name should n't be blank")
	private String productname;

	@Email(message = "Invalid Email Address")
	private String email;
	@NotNull
    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered ")
	private String phone;
	private String city;
	@NotBlank
	private String state;
//	@Min(4)
//	@Max(10)
	private String country;

}
