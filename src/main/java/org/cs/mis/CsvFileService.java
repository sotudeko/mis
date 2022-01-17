package org.cs.mis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvFileService {
	private static final Logger log = LoggerFactory.getLogger(CsvFileService.class);

    @Autowired
    private FileIoService fileIoService;
    
    private String converterName = "xlsx2csv";

    public String createCsvFile(String args[]) throws Exception{
        String csvFile = null;

        if (args.length != 1){
			log.error("usage: <program> <xlsx-file>");
		}
        else {
            String converterPath = this.checkConverterExists();

            if (converterPath != null){     
                String excelFile = args[0];
                csvFile = this.convertExcelToCsv(converterPath, excelFile);
            }
        }

        return csvFile;        
    }

    private String checkConverterExists() throws IOException, InterruptedException {

        String os = System.getProperty("os.name");
        log.info("We are running on: '" + os + "'");
        log.info("Looking for xlsx to csv converter program");

        String command = null;
        String converterPath = null;

        if (os.contains("Win")){
            command = "cmd /c command-to-check-converter-exists";
        }
        else {
            command = String.format("which %s", converterName);
        }

        log.info("Executing command: " + command);

        Process process = Runtime.getRuntime().exec(command);

        String line;
        StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        int exitVal = process.waitFor();

        if (exitVal == 0){
            converterPath = output.toString();
            log.info("Found the converter program at: " + output.toString());
        } 
        else {
            log.error("Did not find the converter: " + converterName);
            log.error("Please install it with 'pip3 install " + converterName + "'");
        } 
        
        return converterPath;
    }

    public String convertExcelToCsv(String converterPath, String excelFile) throws IOException, InterruptedException{
        String csvFile = null;

        log.info("Converting " + excelFile + " to csv");

        if (fileIoService.fileExists(excelFile)){
            String command = null;
            String os = System.getProperty("os.name");

            if (os.contains("Win")){
                command = "cmd /c command-to-check-converter-exists";
            }
            else {
                command = String.format("%s %s", converterPath, excelFile);
            }

            log.info("Executing: " + command);

            Process process = Runtime.getRuntime().exec(command);

            String line;
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
                
            if (exitVal == 0){
                csvFile = this.dumpToFile(output);
            } 
            else {
            }

        }

        return csvFile;
    }

    private String dumpToFile(StringBuilder output) {
        String timestamp = DateTimeFormatter.ofPattern("ddMMyy_HHmm").format(LocalDateTime.now());
        String csvFile = "./mi-" + timestamp + ".csv";

        log.info("Writing output to csv file: " + csvFile);
        //log.info(output.toString());

        return csvFile;

    }

    

}
