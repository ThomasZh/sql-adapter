package cn.cyberlife.formas.sqladapter.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.cyberlife.formas.sqladapter.domain.ExecuteSqlRequest;
import cn.cyberlife.formas.sqladapter.domain.ExecuteSqlResponse;
import cn.cyberlife.formas.sqladapter.domain.ExecuteWithParamsRequest;
import cn.cyberlife.formas.sqladapter.domain.QueryManyResponse;
import cn.cyberlife.formas.sqladapter.domain.QueryOneResponse;
import cn.cyberlife.formas.sqladapter.svc.DatabaseManager;

@RestController
@RequestMapping(value = "/sql-adapter") // 通过这里配置使下面的映射都在/sql下
public class SqlController {
	private final Logger LOGGER = Logger.getLogger(SqlController.class.getName());
    static {
        // 创建SerializeConfig实例
        SerializeConfig config = new SerializeConfig();
    }
    
	/**
	 * 处理"/sql-adapter/query-one/"的POST请求，用来执行sql语句
	 *
	 * @param ExecuteSqlRequest
	 * @return
	 */
	@PostMapping("/query-one")
	public String queryOne(@RequestBody ExecuteSqlRequest req) {
		// @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
		LOGGER.info("\n\n  ... queryOne " + req.getUrl() + "\n" + req.getSql() + " \n\n");

		DatabaseManager db = new DatabaseManager();
		try {
			db.connect(req.getUrl(), req.getUsername(), req.getPassword());
			Map<String, Object> resultSet = db.queryOne(req.getSql());

			QueryOneResponse resp = new QueryOneResponse(200, "Success", resultSet);
	        // 使用WriteMapNullValue特性确保null值被写入
	        String jsonStr = JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue);
			LOGGER.info(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			LOGGER.warning("SQL Error: " + e);
			ExecuteSqlResponse resp = new ExecuteSqlResponse(500, "Server Error", e.toString());
			return JSON.toJSONString(resp); // 将结果转换为JSON字符串
		} finally {
			try {
				db.close();
			} catch (SQLException e) {
				LOGGER.warning("Error closing connection: " + e.getMessage());
			}
		}
	}

	/**
	 * 处理"/sql-adapter/query-many/"的POST请求，用来执行sql语句
	 *
	 * @param ExecuteSqlRequest
	 * @return
	 */
	@PostMapping("/query-many")
	public String queryMany(@RequestBody ExecuteSqlRequest sqlStmt) {
		// @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
		LOGGER.info("\n\n  ... queryMany " + sqlStmt.getUrl() + "\n" + sqlStmt.getSql() + " \n\n");

		DatabaseManager db = new DatabaseManager();
		try {
			db.connect(sqlStmt.getUrl(), sqlStmt.getUsername(), sqlStmt.getPassword());
			List<Map<String, Object>> results = db.queryMany(sqlStmt.getSql());

			QueryManyResponse resp = new QueryManyResponse(200, "Success", results);
	        // 使用WriteMapNullValue特性确保null值被写入
	        String jsonStr = JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue);
			LOGGER.info(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			LOGGER.warning("SQL Error: " + e);
			ExecuteSqlResponse resp = new ExecuteSqlResponse(500, "Server Error", e.toString());
			return JSON.toJSONString(resp); // 将结果转换为JSON字符串
		} finally {
			try {
				db.close();
			} catch (SQLException e) {
				LOGGER.warning("Error closing connection: " + e.getMessage());
			}
		}
	}

