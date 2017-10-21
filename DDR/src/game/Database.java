//package game;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class Database {
//	private Connection conn;
//	public Database(){
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/DDRUsersDatabase?user=root&password=root");
//
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public void addUser(String username, String password){
//		try {
//			Statement statement = conn.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String sql = "INSERT INTO Users" + "VALUES (" + username + "," + password + ")";
//	}
//	
//	//returns true if user exists in database,
//	//false 
//	public boolean checkUser(String username, String pass){
//		boolean userExists = false;
//		
//		
//		return userExists;
//	}
//	
//	
//}
