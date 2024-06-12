package com.dns.admin.category.exporter;


import com.dns.admin.user.export.AbstractExporter;
import com.dns.common.entity.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class CategoryCsvExporter extends AbstractExporter {
    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv; charset=UTF-8", ".csv", "categories_");

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"ID", "Имя"};
            String[] fieldMapping = {"id", "name"};

            csvWriter.writeHeader(csvHeader);

            for (Category category : listCategories) {
                category.setName(category.getName().replace("--", "  "));
                csvWriter.write(category, fieldMapping);
            }
        }
    }
}
