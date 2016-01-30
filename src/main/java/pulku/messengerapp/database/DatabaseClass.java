package pulku.messengerapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseClass {

	String url = "jdbc:postgresql://localhost:5432/";
	String driver = "org.postgresql.Driver";
	String dbName = "messenger";
	String userName = "postgres";
	String password = "postgres";
	
	Connection connection = null;
	Statement statement;
	
	public DatabaseClass(){
		connectDB();
	}
	
	public DatabaseClass(String dbName, String userName, String password){
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		connectDB();
	}
	
	public void connectDB(){
		try {
			if(connection == null || connection.isClosed()){
				Class.forName(driver);
				connection = DriverManager.getConnection(url+dbName,userName, password);
				statement = connection.createStatement();
			}
		} catch (Exception e) {
			System.err.println("DB Connection exception : " + e);
		}
	}
	
	public ResultSet getAllDatas(String tableName){
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("select * from " + tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getData(String tableName, String columnName, String param){
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("select * from " + tableName + " where " + columnName + " = '"+param + "'" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int addData(String query){
		int add = -1;
		try {
			add = statement.executeUpdate(query);
			if(add > 0 ){
				System.out.println("Execution is succesfull!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return add;
	}
	
}