	/**
	 * 处理"/sql-adapter/execute/"的POST请求，用来执行sql语句
	 *
	 * @param ExecuteSqlRequest
	 * @return
	 */
	@PostMapping("/execute")
	public String execute(@RequestBody ExecuteSqlRequest sqlStmt) {
		// @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
		LOGGER.info("\n\n  ... execute " + sqlStmt.getUrl() + "\n" + sqlStmt.getSql() + " \n\n");

		DatabaseManager db = new DatabaseManager();
		try {
			db.connect(sqlStmt.getUrl(), sqlStmt.getUsername(), sqlStmt.getPassword());
			int rowsUpdated = db.execute(sqlStmt.getSql());

			ExecuteSqlResponse resp = new ExecuteSqlResponse(200, "Success", Integer.toString(rowsUpdated));
			String jsonStr = JSON.toJSONString(resp); // 将结果转换为JSON字符串
			LOGGER.info(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			LOGGER.warning("SQL Error: " + e);
			ExecuteSqlResponse resp = new ExecuteSqlResponse(500, "Server Error", e.toString());
			return JSON.toJSONString(resp); // 将结果转换为JSON字符串
		} finally {
			try {
				db.close();
			} catch (SQLException e) {
				LOGGER.warning("Error closing connection: " + e.getMessage());
			}
		}
	}

	/**
	 * 处理"/sql-adapter/query-one-with-params"的POST请求，用来执行sql语句
	 *
	 * @param ExecuteWithParamsRequest
	 * @return String
	 */
	@PostMapping("/query-one-with-params")
	public String queryOneWithParams(@RequestBody ExecuteWithParamsRequest req) {
		// @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
		LOGGER.info("\n\n  ... query-one-with-params " + req.getUrl() + "\n" + req.getSql() + "\n"
				+ req.getParams().toString() + " \n\n");

		DatabaseManager db = new DatabaseManager();
		try {
			db.connect(req.getUrl(), req.getUsername(), req.getPassword());
			Map<String, Object> resultSet = db.queryOneWithParams(req.getSql(), req.getParams());

			QueryOneResponse resp = new QueryOneResponse(200, "Success", resultSet);
	        // 使用WriteMapNullValue特性确保null值被写入
	        String jsonStr = JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue);
			LOGGER.info(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			LOGGER.warning("SQL Error: " + e);
			ExecuteSqlResponse resp = new ExecuteSqlResponse(500, "Server Error", e.toString());
			return JSON.toJSONString(resp); // 将结果转换为JSON字符串
		} finally {
			try {
				db.close();
			} catch (SQLException e) {
				LOGGER.warning("Error closing connection: " + e.getMessage());
			}
		}
	}

	/**
	 * 处理"/sql-adapter/query-many-with-params"的POST请求，用来执行sql语句
	 *
	 * @param ExecuteWithParamsRequest
	 * @return String
	 */
	@PostMapping("/query-many-with-params")
	public String queryManyWithParams(@RequestBody ExecuteWithParamsRequest req) {
		// @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
		LOGGER.info("\n\n  ... query-many-with-params " + req.getUrl() + "\n" + req.getSql() + "\n"
				+ req.getParams().toString() + " \n\n");

		DatabaseManager db = new DatabaseManager();
		try {
			db.connect(req.getUrl(), req.getUsername(), req.getPassword());
			List<Map<String, Object>> resultSet = db.queryManyWithParams(req.getSql(), req.getParams());

			QueryManyResponse resp = new QueryManyResponse(200, "Success", resultSet);
	        // 使用WriteMapNullValue特性确保null值被写入
	        String jsonStr = JSON.toJSONString(resp, SerializerFeature.WriteMapNullValue);
			LOGGER.info(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			LOGGER.warning("SQL Error: " + e);
			ExecuteSqlResponse resp = new ExecuteSqlResponse(500, "Server Error", e.toString());
			return JSON.toJSONString(resp); // 将结果转换为JSON字符串
		} finally {
			try {
				db.close();
			} catch (SQLException e) {
				LOGGER.warning("Error closing connection: " + e.getMessage());
			}
		}
	}


	/**
	 * 处理"/sql-adapter/execute-with-params"的POST请求，用来执行sql语句
	 *
	 * @param ExecuteSqlRequest
	 * @return
	 */
	@PostMapping("/execute-with-params")
	public String executeWithParams(@RequestBody ExecuteWithParamsRequest req) {
		// @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
		LOGGER.info("\n\n  ... execute-with-params " + req.getUrl() + "\n" + req.getSql() + "\n"
				+ req.getParams().toString() + " \n\n");

		DatabaseManager db = new DatabaseManager();
		try {
			db.connect(req.getUrl(), req.getUsername(), req.getPassword());
			int rowsUpdated = db.executeWithParams(req.getSql(), req.getParams());

			ExecuteSqlResponse resp = new ExecuteSqlResponse(200, "Success", Integer.toString(rowsUpdated));
			String jsonStr = JSON.toJSONString(resp); // 将结果转换为JSON字符串
			LOGGER.info(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			LOGGER.warning("SQL Error: " + e);
			ExecuteSqlResponse resp = new ExecuteSqlResponse(500, "Server Error", e.toString());
			return JSON.toJSONString(resp); // 将结果转换为JSON字符串
		} finally {
			try {
				db.close();
			} catch (SQLException e) {
				LOGGER.warning("Error closing connection: " + e.getMessage());
			}
		}
	}
}
