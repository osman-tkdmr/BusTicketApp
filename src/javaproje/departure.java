package javaproje;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class departure extends Main{

	private JPanel panel;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField cTime_1;
	private JTextField cDate_1;
	private JTextField cTarget_1;
	private JTextField cDeparture_1;
	private JComboBox<String> c_idbus;
	private JComboBox<String> c_iddriver;

	public JPanel getPanel() {
		return panel;
	}
	
	public DefaultTableModel modelle() {
		model = new DefaultTableModel();
		String[] str = new String[7];
		str[0] = "id";
		str[1] = "Kalkış";
		str[2] = "Hedef";
		str[3] = "Tarih";
		str[4] = "Saat";
		str[5] = "Otobüs";
		str[6] = "Şoför";
		
		model.setColumnIdentifiers(str);

		ResultSet rs;
		try {
			Statement mystat = conn();
			rs = mystat.executeQuery("select * from busticket.departures, busticket.buses, busticket.driver" +
									"where busticket.departures.idbuses = busticket.buses.idbuses "+
									"and busticket.departures.iddriver = busticket.driver.iddriver");
			while (rs.next()) {
				Object[] obj = new Object[7];
				obj[0] = rs.getInt("iddepartures");
				obj[1] = rs.getString("departure");
				obj[2] = rs.getString("target");
				obj[3] = rs.getDate("departureTime");
				obj[4] = rs.getTime("departureTime");
				obj[5] = rs.getString("busPlate");
				obj[6] = rs.getString("driverName");
				
				model.addRow(obj);
			}

			rs.close();
			mystat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	
	public departure() {
		
		
		
		
		panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.setBounds(10, 90, 880, 460);
		panel.setLayout(null);
		
		
		
		JLabel lDeparture_1 = new JLabel("Nereden :");
		lDeparture_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lDeparture_1.setBounds(10, 10, 200, 30);
		panel.add(lDeparture_1);
		
		cDeparture_1 = new JTextField();
		cDeparture_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cDeparture_1.setBounds(220, 10, 200, 30);
		panel.add(cDeparture_1);
		
		JLabel lTarget_1 = new JLabel("Nereye : ");
		lTarget_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lTarget_1.setBounds(10, 71, 200, 30);
		panel.add(lTarget_1);
		
		cTarget_1 = new JTextField();
		cTarget_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cTarget_1.setBounds(220, 71, 200, 30);
		panel.add(cTarget_1);
		
		JLabel lDate_1 = new JLabel("Tarih :");
		lDate_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lDate_1.setBounds(10, 128, 200, 30);
		panel.add(lDate_1);
		
		cDate_1 = new JTextField();
		cDate_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cDate_1.setBounds(220, 128, 200, 30);
		panel.add(cDate_1);
		
		JLabel lTme_1 = new JLabel("Saat :");
		lTme_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lTme_1.setBounds(10, 185, 200, 30);
		panel.add(lTme_1);
	
		cTime_1 = new JTextField();
		cTime_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cTime_1.setBounds(220, 185, 200, 30);
		panel.add(cTime_1);
		
		JLabel lblNewLabel = new JLabel("Şoför :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 245, 200, 30);
		panel.add(lblNewLabel);
		
		c_iddriver = new JComboBox<String>();
		c_iddriver.setFont(new Font("Tahoma", Font.PLAIN, 20));
		c_iddriver.setBounds(220, 245, 200, 30);
		try {
			Statement mystat = conn();
			ResultSet rs = mystat.executeQuery("select * from busticket.driver");
			while(rs.next())
				c_iddriver.addItem(rs.getString("iddriver"));
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		panel.add(c_iddriver);
		
		JLabel lblNewLabel_1 = new JLabel("Otobüs :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 305, 200, 30);
		panel.add(lblNewLabel_1);
		
		c_idbus = new JComboBox<String>();
		c_idbus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		c_idbus.setBounds(220, 305, 200, 30);
		try {
			Statement mystat = conn();
			ResultSet rs = mystat.executeQuery("select * from busticket.buses");
			while(rs.next())
				c_idbus.addItem(rs.getString("idbuses"));
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		panel.add(c_idbus);
		
		JButton btnSeferEkle = new JButton("Sefer Ekle");
		btnSeferEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnSeferEkle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSeferEkle.setBounds(10, 370, 410, 60);
		panel.add(btnSeferEkle);
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(440, 10, 430, 325);
		panel.add(scrollPane);
		
		
		
		table = new JTable();
		table.setModel(modelle());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumn("id").setPreferredWidth(30);//id dışındaki her kolon scrollpane eşit dağıtılacak
		for(int i = 1;i<table.getColumnCount();i++) 
			table.getColumn(table.getColumnName(i)).setPreferredWidth((scrollPane.getWidth()-30)/(table.getColumnCount()-1));
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				iddeparture = table.getValueAt(table.getSelectedRow(), 0).toString();
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		
		JButton btnNewButton = new JButton("Seferi Kaldır");
		btnNewButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				delete();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnNewButton.setBounds(440, 370, 430, 60);
		panel.add(btnNewButton);
		

		
	}

	public void insert() {
		String Time = cDate_1.getText()+" "+cTime_1.getText();
		try {
			Statement mystat = conn();
			mystat.executeUpdate("insert into busticket.departures values(null, '"+cDeparture_1.getText()
			+"','"+cTarget_1.getText()
			+"','"+Time
			+"','"+c_idbus.getSelectedItem()
			+"','"+c_iddriver.getSelectedItem()+"')");
			yenile();
			JOptionPane.showMessageDialog(null, "Sefer Eklendi", "", JOptionPane.PLAIN_MESSAGE);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	public void delete() {
		Statement mystat;
		try {
			
			if(iddeparture == null)
				JOptionPane.showMessageDialog(null, "Lütfen önce bir sefer seçin", "", JOptionPane.PLAIN_MESSAGE);
			else {
				int sil = JOptionPane.showConfirmDialog(null,"bu sefri silmek istiyor musunuz?",null,JOptionPane.YES_NO_CANCEL_OPTION);
				if(sil == 0) {
					mystat = conn();
					mystat.executeUpdate("delete from busticket.departures where iddepartures = '"+iddeparture+"'");
					yenile();
					JOptionPane.showMessageDialog(null, "Sefer Silindi", "", JOptionPane.PLAIN_MESSAGE);
					
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
}
