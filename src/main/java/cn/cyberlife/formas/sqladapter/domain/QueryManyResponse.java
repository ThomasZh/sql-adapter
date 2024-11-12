package cn.cyberlife.formas.sqladapter.domain;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class QueryManyResponse {
	int errCode;
	String errMsg;
	List<Map<String, Object>> datas;

	public QueryManyResponse(int errCode, String errMsg, List<Map<String, Object>> datas) {
		this.setErrCode(errCode);
		this.setErrMsg(errMsg);
		this.setDatas(datas);
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

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

}
