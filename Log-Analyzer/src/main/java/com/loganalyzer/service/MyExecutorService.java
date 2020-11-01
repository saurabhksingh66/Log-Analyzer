package com.loganalyzer.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

/**
 * This class provides Threads pool to other java classes.
 * 
 * 
 * @author Saurabh
 */
@Service
public class MyExecutorService {

	private ExecutorService tokenExecutorService;
	private ExecutorService dbExecutorService;

	/**
	 * This method return ExecutorService instance for token generation activities
	 * 
	 * @return ExecutorService instance
	 */
	public ExecutorService getTokenExecutorService() {
		if (tokenExecutorService == null || tokenExecutorService.isShutdown())
			tokenExecutorService = Executors.newFixedThreadPool(10);
		return this.tokenExecutorService;
	}

	/**
	 * This method return ExecutorService instance for database related activities
	 * 
	 * @return ExecutorService instance
	 */
	public ExecutorService getDbExecutorService() {
		if (dbExecutorService == null || dbExecutorService.isShutdown())
			dbExecutorService = Executors.newFixedThreadPool(10);
		return this.dbExecutorService;
	}
}
