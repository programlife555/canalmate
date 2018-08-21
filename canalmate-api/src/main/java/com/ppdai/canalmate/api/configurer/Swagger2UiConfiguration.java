package com.ppdai.canalmate.api.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicates;

@Configuration
@EnableSwagger2
public class Swagger2UiConfiguration extends WebMvcConfigurerAdapter  {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定controller存放的目录路径
//                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
//                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
//                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc"))
                .apis(RequestHandlerSelectors.basePackage("com.ppdai.canalmate.api.controller"))
                //.apis(RequestHandlerSelectors.basePackage("com.ppdai.console.api.controller.auth"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     * 防止出现整合swagger2和spring-boot，访问swagger-ui.html，报错404
     *  <!--swagger资源配置-->
     *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("canalConsole_RESTful APIs")
                // 文档描述
                .description("canalConsole控制台的后台接口")
                .termsOfServiceUrl("http://xxx.com/")
                .version("1.0")
                .build();
    }

}