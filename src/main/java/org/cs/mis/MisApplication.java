package org.cs.mis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MisApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(MisApplication.class);

	@Autowired
    private CsvFileService csvFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(MisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting MIs Application");

		String csvFile = csvFileService.createCsvFile(args);

		

		
	}

	
}
