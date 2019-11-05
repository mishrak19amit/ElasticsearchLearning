package org.amit.esdemo.query;

import java.io.IOException;

import org.amit.elasticsearch.ESConnectionQA;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class AggrigationDemo {

	public String selectTermAll(RestHighLevelClient restHighLevelClient, String indexs, String types, String field, String value) {
		try {

			SearchSourceBuilder search = new SearchSourceBuilder();
			if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(value)) {
				search.query(QueryBuilders.termQuery("isActive", true));
			}
			
			search.aggregation(AggregationBuilders.terms("category").field("taxonomyList.keyword"));
			search.explain(false);
			SearchRequest request = new SearchRequest();
			request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
			request.source(search);
			request.indices(indexs.split(","));
			request.types(types.split(","));
			
			System.out.println(search);
			
			SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {

		ESConnectionQA esConnection = new ESConnectionQA();
		RestHighLevelClient restHighLevelClient = esConnection.getConnection();
		AggrigationDemo aggrigationDemo= new AggrigationDemo();
		String response=aggrigationDemo.selectTermAll(restHighLevelClient,"product_v2", "_doc", "isActive", "true");
		
		try {
			esConnection.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response);
		
	}

}
