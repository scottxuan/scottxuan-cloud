package com.scottxuan.web.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author scottxuan
 */
public abstract class AbstractSwaggerConfig {
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(getBasePackage()))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(getTitle())
                .description(getDescription())
                .version(getVersion())
                .build();
    }

    /**
     * swagger扫描的包
     *
     * @return
     */
    protected abstract String getBasePackage();

    /**
     * 文档标题
     *
     * @return
     */
    protected abstract String getTitle();

    /**
     * 文档描述
     *
     * @return
     */
    protected abstract String getDescription();

    /**
     * 文档版本
     *
     * @return
     */
    protected abstract String getVersion();
}
