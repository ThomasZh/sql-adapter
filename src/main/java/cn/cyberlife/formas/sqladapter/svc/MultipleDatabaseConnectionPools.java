package cn.cyberlife.formas.sqladapter.svc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MultipleDatabaseConnectionPools {
    private static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public static Connection getConnection(String url, String username, String password) throws SQLException {
        // Double-checked locking pattern to initialize the DataSource lazily
        DataSource dataSource = dataSourceMap.get(url);
        if (dataSource == null) {
            synchronized (MultipleDatabaseConnectionPools.class) {
                dataSource = dataSourceMap.get(url);
                if (dataSource == null) {
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl(url);
                    config.setUsername(username);
                    config.setPassword(password);
                    // 设置最小连接数（默认值是10）
                    config.setMinimumIdle(1);
                    // 设置最大连接数（默认值是10）
                    config.setMaximumPoolSize(3);
                    dataSource = new HikariDataSource(config);
                    dataSourceMap.put(url, dataSource);
                }
            }
        }
        return dataSource.getConnection();
    }

    // Optionally, you can provide a method to close all data sources when they are no longer needed
    public static void closeAllDataSources() {
        for (DataSource dataSource : dataSourceMap.values()) {
            if (dataSource instanceof HikariDataSource) {
                ((HikariDataSource) dataSource).close();
            }
        }
        dataSourceMap.clear();
    }
}
