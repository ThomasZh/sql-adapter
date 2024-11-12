package cn.cyberlife.formas.sqladapter.domain;

import lombok.Data;

@Data
public class ExecuteSqlResponse {
	int errCode;
	String errMsg;
	String data;

	public ExecuteSqlResponse(int errCode, String errMsg, String data) {
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
