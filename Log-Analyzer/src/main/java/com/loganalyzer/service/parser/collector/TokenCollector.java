package com.loganalyzer.service.parser.collector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.loganalyzer.model.AnalyzeModel;
import com.loganalyzer.service.BeanUtils;
import com.loganalyzer.service.analyzer.AnalyzeModelUtils;

/**
 * This class converts each content blocks in relevant java model objects and
 * add those objects into List so that they can be processed for further
 * execution.
 * 
 * 
 * @author Saurabh
 */
public class TokenCollector implements Runnable {

	private StringBuilder stringBlock;

	/**
	 * This list contains all the details available in one single block in form of
	 * list.
	 */
	private List<String> tokenList;
	private AnalyzeModel analyzeModel;

	public TokenCollector(StringBuilder stringBlock) {
		super();
		this.stringBlock = stringBlock;
	}
	
	/**
	 * This method extracts every single information from the content block and puts
	 * them in list.
	 */
	public void tokenCollector() {
		StringTokenizer tokenizer;
		tokenList = new LinkedList<>();
		String block = stringBlock.toString();
		tokenizer = new StringTokenizer(block, "#,!(");

		while (tokenizer.hasMoreTokens()) {
			tokenList.add(tokenizer.nextToken());
		}
	}

	/**
	 * This method takes all the extracted tokens from list and filter the relevant
	 * details in key value form and converts them in java object.
	 */
	public void tokenMapper() {
		HashMap<String, String> mapTokens = new HashMap<>();

		for (String token : tokenList) {
			String[] values = token.split("=");
			if (values.length == 2)
				mapTokens.put(values[0], values[1]);
		}

		if (mapTokens.size() == 0)
			return;

		analyzeModel = new AnalyzeModel();

		for (Map.Entry<String, String> mapEntry : mapTokens.entrySet()) {
			String mapKey = mapEntry.getKey().replaceAll("\n", "").trim();
			String mapValue = mapEntry.getValue().replaceAll("\n", "").trim();

			if (mapKey.contains("IP-Address"))
				analyzeModel.setIpAddress(mapValue);
			if (mapKey.contains("User-Agent"))
				analyzeModel.setUserAgent(mapValue);

			if (mapKey.contains("Request-Type"))
				analyzeModel.setRequestType(mapValue);

			if (mapKey.contains("Status-Code"))
				analyzeModel.setStatusCode(mapValue);

			if (mapKey.contains("API"))
				analyzeModel.setApi(mapValue);

			if (mapKey.contains("User-Name"))
				analyzeModel.setUser(mapValue);

			if (mapKey.contains("EnterpriseId"))
				analyzeModel.setEntId(mapValue);

			if (mapKey.contains("EnterpriseName"))
				analyzeModel.setEntName(mapValue);
		}
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		tokenCollector();
		tokenMapper();
		BeanUtils.getBean(AnalyzeModelUtils.class).addAnalyzeModel(analyzeModel);
	}
}
