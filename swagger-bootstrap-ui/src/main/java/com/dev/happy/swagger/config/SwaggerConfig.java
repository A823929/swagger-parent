package com.dev.happy.swagger.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger config
 *
 * @author Arno King
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig extends WebMvcConfigurationSupport {
    /**
     * registry the Configuration of swagger-bootstrap-ui to ResourceHandlerRegistry
     *
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/swagger/");
    }

    /**
     * the swagger docket
     *
     * @return docket
     */
    @Bean
    public Docket docket() {
        List<ResponseMessage> responseMessages = new ArrayList();
        responseMessages.add((new ResponseMessageBuilder()).code(200).message("success").build());
        responseMessages.add((new ResponseMessageBuilder()).code(404).message("no resource").build());
        responseMessages.add((new ResponseMessageBuilder()).code(500).message("server error").build());
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        return docket.apiInfo(this.apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();
    }

    /**
     * the base information of api
     *
     * @return apiInfo
     */
    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title("Online API").version("1.0.0").description("Online API").build();
    }
}
