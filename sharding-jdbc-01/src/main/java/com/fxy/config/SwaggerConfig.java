package com.fxy.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chaixuhong
 * @date 2019-08-12
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUi
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enableSwagger;



    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("鸿坤荟-问卷接口V1.0")
                .enable(enableSwagger)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hongkun.controller.question"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(this.getParameterList());

    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("鸿坤荟-小程序接口")
                .version("1.0.0")
                .contact(new Contact("ChaiXuHong", null, "75918847@qq.com"))
                .description("RestApi接口文档")
                .build();
    }


    @Bean
    public Docket docketCommon() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfoCommon())
                .groupName("鸿坤荟-通用接口V1.0")
                .enable(enableSwagger)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hongkun.controller.common"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(this.getParameterList());

    }

    public ApiInfo apiInfoCommon() {
        return new ApiInfoBuilder()
                .title("鸿坤荟-小程序接口")
                .version("1.0.0")
                .contact(new Contact("fengxiaoyang", null, "75918847@qq.com"))
                .description("RestApi接口文档")
                .build();
    }




    @Bean
    public Docket docketLeague() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfoLeague())
                .groupName("鸿坤荟-社团接口V1.0")
                .enable(enableSwagger)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hongkun.controller.league"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(this.getParameterList());

    }

    public ApiInfo apiInfoLeague() {
        return new ApiInfoBuilder()
                .title("鸿坤荟-小程序接口")
                .version("1.0.0")
                .contact(new Contact("fengxiaoyang", null, "75918847@qq.com"))
                .description("RestApi接口文档")
                .build();
    }



    public List<Parameter> getParameterList(){
        List<Parameter> pars = new ArrayList<Parameter>();

        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("token").defaultValue("9B3F09E045DDE78211CE8C5D4F9B3FDD82C335F9341C3E9F6180EFDA3F79305D").description("用户token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());//根据每个方法名也知道当前方法在设置什么参数
        return pars;
    }


}
