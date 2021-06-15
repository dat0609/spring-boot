package com.example.demo.entity;

public class CartItem {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	
//	@ManyToOne
//	@JoinColumn(name = "product_id")
//	private Product product;
//	
//	@ManyToOne
//	@JoinColumn(name = "customer_id")
//	private User user;
//	
//	private int quantity;
//	
//	private double subTotal;
//	
//	public CartItem(Product product) {
//		this.product = product;
//		this.quantity = 1;
//		this.subTotal = product.getPrice();
//	}
//	
//	public double getSubTotal() {
//		subTotal = product.getPrice() * quantity;
//		
//		return subTotal;
//	}
	private Product product;
	private int quantity;
	private double subTotal;
	
	public CartItem(Product product) {
		this.product = product;
		this.quantity = 1;
		this.subTotal = product.getPrice();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotal() {
		subTotal = product.getPrice() * quantity;
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
}
