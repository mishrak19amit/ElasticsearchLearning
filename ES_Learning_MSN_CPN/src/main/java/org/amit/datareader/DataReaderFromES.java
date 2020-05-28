package org.amit.datareader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class DataReaderFromES {
	private int foundMatchCount = 0;
	private static String SEARCH_ALIAS_NAME = "product_v2";
	private static String SEARCH_INDEX_TYPE = "_doc";
	private static LinkedHashMap<String, LinkedHashMap<String, String>> cpnMSNMapping = new LinkedHashMap<String, LinkedHashMap<String, String>>();

	public static MultiMatchQueryBuilder buildSearchQueryOrPerFieldBoost(String searchString) {
		return QueryBuilders.multiMatchQuery(searchString).field("productName.shingle_2", 10)
				.field("categoryString.shingle_category", 2).field("brandName.shingle_2", 4)
				.field("concatenatedAttrValues.concatAttr_shingle", 6).type("most_fields").operator(Operator.OR)
				.tieBreaker(new Float(1.0));

	}

	public static BoolQueryBuilder buildBoolQueryForCPNMSN(String searchString) {
		MultiMatchQueryBuilder queryBuilder = buildSearchQueryOrPerFieldBoost(searchString);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(queryBuilder);
		boolQueryBuilder.filter(QueryBuilders.termQuery("isActive", true));
		boolQueryBuilder.filter(QueryBuilders.termQuery("enterpriseType", "global"));

		return boolQueryBuilder;
	}

	public static SearchSourceBuilder buildSearchSourceBuilder(String searchString) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		BoolQueryBuilder boolQueryBuilder = buildBoolQueryForCPNMSN(searchString);

		searchSourceBuilder.size(1).query(boolQueryBuilder);

		return searchSourceBuilder;
	}

	public static SearchRequest getSearchTypeForMSNCPN(String searchString) {

		SearchRequest searchRequest = new SearchRequest(SEARCH_ALIAS_NAME).types(SEARCH_INDEX_TYPE);
		SearchSourceBuilder searchSourceBuilder = buildSearchSourceBuilder(searchString);
		searchRequest.source(searchSourceBuilder);

		return searchRequest;
	}

	public static SearchResponse getSearchResponseForCPNMSN(String searchString,
			RestHighLevelClient restHighLevelClient) {
		SearchRequest searchRequest = getSearchTypeForMSNCPN(searchString);

		SearchResponse response = null;
		try {
			response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			System.out.println(e);
		}

		return response;
	}

	public void prepareCPNMSNMapping(String searchString, RestHighLevelClient restHighLevelClient,
			String actualCPNDetail, String suggestedMsn) {

		SearchResponse response = getSearchResponseForCPNMSN(searchString, restHighLevelClient);

		boolean isForCategoryIdentification=true;
		
		if (null != response) {

			if(isForCategoryIdentification) {
				prepareCategoryIdentificationForCPNs(response, actualCPNDetail, suggestedMsn);
			}
			else {
				prepareCpnMSnMappingForProduct(response, actualCPNDetail, suggestedMsn);
			}
		
		}


	}
	
	private void prepareCategoryIdentificationForCPNs(SearchResponse response, String actualCPNDetail,
			String suggestedMsn) {
		LinkedHashMap<String, String> msnMap = new LinkedHashMap<String, String>();
		
		for (SearchHit val : response.getHits().getHits()) {
			String taxonomyList = val.getSourceAsMap().get("taxonomyList").toString();
			System.out.println(taxonomyList);
			
		}
		
		
	}

	public void prepareCpnMSnMappingForProduct(SearchResponse response, String actualCPNDetail, String suggestedMsn) {

		LinkedHashMap<String, String> msnMap = new LinkedHashMap<String, String>();
		int pos = 1;
		boolean found = false;
		for (SearchHit val : response.getHits().getHits()) {
			if (val.getSourceAsMap().get("moglixPartNumber").toString().compareToIgnoreCase(suggestedMsn) == 0) {
				msnMap.put("FounPosition", String.valueOf(pos));
				found = true;
				foundMatchCount++;
				break;
			}
			pos++;
		}

		if (found) {
			// msnMap.put("suggestedMSN", "suggestedMSN=" + suggestedMsn);
			int countLimit = 9;
			for (SearchHit val : response.getHits().getHits()) {

				msnMap.put(val.getSourceAsMap().get("moglixPartNumber").toString(),
						val.getSourceAsMap().get("moglixPartNumber").toString());
				if (countLimit == 0)
					break;
				countLimit--;

			}

		} else {
			for (int i = 0; i < 10; i++) {
				msnMap.put("", "");
			}
		}
		cpnMSNMapping.put(actualCPNDetail, msnMap);
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getCPNMSNMApping(LinkedHashMap<String, LinkedHashMap<String, String>> cpnMap,
			RestHighLevelClient restHighLevelClientProd) {
		int cpnCount=cpnMap.size();
		for (int i=1;i<cpnCount; i++) {
			String cpnIndex=String.valueOf(i);
			LinkedHashMap<String, String> msnOrgCpns = cpnMap.get(cpnIndex);
			for (String cpnDesc : msnOrgCpns.keySet()) {
				prepareCPNMSNMapping(cpnDesc, restHighLevelClientProd, cpnIndex, msnOrgCpns.get(cpnDesc));
			}

		}

		LinkedHashMap<String, String> foundNotFound = new LinkedHashMap<String, String>();
		int totalCpnCount = cpnMap.keySet().size();
		System.out.println(foundMatchCount);
		int notFountCount = totalCpnCount - foundMatchCount;
		foundNotFound.put(String.valueOf(foundMatchCount), String.valueOf(notFountCount));
		foundNotFound.put(String.valueOf(notFountCount), String.valueOf(foundMatchCount));
		cpnMSNMapping.put("Total CpnCounts " + String.valueOf(totalCpnCount), foundNotFound);

		return cpnMSNMapping;
	}

}
