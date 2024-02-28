package com.dns.admin.brand.exporter;


import com.dns.admin.user.export.AbstractExporter;
import com.dns.common.entity.Brand;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class BrandCsvExporter extends AbstractExporter {
    public void export(List<Brand> listBrands, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv; charset=UTF-8", ".csv", "brands_");

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"ID", "Название", "Категории"};
            String[] fieldMapping = {"id", "name", "categories"};

            csvWriter.writeHeader(csvHeader);

            for (Brand brand : listBrands) {
                csvWriter.write(brand, fieldMapping);
            }
        }
    }
}
