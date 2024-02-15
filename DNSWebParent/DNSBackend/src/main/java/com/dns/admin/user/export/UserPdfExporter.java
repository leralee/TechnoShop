package com.dns.admin.user.export;

import com.dns.common.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author valeriali on {18.11.2023}
 * @project TechnoShopProject
 */
public class UserPdfExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");
        try(Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            Paragraph paragraph = new Paragraph("Список пользователей", font);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100f);
            table.setSpacingBefore(10);
            table.setWidths(new float[] {1.2f, 3.5f, 2.0f, 3.0f, 3.0f, 2.0f});

            writeTableHeader(table);
            writeTableData(table, listUsers);
            document.add(table);
        }

    }

    private void writeTableData(PdfPTable table, List<User> listUsers) {
        for (User user : listUsers) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        Font font = FontFactory.getFont("Arial");
        font.setSize(16);
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Почта", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Имя", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Фамилия", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Назначения", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Доступ", font));
        table.addCell(cell);
    }
}
