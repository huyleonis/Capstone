package com.fpt.capstone;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

@SpringBootApplication
@ComponentScan(basePackages = "com.fpt.capstone")
@EnableJpaRepositories(basePackages = "com.fpt.capstone.Repositories")
@EntityScan(basePackages = "com.fpt.capstone.Entities")
public class WebServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);

		// Initialize Firebase
		try {
			FileInputStream serviceAccount =
					  new FileInputStream("src/main/resources/service-account.json");
					
					FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					  .setDatabaseUrl("https://atsapp-bf5ae.firebaseio.com")
					  .build();
					
					FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
		}

    }
}
