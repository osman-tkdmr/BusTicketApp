package javaproje;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class driver extends Main{
	private JTextField fdriverName;
	private JPanel panel;
	private JButton btnNewButton_1;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField fdriverTel;

	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Create the panel.
	 */
	public driver() {
		panel = new JPanel();
		
		panel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.setBounds(10, 90, 880, 460);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Şoför Adı :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 79, 200, 30);
		panel.add(lblNewLabel);
		
		fdriverName = new JTextField();
		fdriverName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fdriverName.setText("");
		fdriverName.setBounds(220, 79, 200, 30);
		panel.add(fdriverName);
		fdriverName.setColumns(10);
		
		JButton btnNewButton = new JButton("Şofor Ekle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(10, 370, 410, 60);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Şoför Kaldır");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setBounds(440, 370, 430, 60);
		panel.add(btnNewButton_1);
		
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
				iddriver = table.getValueAt(table.getSelectedRow(), 0).toString();
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblofrTel = new JLabel("Şoför Tel :");
		lblofrTel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblofrTel.setBounds(10, 148, 200, 30);
		panel.add(lblofrTel);
		
		fdriverTel = new JTextField();
		fdriverTel.setText("");
		fdriverTel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fdriverTel.setColumns(10);
		fdriverTel.setBounds(220, 148, 200, 30);
		panel.add(fdriverTel);

	}

	

	@Override
	public DefaultTableModel modelle() {
		model = new DefaultTableModel();
		String[] str = new String[3];
		str[0] = "id";
		str[1] = "Şoför Adı";
		str[2] = "Şoför Tel";
		
				
		model.setColumnIdentifiers(str);

		ResultSet rs;
		try {
			Statement mystat = conn();
			rs = mystat.executeQuery("select * from busticket.driver");
			while(rs.next()) {
				Object[] obj = new Object[3];
				obj[0] = rs.getInt("iddriver");
				obj[1] = rs.getString("driverName");
				obj[2] = rs.getString("driverTel");
				
				
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
			
			if(iddriver == null)
				JOptionPane.showMessageDialog(null, "Lütfen önce bir şoför seçin", "", JOptionPane.PLAIN_MESSAGE);
			else {
				int sil = JOptionPane.showConfirmDialog(null,"bu şoförü silmek istiyor musunuz?",null,JOptionPane.YES_NO_CANCEL_OPTION);
				if(sil == 0) {
					mystat = conn();
					mystat.executeUpdate("delete from busticket.driver where iddriver = '"+iddriver+"'");
					yenile();
					JOptionPane.showMessageDialog(null, "Şoför Silindi", "", JOptionPane.PLAIN_MESSAGE);
					
				}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
	}

	@Override
	public void insert() {
		try {
			Statement mystat = conn();
			mystat.executeUpdate("insert into busticket.driver values(null, '"+fdriverName.getText()+"','"+fdriverTel.getText()+"')");
			yenile();
			JOptionPane.showMessageDialog(null, "Şoför Eklendi", "", JOptionPane.PLAIN_MESSAGE);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
}
