package com.loganalyzer.service.analyzer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.loganalyzer.model.AnalyzeModel;
import com.loganalyzer.service.BeanUtils;

/**
 * This class takes responsibility of data interaction between JAVA objects and
 * the database.
 * 
 * 
 * @author Saurabh
 */
public class AnalyzerDbUtils implements Runnable {

	private BasicDataSource datasource;
	private JdbcTemplate jdbcTemplate;
	private List<AnalyzeModel> listAnalyzeModel;

	public AnalyzerDbUtils(List<AnalyzeModel> listAnalyzeModel) {
		datasource = BeanUtils.getBean(BasicDataSource.class);
		jdbcTemplate = new JdbcTemplate(datasource);
		this.listAnalyzeModel = listAnalyzeModel;	
	}

	/**
	 * This method adds the list of LOG_ANALYZE model objects into database using
	 * batch insertion query.
	 */
	public void addListAnalyzeModel() {
		String sql = "INSERT INTO LOG_ENTITIES VALUES (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				AnalyzeModel analyzeModel = listAnalyzeModel.get(i);
				ps.setString(1, null);
				ps.setString(2, analyzeModel.getIpAddress());
				ps.setString(3, analyzeModel.getUserAgent());
				ps.setString(4, analyzeModel.getStatusCode());
				ps.setString(5, analyzeModel.getRequestType());
				ps.setString(6, analyzeModel.getApi());
				ps.setString(7, analyzeModel.getUser());
				ps.setString(8, analyzeModel.getEntId());
				ps.setString(9, analyzeModel.getEntName());
				
			}
			
			@Override
			public int getBatchSize() {
				return listAnalyzeModel.size();
			}
		});
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		addListAnalyzeModel();
	}

}
