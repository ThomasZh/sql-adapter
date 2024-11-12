package cn.cyberlife.formas.sqladapter.domain;

import java.util.Map;
import lombok.Data;

@Data
public class QueryOneResponse {
	int errCode;
	String errMsg;
	Map<String, Object> data;

	public QueryOneResponse(int errCode, String errMsg, Map<String, Object> data) {
		this.setErrCode(errCode);
		this.setErrMsg(errMsg);
		this.setData(data);
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
