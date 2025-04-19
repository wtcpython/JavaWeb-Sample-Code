package com.wtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class TliasmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TliasmanagementApplication.class, args);
	}
}
