package com.lonsec.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.lonsec.exceptions.BusinessException;
import com.lonsec.exceptions.TechnicalException;

public class FileUtils {
	
	private static final int MAX_DEPTH = 5;
	
	public static BufferedReader getFileBufferReaderForFile(File file) throws BusinessException {
		
		InputStream inputStr = null;
		Stream<Path> matches = null;
		try {
			Path startPath = Paths.get(".");
			matches = Files.find(startPath,MAX_DEPTH,(path, basicFileAttributes) -> path.getFileName().toString().equals(file.getName()));
			Path foundPath = matches.findAny().get();
			inputStr = new FileInputStream(new File(foundPath.toString()));
			
		}catch (IOException e) {
			TechnicalException technicalException = new TechnicalException("400", e.getMessage());
			throw technicalException;
			
		}
		
		matches.close();
		return new BufferedReader(new InputStreamReader(inputStr));
		
	}
	
	

}
