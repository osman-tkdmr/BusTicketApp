package javaproje;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class bus  extends Main{
	private JTextField fplaka;
	private JTextField fkoltuk;
	private JButton btnNewButton;
	private JPanel panel;
	private DefaultTableModel model;
	
	private JTable table;
	private JScrollPane scrollPane;

	public JPanel getPanel() {
		return panel;
	}

	
	public bus() {
		panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.setBounds(10, 90, 880, 460);
		panel.setLayout(null);
		
		JLabel lblPlaka = new JLabel("Otobus plakası :");
		lblPlaka.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlaka.setBounds(10, 80, 200, 30);
		panel.add(lblPlaka);
		
		fplaka = new JTextField();
		fplaka.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fplaka.setBounds(215, 80, 200, 30);
		panel.add(fplaka);
		fplaka.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Koltuk sayısı :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 150, 200, 30);
		panel.add(lblNewLabel);
		
		fkoltuk = new JTextField();
		fkoltuk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fkoltuk.setBounds(215, 150, 200, 30);
		panel.add(fkoltuk);
		fkoltuk.setColumns(10);
		
		btnNewButton = new JButton("Otobüs Ekle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(10, 370, 410, 60);
		panel.add(btnNewButton);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(440, 10, 430, 325);
		panel.add(scrollPane);
		
		
		
		table = new JTable();
		table.setModel(modelle());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for(int i = 0;i<table.getColumnCount();i++) //tüm kolonlar scrollpane sığdırılacak
		table.getColumn(table.getColumnName(i)).setPreferredWidth(scrollPane.getWidth()/table.getColumnCount());
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				idbuses = table.getValueAt(table.getSelectedRow(), 0).toString();
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		
		JButton btnNewButton = new JButton("Otobüsü Kaldır");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnNewButton.setBounds(440, 370, 430, 60);
		panel.add(btnNewButton);
		

	}


	public void delete() {
		Statement mystat;
		try {
			
			if(idbuses == null)
				JOptionPane.showMessageDialog(null, "Lütfen önce bir otobüs seçin", "", JOptionPane.PLAIN_MESSAGE);
			else {
				int sil = JOptionPane.showConfirmDialog(null,"bu otobüsü silmek istiyor musunuz?",null,JOptionPane.YES_NO_CANCEL_OPTION);
				if(sil == 0) {
					mystat = conn();
					mystat.executeUpdate("delete from busticket.buses where idbuses = '"+idbuses+"'");
					JOptionPane.showMessageDialog(null, "Otobus Silindi", "", JOptionPane.PLAIN_MESSAGE);
					 yenile();
					
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}


	


	@Override
	public DefaultTableModel modelle() {
		model = new DefaultTableModel();
		String[] str = new String[3];
		str[0] = "id";
		str[1] = "Plaka";
		str[2] = "Koltuk Sayısı";
				
		model.setColumnIdentifiers(str);

		ResultSet rs;
		try {
			Statement mystat = conn();
			rs = mystat.executeQuery("select * from busticket.buses");
			while(rs.next()){
				Object[] obj = new Object[3];
				obj[0] = rs.getInt("idbuses");
				obj[1] = rs.getString("busPlate");
				obj[2] = rs.getString("numberOfSeats");
				
				
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
	public void insert() {
		try {
			Statement mystat = conn();
			mystat.executeUpdate("insert into busticket.buses values(null, '"+fplaka.getText()+"','"+fkoltuk.getText()+"')");
			yenile();
			JOptionPane.showMessageDialog(null, "Otobüs Eklendi", "", JOptionPane.PLAIN_MESSAGE);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
	}

}
