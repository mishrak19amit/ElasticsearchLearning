package org.amit.elasticsearch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9158099361017623205L;
	private String moglixPartNumber;
	private String moglixProductNo;
	private String supplierPartNumber;
	/* prepare map by country name and price */
	private Map<String, Double> mrp;
	private Map<String, Double> salesPrice;			//country to sales price map
	private Map<String, String> priceRange;			//country to price range map
	private String manufacturerId;
	private String manufacturerName;
	private String itemCode;
	private String variantName;
	private String productName;
	private List<String> productUrl;
	private String shortDesc;
	//private String description;
	private String brandId;
	private String brandName;
	private String brandDisplayName;
	private String brandLink;
	/* last level category code */
	private List<String> categoryCodes;
	private List<String> taxonomyList;
	/* SEO Details */
	/*private List<String> metaKeywords;
	private String metaDescription;
	private String metaTitle;
	private List<String> metaTags;*/
	/* map by country name */
	private Map<String, Double> quantityAvailable;
	private Map<String, Double> moq;
	/* add image details */
	private String mainImagePath;
	private String moglixImageNumber;
	private String mainImageLink;
	private String mainImageAltTag;
	private boolean defaultCombination;
	/* list of product tags */
	//private List<String> productTags;
	/* list of key features */
	//private List<String> keyFeatures;
	/* list of countries */
	private List<String> countriesAvailableIn;
	private Map<String, List<String>> attributeValuesForPart;
	private Map<String, List<String>> aggregatedAttributeValues;
	private List<String> categoryString;
	//@Ignore
	private boolean isActive;
	private Map<String, Double> priceWithoutTax;
	private Map<String, Double> discountPercentage;				//discount percentage by country
	private Map<String, Double> salesPriceSortFieldAsc;
	private Map<String, Double> salesPriceSortFieldDesc;
	//supplier prices
	private transient Map<String, ProductPriceInfo> priceBySupplierId;
	private String productType;
	private String enterpriseType;
	private String suggestionTerms;
	private String productNameBrandCategoryAttr;
	private String concatenatedAttrValues;
	private Map<String, Double> score;
	
	private String uom;
	private Integer quantityUom;
	private String itemsInPack;
	private String productHsn;
	private String rating;


	public ProductInfo() {
		this.mrp = new HashMap<String, Double>();
		this.salesPrice = new HashMap<String, Double>();
		this.priceRange = new HashMap<String, String>();
		this.categoryCodes = new ArrayList<>();
		this.taxonomyList = new ArrayList<>();
		this.categoryString = new ArrayList<>();
		this.attributeValuesForPart = new HashMap<String, List<String>>();
		this.aggregatedAttributeValues = new HashMap<String, List<String>>();
		this.countriesAvailableIn = new ArrayList<String>();
		this.moq = new HashMap<String, Double>();
		
		this.quantityAvailable = new HashMap<String, Double>();
		this.defaultCombination = true;
		this.isActive = true;
		this.priceWithoutTax = new HashMap<>();
		this.discountPercentage = new HashMap<String, Double>();
		this.salesPriceSortFieldAsc = new HashMap<String, Double>();
		this.salesPriceSortFieldDesc = new HashMap<String, Double>();
		this.priceBySupplierId = new HashMap<String, ProductPriceInfo>();
		this.score = new HashMap<String, Double>();
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String getManufacturerId() {
		return manufacturerId;
	}


	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}


	public String getBrandId() {
		return brandId;
	}


	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}


	public String getMoglixPartNumber() {
		return moglixPartNumber;
	}

	public void setMoglixPartNumber(String moglixPartNumber) {
		this.moglixPartNumber = moglixPartNumber;
	}

	public String getMoglixProductNo() {
		return moglixProductNo;
	}

	public void setMoglixProductNo(String moglixProductNo) {
		this.moglixProductNo = moglixProductNo;
	}

	public String getSupplierPartNumber() {
		return supplierPartNumber;
	}

	public void setSupplierPartNumber(String supplierPartNumber) {
		this.supplierPartNumber = supplierPartNumber;
	}

	public Map<String, Double> getMrp() {
		return mrp;
	}

	public void setMrp(Map<String, Double> mrp) {
		this.mrp = mrp;
	}

	public Map<String, Double> getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Map<String, Double> salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getVariantName() {
		return variantName;
	}

	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<String> getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(List <String> urlList) {
		this.productUrl = urlList;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	
	public Map<String, Double> getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Map<String, Double> quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Map<String, Double> getMoq() {
		return moq;
	}

	public void setMoq(Map<String, Double> moq) {
		this.moq = moq;
	}

	public String getMainImagePath() {
		return mainImagePath;
	}

	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}

	public String getMoglixImageNumber() {
		return moglixImageNumber;
	}

	public void setMoglixImageNumber(String moglixImageNumber) {
		this.moglixImageNumber = moglixImageNumber;
	}

	public String getMainImageLink() {
		return mainImageLink;
	}

	public void setMainImageLink(String mainImageLink) {
		this.mainImageLink = mainImageLink;
	}

	public String getMainImageAltTag() {
		return mainImageAltTag;
	}

	public void setMainImageAltTag(String mainImageAltTag) {
		this.mainImageAltTag = mainImageAltTag;
	}

	public List<String> getCountriesAvailableIn() {
		return countriesAvailableIn;
	}

	public void setCountriesAvailableIn(List<String> countriesAvailableIn) {
		this.countriesAvailableIn = countriesAvailableIn;
	}

	public Map<String, String> getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(Map<String, String> priceRange) {
		this.priceRange = priceRange;
	}

	public boolean isDefaultCombination() {
		return defaultCombination;
	}

	public void setDefaultCombination(boolean isDefaultCombination) {
		this.defaultCombination = isDefaultCombination;
	}

	public Map<String, List<String>> getAttributeValuesForPart() {
		return attributeValuesForPart;
	}

	public void setAttributeValuesForPart(Map<String, List<String>> attributeValuesForPart) {
		this.attributeValuesForPart = attributeValuesForPart;
	}

	public Map<String, List<String>> getAggregatedAttributeValues() {
		return aggregatedAttributeValues;
	}

	public void setAggregatedAttributeValues(Map<String, List<String>> aggregatedAttributeValues) {
		this.aggregatedAttributeValues = aggregatedAttributeValues;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public void setTaxonomyList(List<String> taxonomyList) {
		this.taxonomyList = taxonomyList;
	}

	public List<String> getTaxonomyList() {
		return taxonomyList;
	}

	public void setCategoryStrings(List<String> categoryString) {
		this.categoryString = categoryString;
	}

	public List<String> getCategoryString() {
		return categoryString;
	}

	public void setCategoryString(List<String> categoryString) {
		this.categoryString = categoryString;
	}
	

	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	public Map<String, Double> getPriceWithoutTax() {
		return priceWithoutTax;
	}


	public void setPriceWithoutTax(Map<String, Double> priceWithoutTax) {
		this.priceWithoutTax = priceWithoutTax;
	}

	public Map<String, Double> getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Map<String, Double> discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Map<String, Double> getSalesPriceSortFieldAsc() {
		return salesPriceSortFieldAsc;
	}


	public void setSalesPriceSortFieldAsc(Map<String, Double> salesPriceSortFieldAsc) {
		this.salesPriceSortFieldAsc = salesPriceSortFieldAsc;
	}


	public Map<String, Double> getSalesPriceSortFieldDesc() {
		return salesPriceSortFieldDesc;
	}


	public void setSalesPriceSortFieldDesc(
			Map<String, Double> salesPriceSortFieldDesc) {
		this.salesPriceSortFieldDesc = salesPriceSortFieldDesc;
	}
	

	public String getBrandLink() {
		return brandLink;
	}


	public void setBrandLink(String brandLink) {
		this.brandLink = brandLink;
	}
	

	public Map<String, ProductPriceInfo> getPriceBySupplierId() {
		return priceBySupplierId;
	}


	public void setPriceBySupplierId(Map<String, ProductPriceInfo> priceBySupplierId) {
		this.priceBySupplierId = priceBySupplierId;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}
	

	public String getBrandDisplayName() {
		return brandDisplayName;
	}


	public void setBrandDisplayName(String brandDisplayName) {
		this.brandDisplayName = brandDisplayName;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getSuggestionTerms() {
		return suggestionTerms;
	}


	public void setSuggestionTerms(String suggestionTerms) {
		this.suggestionTerms = suggestionTerms;
	}


	public String getProductNameBrandCategoryAttr() {
		return productNameBrandCategoryAttr;
	}


	public void setProductNameBrandCategoryAttr(String productNameBrandCategoryAttr) {
		this.productNameBrandCategoryAttr = productNameBrandCategoryAttr;
	}


	public String getConcatenatedAttrValues() {
		return concatenatedAttrValues;
	}


	public void setConcatenatedAttrValues(String concatenatedAttrValues) {
		this.concatenatedAttrValues = concatenatedAttrValues;
	}
	public Map<String, Double> getScore() {
		return score;
	}
	public void setScore(Map<String, Double> score) {
		this.score = score;
	}
	
	public String getUom(){
		return uom;
	}
	
	public void setUom(String uom){
		this.uom = uom;
	}

	public Integer getQuantityUom(){
		return quantityUom;
	}
	
	public void setQuantityUom(Integer quantityUom){
		this.quantityUom = quantityUom;
	}
	
	public String getProductHsn() {
		return productHsn;
	}


	public void setProductHsn(String productHsn) {
		this.productHsn = productHsn;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getItemsInPack() {
		return itemsInPack;
	}

	public void setItemsInPack(String itemsInPack) {
		this.itemsInPack = itemsInPack;
	}
	
	
}
