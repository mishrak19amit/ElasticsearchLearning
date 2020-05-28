package org.amit.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CSVreaderForCPTBrand {

	public static Map<String,Map<String, String>> rowDetailsFromcsvFile(String csvFile) {
		String line = "";
		String cvsSplitBy = ",";

		LinkedHashMap<String,Map<String, String>> cpnMap = new LinkedHashMap<String, Map<String,String>>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				System.out.println(line);
				
				LinkedHashMap<String,String> msnOrgCpns=new LinkedHashMap<String, String>();
				String[] msnCpns=line.split(cvsSplitBy);
				
				//System.out.println(str);
			 	msnOrgCpns.put(msnCpns[0], msnCpns[1]);
				cpnMap.put("", msnOrgCpns);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return cpnMap;
	}
	
	public static void main(String[] args) {
		String fileName="/home/moglix/Desktop/CPT_Brand_Data/Brand_Supplier_CPT_Final Sheet _North.csv";
		
		rowDetailsFromcsvFile(fileName);
	}
	
}
