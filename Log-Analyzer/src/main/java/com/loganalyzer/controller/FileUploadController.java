package com.loganalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loganalyzer.model.MyFile;
import com.loganalyzer.service.parser.LogParser;
import com.loganalyzer.service.validator.FileValidator;

/**
 * This class contains controller methods to handles all the requests that are
 * related to File Upload.
 * 
 * 
 * @author Saurabh
 */
@Controller
public class FileUploadController {

	@Autowired
	private FileValidator fileValidator;
	@Autowired
	private MyFile myFile;

	/**
	 * Setting a fileValidator to WebDataBinder
	 * 
	 * @param binder
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	@GetMapping(value = "/upload")
	public ModelAndView getFile() {
		return new ModelAndView("upload");
	}

	/**
	 * This method validates the uploaded file and stores the validated result into
	 * bindingResult. If file validation is successful it calls service methods to
	 * continue the execution.
	 * 
	 * @param myFile
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/success")
	public ModelAndView fileUpload(@ModelAttribute @Validated MyFile file, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("error");
		}

		ModelAndView modelAndView = new ModelAndView("success");
		myFile.setFile(file.getFile());
		new LogParser().logParsing(myFile.getFile());

		return modelAndView;
	}

}
