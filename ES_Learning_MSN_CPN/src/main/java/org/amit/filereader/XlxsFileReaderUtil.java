package org.amit.filereader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import statements
public class XlxsFileReaderUtil 
{
  public static void main(String[] args) 
  {
      try
      {
          FileInputStream file = new FileInputStream(new File("/home/moglix/Desktop/CPN_MSN_Mapping/cpn_data.xlsx"));

          cpnListFromXlxsFile("/home/moglix/Desktop/CPN_MSN_Mapping/cpn_data.xlsx");
          
          //Create Workbook instance holding reference to .xlsx file
          XSSFWorkbook workbook = new XSSFWorkbook(file);

          //Get first/desired sheet from the workbook
          XSSFSheet sheet = workbook.getSheetAt(0);

          //Iterate through each rows one by one
          Iterator<Row> rowIterator = sheet.iterator();
          while (rowIterator.hasNext()) 
          { String[] vals= new String[3];
          	int i=0;
              Row row = rowIterator.next();
              //For each row, iterate through all the columns
              Iterator<Cell> cellIterator = row.cellIterator();
               
              while (cellIterator.hasNext()) 
              {
                  Cell cell = cellIterator.next();
                  //Check the cell type and format accordingly
                  switch (cell.getCellType()) 
                  {
                      case Cell.CELL_TYPE_NUMERIC:
                    	  vals[i]=String.valueOf(cell.getNumericCellValue());
                    	  i++;
                          System.out.print(cell.getNumericCellValue() + "\t");
                          break;
                      case Cell.CELL_TYPE_STRING:
                    	  vals[i]=cell.getStringCellValue();
                    	  i++;
                          System.out.print(cell.getStringCellValue() + "\t");
                          break;
                  }
              }
              LinkedHashMap<String, String> cpnSuggMSN= new LinkedHashMap<String, String>();
              cpnSuggMSN.put(vals[1], vals[0]);
              
              System.out.println("");
          }
          file.close();
      } 
      catch (Exception e) 
      {
          e.printStackTrace();
      }
  }
  
  public static LinkedHashMap<String,LinkedHashMap<String, String>> cpnListFromXlxsFile(String xlsxfilePath)
  {
	  LinkedHashMap<String,LinkedHashMap<String, String>> cpnMap = new LinkedHashMap<String, LinkedHashMap<String,String>>();
      try
      {
          FileInputStream file = new FileInputStream(new File(xlsxfilePath));

          //Create Workbook instance holding reference to .xlsx file
          XSSFWorkbook workbook = new XSSFWorkbook(file);

          //Get first/desired sheet from the workbook
          XSSFSheet sheet = workbook.getSheetAt(0);

          //Iterate through each rows one by one
          Iterator<Row> rowIterator = sheet.iterator();
          while (rowIterator.hasNext()) 
          { String[] vals= new String[3];
          	int i=0;
              Row row = rowIterator.next();
              //For each row, iterate through all the columns
              Iterator<Cell> cellIterator = row.cellIterator();
               
              while (cellIterator.hasNext()) 
              {
                  Cell cell = cellIterator.next();
                  //Check the cell type and format accordingly
                  switch (cell.getCellType()) 
                  {
                      case Cell.CELL_TYPE_NUMERIC:
                    	  vals[i]=String.valueOf(cell.getNumericCellValue());
                    	  i++;
                          break;
                      case Cell.CELL_TYPE_STRING:
                    	  vals[i]=cell.getStringCellValue();
                    	  i++;
                          break;
                  }
              }
              LinkedHashMap<String, String> cpnSuggMSN= new LinkedHashMap<String, String>();
              cpnSuggMSN.put(vals[1], vals[0]);
              cpnMap.put(vals[2].replace(".0", ""), cpnSuggMSN);
          }
          file.close();
      } 
      catch (Exception e) 
      {
          e.printStackTrace();
      }
      
      return cpnMap;
  }
}
