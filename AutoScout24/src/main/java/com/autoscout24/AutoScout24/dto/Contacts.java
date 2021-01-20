package com.autoscout24.AutoScout24.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.opencsv.bean.CsvBindByName;

public class Contacts {
	
	@CsvBindByName
	private long listing_id;
	@CsvBindByName(column="contact_date")
	private long contactDateEpoch;
	
	private float contact_date;
	
	public long getListing_id() {
		return listing_id;
	}
	public void setListing_id(long listing_id) {
		this.listing_id = listing_id;
	}

	public long getContactDateEpoch() {
		return contactDateEpoch;
	}
	public void setContactDateEpoch(long contactDateEpoch) {
		this.contactDateEpoch = contactDateEpoch;
	}
	public float getContact_date() {
		return contact_date;
	}
	public void setContact_date(float contact_date) {
		this.contact_date = contact_date;
	}
	public Contacts() {}
	public Contacts(long listing_id, long contactDateEpoch, float contact_date) {
		super();
		this.listing_id = listing_id;
		this.contactDateEpoch = contactDateEpoch;
		this.contact_date = contact_date;
	}
	
	
	
	

}
