package org.cs.mis.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileIoService {
	private static final Logger log = LoggerFactory.getLogger(FileIoService.class);

    public boolean fileExists(String filename) throws IOException {
		boolean exists = false;
		
		File f = new File(filename);
		
		if (f.exists() && f.length() > 0){
			exists = true;
		}
		else {
			log.error("File not found: " + filename);
		}
		
		return exists;
	}

	public Map<String, Integer> readFile(String filename) throws IOException{
		Map<String, Integer> lines = new HashMap<String, Integer>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;

			while ((line = br.readLine()) != null) {
				lines.put(line, 1);
			}
		}
		return lines;
	}
    
}
