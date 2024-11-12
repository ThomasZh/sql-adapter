# Readme
sql adapter for formas api by java

## 开发环境
* jdk_11
* eclipse

## 构建
```
mvn clean install
```

## 运行
```
java -jar sql-adapter-1.3.1.jar >/dev/null 2>&1
```

## 数据库链接示例
```
    String url = "jdbc:mysql://127.0.0.1:3306/formas?useSSL=false";
    String url = "jdbc:sqlserver://127.0.0.1\\databoost;port=2012;databaseName=formas";
```
