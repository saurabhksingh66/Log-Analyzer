package com.loganalyzer.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * This class is responsible to provide Spring Factory Beans to Normal java
 * classes.
 * 
 * 
 * @author Saurabh
 */
@Service
public class BeanUtils implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanUtils.applicationContext = applicationContext;		
	}
	
	/**
	 * This method provides an object from Spring Factory Beans.
	 * 
	 * @param beanClass
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return applicationContext.getBean(beanClass);
	}
	
}
