package Ignitejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateDemo
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin://192.168.1.44/");
		try (Statement stmt = conn.createStatement()) 
		{
		    stmt.executeUpdate("UPDATE City SET name = 'Mouse' WHERE id = 2");
		}
		System.out.println("Record updated");
	}
}
