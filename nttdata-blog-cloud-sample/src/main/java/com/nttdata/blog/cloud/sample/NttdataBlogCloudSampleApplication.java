package com.nttdata.blog.cloud.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.nttdata.blog.cloud.sample.controllers","com.nttdata.blog.cloud.sample.dtos",
		"com.nttdata.blog.cloud.sample.entities", "com.nttdata.blog.cloud.sample.repositories", "com.nttdata.blog.cloud.sample.services",
		"com.nttdata.blog.cloud.sample.utils", "com.nttdata.blog.cloud.sample.mappers"})
public class NttdataBlogCloudSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NttdataBlogCloudSampleApplication.class, args);
	}

}
