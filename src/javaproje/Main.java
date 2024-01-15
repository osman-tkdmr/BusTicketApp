package javaproje;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

import javax.swing.table.DefaultTableModel;

public abstract class Main {
	public static String iddeparture ;
	public static String idcustomer;
	public static String idbuses;
	public static String iddriver;
	public static String seat;
	public static Entery entery;
	
	public static Statement conn() throws SQLException {
		Connection myConn = DriverManager.getConnection("jdbc:mySql://localhost:3306/","root","Osman4242");
		Statement stat = myConn.createStatement();
		
		return stat;
			
	}
	
	public void yenile() {
		entery.setVisible(false);
		entery = new Entery();
		entery.setVisible(true);
	}
	
	public abstract void delete();
	public abstract void insert();
	public abstract DefaultTableModel modelle();
	
	
	public static void main(String[] args)  {
	
		try {
			Statement a = conn();
			

			try (BufferedReader reader = new BufferedReader(new FileReader("src/busTicket.sql"));
				Statement statement = a) {
				String line;
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				String[] queries = sb.toString().split(";");

				for (String query : queries) {
					statement.execute(query);
					System.out.println(query);
				}

	               	System.out.println("Tablolar oluşturuldu.");
	           	} catch (Exception e) {
	               	e.printStackTrace();
	           	}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		entery = new Entery();
		entery.setVisible(true);
		
		
		
	
	}
}
