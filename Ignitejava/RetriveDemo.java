package Ignitejava;

import java.sql.*;

public class RetriveDemo 
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin://192.168.1.44/");
		try (Statement stmt = conn.createStatement()) 
		{
		    try (ResultSet rs =stmt.executeQuery("SELECT p.name, c.name " +
   									" FROM PERSON p, CITY c " +
		    									" WHERE p.city_id = c.id"))
		    {
		    	System.out.println("Query result is:");
		    	 while (rs.next())
		         System.out.println(rs.getString(1) + ", " + rs.getString(2));
		    }
		}
	}
}
