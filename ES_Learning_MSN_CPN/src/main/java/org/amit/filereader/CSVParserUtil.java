package org.amit.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CSVParserUtil {

	public static Map<String,Map<String, String>> cpnListFromcsvFile(String csvFile) {
		String line = "";
		String cvsSplitBy = ",";

		LinkedHashMap<String,Map<String, String>> cpnMap = new LinkedHashMap<String, Map<String,String>>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				LinkedHashMap<String,String> msnOrgCpns=new LinkedHashMap<String, String>();
				String[] msnCpns=line.split(cvsSplitBy);
				
			 	String str=replaceSpecialChar(msnCpns[2]);
				//System.out.println(str);
			 	msnOrgCpns.put(msnCpns[0], msnCpns[1]);
				cpnMap.put(str, msnOrgCpns);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return cpnMap;
	}
	
	public static String replaceSpecialChar(String str)
	{
		return str.replace(",", " ").replaceAll("\"", "").replaceAll("([;':)(])", "");
	}

}
