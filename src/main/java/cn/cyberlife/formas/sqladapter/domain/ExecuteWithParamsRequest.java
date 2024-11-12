package cn.cyberlife.formas.sqladapter.domain;

import java.util.List;

import lombok.Data;

@Data
public class ExecuteWithParamsRequest {
	private String url;
	private String username;
	private String password;
	private String sql;
	private List<Object> params;

	// Constructors, getters and setters
	public ExecuteWithParamsRequest() {
	}

	public ExecuteWithParamsRequest(String url, String username, String password, String sql, List<Object> params) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.sql = sql;
		this.params = params;
	}

	// getters and setters
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
