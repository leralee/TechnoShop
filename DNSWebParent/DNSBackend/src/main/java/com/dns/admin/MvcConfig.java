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
        String dirName = "user-photos";
        Path userPhotosDir = Paths.get(dirName);
        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + userPhotosPath + "/");

        String categoryImagesDirName = "../category-images";
        Path categoryImagesDir = Paths.get(categoryImagesDirName);
        String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/category-images/**")
                .addResourceLocations("file:" + categoryImagesPath + "/");
    }
}
