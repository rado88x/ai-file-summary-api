package com.wbg.filesummary.file_summary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI fileSummaryOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("File Summary API")
                        .description("REST API for processing and summarise resumes with OpenAI/ChatGPT")
                        .version("v1.0.0"));
    }
}
