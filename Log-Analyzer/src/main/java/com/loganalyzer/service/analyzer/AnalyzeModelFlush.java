package com.loganalyzer.service.analyzer;

import java.util.LinkedList;
import java.util.List;

import com.loganalyzer.model.AnalyzeModel;
import com.loganalyzer.service.BeanUtils;
import com.loganalyzer.service.MyExecutorService;

/**
 * This class take responsibly that each and every objects has been inserted
 * into database and all relevant parameters has been reset.
 * 
 * 
 * @author Saurabh
 */
public class AnalyzeModelFlush {

	private List<AnalyzeModel> listAnalyzeModel;

	private AnalyzeModelUtils analyzeModelUtils;

	public AnalyzeModelFlush() {
		this.listAnalyzeModel = BeanUtils.getBean(AnalyzeModelUtils.class).getListAnalyzeModel();
		this.analyzeModelUtils = BeanUtils.getBean(AnalyzeModelUtils.class);
	}

	/**
	 * This Method adds the list of models in the data base if not empty and reset
	 * the list
	 */
	public void analyzeModelFlush() {
		if (!listAnalyzeModel.isEmpty())
			new AnalyzerDbUtils(listAnalyzeModel).addListAnalyzeModel();
		analyzeModelUtils.setListAnalyzeModel(new LinkedList<>());
		BeanUtils.getBean(MyExecutorService.class).getDbExecutorService().shutdown();
	}
}
