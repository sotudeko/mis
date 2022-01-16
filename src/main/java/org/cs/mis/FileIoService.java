package org.cs.mis;

import java.io.File;
import java.io.IOException;

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
    
}
