package project.test.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataSource {
	private static DataSource dataSource = new DataSource(); //싱글톤으로 본인 스스로 인스턴스 만듬
	private DataSource() {}; //외부접근자 막기위해 private를 사용
	
	private Connection conn; //JDBC의 연결통로를 다루는게 Connection
	private String driver;
	private String url;
	private String user;
	private String password;
	//이것들은 외부에서 사용할것들
	
	
	public static DataSource getInstance() {
		return dataSource;
	}
	
	public Connection getConnection() {
		dbConfig();
		try {
			Class.forName(driver); 
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB 연결성공!");
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연결실패 ~!");
		}
		return conn;
	}
	
	private void dbConfig() {
		Properties properties = new Properties();
		String resource = getClass().getResource("/db.properties").getPath();
	
		try {
			properties.load(new FileInputStream(resource));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
