package com.example.demo.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	private String description;
	
	@Column(name = "date")
	private Date createDay;

	@Column(name = "image")
	private String img;
	
	public Date getDate() {
		return createDay;
	}
	
	@Transient
	public String getImg() {
		if(id == 0 || img == null) {
			return null;
		}
		return "/product-images/" + id + "/" + img;
	}
	
}
