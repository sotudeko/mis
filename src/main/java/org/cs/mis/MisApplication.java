package org.cs.mis;

import org.cs.mis.service.CsvFileService;
import org.cs.mis.service.FileIoService;
import org.cs.mis.service.MIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MisApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(MisApplication.class);

	@Autowired
    private CsvFileService csvFileService;
	
	@Autowired
    private MIService miService;

	@Autowired
	private FileIoService fileIoService;

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

		String excelFile = args[0];

		if (!fileIoService.fileExists(excelFile)) {
			log.error("Exiting...");
			System.exit(-1);
		}

		String csvFile = csvFileService.excelToCsvFile(excelFile);

		if (csvFile == null){
			log.error("Error creating csv file... exiting");
			System.exit(-1);
		}

		log.info("Processing csv file '" + csvFile + "' for MI count ");
		List<List<String>> records = miService.getMIs(csvFile);

		for (List<String> record : records){
			//log.info(record.toString());
		}
	}

	
}
