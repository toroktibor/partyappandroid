package hu.schonherz.y2014.partyappandroid.xls;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import android.util.Log;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
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
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("hu", "HU"));
			ws.setEncoding("ISO-8859-2");
			w = Workbook.getWorkbook(inputWorkbook,ws);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			String name = null, currency = null, unit = null, category = null;
			Integer id = 0, price = null, discount = null, sort = 1;
			// MenuItem newMenuItem = new MenuItem(0, name, price, currency,
			// unit, discount, category, 1);

			// Loop over first 10 column and lines

			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					// CellType type = cell.getType();
					// if (cell.getType() == CellType.LABEL) {
					// System.out.println("I got a label "
					// + cell.getContents());
					// }
					//
					// if (cell.getType() == CellType.NUMBER) {
					// System.out.println("I got a number "
					// + cell.getContents());
					// }
					switch (j) {
					case 0:
						category = cell.getContents().toString();
						break;
					case 1:
						name = cell.getContents().toString();
						break;
					case 2:
						price = Integer.parseInt(cell.getContents().toString());
						break;
					case 3:
						currency = cell.getContents().toString();
						break;
					case 4:
						unit = cell.getContents().toString();
						break;
					case 5:
						discount = Integer.parseInt(cell.getContents()
								.toString());
						break;
					case 6:
						sort = Integer.parseInt(cell.getContents().toString());
						break;

					default:
						break;
					}
				}
				MenuItem newMenuItem = new MenuItem(id, name, price, currency,
						unit, discount, category, sort);

				int clubListPosition = ClubActivity.intent.getExtras().getInt(
						"listPosition");
				Session.getSearchViewClubs().get(clubListPosition).menuItems
						.add(newMenuItem);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}
	}
}
