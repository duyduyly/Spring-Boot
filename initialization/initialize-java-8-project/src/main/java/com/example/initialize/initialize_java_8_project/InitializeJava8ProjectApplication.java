package com.example.initialize.initialize_java_8_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class InitializeJava8ProjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(InitializeJava8ProjectApplication.class, args);
	}


    @Override
    public void run(String... args) throws Exception {
//        ClassPathResource resource = new ClassPathResource("db.changelog/changelog-master.xml");
//        System.out.println("Liquibase file exists = " + resource.exists());
    }
}
