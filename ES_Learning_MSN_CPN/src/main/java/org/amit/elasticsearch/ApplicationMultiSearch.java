package org.amit.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.amit.elasticsearch.model.Person;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationMultiSearch {
	
	// The config parameters for the connection
	private static final String HOST = "localhost";
	private static final int PORT_ONE = 9200;
	private static final int PORT_TWO = 9201;
	private static final String SCHEME = "http";

	private static RestHighLevelClient restHighLevelClient;
	private static ObjectMapper objectMapper = new ObjectMapper();

	private static final String INDEX = "persondata";
	private static final String TYPE = "person";

	/**
	 * Implemented Singleton pattern here so that there is just one connection at a
	 * time.
	 * 
	 * @return RestHighLevelClient
	 */
	private static synchronized RestHighLevelClient makeConnection() {

		if (restHighLevelClient == null) {
			restHighLevelClient = new RestHighLevelClient(
					RestClient.builder(new HttpHost(HOST, PORT_ONE, SCHEME), new HttpHost(HOST, PORT_TWO, SCHEME)));
		}

		return restHighLevelClient;
	}

	private static synchronized void closeConnection() throws IOException {
		restHighLevelClient.close();
		restHighLevelClient = null;
	}

	private static Person insertPerson(Person person) {
		//person.setPersonId(UUID.randomUUID().toString());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("personId", person.getPersonId());
		dataMap.put("name", person.getName());
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, person.getPersonId()).source(dataMap);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return person;
	}

	private static Person getPersonById(String id) {
		GetRequest getPersonRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getPersonRequest);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return getResponse != null ? objectMapper.convertValue(getResponse.getSourceAsMap(), Person.class) : null;
	}

	private static Person updatePersonById(String id, Person person) {
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id).fetchSource(true); // Fetch Object after its
																							// update
		try {
			String personJson = objectMapper.writeValueAsString(person);
			updateRequest.doc(personJson, XContentType.JSON);
			UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
			return objectMapper.convertValue(updateResponse.getGetResult().sourceAsMap(), Person.class);
		} catch (JsonProcessingException e) {
			e.getMessage();
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		System.out.println("Unable to update person");
		return null;
	}

	private static void deletePersonById(String id) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		try {
			DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
	}

	public static void main(String[] args) throws IOException {

		makeConnection();

		System.out.println("Inserting a new Person with name Pragyanand Mishra...");
		Person person1 = new Person();
		person1.setPersonId("121");
		person1.setName("Pragyanand Mishra");
		person1 = insertPerson(person1);
		System.out.println("Person inserted --> " + person1);

		System.out.println("Inserting a new Person with name Amit Mishra...");
		Person person2 = new Person();
		person2.setPersonId("122");
		person2.setName("Amit Mishra");
		person2 = insertPerson(person2);
		System.out.println("Person inserted --> " + person2);

		
//		System.out.println("Changing name to `Pragyanand Mishra`...");
//		person.setName("Pragyanand Mishra");
//		updatePersonById(person.getPersonId(), person);
//		System.out.println("Person updated  --> " + person);
//
//		System.out.println("Getting Pragyanand...");
//		Person personFromDB = getPersonById(person.getPersonId());
//		System.out.println("Person from DB  --> " + personFromDB);
//
//		System.out.println("Deleting Pragyanand...");
//		//deletePersonById(personFromDB.getPersonId());
//		System.out.println("Person Deleted");
		
		multiSearchQuery();
		closeConnection();

	}
	
	public static SearchRequest getSearchQuery(String field, String id)
	{
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(field, id));
		searchRequest.source(searchSourceBuilder);
		
		return searchRequest;
	}

	private static void multiSearchQuery() {

		MultiSearchRequest request = new MultiSearchRequest();
		request.add(getSearchQuery("personId", "121"));
		request.add(getSearchQuery("personId", "122"));
		

		try {
			MultiSearchResponse multiSearchResponse = restHighLevelClient.msearch(request,
					RequestOptions.DEFAULT);
			List<Person> personList = new ArrayList<Person>();
			for (MultiSearchResponse.Item trendingResponse : multiSearchResponse.getResponses()) {
				SearchHit srHit[] = trendingResponse.getResponse().getHits().getHits();
				for (SearchHit hit : srHit) {
					//System.out.println(hit.getSourceAsString().toString());
					Person person = fromJson(hit.getSourceAsString());
					personList.add(person);
				}
			}
			
			printClassObject(personList);
			
		} catch (IOException e) {
			System.out.println("Exception occur: " + e);
		}

	}

	private static void printClassObject(List<Person> personList) {
		for(Person person: personList)
		{
			System.out.println(person.toString());
		}
		
	}

	public static Person fromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		Person person = new ObjectMapper().readValue(json, Person.class);

		return person;
	}
}
