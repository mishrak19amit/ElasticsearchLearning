package org.amit.esdemo.query;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ProductQuery {

	ScoreFunctionBuilder scoreFunctionbuilder = null;
	
	public ScoreFunctionBuilder getFunctionScoreQuery()
	{
		scoreFunctionbuilder=ScoreFunctionBuilders.scriptFunction("if(doc['quantityAvailable.INDIA'].value == 0) {return _score*doc['quantityAvailable.INDIA'].value;} return _score;");
		return scoreFunctionbuilder;
	}
	
	public SearchSourceBuilder getBoolQuery()
	{
		BoolQueryBuilder boolQuery= new BoolQueryBuilder();
		QueryBuilder queryBuilder= QueryBuilders.multiMatchQuery("Amit Mishra").field("productName.shingle_2", 10).operator(Operator.OR).minimumShouldMatch("1");
		boolQuery.must(queryBuilder);
		QueryBuilder filterQueryBuilder= QueryBuilders.termQuery("quantityAvailable.INDIA", "0");
		QueryBuilder termsQueryBuilder= QueryBuilders.termsQuery("quantityAvailable.INDIA", "0");
		boolQuery.filter(filterQueryBuilder);
		boolQuery.filter(termsQueryBuilder);
		boolQuery.must(QueryBuilders.wildcardQuery("productName", "Pump*"));
		boolQuery.must(QueryBuilders.regexpQuery("productName", "Pump*"));
		boolQuery.must(QueryBuilders.fuzzyQuery("productName", "Pump*").fuzziness(Fuzziness.AUTO));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolQuery);
		
		ValueCountAggregationBuilder aggBuilder= AggregationBuilders.count("_id");
		searchSourceBuilder.aggregation(aggBuilder);
		return searchSourceBuilder;
	}
	
	public SearchSourceBuilder getAggrigationQuery()
	{
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		//AggregationBuilders aggBuilder= AggregationBuilders.terms("Amit").field("keyword").size(100);
		
		return searchSourceBuilder;
	}
	
	public static void main(String[] args) {
		ProductQuery obj= new ProductQuery();
		
		System.out.println(obj.getBoolQuery().toString());
	}
}
