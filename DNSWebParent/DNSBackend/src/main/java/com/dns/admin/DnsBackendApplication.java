package com.dns.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.dns.common.entity"})
public class DnsBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(DnsBackendApplication.class, args);
	}

}





