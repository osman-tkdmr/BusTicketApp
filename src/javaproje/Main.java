package javaproje;

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
	
		
		entery = new Entery();
		entery.setVisible(true);
		
		
		
	
	}
}
