package org.cs.mis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
//@Slf4j
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

		if (csvFile == null){
			log.error("No csv file.. exiting");
			System.exit(-1);
		}

		log.info("Processing csv file '" + csvFile + "' for MI count ");

		

		
	}

	
}
