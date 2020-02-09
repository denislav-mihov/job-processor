package com.mihov.jobprocessor.config;

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
 * Created by Denis on 09-Feb-20.
 *
 * @author Denis
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.mihov.jobprocessor"))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder()
      .title("Job Processor API")
      .description("Sort tasks and produce a shell script containing the commands that could be pipelined to bash.")
      .contact(new Contact("Denislav Mihov", "https://github.com/denislav-mihov", "dmikhov@gmail.com"))
      .version("1.0")
      .build();
  }
}
