package com.Company.Product.ApiOperation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Company.Product.Constants.ProductConstant;
import com.Company.Product.Entity.Product;
import com.Company.Product.Entity.ProductRequest;
import com.Company.Product.ExceptionHandler.ProductNotFoundException;
import com.Company.Product.Repository.ProductRepository;
import com.Company.Product.Service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
@RestController
@RequestMapping("/check")
@Api(value = "Book Service", description = "My Book Store")
public class ProductController {

	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

	@Autowired
	private ProductRepository Productrepo;

	@Autowired
	private ProductConstant Productcont;

	@Autowired
	private ProductService productservice;

//	@Autowired
//	private ProductValidation productvalid;
	@ApiOperation(value = "store book api")
	@GetMapping("/welcome")
	public static ResponseEntity<String> helloWorld() {
		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
	@ApiOperation(value = "store book api")
	@SuppressWarnings("static-access")
	@PostMapping
	public ResponseEntity<?> AddProducts(@RequestBody Product product, Integer id) {
		String errormsg;
		try {

			if (product.getEmail() == null || product.getEmail().isEmpty()) {
				errormsg = Productcont.MANDATORY_ATTRIBUTES + "for  Email Id ::";
				LOGGER.warning(errormsg);
				return ResponseEntity.status(404).body(errormsg);

			} else if (product.getPhone() == null || product.getPhone().isEmpty()) {
				errormsg = Productcont.MANDATORY_ATTRIBUTES + "for Phone Number ::";
				LOGGER.warning(errormsg);
				return ResponseEntity.status(404).body(errormsg);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		Productrepo.save(product);
		return ResponseEntity.status(200).body("Sucessfully Created Customer Details: " + id);

	}
	@ApiOperation(value = "search book api")
	@GetMapping("/getting/{id}")
	public ResponseEntity<?> getcustomerById(@PathVariable Integer id) {

		try {
			Product c1 = Productrepo.findById(id).get();
			LOGGER.info("Fetching Datas ------------." + id);
			return ResponseEntity.ok(c1);

		} catch (Exception e) {
			LOGGER.warning("Invalid Data for customerId " + id);
			return ResponseEntity.status(404).body("No Datas Presented in given CustomerId " + id);

		}
	}

	@GetMapping
	public Iterable<Product> getProduct(@RequestParam(name = "_page", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "_limit", defaultValue = "40") Integer pagesize) {
		PageRequest p = PageRequest.of(pageNum - 1, pagesize);
		return Productrepo.findAll(p).getContent();

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> UpdateCustomer(@PathVariable("id") Integer id, @RequestBody Product product) {
		try {
			product.setId(id);
			Productrepo.save(product);
			LOGGER.info("Sucessfully Update Customer Details: " + id);
//		return ResponseEntity.ok(customer);
			return ResponseEntity.status(200).body("Sucessfully Update Customer Details: " + id);
		} catch (Exception e) {
			LOGGER.warning("Invalid datas");
			return ResponseEntity.status(500).body("Invalid Datas are trying to update");

		}
	}

	@PatchMapping("/{id}")
	public Product updateProductFields(@PathVariable int id, @RequestBody Map<String, Object> fields) {
		return updateProductByFields(id, fields);
	}

	public Product updateProductByFields(int id, Map<String, Object> fields) {
		Optional<Product> existingProduct = Productrepo.findById(id);

		if (existingProduct.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Product.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, existingProduct.get(), value);
			});
			return Productrepo.save(existingProduct.get());
		}
		return null;
	}

	@GetMapping("/total")
	public ResponseEntity<?> getCountWithCrudRepository() {
		return ResponseEntity.status(200).body("Total Products : " + Productrepo.count());
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> DeleteCustomer(@PathVariable("id") Integer id, Product product) {
		try {
			Product c1 = Productrepo.findById(id).get();
			Productrepo.delete(c1);
			return ResponseEntity.status(200).body("Deleted Suceesfully CustomerId " + id);

		} catch (Exception e) {
			return ResponseEntity.status(404).body("Doesn't exist CustomerId " + id);
		}
	}

	@DeleteMapping
	public ResponseEntity<?> DeleteAllCustomer(Integer id, Product product) {
		try {
			Iterable<Product> c1 = Productrepo.findAll();
			Productrepo.deleteAll();
			return ResponseEntity.status(200).body("Deleted Suceesfully All Customer ");

		} catch (Exception e) {
			return ResponseEntity.status(404).body("Doesn't exist CustomerId " + id);
		}
	}

	@GetMapping("/get")
	public List<Product> findAll() {

		return (List<Product>) Productrepo.findAll();

	}

	// Another Method to handle @Validation Method

	@PostMapping("/Signup")
	public ResponseEntity<Product> AddProducts(@RequestBody @Valid ProductRequest productrequest) {
		return new ResponseEntity<>(productservice.AddProducts(productrequest), HttpStatus.CREATED);

	}

	@GetMapping("/fetchAll")
	public ResponseEntity<List<Iterable<Product>>> getAllProducts() {
		return ResponseEntity.ok(productservice.getALlProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer id) throws ProductNotFoundException {
		return ResponseEntity.ok(productservice.getProduct(id));
	}

}
