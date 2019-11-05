package org.amit.filereader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApachePOIExcelRead {

	private static final String DIRC_NAME = "/home/moglix/Downloads/Moglix_Search_Terms_Reports/";

	public static void main(String[] args) {

		List<String> files = getFileListFromDirect();

		for (String file : files) {
			try {
				int count = 0;
				FileInputStream excelFile = new FileInputStream(new File(file));
				Workbook workbook = new XSSFWorkbook(excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {

					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();

					while (cellIterator.hasNext()) {

						Cell currentCell = cellIterator.next();
						// getCellTypeEnum shown as deprecated for version 3.15
						// getCellTypeEnum ill be renamed to getCellType starting from version 4.0
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							System.out.print(currentCell.getStringCellValue() + ",");
						} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							System.out.print(currentCell.getNumericCellValue() + ",");
						}

					}
					count++;
					System.out.println();

				}
				System.out.println(count);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static List<String> getFileListFromDirect() {
		List<String> result = new ArrayList<String>();
		try (Stream<Path> walk = Files.walk(Paths.get(DIRC_NAME))) {

			result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

			result.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
