package com.autoscout24.AutoScout24.controller;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.autoscout24.AutoScout24.dto.ContactedListings;
import com.autoscout24.AutoScout24.dto.Contacts;
import com.autoscout24.AutoScout24.dto.Listings;
import com.autoscout24.AutoScout24.services.ASListeningReportServices;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


@Controller
public class ASListingReportController {
	
	@Autowired
	ASListeningReportServices ASListeningReportServicesObj;
	
	@Value("${listingFile.path}")
	private String listingFilePath;
	
	@Value("${contactsFile.path}")
	private String contactsFilePath;
	
	
	 @GetMapping("/AS24/home")
	 public String showAS24Report( Model model) {

	     try{
	        //String listingsfileName = "C:\\Users\\amsa6576\\Desktop\\AutoScout24\\listings.csv";

	        List<Listings> listingsList = new CsvToBeanBuilder(new FileReader(listingFilePath))
	                        .withType(Listings.class)
	                        .build()
	                        .parse();
	                
	         //Average Listing Selling Price per Seller Type
	         Map<String, String> avgSPperSellerTypeMap = ASListeningReportServicesObj.getAvgListingSPperSellerType(listingsList);
	         model.addAttribute("avgSPperSellerTypeMap", avgSPperSellerTypeMap);
	                
	                
	         //Percentual distribution of available cars by Make
	         Map<String, String> makeDistributionPercentualMap = ASListeningReportServicesObj.getPerDistributionOfCarByMake(listingsList);
	         model.addAttribute("makeDistributionPercentualMap", makeDistributionPercentualMap);
	                
	                
	         //Average price of the 30% most contacted listings
	          //String contactsfileName = "C:\\Users\\amsa6576\\Desktop\\AutoScout24\\contacts.csv";
	          List<Contacts> contactsList = new CsvToBeanBuilder(new FileReader(contactsFilePath))
	                        .withType(Contacts.class)
	                        .build()
	                        .parse();
	          

	          String avgPrice = ASListeningReportServicesObj.getAvgPriceOfMostContactedListings(listingsList, contactsList);
	          model.addAttribute("avgPrice",avgPrice);
	              
	              
	          //The Top 5 most contacted listings per Month
	          Map<Float , List<ContactedListings>> contactDtWithcontactedListMap = ASListeningReportServicesObj.getContactedListingsPerMonth(listingsList, contactsList);
	          model.addAttribute("contactDtWithcontactedListMap", contactDtWithcontactedListMap);
					
	      } catch (Exception ex) {
	           model.addAttribute("message", "An error occurred while processing the reports.");
	           model.addAttribute("status", false);
	      }
	       
	        return "AS24Report.html";
	    }
	 
	 
	 
}
