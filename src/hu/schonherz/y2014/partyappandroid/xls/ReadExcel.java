package hu.schonherz.y2014.partyappandroid.xls;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException {
		File inputWorkbook = new File(inputFile);
		File parent_dir = inputWorkbook.getParentFile();
		Workbook w;
		try {
			System.out.println("Parent dir" + parent_dir);
			if (parent_dir.exists() == true) {
				System.out.println("Pardent_dir failed" + "1");
			} else {
				System.out.println("Pardent _ dir not failed" + "2");
			}
			if (inputWorkbook.exists() == true) {
				System.out.println("File Exists");
			} else {
				System.out.println("File NOt Exists");
				System.out.println("Path " + inputWorkbook.getAbsoluteFile());
			}
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines

			for (int j = 0; j < sheet.getColumns(); j++) {
				for (int i = 0; i < sheet.getRows(); i++) {
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					if (cell.getType() == CellType.LABEL) {
						System.out.println("I got a label "
								+ cell.getContents());
					}

					if (cell.getType() == CellType.NUMBER) {
						System.out.println("I got a number "
								+ cell.getContents());
					}

				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}
}
