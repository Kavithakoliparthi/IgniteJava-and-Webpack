package Ignitejava;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;

public class TableCreation
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
					// Register JDBC driver.
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

					// Open JDBC connection.
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin:// 192.168.1.44/");

					// Create database tables.
		try (Statement stmt = conn.createStatement())
		{
					// Create table based on REPLICATED template.
		    stmt.executeUpdate("CREATE TABLE CITY (" + 
		    " id LONG PRIMARY KEY, name VARCHAR) " +
		    " WITH \"template=replicated\"");

		    		// Create table based on PARTITIONED template with one backup.
		    stmt.executeUpdate("CREATE TABLE PERSON (" +
		    " id LONG, name VARCHAR, city_id LONG, " +
		    " PRIMARY KEY (id, city_id)) " +
		    " WITH \"backups=1, affinityKey=city_id\"");
		  
		    		// Create an index on the City table.
		    stmt.executeUpdate("CREATE INDEX idx_city_name ON CITY (name)");

		    		// Create an index on the Person table.
		    stmt.executeUpdate("CREATE INDEX idx_person_name ON PERSON (name)");
		}
		System.out.println("Table created sucessfully");
	}
}
