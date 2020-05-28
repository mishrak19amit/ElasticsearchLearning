package org.amit.esdemo.query;

import java.io.IOException;

import org.amit.elasticsearch.ESConnectionQA;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class AggrigationDemo {

	public String selectTermAll(RestHighLevelClient restHighLevelClient, String indexs, String types, String field,
			String value) {
		try {

			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(value)) {
				QueryBuilder termQ = QueryBuilders.termsQuery("tags.keyword", "MTI2");
				QueryBuilder matchQ = QueryBuilders.matchQuery("tags.keyword", "MTI5");

				QueryBuilder qb = QueryBuilders.boolQuery().filter(termQ).must(matchQ);

				searchSourceBuilder.query(qb);
			}

			searchSourceBuilder.aggregation(AggregationBuilders.terms("taging").field("tags.keyword"));
			searchSourceBuilder.explain(false);
			SearchRequest request = new SearchRequest();
			request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
			request.source(searchSourceBuilder);
			request.indices(indexs.split(","));
			request.types(types.split(","));

			System.out.println(searchSourceBuilder);

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
		AggrigationDemo aggrigationDemo = new AggrigationDemo();
		String response = aggrigationDemo.selectTermAll(restHighLevelClient, "producttag_v1", "_doc", "isActive",
				"true");

		try {
			esConnection.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response);

	}

}
