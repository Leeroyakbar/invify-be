package com.invify.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/templates/**")
                .addResourceLocations("file:uploads/templates/");

        registry.addResourceHandler("/images/invitations/couples/**")
                .addResourceLocations("file:uploads/invitations/couples/");

        registry.addResourceHandler("/invitations/music/**")
                .addResourceLocations("file:uploads/invitations/music/");

        registry.addResourceHandler("/images/invitations/gallery/**")
                .addResourceLocations("file:uploads/invitations/gallery/");
    }
}
