package org.amit.datawriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import statements 
public class XlxDataWriter {

	public static void saveIxcelFile(String fileName, LinkedHashMap<String, LinkedHashMap<String, String>> cpnMSNMapping) {
		{
			// Blank workbook
			XSSFWorkbook workbook = new XSSFWorkbook();

			// Create a blank sheet
			XSSFSheet sheet = workbook.createSheet("CPN_MSN_Mapping_Details");

			// This data needs to be written (Object[])
			LinkedHashMap<String, List<String>> data = new LinkedHashMap<String, List<String>>();
			data.put("1", Arrays.asList("CPN_Details","Position", "MSN1", "MSN2", "MSN3" , "MSN4", "MSN5", "MSN6", "MSN7", "MSN8", "MSN9", "MSN10" ));

			prepareData(data, cpnMSNMapping);

			// Iterate over data and write to sheet
			Set<String> keyset = data.keySet();
			int rownum = 0;
			for (String key : keyset) {
				// this creates a new row in the sheet
				Row row = sheet.createRow(rownum++);
				List<String> objArr = data.get(key);
				int cellnum = 0;
				for (String obj : objArr) {
					// this line creates a cell in the next column of that row
					Cell cell = row.createCell(cellnum++);
						cell.setCellValue(obj);
					
				}
			}
			try {
				// this Writes the workbook gfgcontribute
				FileOutputStream out = new FileOutputStream(new File(fileName));
				workbook.write(out);
				out.close();
				System.out.println(fileName+" written successfully on disk.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void prepareData(LinkedHashMap<String, List<String>> data, LinkedHashMap<String, LinkedHashMap<String, String>> cpnMSNMapping) {
		
		int i=2;
		
		for(String cpnDetails: cpnMSNMapping.keySet())
		{
			Map<String, String> msnProd= new HashMap<String, String>();
			msnProd=cpnMSNMapping.get(cpnDetails);
			List<String> prods=new ArrayList<String>();
			prods.add(cpnDetails);
			if(!msnProd.isEmpty())
			{    int j=1;
				for(String msnID: msnProd.keySet() ) {
					String productName=msnProd.get(msnID);
					prods.add(productName);
					j++;
				}
			}
			data.put(String.valueOf(i), prods);
			
			i++;
		}
		
	}

}
