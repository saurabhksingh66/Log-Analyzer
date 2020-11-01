package com.loganalyzer.service.analyzer;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loganalyzer.model.AnalyzeModel;
import com.loganalyzer.service.MyExecutorService;

/**
 * This class manages a list of LOG-ANALYZE object model and make a call to
 * DBUtils methods once the BUFFER size has been reached.
 * 
 * 
 * @author Saurabh
 */
@Service
public class AnalyzeModelUtils {
	
	private static final int BUFFER_SIZE = 1000;
	private List<AnalyzeModel> listAnalyzeModel;
	
	@Autowired
	private MyExecutorService myExecutorService;
	
	public AnalyzeModelUtils() {
		this.listAnalyzeModel = new LinkedList<>();
	}

	/**
	 * This method add the LOG-ANALYZE object model in the list and list will be
	 * processed for storage activities once the list size has increased to
	 * BUFFER_SIZE
	 */
	public synchronized void addAnalyzeModel(AnalyzeModel analyzeModel) {
		if (listAnalyzeModel.size() == BUFFER_SIZE) {

			myExecutorService.getDbExecutorService().submit(new AnalyzerDbUtils(listAnalyzeModel));
			listAnalyzeModel = new LinkedList<>();
		}

		listAnalyzeModel.add(analyzeModel);
	}

	public List<AnalyzeModel> getListAnalyzeModel() {
		return listAnalyzeModel;
	}

	public void setListAnalyzeModel(List<AnalyzeModel> listAnalyzeModel) {
		this.listAnalyzeModel = listAnalyzeModel;
	}

}
