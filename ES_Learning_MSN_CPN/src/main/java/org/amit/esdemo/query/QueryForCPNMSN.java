package org.amit.esdemo.query;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.amit.datareader.DataReaderFromES;
import org.amit.datawriter.DataWriterInES;
import org.amit.datawriter.XlxDataWriter;
import org.amit.elasticsearch.ESConnectionProd;
import org.amit.elasticsearch.ESConnectionQA;
import org.amit.filereader.CSVUtils;
import org.amit.filereader.XlxsFileReaderUtil;
import org.elasticsearch.client.RestHighLevelClient;

public class QueryForCPNMSN {

	public static void main(String[] args) {

		ESConnectionProd esConnectionProd = new ESConnectionProd();
		RestHighLevelClient restHighLevelClientProd = esConnectionProd.getConnection();

		LinkedHashMap<String,LinkedHashMap<String, String>> cpnMap = XlxsFileReaderUtil.cpnListFromXlxsFile("/home/moglix/Desktop/CPN_MSN_Mapping/cpn_data.xlsx");

		DataReaderFromES dataReaderFromES= new DataReaderFromES();
		LinkedHashMap<String, LinkedHashMap<String, String>>  cpnMSNMapping=dataReaderFromES.getCPNMSNMApping(cpnMap, restHighLevelClientProd);

		//saveInES(cpnMSNMapping);
		//saveInCSV(cpnMSNMapping);
		saveInExcel(cpnMSNMapping);

		try {
			esConnectionProd.closeConnection();

		} catch (IOException e) {
			System.out.println(e);
		} 
	}

	private static void saveInExcel(LinkedHashMap<String, LinkedHashMap<String, String>> cpnMSNMapping) {
		String excelFile = "/home/moglix/Desktop/CPN_MSN_Mapping/CPN_ProductNames_Mapping_Top10MSNWithPosition.xlsx";
		
		XlxDataWriter.saveIxcelFile(excelFile, cpnMSNMapping);
		
	}

	private static void saveInCSV(Map<String, Map<String, String>>  cpnMSNMapping) {
		try {
			String csvFile = "/home/moglix/Desktop/CPN_SuggestedMSN_MSN_Position_Mapping.csv";
			FileWriter writer = new FileWriter(csvFile);
			CSVUtils.writeLine(writer, Arrays.asList("CPN_Details", "Position", "Position2", "Position3"));

			//
			printCPNMSNMap(writer, cpnMSNMapping);

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void saveInES(Map<String, Map<String, String>>  cpnMSNMapping) {
		ESConnectionQA esConnectionQA = new ESConnectionQA();
		RestHighLevelClient restHighLevelClientQA = esConnectionQA.getConnection();
		
		DataWriterInES dataWriterInES = new DataWriterInES();
		
		dataWriterInES.writeCPNMSNMap(restHighLevelClientQA, cpnMSNMapping);

		try {
			esConnectionQA.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printCPNMSNMap(FileWriter writer, Map<String, Map<String, String>>  cpnMSNMapping) {


		for (String key : cpnMSNMapping.keySet()) {
			List<String> values = new ArrayList<String>();
			Map<String, String> msnMap = cpnMSNMapping.get(key);
			
			values.add(key);

			System.out.println("CPN Details: " + key);
			System.out.println("Search result is below: ");
			for (String mapKey : msnMap.keySet()) {

				//values.add(mapKey.replace(",", " ")+"_"+msnMap.get(mapKey).replace(",", " "));
				values.add(mapKey.replace(",", " "));
				System.out.println("MSN: " + mapKey + " -> ProductName: " + msnMap.get(mapKey));

			}

			try {
				CSVUtils.writeLine(writer, values);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}


}
