package com.example.demo.controller;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class CasdoorBeans {

    @Value("${casdoor.endpoint}")
    private String endpoint;

    @Value("${casdoor.client-id}")
    private String clientId;

    @Value("${casdoor.client-secret}")
    private String clientSecret;

    @Value("${casdoor.organization-name}")
    private String organizationName;

    @Value("${casdoor.application-name}")
    private String applicationName;

    // ✅ Cho phép cấu hình inline certificate hoặc qua đường dẫn (tùy bạn)
    @Value("${casdoor.certificate:}")
    private String certificateInline;   // PEM inline, có \n

    @Value("${casdoor.certificate-path:}")
    private String certificatePath;     // ví dụ: classpath:casdoor_public_key.pem

    @Bean
    public CasdoorConfig casdoorConfig() throws IOException {
        String certPem;

        if (certificateInline != null && !certificateInline.isBlank()) {
            // chuyển \n trong .properties thành xuống dòng thật
            certPem = certificateInline.replace("\\n", "\n");
        } else if (certificatePath != null && !certificatePath.isBlank()) {
            Resource res = new DefaultResourceLoader().getResource(certificatePath);
            certPem = new String(res.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } else {
            throw new IllegalStateException(
                    "Missing Casdoor certificate: set 'casdoor.certificate' (inline) hoặc 'casdoor.certificate-path'.");
        }

        return new CasdoorConfig(
                endpoint,
                clientId,
                clientSecret,
                organizationName,
                applicationName,
                certPem
        );
    }

    @Bean
    public CasdoorAuthService casdoorAuthService(CasdoorConfig config) {
        return new CasdoorAuthService(config);
    }
}
