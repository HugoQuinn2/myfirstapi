package com.hq.myfirtsapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyfirtsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfirtsapiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		String version = MyfirtsapiApplication.class.getPackage().getImplementationVersion();
		return new OpenAPI()
				.info(new Info()
						.title("Mi Priema Api Basica")
						.version(version)
						.description("Esta es mi primera Api, es muy basica :3")
					);
	}

}