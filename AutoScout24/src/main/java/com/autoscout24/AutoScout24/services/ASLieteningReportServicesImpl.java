package com.autoscout24.AutoScout24.services;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.autoscout24.AutoScout24.dto.ContactedListings;
import com.autoscout24.AutoScout24.dto.Contacts;
import com.autoscout24.AutoScout24.dto.Listings;
import com.autoscout24.AutoScout24.utility.AS24utility;

public class ASLieteningReportServicesImpl implements ASListeningReportServices{

	AS24utility AS24utilityObj = new AS24utility();
	
	
	//Average Listing Selling Price per Seller Type
	public Map<String, String> getAvgListingSPperSellerType(List<Listings> listingsList) {
		
        Map<String, String> avgSPperSellerTypeMap = new HashMap<String, String>();
        
        Map<String, Double> makeDistributionPercentualMap = listingsList.stream()
        .collect(Collectors.groupingBy(Listings::getSeller_type, Collectors.averagingLong(Listings::getPrice)));

        makeDistributionPercentualMap.entrySet().stream().forEach(x -> avgSPperSellerTypeMap.put(x.getKey(),AS24utilityObj.getPriceFormattedValue(x.getValue())));
		
       
        return avgSPperSellerTypeMap;
        
	}


	@Override
	public Map<String, String> getPerDistributionOfCarByMake(List<Listings> listingsList) {
		
		Map<String, String> makeDistributionMap = new LinkedHashMap();
		
		Map<String, Long> makeDistributionPercentualMap = listingsList.stream()
                .collect(Collectors.groupingBy(Listings::getMake, Collectors.counting()));
        
        float total = (float)listingsList.size();
        
        makeDistributionPercentualMap.replaceAll((key, val) -> (long)Math.round((val*100)/total));
        
        Map<String, Long> makeDistributionPercentualSortedMap =
        		makeDistributionPercentualMap.entrySet().stream()
        	       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        	       .collect(Collectors.toMap(
        	          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        
        makeDistributionPercentualSortedMap.entrySet().stream().forEach(x -> makeDistributionMap.put(x.getKey(), x.getValue()+" %"));
		
        return makeDistributionMap;
	}


	@Override
	public String getAvgPriceOfMostContactedListings(List<Listings> listingsList, List<Contacts> contactsList) {
		Map<Long, Long> contactsMap = contactsList.stream()
                .collect(Collectors.groupingBy(Contacts::getListing_id, Collectors.counting()));
        
        long total = listingsList.size();
        
        Map<Long, Long> contactsSortedMap =
        		contactsMap.entrySet().stream()
        	       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        	       .collect(Collectors.toMap(
        	          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //find 30% of contacts
        long t = (long) (0.30*total);
        List mostContactedList= contactsSortedMap.keySet().stream().limit(t).collect(Collectors.toList());
        
        Double price =listingsList.stream().filter(l-> mostContactedList.contains(l.getId()))
        .collect(Collectors.averagingLong(Listings::getPrice));
        
		return AS24utilityObj.getPriceFormattedValue(price);
	}


	@Override
	public Map<Float, List<ContactedListings>> getContactedListingsPerMonth(List<Listings> listingsList,
			List<Contacts> contactsList) {
		
		Map<Float , List<ContactedListings>> contactDtWithcontactedListMap = new LinkedHashMap<Float , List<ContactedListings>>();
	    
		setContactDateVal(contactsList);
		
		Map<Float, Map<Long, Long>> contactDateAndListingMap = contactsList.stream()
			  .collect(Collectors.groupingBy(Contacts::getContact_date,
			  Collectors.groupingBy(Contacts::getListing_id, Collectors.counting())));
			 
        Map<Long, Listings> listingsMap = listingsList.stream().collect(Collectors.toMap(Listings::getId, listingObj -> listingObj));
       
        
        contactDateAndListingMap.entrySet().forEach(e -> {
        	Map<Long,Long> listingWithCountSortedMap = e.getValue().entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	     	  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	     	 
        	List<ContactedListings> contactedListingsList = new LinkedList<ContactedListings>();
	     	  AtomicInteger ranking=new AtomicInteger(0); 
	     	  
	     	 listingWithCountSortedMap.entrySet().stream().limit(5).forEach(m-> {
	     		  Listings listingObj= listingsMap.get(m.getKey());
	
	     		 contactedListingsList.add(new ContactedListings(ranking.incrementAndGet(), m.getKey(), listingObj.getMake(), AS24utilityObj.getPriceFormattedValue(listingObj.getPrice()), AS24utilityObj.changeToDistanceFormat(listingObj.getMileage()), m.getValue()));
	     	  });
	     	  
	     	contactDtWithcontactedListMap.put(e.getKey(), contactedListingsList);
     	  
       });
        
        Map<Float, List<ContactedListings>> contactDtWithcontactedListSortedMap=
        		contactDtWithcontactedListMap.entrySet().stream()
        	       .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
        	       .collect(Collectors.toMap(
        	          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        
		
		return contactDtWithcontactedListSortedMap;
	}
	
	private List<Contacts> setContactDateVal(List<Contacts> contactsList){
		contactsList.stream().forEach(e-> 
		{
			LocalDate contactDt = Instant.ofEpochMilli(e.getContactDateEpoch()).atZone(ZoneId.systemDefault()).toLocalDate();
			String contactDtStr =contactDt.toString();
			  contactDtStr = contactDtStr.replaceAll("-", ".");
			  contactDtStr = contactDtStr.substring(0, contactDtStr.length()-3);
			  e.setContact_date(Float.parseFloat(contactDtStr));
			 
		});
			
		return contactsList;	
	}
	
}
