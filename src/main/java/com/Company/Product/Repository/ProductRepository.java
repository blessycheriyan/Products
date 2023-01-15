package com.Company.Product.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.Company.Product.Entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{

}
