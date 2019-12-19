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
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 15:01
 * @Description
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @author Seven Lee
     * @description
     *      RequestHandlerSelectors.basePackage():扫描需要访问的controller
     *      SAAS:自定义化系统
     * @param []
     * @date 2019/12/19
     * @return springfox.documentation.spring.web.plugins.Docket
     * @throws
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
                .contact(new Contact("Seven Lee", "http://www.seven.com", "sevenLee@gmain.com"))
                .version("1.0")
                .build();
    }

}
