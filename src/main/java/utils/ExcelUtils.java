package utils;
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 
public class ExcelUtils {
 
    private static Workbook openOrCreateWorkbook(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return new XSSFWorkbook(fis);
            }
        } else {
            file.getParentFile().mkdirs();
            return new XSSFWorkbook();
        }
    }
    
    
    public static Object[][] readTestCaseData(String filePath) {

        List<Object[]> rows = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists())
            return new Object[0][];

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("TestCases");

            if (sheet == null)
                return new Object[0][];

            for (Row row : sheet) {

                if (row.getRowNum() == 0)
                    continue;

                Cell idCell = row.getCell(0);

                if (idCell == null ||
                        idCell.getStringCellValue().trim().isEmpty())
                    continue;

                rows.add(new Object[]{

                        row.getCell(0).getStringCellValue().trim(), // TestCaseID

                        row.getCell(1).getStringCellValue().trim(), // Keyword

                        row.getCell(2).getStringCellValue().trim(), // Level

                        row.getCell(3).getStringCellValue().trim(), // Language

                        row.getCell(4).getStringCellValue().trim(), // FirstName

                        row.getCell(5).getStringCellValue().trim(), // LastName

                        row.getCell(6).getStringCellValue().trim(), // Email

                        row.getCell(7).getStringCellValue().trim()  // Phone
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows.toArray(new Object[0][]);
    }
 
    /**
     * Appends one row to TestResults. The "type" column distinguishes what kind of
     * result this is: COURSE (Journey 1), CATEGORY (Journey 2), FORM (Journey 3),
     * or RUN_STATUS (pass/fail from TestListener) -- all in one unified output sheet.
     */
    public static synchronized void appendResult(String filePath, String sheetName,
            String testCaseId, String type, String detail1, String detail2, String detail3, String status) {
        try {
            Workbook workbook = openOrCreateWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);
 
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
                Row header = sheet.createRow(0);
                String[] headers = {"TestCaseID", "Type", "Detail1", "Detail2", "Detail3", "Status", "Timestamp"};
                for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);
            }
 
            int nextRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(nextRow);
            row.createCell(0).setCellValue(testCaseId);
            row.createCell(1).setCellValue(type);
            row.createCell(2).setCellValue(detail1 != null ? detail1 : "");
            row.createCell(3).setCellValue(detail2 != null ? detail2 : "");
            row.createCell(4).setCellValue(detail3 != null ? detail3 : "");
            row.createCell(5).setCellValue(status != null ? status : "");
            row.createCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
 
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}