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
    private FileService fileService;
	
	public static void main(String[] args) {
		SpringApplication.run(MisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting MIs Application");

		if (args.length != 1){
			log.error("usage: <program> <xlsx-file>");
			System.exit(-1);
		}

		// check xlsx to csv program is available
		if (!fileService.checkConverterExists()){
			log.error("Exiting...");
            System.exit(-1);
		}

		String excelFile = args[0];

		if (fileService.fileExists(excelFile)){
			String csvFile = fileService.convertExcelToCsv(excelFile);
			log.info(csvFile);
	
			fileService.getMIs(csvFile);
		}
		else {
			log.error("File does not exist: " + excelFile);
			System.exit(-1);
		}
	}
}
