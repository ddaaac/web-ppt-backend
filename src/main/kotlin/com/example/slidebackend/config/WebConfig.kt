package com.example.slidebackend.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods(HttpMethod.GET.name, HttpMethod.PUT.name, HttpMethod.POST.name, HttpMethod.DELETE.name)
                .allowedOrigins("*")
    }
}