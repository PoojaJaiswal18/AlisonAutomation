package utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CourseData;
 
public class ExcelUtils {
 
    private static Workbook openOrCreateWorkbook(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return new XSSFWorkbook(fis);
            }
        } else {
            file.getParentFile().mkdirs();
            return new XSSFWorkbook(); // brand-new workbook if file doesn't exist yet
        }
    }
 
    public static Map<String, String> readConfigSheet(String filePath, String sheetName) {
        Map<String, String> data = new LinkedHashMap<>();
        File file = new File(filePath);
        if (!file.exists()) return data;
 
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
 
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return data;
 
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);
                if (keyCell != null && valueCell != null) {
                    data.put(keyCell.getStringCellValue(), valueCell.getStringCellValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
 
    public static void writeCourseResults(String filePath, String sheetName, List<CourseData> courses) {
        try {
            Workbook workbook = openOrCreateWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) sheet = workbook.createSheet(sheetName);
 
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Course Name");
            header.createCell(1).setCellValue("Duration");
            header.createCell(2).setCellValue("Rating");
 
            int rowIdx = 1;
            for (CourseData course : courses) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(course.getName());
                row.createCell(1).setCellValue(course.getDuration());
                row.createCell(2).setCellValue(course.getRating());
            }
 
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void writeCategorySummary(String filePath, String sheetName, Map<String, Integer> summary) {
        try {
            Workbook workbook = openOrCreateWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) sheet = workbook.createSheet(sheetName);
 
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Filter Option");
            header.createCell(1).setCellValue("Course Count");
 
            int rowIdx = 1;
            for (Map.Entry<String, Integer> entry : summary.entrySet()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }
 
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 