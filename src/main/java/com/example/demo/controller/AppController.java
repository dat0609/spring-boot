package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.authentication.MyUserDetails;
import com.example.demo.entity.FileUploadUtil;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class AppController {

	@Autowired
	UserService service;
	@Autowired
	ProductService productService;

	@GetMapping("")
	public String viewHome(Model model) {
		return listByPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<Product> page = productService.listAll(currentPage);
		long totalItem = page.getTotalElements();
		int totalPage = page.getTotalPages();
		List<Product> list = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list);
		return "home";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register_form";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}

	@GetMapping("/profile")
	public String viewProfile(@AuthenticationPrincipal MyUserDetails user, Model model) {
		model.addAttribute("user", user);
		return "profile";
	}

	@PostMapping("/process_register")
	public String processRegistration(User user) {

		service.saveUserWithDefaultRole(user);

		return "redirect:/login";
	}

	@GetMapping("/admin")
	public String adminPage() {
		return "admin";
	}

	@GetMapping("/new")
	public String showNewProductForm(Model model) {
		model.addAttribute("product", new Product());
		return "new_product";
	}

	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") Product product,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

		String img = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		product.setImg(img);
		
		Product saved = productService.save(product);
		
		String uploadDir = "./product-images/" + saved.getId();
		
		FileUploadUtil.saveFile(uploadDir, multipartFile, img);
		
		return "redirect:/";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Integer id) {

		ModelAndView mav = new ModelAndView("edit_product");
		Product product = productService.getById(id);
		mav.addObject("product", product);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		productService.delete(id);

		return "redirect:/";
	}

	@RequestMapping("/cart")
	public String showCart() {	
		return "cart";
	}

	@RequestMapping("/detail/{id}")
	public String detailProduct(@PathVariable("id")int id,Model model) {
		
		Product product = productService.getById(id);
		model.addAttribute("product", product);
		return "detail";
	}
}
