package com.example.auction_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class AuctionApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			dotenv.entries().forEach(e -> 
				System.setProperty(e.getKey(), e.getValue())
		);

		System.out.println("=== DOTENV DEBUG ===");
		System.out.println("Loaded keys: " + dotenv.entries().size());
		System.out.println("PORT=" + dotenv.get("PORT"));
		System.out.println("DATABASE_URL=" + dotenv.get("DATABASE_URL"));
		System.out.println("====================");

		dotenv.entries().forEach(e ->
			System.setProperty(e.getKey(), e.getValue())
		);


		SpringApplication.run(AuctionApplication.class, args);
	}

}
