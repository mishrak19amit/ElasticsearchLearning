/**
 * 
 */
package org.amit.elasticsearch.model;

import java.util.HashMap;
import java.util.Map;


public class ProductPriceInfo {

	private Map<String, Map<String, Double>> salesPrice;			//country to price Type to sales price map
	private Map<String, Map<String, String>> priceRange;			//country to price range map
	private Map<String, Map<String, Integer>> quantityAvailable;		//country to quantity map
	private Map<String, Map<String, Integer>> moq;				//country to moq map
	private Map<String, Map<String, Double>> priceWithoutTax;	
	private Map<String, Map<String, Double>> discountPercentage;				//discount percentage by country
	private Map<String, Map<String, Boolean>> outOfStock;
	
	public ProductPriceInfo(){
		this.salesPrice = new HashMap<String, Map<String,Double>>();
		this.priceRange = new HashMap<String, Map<String,String>>();
		this.quantityAvailable = new HashMap<String, Map<String,Integer>>();
		this.moq = new HashMap<String, Map<String,Integer>>();
		this.priceWithoutTax = new HashMap<String, Map<String,Double>>();
		this.discountPercentage = new HashMap<String, Map<String,Double>>();
		this.outOfStock = new HashMap<String, Map<String,Boolean>>();
	}

	public Map<String, Map<String, Double>> getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Map<String, Map<String, Double>> salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Map<String, Map<String, String>> getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(Map<String, Map<String, String>> priceRange) {
		this.priceRange = priceRange;
	}

	public Map<String, Map<String, Integer>> getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(
			Map<String, Map<String, Integer>> quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Map<String, Map<String, Integer>> getMoq() {
		return moq;
	}

	public void setMoq(Map<String, Map<String, Integer>> moq) {
		this.moq = moq;
	}

	public Map<String, Map<String, Double>> getPriceWithoutTax() {
		return priceWithoutTax;
	}

	public void setPriceWithoutTax(Map<String, Map<String, Double>> priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}

	public Map<String, Map<String, Double>> getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(
			Map<String, Map<String, Double>> discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Map<String, Map<String, Boolean>> getOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(Map<String, Map<String, Boolean>> outOfStock) {
		this.outOfStock = outOfStock;
	}
	
	
	
}
