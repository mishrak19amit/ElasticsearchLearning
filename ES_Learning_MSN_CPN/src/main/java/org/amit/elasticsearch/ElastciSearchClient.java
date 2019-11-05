package org.amit.elasticsearch;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElastciSearchClient {

	public RestHighLevelClient searchClient() {

		RestHighLevelClient clients = null;
		System.out.println("Trying to create elastic client...");
		
		String elasticServerIP = "127.0.0.1";
	    int elasticServerPort = 9200;
		
		
		clients = new RestHighLevelClient(RestClient.builder(new HttpHost(elasticServerIP,elasticServerPort)));
		
		System.out.println("client created succesfully..."+clients.toString());
		
		return clients;

	}
	
	
}
