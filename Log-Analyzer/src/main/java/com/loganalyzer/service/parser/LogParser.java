package com.loganalyzer.service.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.loganalyzer.service.BeanUtils;
import com.loganalyzer.service.MyExecutorService;
import com.loganalyzer.service.analyzer.AnalyzeModelFlush;
import com.loganalyzer.service.parser.collector.TokenCollector;

/**
 * This class converts the content of the file in required blocks where each
 * block contains information of one unit.
 * 
 * 
 * @author Saurabh
 */
public class LogParser {

	private StringBuilder stringBlock;
	private ExecutorService tokenExecutorService;

	public LogParser() {
		this.tokenExecutorService = BeanUtils.getBean(MyExecutorService.class).getTokenExecutorService();
	}

	/**
	 * This method takes the uploaded file as parameter and converts the content the
	 * file in required blocks where each block contains information of one unit.
	 * This method uses Regular Expressions to divide the content into blocks. It
	 * puts all the blocks into Thread pool immediately. Once all the threads
	 * complete their executions it calls FLUSH to ensure each and every block has
	 * been processed successfully.
	 * 
	 * @param myFile
	 */
	public void logParsing(MultipartFile file) {
		BufferedReader reader;
		String readLine;
		Matcher matcher;
		Pattern pattern = Pattern.compile("[0-9]{4}-([0][1-9]|[1][0-2])-([0-2][0-9]|[3][0-1])");

		try {
			reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			readLine = reader.readLine();
			while (readLine != null) {
				matcher = pattern.matcher(readLine);
				if (matcher.find()) {
					tokenExecutorService.submit(new TokenCollector(stringBlock));
					stringBlock = new StringBuilder("");
				}

				stringBlock.append(readLine);
				stringBlock.append("\n");
				readLine = reader.readLine();
			}

			if (stringBlock != null) {
				tokenExecutorService.submit(new TokenCollector(stringBlock));
			}

			reader.close();
			tokenExecutorService.shutdown();
			tokenExecutorService.awaitTermination(1, TimeUnit.HOURS);
			new AnalyzeModelFlush().analyzeModelFlush();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
