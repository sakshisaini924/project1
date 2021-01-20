package com.autoscout24.AutoScout24.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ContactedListings implements Serializable{
	
	private int ranking;
	private long listingId;
	private String make;
	private String sellingPrice;
	private String mileage;
	private long totalContacts;
	
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public long getListingId() {
		return listingId;
	}
	public void setListingId(long listingId) {
		this.listingId = listingId;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public long getTotalContacts() {
		return totalContacts;
	}
	public void setTotalContacts(long totalContacts) {
		this.totalContacts = totalContacts;
	}
	
	public ContactedListings() {}
	
	public ContactedListings(int ranking, long listingId, String make, String sellingPrice, String mileage,
			long totalContacts) {
		super();
		this.ranking = ranking;
		this.listingId = listingId;
		this.make = make;
		this.sellingPrice = sellingPrice;
		this.mileage = mileage;
		this.totalContacts = totalContacts;
	}
	
	@Override
	public String toString() {
		return "ContactedListings [ranking=" + ranking + ", listingId=" + listingId + ", make=" + make
				+ ", sellingPrice=" + sellingPrice + ", mileage=" + mileage + ", totalContacts=" + totalContacts + "]";
	}
	
	

}
