package Ignitejava;

import java.sql.*;

public class InsertData
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		Connection con=DriverManager.getConnection("jdbc:ignite:thin://192.168.1.44/");
		try(PreparedStatement stmt=con.prepareStatement("INSERT INTO CITY(id,name) VALUES(?,?)"))
		{
			stmt.setLong(1, 14);
			stmt.setString(2, "dsf");
			stmt.executeUpdate();
			
			stmt.setLong(1,15);
			stmt.setString(2, "qwe");
			stmt.executeUpdate();
			
			stmt.setLong(1, 16);
			stmt.setString(2, "mnb");
			stmt.executeUpdate();
			
			stmt.setLong(1, 17);
			stmt.setString(2, "uhg");
			stmt.executeUpdate();
		}
		try(PreparedStatement stmt=con.prepareStatement("INSERT INTO PERSON(id,name) VALUES(?,?)"))
		{
			stmt.setLong(1, 5);
			stmt.setString(2, "tgb");
			stmt.executeUpdate();
			
			stmt.setLong(1,6);
			stmt.setString(2, "wxk");
			stmt.executeUpdate();
			
			stmt.setLong(1, 7);
			stmt.setString(2, "poy");
			stmt.executeUpdate();
			
			stmt.setLong(1, 8);
			stmt.setString(2, "fgh");
			stmt.executeUpdate();
			
			stmt.setLong(1, 9);
			stmt.setString(2, "tyu");
			stmt.executeUpdate();
		}
		System.out.println("Records inserted sucessfully");
	}
}
