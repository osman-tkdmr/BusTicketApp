package javaproje;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class user extends Main{

	private JComboBox<String> cDeparture;
	private JComboBox<String> cTarget;
	private JComboBox<String> cDate;
	private JComboBox<String> cTime;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnbilet;
	
	
	public JPanel getPanel() {
		return panel;
	}


	private void comboAction() throws SQLException {//for comboboxes to get items hierarchically
		Statement myStat = conn();
		ResultSet departures = myStat.executeQuery("SELECT DISTINCT departure FROM busticket.departures");
		cDeparture.addItem("");
		while(departures.next())		
			cDeparture.addItem(departures.getString("departure"));	
		departures.close();
		
		cDeparture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cTarget.removeAllItems();cTarget.addItem("");
				try {
					ResultSet targets = myStat.executeQuery("SELECT DISTINCT target FROM busticket.departures where "
							+ "departure = '"+cDeparture.getSelectedItem()+"' ");
					while(targets.next())
						cTarget.addItem(targets.getString("target"));
					targets.close();
				}catch (Exception e1) {
					
				}
			}
		});
		cTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cDate.removeAllItems();cDate.addItem("");
				try {
					ResultSet dates = myStat.executeQuery("SELECT DISTINCT date(departureTime) FROM busticket.departures where "
							+ "departure = '"+cDeparture.getSelectedItem()+"'"
							+ "and target = '"+cTarget.getSelectedItem()+"'");
					while(dates.next())
						cDate.addItem(dates.getString("date(departureTime)"));
					dates.close();
				
				}catch (Exception e1) {
					
				}
			}
		});
		cDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cTime.removeAllItems();cTime.addItem("");
				try {
					ResultSet times = myStat.executeQuery("SELECT DISTINCT time(departureTime) FROM busticket.departures where "
							+ "departure = '"+cDeparture.getSelectedItem()+"'"
							+ "and target = '"+cTarget.getSelectedItem()+"'"
							+ "and date(departureTime) = '"+cDate.getSelectedItem()+"'");
					while(times.next())
						cTime.addItem(times.getString("time(departureTime)"));
					times.close();
				
				}catch (Exception e1) {
					
				}
			}
		});
		
		
	}
	
	public void depar() throws SQLException {//to get the id of the departure
		Statement myStat = conn();
		ResultSet buses  = myStat.executeQuery("select iddepartures from busticket.departures"
				 + " where departure='"+cDeparture.getSelectedItem()+"'"
				 + " and target='"+cTarget.getSelectedItem()+"'"
				 + " and date(departureTime)='"+cDate.getSelectedItem()+"'"
				 + " and time(departureTime)='"+cTime.getSelectedItem()+"'");
			
		while(buses.next()) 
			iddeparture = buses.getString("iddepartures");
			
		if(iddeparture == null) 
			JOptionPane.showMessageDialog(null, "Böyle bir sefer bulunmamaktadır", "", JOptionPane.PLAIN_MESSAGE);
		else {
				Otobus bus = new Otobus();
				bus.setVisible(true);
				entery.dispose();
				
		}
	}
	
	public user() {

		panel = new JPanel(); 
		panel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.setBounds(10, 90, 880, 460);
		
		panel.setLayout(null);
		
		JLabel lDeparture = new JLabel("Nereden :");
		lDeparture.setBounds(10, 10, 200, 30);
		lDeparture.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lDeparture);
		
		JLabel lTarget = new JLabel("Nereye : ");
		lTarget.setBounds(10, 100, 200, 30);
		lTarget.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lTarget);
		
		JLabel lDate = new JLabel("Tarih :");
		lDate.setBounds(10, 201, 200, 30);
		lDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lDate);
		
		JLabel lTme = new JLabel("Saat :");
		lTme.setBounds(10, 305, 200, 30);
		lTme.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lTme);
		
		
		cDeparture = new JComboBox<String>();
		cDeparture.setBounds(109, 10, 200, 30);
		cDeparture.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(cDeparture);
		
		cTarget = new JComboBox<String>();
		cTarget.setBounds(109, 100, 200, 30);
		cTarget.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(cTarget);
		
		cDate = new JComboBox<String>();
		cDate.setBounds(109, 201, 200, 30);
		cDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(cDate);
		
		cTime = new JComboBox<String>();
		cTime.setBounds(109, 305, 200, 30);
		cTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(cTime);
		try {
			comboAction();
		} catch (SQLException e2) {
			
			e2.printStackTrace();
		}
		
		btnbilet = new JButton("Koltuk Seç");
		btnbilet.setBounds(10, 370, 410, 60);
		
		insert();
		
		btnbilet.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(btnbilet);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(319, 10, 551, 325);
		panel.add(scrollPane);
		
		
		
		table = new JTable();
		table.setModel(modelle());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				idcustomer = table.getValueAt(table.getSelectedRow(), 0).toString();
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		
		JButton btnNewButton = new JButton("Bilet İptal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(440, 370, 430, 60);
		panel.add(btnNewButton);
		
		

	}


	


	@Override
	public DefaultTableModel modelle() {
		model = new DefaultTableModel();
		String[] str = new String[10];
		str[0] = "id";
		str[1] = "İsim";
		str[2] = "Tel";
		str[3] = "Nerden";
		str[4] = "Nereye";
		str[5] = "Tarih";
		str[6] = "Saat";
		str[7] = "Koltuk";
		str[8] = "Yaş";
		str[9] = "Cinsiyet";
		
		model.setColumnIdentifiers(str);

		ResultSet rs;
		try {
			Statement mystat = conn();
			rs = mystat.executeQuery("select * from busticket.customers, busticket.departures where busticket.customers.iddepartures = busticket.departures.iddepartures");
			for(int i = 0; rs.next();i++) {
				Object[] obj = new Object[10];
				obj[0] = rs.getInt("idcustomers");
				obj[1] = rs.getString("cusName");
				obj[2] = rs.getString("cusPhone");
				obj[3] = rs.getString("departure");
				obj[4] = rs.getString("target");
				obj[5] = rs.getDate("departureTime");
				obj[6] = rs.getTime("departureTime");
				obj[7] = rs.getString("cusSeat");
				obj[8] = rs.getString("cusAge");
				obj[9] = rs.getString("cusGender");
				
				
				model.addRow(obj);
			}
			rs.close();
			mystat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}


	@Override
	public void delete() {
		Statement mystat;
		try {
			
			if(idcustomer == null)
				JOptionPane.showMessageDialog(null, "Lütfen önce bir yolcu seçin", "", JOptionPane.PLAIN_MESSAGE);
			else {
				int sil = JOptionPane.showConfirmDialog(null,"bu yolcu silmek istiyor musunuz?",null,JOptionPane.YES_NO_CANCEL_OPTION);
				if(sil == 0) {
					mystat = conn();
					mystat.executeUpdate("delete from busticket.customers where idcustomers = '"+idcustomer+"'");
					yenile();
					JOptionPane.showMessageDialog(null, "Yolcu Silindi", "", JOptionPane.PLAIN_MESSAGE);
					
				}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
	}


	@Override
	public void insert() {
		btnbilet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(cDeparture.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(cDeparture, "Lütfen bu alanı doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else if(cTarget.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(cTarget, "Lütfen bu alanı doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else if(cDate.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(cDate, "Lütfen bu alanı doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else if(cTime.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(cTime, "Lütfen bu alanı doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else {
					try {
						depar();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		
			}
			
		});
		
	}
}
