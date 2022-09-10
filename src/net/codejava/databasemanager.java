package net.codejava;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class databasemanager {
	
	//connection to database
	protected static Connection dbConnection()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			
			String jdbcUrl = "jdbc:sqlite:/F:\\Java\\SQLite\\sqlite-tools-win32-x86-3380100\\subjects.db";
			
			Connection connection = DriverManager.getConnection(jdbcUrl);
			
			return connection;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	//Checks if entered subject already exists in database
	public static boolean checkIfExist(String subjectName) throws SQLException {
		
		String sql = "SELECT * FROM subjects WHERE subjectName = " + subjectName;
		
		String sqlTest = "SELECT COUNT(*) FROM subjects WHERE subjectName = "+ "'" +subjectName + "'";
		
		Connection conn = dbConnection();
		
		boolean itExist = true;
				
		
		PreparedStatement st = conn.prepareStatement("select * from subjects where subjectName= ?");
		st.setString(1, subjectName);
		ResultSet r1 = st.executeQuery();
		if(r1.next()) {
			System.out.println("   !!!!!!!!!!!!!!!");
			itExist = true;
			r1.close();
		}else {
			itExist = false;
		}

		return itExist;
	}
	
	
	
	//Updates table content
	public static void updateTable(int id, String subjectName, String profName, int ECTS, int hours) throws SQLException {
		String sql = "UPDATE subjects SET subjectName= ?, profName= ?, ECTS = ?, hours = ? WHERE id = ? ";
		
		Connection conn = dbConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		
		st.setString(1, subjectName);
		st.setString(2, profName);
		st.setInt(3, ECTS);
		st.setInt(4, hours);
		st.setInt(5, id);
		
		//st.executeQuery();
		
		st.executeUpdate();
		
		System.out.print("pucam ");
		st.close();
		conn.close();
		//ResultSet r1 = st.executeQuery();
	}
	
	
	//import subjects to database
	public static void importSubject(int id, String courseName, String profName, int ECTS, int hours) throws SQLException {
		String sql = "INSERT INTO subjects ( subjectName, profName, ECTS, hours ) VALUES ('" + courseName + "', ' " + profName + "', ' " + ECTS + "', ' " + hours + "');";
		
		Connection conn = dbConnection();
		
		Statement statement = conn.createStatement();
		
		statement.executeUpdate(sql);
		
		Statement stmt = conn.createStatement();
		String queryTest = "select * from subjects where subjectName=" + "\""+courseName+"\""+";";
		ResultSet rsTest = stmt.executeQuery(queryTest);
		String checkUser = rsTest.getString(1);
		
		conn.close();
	}
	
	
	//read subjects from database
	public static String readFromDb() throws SQLException{
		
		Connection conn = dbConnection();
		Statement stmt = conn.createStatement();
		
		ResultSet results = stmt.executeQuery("SELECT * FROM subjects ORDER BY ECTS ASC");
			
		String result = results.getString(1);
		
		conn.close();
		
		return result;
		
	}	
}
