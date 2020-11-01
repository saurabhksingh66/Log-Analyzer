package com.loganalyzer.service.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.loganalyzer.model.MyFile;

/**
 * This class validates the uploaded file
 * 
 * 
 * @author Saurabh
 */
@Service
public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {

		return MyFile.class.equals(paramClass);
	}

	/**
	 * This method validates the uploaded file and only allows the files which have
	 * size greater than zero and content type as text/plain.
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		MyFile file = (MyFile) target;
		if (file.getFile().getSize() == 0 || !file.getFile().getContentType().equals("text/plain")) {
			errors.rejectValue("file", "Please Select a file");
		}

	}

}
