package com.autoscout24.AutoScout24.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.autoscout24.AutoScout24.dto.ContactedListings;
import com.autoscout24.AutoScout24.dto.Contacts;
import com.autoscout24.AutoScout24.dto.Listings;

@Service
public interface ASListeningReportServices {

	public Map<String, String> getAvgListingSPperSellerType(List<Listings> listingsList);
	
	public Map<String, String> getPerDistributionOfCarByMake(List<Listings> listingsList);
	
	public String getAvgPriceOfMostContactedListings(List<Listings> listingsList, List<Contacts> contactsList);
	
	public Map<Float , List<ContactedListings>> getContactedListingsPerMonth(List<Listings> listingsList, List<Contacts> contactsList);
}
