package com.dns.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* в настройках конфигурации run для DnsBackendApplication мы указали
        текущий запуск с модуля DnsBackend, поэтому для правильного формирования пути
        мы указываем dirName начиная с user-photos, тем самым абсолютный путь получается
        /Users/valeriali/IdeaProjects/TechnoShopProject/DNSWebParent/DNSBackend/user-photos
         */
        exposeDirectory("user-photos", registry);
        exposeDirectory("../category-images", registry);
        exposeDirectory("../brand-logos", registry);
        exposeDirectory("../product-images", registry);

    }

    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
        Path path = Paths.get(pathPattern);
        String absolutePath = path.toFile().getAbsolutePath();

        String logicalPath = pathPattern.replace("..", "") + "/**";

        registry.addResourceHandler(logicalPath)
                .addResourceLocations("file:" + absolutePath + "/");
    }
}
