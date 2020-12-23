package Base;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.util.IOUtils;

import static Base.BaseTest.step_count;


public class ExcelProcess {
    static SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmm");
    static Date date = new Date();
    static String path = System.getProperty("user.dir")+"//Reports//Report_"+(formatter.format(date)).toString()+".xlsx";
    public void excelCreation() {
        File f =new File(path);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet2 = workbook.createSheet("TSR");
        Sheet sheet = workbook.createSheet("JuniorEngineers");
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Row row0 = sheet.createRow(0);
        Cell cell00 = row0.createCell(1);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        cell00.setCellStyle(style);
        sheet.setColumnWidth(1, 5000);
        cell00.setCellValue("Junior Engineers");
        Row sheet_two_row0 = sheet2.createRow(0);
        Cell sheet_two_cell00 = sheet_two_row0.createCell(1);
        Cell sheet_two_cell01 = sheet_two_row0.createCell(2);
        Cell sheet_two_cell02 = sheet_two_row0.createCell(3);
        Cell sheet_two_cell03 = sheet_two_row0.createCell(4);
        sheet_two_cell00.setCellStyle(style);
        sheet_two_cell01.setCellStyle(style);
        sheet_two_cell02.setCellStyle(style);
        sheet_two_cell03.setCellStyle(style);
        sheet2.setColumnWidth(1, 1500);
        sheet2.setColumnWidth(2, 25000);
        sheet2.setColumnWidth(3, 2000);
        sheet2.setColumnWidth(4, 10000);
        sheet_two_cell00.setCellValue("Step");
        sheet_two_cell01.setCellValue("Test Step Description");
        sheet_two_cell02.setCellValue("Status");
        sheet_two_cell03.setCellValue("Snap");
        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            workbook.write(fileOut);
            fileOut.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateSheet(String value) throws IOException {
        File f = new File(path);
        FileInputStream inputStream = new FileInputStream(f);
        Workbook workbook = WorkbookFactory.create(inputStream);
        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.MEDIUM);
        style2.setBorderBottom(BorderStyle.MEDIUM);
        style2.setBorderLeft(BorderStyle.MEDIUM);
        style2.setBorderRight(BorderStyle.MEDIUM);
        Sheet sheet = workbook.getSheet("JuniorEngineers");
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount+1);
        Cell cell0 = row.createCell(1);
        cell0.setCellStyle(style2);
        cell0.setCellValue(value);
        rowCount++;
        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            workbook.write(fileOut);
            fileOut.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void updateReport(String description, String status, String image) throws IOException {
        File f = new File(path);
        FileInputStream inputStream = new FileInputStream(f);
        Workbook workbook = WorkbookFactory.create(inputStream);
        CellStyle style2 = workbook.createCellStyle();
        CellStyle style3 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.MEDIUM);
        style2.setBorderBottom(BorderStyle.MEDIUM);
        style2.setBorderLeft(BorderStyle.MEDIUM);
        style2.setBorderRight(BorderStyle.MEDIUM);
        style3.setBorderTop(BorderStyle.MEDIUM);
        style3.setBorderBottom(BorderStyle.MEDIUM);
        style3.setBorderLeft(BorderStyle.MEDIUM);
        style3.setBorderRight(BorderStyle.MEDIUM);
        Sheet sheet = workbook.getSheet("TSR");
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount+1);
        if(status.equals("Pass")){
            style3.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }else if(status.equals("Fail")){
            style3.setFillForegroundColor(IndexedColors.RED.getIndex());
            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        if(status.equals("")){
            Font font = workbook.createFont();
            font.setBold(true);
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setFont(font);
            row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            style2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style3.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        Cell cell0 = row.createCell(1);
        Cell cell02 = row.createCell(2);
        Cell cell03 = row.createCell(3);
        Cell cell04 = row.createCell(4);
        cell0.setCellStyle(style2);
        cell02.setCellStyle(style2);
        cell03.setCellStyle(style3);
        cell04.setCellStyle(style2);
        if(status.equals("")){
            cell0.setCellValue("");
        }else {
            cell0.setCellValue(step_count);
        }
        cell02.setCellValue(description);
        cell03.setCellValue(status);
        if(!image.equals("")) {
            InputStream inputStream1 = new FileInputStream(image);
            row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            byte[] imageBytes = IOUtils.toByteArray(inputStream1);
            int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(4);
            anchor.setRow1(rowCount + 1);
            anchor.setCol2(5);
            anchor.setRow2(rowCount + 2);
            drawing.createPicture(anchor, pictureureIdx);
        }
        rowCount++;
        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}