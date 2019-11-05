package org.amit.esdemo.query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.amit.elasticsearch.ESConnectionQA;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion.Entry.Option;
import org.elasticsearch.search.suggest.phrase.DirectCandidateGeneratorBuilder;

public class PhraseSuggestDemo {

	public SearchRequest getPhraseSearchRequest(String text, String query) {
		int editsize = 2;

		String SUGGESTION_FIELD = "suggestionTerms.v2";

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		SuggestionBuilder phraseSugggestionBuilder = SuggestBuilders.phraseSuggestion(SUGGESTION_FIELD).text(text)
				.highlight("<em>", "</em>").maxErrors(new Float(Double.parseDouble("0.9")))
				.gramSize(new Integer(Integer.parseInt("3"))).realWordErrorLikelihood(new Float(0.95))
				.addCandidateGenerator(
						new DirectCandidateGeneratorBuilder(SUGGESTION_FIELD).suggestMode("always").maxEdits(editsize))
				// .collateQuery(query)
				.size(2);
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		suggestBuilder.addSuggestion("phrase-suggestion", phraseSugggestionBuilder);
		searchSourceBuilder.suggest(suggestBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("product_v2");
		searchRequest.types("_doc");
		searchRequest.source(searchSourceBuilder);

		System.out.println(searchSourceBuilder);

		return searchRequest;
	}

	public SearchRequest getSuggestionSearchRequest(String name, String query) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		int qlen = query.length();
		SuggestionBuilder termSuggestionBuilder;
		// termSuggestionBuilder =
		// SuggestBuilders.completionSuggestion(name).text(query);
		// Fuzzy functionality has been added for suggestion
		if (qlen <= 3)
			termSuggestionBuilder = SuggestBuilders.completionSuggestion(name).prefix(query).size(20);
		else if (3 < qlen && qlen < 7)
			termSuggestionBuilder = SuggestBuilders.completionSuggestion(name).prefix(query, Fuzziness.ONE).size(20);
		else
			termSuggestionBuilder = SuggestBuilders.completionSuggestion(name).prefix(query, Fuzziness.TWO).size(20);
		// SuggestionBuilder termSuggestionBuilder =
		// SuggestBuilders.completionSuggestion(name).prefix(query).size(20);
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		suggestBuilder.addSuggestion("auto-complete", termSuggestionBuilder);
		searchSourceBuilder.suggest(suggestBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("suggestion");
		searchRequest.types("suggest");
		searchRequest.source(searchSourceBuilder);
		System.out.println(searchSourceBuilder);
		return searchRequest;
	}

	public String getSuggestedCategory(SearchResponse suggestionsResponse) {
		String suggestedcategory = "";
		Suggest suggest = suggestionsResponse.getSuggest();
		CompletionSuggestion completionSuggestion = suggest.getSuggestion("auto-complete");
		int suggestionWeight = 0;
		if (completionSuggestion != null && completionSuggestion.getOptions() != null
				&& !completionSuggestion.getOptions().isEmpty()) {
			List<Option> suggestions = completionSuggestion.getOptions();
			Iterator<Option> optionsItr = suggestions.iterator();

			while (optionsItr.hasNext()) {

				CompletionSuggestion.Entry.Option option = optionsItr.next();
				SearchHit hit = option.getHit();
				Map<String, Object> sourceMap = hit.getSourceAsMap();
				Map<String, Object> payload = (Map<String, Object>) sourceMap.get("payload");
				Map<String, Object> autoCompleteEntry = (Map<String, Object>) sourceMap.get("autoCompleteEntry");
				String weight = autoCompleteEntry.get("weight").toString();
				int outputWeight = Integer.parseInt(weight);
				/* Suggesting higher weight category */
				if (suggestionWeight < outputWeight) {
					suggestedcategory = payload.get("category").toString();
				}
			}
		}
		return suggestedcategory;
	}

	public static void main(String[] args) {
		ESConnectionQA esConnection = new ESConnectionQA();
		RestHighLevelClient restHighLevelClient = esConnection.getConnection();
		PhraseSuggestDemo phraseSuggestDemo = new PhraseSuggestDemo();
		// SearchRequest searchRequest=phraseSuggestDemo.getPhraseSearchRequest("shoes",
		// "shoes");

		Map<String, String> keyWordCategoryMapping= new HashMap<String, String>();
		keyWordCategoryMapping.put("shoes", "");
		keyWordCategoryMapping.put("bsp outlet", "");
		keyWordCategoryMapping.put("10 hp sumer", "");
		keyWordCategoryMapping.put("100 watts led street light", "");
		
		try {
			
			for(String keyWord: keyWordCategoryMapping.keySet()) {
			
			SearchRequest searchautocompleteRequest = phraseSuggestDemo
					.getSuggestionSearchRequest("autoCompleteEntry.input", keyWord);
			SearchResponse response = restHighLevelClient.search(searchautocompleteRequest, RequestOptions.DEFAULT);

			String suggestedcategory=phraseSuggestDemo.getSuggestedCategory(response);
			
			System.out.println("Suggested Category for keyword:"+ keyWord +" is ["+ suggestedcategory+"]");
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				esConnection.closeConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println(searchRequest.toString());
	}

}
