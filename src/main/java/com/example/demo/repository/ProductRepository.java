package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	Product findByName(String name);
}
