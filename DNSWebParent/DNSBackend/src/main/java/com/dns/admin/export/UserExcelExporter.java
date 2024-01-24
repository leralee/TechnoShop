package com.dns.admin.export;


import com.dns.admin.user.AbstractExporter;
import com.dns.common.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");
        writeHeaderLine();
        writeDataLine(listUsers);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Пользователи");
        String[] excelHeader = {"ID", "Почта", "Имя", "Фамилия", "Назначения", "Доступ"};
        XSSFRow headerRow = sheet.createRow(0);

        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        headerStyle.setFont(font);


        for (int i = 0; i < excelHeader.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);
        }
    }

    private void writeDataLine(List<User> listUsers) {

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        int rowNum = 1;
        for (User user : listUsers) {
            XSSFRow row = sheet.createRow(rowNum++);
            XSSFCell cell;

            cell = row.createCell(0);
            cell.setCellValue(user.getId());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(user.getEmail());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(user.getFirstName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue(user.getLastName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue(user.getRoles().toString());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue(user.isEnabled());
            cell.setCellStyle(cellStyle);
        }
    }
}
