package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;

	public Page<Product> listAll(int pageNumber) {
		Sort sort = Sort.by("name").ascending();
		Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
		return productRepo.findAll(pageable);
	}

	public Product save(Product product) {
		Product save = productRepo.save(product);
		return save;
	}

	public Product getById(Integer id) {
		return productRepo.findById(id).get();
	}
	
	public void delete(Integer id) {
		productRepo.deleteById(id);
	}
	
	public List<Product> getAll(){
		return (List<Product>) productRepo.findAll();
	}
	
}
