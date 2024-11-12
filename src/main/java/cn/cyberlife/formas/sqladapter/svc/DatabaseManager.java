package cn.cyberlife.formas.sqladapter.svc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DatabaseManager {
	private final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
	private Connection conn;

	// 修改connect方法，使用url作为查找MultipleDatabaseConnectionPools.getConnection()的key
	public void connect(String url, String username, String password) throws SQLException {
		// 将URL作为key来获取连接
		conn = MultipleDatabaseConnectionPools.getConnection(url, username, password);
	}

	// 其余方法保持不变
	public List<Map<String, Object>> queryMany(String sql) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			List<Map<String, Object>> results = new ArrayList<>();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = rs.getObject(i);
					row.put(columnName, columnValue);
				}

				results.add(row);
			}
			return results;
		}
	}

	public Map<String, Object> queryOne(String sql) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			Map<String, Object> result = new HashMap<>();

			if (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = rs.getObject(i);
					result.put(columnName, columnValue);
				}
			}
			return result;
		}
	}

	public int execute(String sql) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			return stmt.executeUpdate();
		}
	}

	public Map<String, Object> queryOneWithParams(String sql, List<Object> params) throws SQLException {
		// Create a PreparedStatement with a parameterized query
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the values for the parameters
			for (int i = 0; i < params.size(); i++) { // 这里应该使用 params.size()
				pstmt.setObject(i + 1, params.get(i));
			}

			// Execute the query
			ResultSet rs = pstmt.executeQuery();

			Map<String, Object> result = new HashMap<>();

			if (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = rs.getObject(i);
					result.put(columnName, columnValue);
				}
			}
			return result;
		}
	}

	public List<Map<String, Object>> queryManyWithParams(String sql, List<Object> params) throws SQLException {
		// Create a PreparedStatement with a parameterized query
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the values for the parameters
			for (int i = 0; i < params.size(); i++) { // 这里应该使用 params.size()
				pstmt.setObject(i + 1, params.get(i));
			}

			// Execute the query
			ResultSet rs = pstmt.executeQuery();

			List<Map<String, Object>> results = new ArrayList<>();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = rs.getObject(i);
					row.put(columnName, columnValue);
				}

				results.add(row);
			}
			return results;
		}
	}

	public int executeWithParams(String sql, List<Object> params) throws SQLException {
		// Create a PreparedStatement with a parameterized query
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the values for the parameters
			for (int i = 0; i < params.size(); i++) { // 这里应该使用 params.size()
				pstmt.setObject(i + 1, params.get(i));
			}

			return pstmt.executeUpdate();
		}
	}

	public void close() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
}
