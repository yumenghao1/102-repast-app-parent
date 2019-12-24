package com.aaa.lee.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Company
 * @Author YMH
 * @Date Create in 2019/12/24 23:29
 * @Description
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description
     * @date create in 2019/12/24 23:37
     **/
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.aaa.lee.app.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SAAS 接口文档")
                .description("SAAS Framework 接口文档项目描述")
                .contact(new Contact("MengHao YU", "http://www.seven.com", "sevenLee@gmain.com"))
                .version("1.0")
                .build();
    }

}
