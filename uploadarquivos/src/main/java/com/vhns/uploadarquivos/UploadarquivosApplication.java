package com.vhns.uploadarquivos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UploadarquivosApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadarquivosApplication.class, args);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("1234"));
	}

}
