package com.autoscout24.AutoScout24.dto;

import com.opencsv.bean.CsvBindByName;

public class Listings {
	
	@CsvBindByName
	private long id;
	
	@CsvBindByName
	private String make;
	
	@CsvBindByName
	private long price;
	
	@CsvBindByName
	private long mileage;
	
	@CsvBindByName
	private String seller_type;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getMileage() {
		return mileage;
	}
	public void setMileage(long mileage) {
		this.mileage = mileage;
	}
	public String getSeller_type() {
		return seller_type;
	}
	public void setSeller_type(String seller_type) {
		this.seller_type = seller_type;
	}
	
	public Listings() {
		
	}
	
	public Listings(long id, String make, long price, long mileage, String seller_type) {
		super();
		this.id = id;
		this.make = make;
		this.price = price;
		this.mileage = mileage;
		this.seller_type = seller_type;
	}
	
	

}
