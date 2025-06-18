package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {

    public static String readCellValue(String filePath, String sheetName, String columnName, int rowNumber) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) throw new RuntimeException("Header row is missing.");

            // Find the column index by name
            int columnIndex = -1;
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new RuntimeException("Column not found: " + columnName);
            }

            Row row = sheet.getRow(rowNumber);
            if (row == null) return null;

            Cell cell = row.getCell(columnIndex);
            return cell != null ? getCellValueAsString(cell) : null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel: " + e.getMessage(), e);
        }
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            case BLANK:   return "";
            default:      return cell.toString();
        }
    }
}