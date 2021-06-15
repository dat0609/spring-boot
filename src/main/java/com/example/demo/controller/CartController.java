package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingCartService;

@Controller
public class CartController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ShoppingCartService cartService;
	
	@RequestMapping("/add")
	public String add(HttpSession session, @RequestParam("id")Integer id,
			@RequestParam(value = "qty", required = false, defaultValue = "1")int qty) {
		Product product = productService.getById(id);
	
		Cart cart = cartService.getCart(session);
		cart.addItem(product, qty);
		return "redirect:/";
	}
	
	@RequestMapping("/remove")
	public String remove(HttpSession session,@RequestParam("id")Integer id) {
		Product product = productService.getById(id);
		Cart cart = cartService.getCart(session);
		cart.removeItem(product);
		
		return "cart";
	}
	
	@RequestMapping("/update")
	public String update(HttpSession session, @RequestParam("id")Integer id,@RequestParam("qty")int qty) {
		
		Product product = productService.getById(id);
		Cart cart = cartService.getCart(session);
		cart.updateItem(product, qty);
		return "cart";
	}
	
}
