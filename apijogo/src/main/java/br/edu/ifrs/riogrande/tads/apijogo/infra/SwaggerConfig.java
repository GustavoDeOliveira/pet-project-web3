package br.edu.ifrs.riogrande.tads.apijogo.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("apijogo-1.0")
			.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/v1/**"))
                .build()
            .useDefaultResponseMessages(false)
			.directModelSubstitute(Object.class, java.lang.Void.class)         
			.apiInfo(new ApiInfoBuilder()
						.description("Uma ideia maluca que tenta aplicar os conceitos de um livro-jogo em uma API. Ainda por cima é escrita em Java.")
						.title("API-Jogo")
						.version("1.0.0")
						.build());
	}

}