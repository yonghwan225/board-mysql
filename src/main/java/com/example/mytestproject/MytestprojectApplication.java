package com.example.mytestproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MytestprojectApplication {

	// 서버 키고 콘솔창 jbdc옆 내용 복사후 서버에 http://localhost:8080/h2-console 이거 치고 jdbc에 넣으면 sql가능
	public static void main(String[] args) {

		SpringApplication.run(MytestprojectApplication.class, args);
	}


}
