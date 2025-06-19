package excel;

import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelWriter {

    public static void writeCellValue(String filePath, String sheetName, String columnName, int rowNumber, String value) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) throw new RuntimeException("Header row is missing.");

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

            // Get or create the target row
            Row targetRow = sheet.getRow(rowNumber);
            if (targetRow == null) {
                targetRow = sheet.createRow(rowNumber);
            }

            // Get or create the target cell
            Cell cell = targetRow.getCell(columnIndex);
            if (cell == null) {
                cell = targetRow.createCell(columnIndex);
            }

            cell.setCellValue(value);

            // Save changes
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to write to Excel: " + e.getMessage(), e);
        }
    }
}