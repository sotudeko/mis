package org.cs.mis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MIService {
    private static final Logger log = LoggerFactory.getLogger(MIService.class);

    private String CSES_LISTFILE = "./cses.txt";

    @Autowired
    private FileIoService fileIoService;

    public void getMIs(String csvFile) throws FileNotFoundException, IOException{
        Map<String, Integer> cses = fileIoService.readFile(CSES_LISTFILE);

        List<List<String>> records = new ArrayList<List<String>>();

        int uniqueCustomers = 0;
        int totalMis = 0;

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));

                if (values.length > 3){
                    String customer = values[1];
                    String cse = values[2];
                    String mis = values[3];

                    if (cses.containsKey(cse)){
                        uniqueCustomers++;
                        totalMis+=Integer.valueOf(mis);
                        //log.info(customer + ":" + cse + ":" + mis);
                    }
                }
            }
        }

        log.info("Unique customers: " + uniqueCustomers);
        log.info("Meaningful interactions: " + totalMis);
    }

}
