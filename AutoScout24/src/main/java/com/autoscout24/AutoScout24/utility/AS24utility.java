package com.autoscout24.AutoScout24.utility;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class AS24utility {
	
	public String getPriceFormattedValue(double price) {
		Locale locale = new Locale("de", "DE");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String euroSymbol = currencyFormatter.getCurrency().getSymbol();
        
        String priceStr= currencyFormatter.format(price);
        priceStr = euroSymbol+" "+priceStr.substring(0,priceStr.length()-4)+" - ";
        return priceStr;
	}
	
	public String changeToDistanceFormat(long distance) {
		Locale locale = new Locale("de", "DE");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String distStr= currencyFormatter.format(distance);
        distStr = distStr.substring(0,distStr.length()-5)+" KM";
		return distStr;
	}	
	
	public String changeDateFormat(Float contactData) {
		return "Month : "+contactData.toString().split(".")[1]+""+contactData.toString().split(".")[0];
		
	}
	 

}
