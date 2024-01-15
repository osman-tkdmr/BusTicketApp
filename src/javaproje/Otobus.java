package javaproje;

import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;



public class Otobus extends JFrame {
 
	private JPanel contentPane;
	private JPanel panel;
	private JButton koltuk;

	private void coloring() throws SQLException {//to button coloring by genders of customer
		Statement myStat = Main.conn();
	    ResultSet rs = myStat.executeQuery("select * from busticket.customers where iddepartures = "+Main.iddeparture+"");
	    
		while(rs.next()) {
			for(int i = 0; i<panel.getComponentCount();i++) {
				
				if(((JButton)panel.getComponent(i)).getText().equals(rs.getString("cusSeat")) ) {
					
					if(rs.getString("cusGender").equals("BAY")) 
						((JButton)panel.getComponent(i)).setBackground(Color.cyan);
						
					else
						((JButton)panel.getComponent(i)).setBackground(Color.pink);
					
				}
				
			}
			
		}
		
	}
	
	public void buttonAction(JButton btn ) {//to able to listen to each button 
		
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btn.getBackground() == Color.cyan || btn.getBackground() == Color.pink)
					JOptionPane.showMessageDialog(btn, "Bu koltul dolu!!", "", JOptionPane.PLAIN_MESSAGE);
				else {
					Main.seat = btn.getText();
					Rezervasyon ekle = new Rezervasyon();
					ekle.setVisible(true);
					dispose();
				}
			}
			
		});
	}
	
	
	public Otobus()  {
		
		
		
		setResizable(false);
		setBounds(100, 100, 1053, 362);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(50, 10, 1020, 310);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton geri = new JButton("");
		geri.setIcon(new ImageIcon(Otobus.class.getResource("/javaproje/arrow2.png")));
		geri.setFont(new Font("Tahoma", Font.BOLD, 11));
		geri.setBounds(10, 10, 47, 29);
		contentPane.add(geri);
		
		
		
		geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.entery = new Entery();
				Main.entery.setVisible(true);
				dispose();
			}
		});
		
		
		//dynamic button positioning
		int sayac = 1;
		for(int j = 1; j<16; j++) {
			
			for(int i = 1; i<5; i++) {
				
				if(j==8 & i!=4)
					continue;
				if(i==3 & j != 15)
					continue;
				koltuk = new JButton(Integer.toString(sayac));
				koltuk.setName("koltuk"+Integer.toString(sayac));
				koltuk.setBounds(j*60, i*60, 50, 50);
				panel.add(koltuk);
				sayac++;
				buttonAction(koltuk);
				
			}
		}
		
		
		try {
			coloring();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
	}


	
}
