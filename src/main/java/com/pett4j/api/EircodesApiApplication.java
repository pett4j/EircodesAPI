package com.pett4j.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableZuulProxy
public class EircodesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EircodesApiApplication.class, args);
	}
}