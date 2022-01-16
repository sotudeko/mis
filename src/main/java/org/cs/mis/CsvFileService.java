package org.cs.mis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvFileService {
	private static final Logger log = LoggerFactory.getLogger(CsvFileService.class);

    @Autowired
    private FileIoService fileIoService;
    
    private String converter = "xlsx2csv";

    public String createCsvFile(String args[]) throws Exception{
        String csvFile = null;

        if (args.length != 1){
			log.error("usage: <program> <xlsx-file>");
		}
        else {
            if (this.checkConverterExists()){     
                String excelFile = args[0];

                if (fileIoService.fileExists(excelFile)){
                    csvFile = this.convertExcelToCsv(excelFile);
                }
            }
        }

        return csvFile;        
    }

    private boolean checkConverterExists() throws IOException, InterruptedException {

        boolean status = false;

        String os = System.getProperty("os.name");
        log.info("We are running on: '" + os + "'");

        String command;

        if (os.contains("Win")){
            command = "cmd /c command-to-check-converter-exists";
        }
        else {
            command = String.format("which %s", converter);
        }

        Process process = this.execCommand(command);

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
        
        return status;
    }

    public String convertExcelToCsv(String excelFile){
        log.info("Converting " + excelFile + " to csv");

        return "/tmp/conv.csv";
    }

    private Process execCommand(String command) throws IOException{
        Process process = Runtime.getRuntime().exec(command);
        return process;
    }

}
