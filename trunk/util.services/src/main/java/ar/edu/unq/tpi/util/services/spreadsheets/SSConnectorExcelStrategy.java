package ar.edu.unq.tpi.util.services.spreadsheets;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections15.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public final class SSConnectorExcelStrategy implements SSConnectorStrategy {

    private final Map<Integer, Map<String, Container>> values = new ListOrderedMap<Integer, Map<String, Container>>();
    private final Map<Integer, String> headers = new ListOrderedMap<Integer, String>();

    private Collection<Map<String, Container>> result;

    public static void main(String[] args) {
        final SSConnectorExcelStrategy strategy = new SSConnectorExcelStrategy();
        strategy.parseSheet("RT-Cars", "Cars");
        strategy.printInfo();
    }

    @Override
    public void setConnectionData(final String user, final String password) {
        // nothing to do
    }


    @Override
    public void connect() {
        // nothing to do
    }


    @Override
    public void parseSheet(final String sheetName) {
        parseSheet(sheetName, null);
    }

    @Override
    public void parseSheet(final String spreadsheetEntryName, final String worksSheetName) {
        try {
            parseSheetImpl(spreadsheetEntryName, worksSheetName);
            result = values.values();
        } catch (Exception e) {
            throw new GeneratorExcepction("PROGRAMMING", e);
        }
    }

    private void parseSheetImpl(final String spreadsheetEntryName, final String worksSheetName) throws Exception {
        FileInputStream inputStream = null;

        values.clear();
        headers.clear();

        try {
            inputStream = new FileInputStream(new File(DDLGenerator.ROOT_RT_PATH() + "excels-cache/" + spreadsheetEntryName + ".xls"));

            final POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
            final HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
            final HSSFSheet sheet = worksSheetName == null ? workBook.getSheetAt(0) : workBook.getSheet(worksSheetName);
            final Iterator<Row> rows = sheet.rowIterator();
            boolean isHeader = true;
            
            final FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();

            while (rows.hasNext()) {
                final Row row = rows.next();
                final Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
                boolean firstCellByRow = true;

                while (cellIterator.hasNext()) {
                    final org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                    String value;

                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            value = "" + new Double(cell.getNumericCellValue()).intValue();
                            break;

                        case HSSFCell.CELL_TYPE_FORMULA:
                        	CellValue cellValue = evaluator.evaluate(cell);

                        	switch (cellValue.getCellType()) {
                        	    case Cell.CELL_TYPE_NUMERIC:
                        	    	value = "" + new Double(cellValue.getNumberValue()).intValue();
                        	        break;
                        	    default:
                        	        value = cellValue.getStringValue().trim();
                        	        break;
                        	}
                        	break;

                        default:
                            value = cell.getRichStringCellValue().getString().trim();
                            break;
                    }

                    if (firstCellByRow && value.length() == 0) {
                        return;
                    }

                    firstCellByRow = false;

                    if (isHeader) {
                        headers.put(cell.getColumnIndex(), value);
                    } else {
                        Map<String, Container> map = values.get(cell.getRowIndex());

                        if (map == null) {
                            map = new HashMap<String, Container>();
                            values.put(cell.getRowIndex(), map);
                        }

                        map.put(headers.get(cell.getColumnIndex()), new Container(value));
                    }
                }
                isHeader = false;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override
    public void printInfo() {
        System.err.println("Headers:");
        for (final Map.Entry<Integer, String> entry : headers.entrySet()) {
            System.err.println("[" + entry.getKey() + "] is [" + entry.getValue() + "]");
        }

        System.err.println("\nValues:");
        for (final Map.Entry<Integer, Map<String, Container>> rowEntry : values.entrySet()) {
            for (final Map.Entry<String, Container> colEntry : rowEntry.getValue().entrySet()) {

                System.err.println("row [" + rowEntry.getKey() + "] header [" + colEntry.getKey() + "] has value [" + colEntry.getValue().asString() + "]");
            }
        }
    }


    @Override
    public Collection<Map<String, Container>> getValues() {
        return result;
    }
}
