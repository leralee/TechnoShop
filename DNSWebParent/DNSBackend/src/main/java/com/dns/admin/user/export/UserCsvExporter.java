package com.dns.admin.user.export;


import com.dns.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class UserCsvExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv; charset=UTF-8", ".csv");

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"ID", "Почта", "Имя", "Фамилия", "Назначения", "Доступ"};
            String[] fieldMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};

            csvWriter.writeHeader(csvHeader);

            for (User user : listUsers) {
                csvWriter.write(user, fieldMapping);
            }
        }
    }
}
