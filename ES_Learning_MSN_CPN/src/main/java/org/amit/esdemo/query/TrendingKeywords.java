package org.amit.esdemo.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.amit.elasticsearch.ESConnectionQA;
import org.amit.elasticsearch.model.TrendingResponse;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class TrendingKeywords {

	public static void main(String[] args) {

		ESConnectionQA esConnection = new ESConnectionQA();
		RestHighLevelClient restHighLevelClient = esConnection.getConnection();
		TrendingKeywords trendingKeywords = new TrendingKeywords();
		SearchResponse response = trendingKeywords.selectTermAll(restHighLevelClient, "trendingcategory", "_doc",
				"isActive");

		if (null != response) {
			List<TrendingResponse> trendingResponse = trendingKeywords.getTrendingResponse(response);
			printTrendingResponse(trendingResponse);
		}

		try {
			esConnection.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(response);
	}

	public SearchResponse selectTermAll(RestHighLevelClient restHighLevelClient, String indexs, String types,
			String field) {
		try {

			SearchSourceBuilder search = new SearchSourceBuilder();
			if (!StringUtils.isEmpty(field)) {
				search.query(QueryBuilders.termQuery(field, true));
			}
			search.sort("searchCount", SortOrder.DESC);
			search.explain(false);
			SearchRequest request = new SearchRequest();
			request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
			request.source(search);
			request.indices(indexs.split(","));
			request.types(types.split(","));

			System.out.println(search);

			SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<TrendingResponse> getTrendingResponse(SearchResponse response) {
		List<TrendingResponse> trendingResponse = new ArrayList<TrendingResponse>();

		for (SearchHit val : response.getHits().getHits()) {

			TrendingResponse trendingResponseObj = new TrendingResponse();

			trendingResponseObj.setCategoryCode(val.getSourceAsMap().get("categoryCode").toString());
			trendingResponseObj.setCategoryName(val.getSourceAsMap().get("category").toString());
			trendingResponseObj.setSearchCount(Integer.parseInt(val.getSourceAsMap().get("searchCount").toString()));

			trendingResponse.add(trendingResponseObj);

		}

		return trendingResponse;

	}

	private static void printTrendingResponse(List<TrendingResponse> trendingResponse) {

		for (TrendingResponse response : trendingResponse) {
			System.out.println("-------------");
			System.out.println(response.toString());
			System.out.println("-------------");
		}
	}
}
