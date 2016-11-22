package net.htwater.myfinal.config;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class DataSourceItem {

	public static final String DRIVERCLASSNAME = "driverClassName";
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";


	String datasourceName;
	String driverClassName;
	String url;
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	boolean enable;
	
	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public ActiveRecordPlugin getActiveRecord() {
		return activeRecord;
	}

	public void setActiveRecord(ActiveRecordPlugin activeRecord) {
		this.activeRecord = activeRecord;
	}

	String username;
	String password;
	ActiveRecordPlugin activeRecord;

	/**
	 * 
	 * get db driver name
	 * 
	 * @return String
	 * 
	 * 
	 */

	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * 
	 * set db driver name
	 * 
	 * @param driverClassName
	 * @return void
	 * 
	 * 
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * 
	 * get db url name
	 * 
	 * @return String
	 * 
	 * 
	 */

	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * set db url name
	 * 
	 * @param url
	 * @return void
	 * 
	 * 
	 */

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * get db user name
	 * 
	 * @return String
	 * 
	 * 
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * set db user name
	 * 
	 * @param username
	 * @return void
	 * 
	 * 
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * get db password
	 * 
	 * @return String
	 * 
	 * 
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * set db password
	 * 
	 * @param password
	 * @return void
	 * 
	 * 
	 */

	public void setPassword(String password) {
		this.password = password;
	}

	public DataSourceItem(String datasourceName, String driverClassName, String url, String username, String password) {

		this.datasourceName = datasourceName;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public DataSourceItem(String datasourceName) {
		this.datasourceName = datasourceName;
	}

}
