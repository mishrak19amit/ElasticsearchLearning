package org.amit.esdemo.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.amit.elasticsearch.ESConnectionQA;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProductSearchQuery {
	private static String SEARCH_ALIAS_NAME = "producttag_v1";
	private static String SEARCH_INDEX_TYPE = "_doc";

	public static void main(String[] args) {

		ESConnectionQA esConnection = new ESConnectionQA();
		RestHighLevelClient restHighLevelClient = esConnection.getConnection();
		ProductSearchQuery aggrigationDemo = new ProductSearchQuery();
		SearchResponse response = aggrigationDemo.selectTermAll(restHighLevelClient, "producttag_v1", "_doc",
				"isActive", "true");

		if (null != response) {

			aggrigationDemo.transFormToObject(response, restHighLevelClient);
		}

		try {
			esConnection.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(response);

	}

	public static SearchRequest getSearchRequestForBoolQuery() {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		boolQueryBuilder.filter(QueryBuilders.termQuery("isActive", true));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolQueryBuilder);

		SearchRequest searchRequest = new SearchRequest(SEARCH_ALIAS_NAME).types(SEARCH_INDEX_TYPE);
		searchRequest.source(searchSourceBuilder);

		return searchRequest;
	}

	private void transFormToObject(SearchResponse response, RestHighLevelClient restHighLevelClient) {
		SearchHit[] results = response.getHits().getHits();
		for (SearchHit hit : results) {
			String sourceAsString = hit.getSourceAsString();
			Map<String, DocumentField> responseFields = hit.getFields();
			//DocumentField msn = responseFields.get("moglixPartNumber");
			//System.out.println(msn);
			Map map = hit.getSourceAsMap();
			String msn=map.get("moglixPartNumber").toString();
			System.out.println(msn);
			// System.out.println(hit.getSourceAsString());
			List<String> tags = new ArrayList<String>();
			tags.add("MTI1");
			tags.add("MTI2");
			tags.add("MTI3");
			map.put("productTagId", tags);

			String productInfo = mapToObjectConverter(map);
			
			addProductInESIndex(restHighLevelClient, productInfo, msn);
			
		}
	}

	private String mapToObjectConverter(Map map) {
		GsonBuilder gsonMapBuilder = new GsonBuilder();
		Gson gsonObject = gsonMapBuilder.create();
		String jsonObject = gsonObject.toJson(map);
		System.out.println(jsonObject);
		return jsonObject;

	}

	private JSONObject getAsJson(String productInfo) {
		JSONParser parser = new JSONParser();

		JSONObject obj = new JSONObject();
		try {

			obj = (JSONObject) parser.parse(productInfo);

		} catch (ParseException e) {

			System.out.println("Exception occured" + e);
		}
		return obj;
	}

	private void addProductInESIndex(RestHighLevelClient restHighLevelClient, String productInfo, String MSN) {
		JSONObject dataAsJson = getAsJson(productInfo);
		
		IndexRequest indexRequest = new IndexRequest(SEARCH_ALIAS_NAME, SEARCH_INDEX_TYPE, MSN).source(dataAsJson);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
	}

	private SearchResponse selectTermAll(RestHighLevelClient restHighLevelClient, String productIndex, String indexType,
			String fieldName, String fieldValue) {

		SearchRequest searchRequest = getSearchRequestForBoolQuery();

		SearchResponse response = null;
		try {
			response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

			System.out.println(response);

		} catch (IOException e) {
			System.out.println(e);
		}

		return response;
	}

}
