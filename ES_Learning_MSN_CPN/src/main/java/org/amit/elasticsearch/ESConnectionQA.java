package org.amit.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESConnectionQA {

	// The config parameters for the connection
	//private static final String HOST = "103.96.251.76";
	private static final String HOST = "13.234.108.103";
	//private static final String HOST = "localhost";
	private static final int PORT_ONE = 9200;
	private static final int PORT_TWO = 9201;
	private static final String SCHEME = "http";

	private static RestHighLevelClient restHighLevelClient;

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

	public static synchronized void closeConnection() throws IOException {
		restHighLevelClient.close();
		restHighLevelClient = null;
	}
	
	public RestHighLevelClient getConnection() {
		if(restHighLevelClient==null) {
			makeConnection();
		}
		
		return restHighLevelClient;
		
	}
	
}
