package org.amit.datawriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.amit.elasticsearch.model.CPNMSNMapping;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class DataWriterInES {
	
	
	public static void writeCPNMSNMap(RestHighLevelClient restHighLevelClientQA, Map<String, Map<String, String>> cpnMSNMapping) {

		int count=0;

		for (String key : cpnMSNMapping.keySet()) {
			
			CPNMSNMapping cpnMsnMapping = new CPNMSNMapping();

			Map<String, String> msnMap = cpnMSNMapping.get(key);

			cpnMsnMapping.setCpnDetails(key);

			List<String> msnProduct= new ArrayList<String>();
			
			//System.out.println("CPN Details: " + key);
			//System.out.println("Search result is below: ");
			for (String mapKey : msnMap.keySet()) {
				msnProduct.add(mapKey+"_"+msnMap.get(mapKey));
				//System.out.println("MSN: " + mapKey + " -> ProductName: " + msnMap.get(mapKey));

			}

			System.out.println(count++);
			
			setMSNCPNMappingPojo(cpnMsnMapping, msnProduct);
			
			insertCPNMSNMapping(cpnMsnMapping, restHighLevelClientQA);

		}

	}

	private static void setMSNCPNMappingPojo(CPNMSNMapping cpnMsnMapping, List<String> msn_Product) {
		if(msn_Product.size()==5) {
			cpnMsnMapping.setMsnProductName1(msn_Product.get(0));
			cpnMsnMapping.setMsnProductName2(msn_Product.get(1));
			cpnMsnMapping.setMsnProductName3(msn_Product.get(2));
			cpnMsnMapping.setMsnProductName4(msn_Product.get(3));
			cpnMsnMapping.setMsnProductName5(msn_Product.get(4));
		}
		
		if(msn_Product.size()==4) {
			cpnMsnMapping.setMsnProductName1(msn_Product.get(0));
			cpnMsnMapping.setMsnProductName2(msn_Product.get(1));
			cpnMsnMapping.setMsnProductName3(msn_Product.get(2));
			cpnMsnMapping.setMsnProductName4(msn_Product.get(3));
		}
		
		if(msn_Product.size()==3) {
			cpnMsnMapping.setMsnProductName1(msn_Product.get(0));
			cpnMsnMapping.setMsnProductName2(msn_Product.get(1));
			cpnMsnMapping.setMsnProductName3(msn_Product.get(2));
		}
		
		if(msn_Product.size()==2) {
			cpnMsnMapping.setMsnProductName1(msn_Product.get(0));
			cpnMsnMapping.setMsnProductName2(msn_Product.get(1));
		}
		
		if(msn_Product.size()==1) {
			cpnMsnMapping.setMsnProductName1(msn_Product.get(0));
		}
		
		
		
	}

	private static CPNMSNMapping insertCPNMSNMapping(CPNMSNMapping msnMappingInfo,
			RestHighLevelClient restHighLevelClientQA) {

		IndexRequest indexRequest = new IndexRequest("cpnmsnmapping_v1", "_doc", UUID.randomUUID().toString())
				.source(getAsJson(msnMappingInfo));
		try {
			IndexResponse response = restHighLevelClientQA.index(indexRequest, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return msnMappingInfo;
	}

	private static JSONObject getAsJson(CPNMSNMapping msnMappingInfo) {
		JSONParser parser = new JSONParser();

		JSONObject obj = new JSONObject();
		try {
			Gson gson = new Gson();
			String jsonProduct = gson.toJson(msnMappingInfo);
			obj = (JSONObject) parser.parse(jsonProduct);

		} catch (ParseException e) {

			System.out.println("Exception occured " + e);
		}
		return obj;
	}

}
