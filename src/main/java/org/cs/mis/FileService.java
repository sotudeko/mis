package org.cs.mis;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileService {
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

    private String COMMA_DELIMITER = ",";
    private String converter = "xlsx2csv";

    public boolean checkConverterExists() {
        Process process;

        boolean status = false;

        String os = System.getProperty("os.name");
        log.info("We are running on: '" + os + "'");

        try {
            if (os.contains("Win")){
                process = Runtime.getRuntime().exec("cmd /c command-to-check-converter-exists");
            }
            else {
                process = Runtime.getRuntime().exec(String.format("which %s", converter));
            }

            String line;
            StringBuilder output = new StringBuilder();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            
            if (exitVal == 0) {
                log.info("Found the converter program: " + output.toString());
                status = true;
            } 
            else {
                log.error("Did not find the converter: " + converter);
                log.error("Please install it with 'pip3 install " + converter + "'");
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        } 
        
    
	    // Path path = Paths.get(converter);

        // if (Files.isExecutable(path)){
        //     log.info("We have converter");
        // }
        // else {
        //     log.error("We do not have converter");
        // }

        return status;
    }

    

    // private void winCheck(){
    //     process = Runtime.getRuntime()
    //   .exec(String.format("cmd.exe /c dir %s", homeDirectory));
    // }

    public String convertExcelToCsv(String excelFile){
        log.info("Converting " + excelFile + " to csv");

        // check which xlsx2csv

        return "/tmp/conv.csv";
    }

    public void getMIs(String csvFile) throws FileNotFoundException, IOException {

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
    }



    public boolean fileExists(String excelFile) {
        return false;
    }
}
