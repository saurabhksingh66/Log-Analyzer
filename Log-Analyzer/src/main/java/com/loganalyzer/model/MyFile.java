package com.loganalyzer.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class represents the model object for MultipartFile.
 * 
 * 
 * @author Saurabh
 */
public class MyFile {

	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
