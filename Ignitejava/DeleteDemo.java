package Ignitejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDemo 
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin://192.168.1.44/");
		try (Statement stmt = conn.createStatement())
		{
			stmt.executeUpdate("delete from city");
			stmt.executeUpdate("delete from person");
		    //stmt.executeUpdate("DELETE FROM PERSON WHERE name = 'tgb'");
		}
		System.out.println("Table deleted");
	}
}
