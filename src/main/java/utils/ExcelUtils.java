package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtils {

    private static Workbook openOrCreateWorkbook(
            String filePath) throws IOException {

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
    
    private static String getCellValue(Row row,int columnIndex) {

        Cell cell = row.getCell(columnIndex);

        if (cell == null) {
            return "";
        }

        return cell.toString().trim();
    }

    public static Object[][] readTestCaseData(String filePath) {

        List<Object[]> rows =new ArrayList<>();

        File file =new File(filePath);

        if (!file.exists()) {

            return new Object[0][];
        }

        try (FileInputStream fis =new FileInputStream(file);

             Workbook workbook =new XSSFWorkbook(fis)) {

            Sheet sheet =workbook.getSheet("TestCases");

            if (sheet == null) {

                return new Object[0][];
            }

            for (Row row : sheet) {

                if (row.getRowNum() == 0) {

                    continue;
                }

                String testCaseId =getCellValue(row, 0);

                if (testCaseId.isEmpty()) {

                    continue;
                }

                rows.add(new Object[] {

                        getCellValue(row, 0),   // TestCaseID
                        getCellValue(row, 1),   // Keyword
                        getCellValue(row, 2),   // LevelFilter
                        getCellValue(row, 3),   // LanguageFilter

                        getCellValue(row, 4),   // FirstName
                        getCellValue(row, 5),   // LastName
                        getCellValue(row, 6),   // WorkEmail

                        getCellValue(row, 7),   // Department
                        getCellValue(row, 8),   // Product
                        getCellValue(row, 9),   // Industry

                        getCellValue(row, 10),  // OrganisationName
                        getCellValue(row, 11),  // OrganisationType
                        getCellValue(row, 12),  // OrganisationSize

                        getCellValue(row, 13),  // Country
                        getCellValue(row, 14),   // TrainingRequirement
                        getCellValue(row, 15)
                });
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rows.toArray(
                new Object[0][]);
    }
   
   
    public static synchronized void appendCourseDetails(
            String filePath,
            String testCaseId,
            String course,
            String description,
            String duration,
            String enrollment,
            String modules,
            String publisher) {

        try {

            Workbook workbook =openOrCreateWorkbook(filePath);

            Sheet sheet =workbook.getSheet("CourseDetails");

            if (sheet == null) {

                sheet =workbook.createSheet("CourseDetails");

                Row header =sheet.createRow(0);

                String[] headers = {
                        "TestCaseID",
                        "Course",
                        "Course Description",
                        "Duration",
                        "No. of enrollment",
                        "No. of Modules",
                        "Course Publisher"
                        
                };

                for (int i = 0;i < headers.length;i++) {

                    header.createCell(i).setCellValue(headers[i]);
                }
            }

            int nextRow =sheet.getLastRowNum() + 1;

            Row row =sheet.createRow(
                            nextRow);

            row.createCell(0).setCellValue(testCaseId);

            row.createCell(1).setCellValue(course);

            row.createCell(2).setCellValue(description);

            row.createCell(3).setCellValue(duration);

            row.createCell(4).setCellValue(enrollment);

            row.createCell(5).setCellValue(modules);

            row.createCell(6).setCellValue(publisher);

           

            try (FileOutputStream fos =new FileOutputStream(filePath)) {

                workbook.write(fos);
            }

            workbook.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}