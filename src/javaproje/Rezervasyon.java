package javaproje;

import javax.swing.*;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Statement;


public class Rezervasyon extends JFrame {
	
	
	
	private JPanel contentPane;
	private JTextField fName;
	private JTextField fPhone;
	private JTextField fAge;
	private JComboBox<String> cGender;
	private JButton kayit;
	
	private void reservation() throws SQLException {
		Statement myStat = Main.conn();
		myStat.executeUpdate("insert into busticket.customers "
			+"values(null,'"+fName.getText()
			+"','"+cGender.getItemAt(cGender.getSelectedIndex())
			+"','"+fAge.getText()
			+"','"+fPhone.getText()
			+"','"+Main.seat
			+"','"+Main.iddeparture+"')");
			
	}
	
	public boolean isNumeric(String str) {
		try {  
		    Double.parseDouble(str);
		    return true;
	  } catch(NumberFormatException e){
		  return false;  
	  } 
	  
	}
	
	public Rezervasyon() {
		
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 600);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel user = new JPanel();
		user.setBounds(10, 90, 430, 460);
		contentPane.add(user);
		user.setLayout(null);
		
		JLabel lName = new JLabel("İsim Soyisim :");
		lName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lName.setBounds(10, 10, 200, 30);
		user.add(lName);
		
		JLabel lPhone = new JLabel("Telefon :");
		lPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lPhone.setBounds(10, 80, 200, 30);
		user.add(lPhone);
		
		JLabel lAge = new JLabel("Yaş : ");
		lAge.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lAge.setBounds(10, 160, 200, 30);
		user.add(lAge);
		
		JLabel lGander = new JLabel("Cinsiyet :");
		lGander.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lGander.setBounds(10, 240, 200, 30);
		user.add(lGander);
		
		fName = new JTextField();
		fName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fName.setBounds(220, 10, 200, 30);
		fName.setColumns(10);
		user.add(fName);
		
		
		fPhone = new JTextField();
		fPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fPhone.setColumns(10);
		fPhone.setBounds(220, 80, 200, 30);
		user.add(fPhone);
		
		fAge = new JTextField();
		fAge.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fAge.setBounds(220, 160, 200, 30);
		fAge.setColumns(10);
		user.add(fAge);
		
		cGender = new JComboBox<String>();
		cGender.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cGender.setModel(new DefaultComboBoxModel<String>(new String[] {"", "BAY", "BAYAN"}));
		cGender.setBounds(220, 240, 200, 30);
		user.add(cGender);
		
		
		
		kayit = new JButton("KAYDET");
		kayit.setFont(new Font("Tahoma", Font.BOLD, 20));
		kayit.setBounds(10, 300, 410, 60);
		user.add(kayit);
		
		kayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fName.getText().equals(""))
					JOptionPane.showMessageDialog(fName, "Lütfen bu alanı doğru doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else if(fPhone.getText().equals("") || !isNumeric(fPhone.getText())) 
					JOptionPane.showMessageDialog(fPhone, "Lütfen bu alanı doğru doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else if(fAge.getText().equals("")|| !isNumeric(fAge.getText())) 
					JOptionPane.showMessageDialog(fAge, "Lütfen bu alanı doğru doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else if(cGender.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(cGender, "Lütfen bu alanı doğru doldurduğunuzdan emin olun!", "", JOptionPane.PLAIN_MESSAGE);
				else {
					try {
						reservation();
						JOptionPane.showMessageDialog(null, "Rezervasyon işlemi başarılı", "", JOptionPane.PLAIN_MESSAGE);
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					kayit.setVisible(false);
				}
				
			}
			
		});
	
		
		
		
		JButton geri = new JButton();
		geri.setIcon(new ImageIcon(Rezervasyon.class.getResource("/javaproje/arrow2.png")));
		geri.setBounds(10, 10, 47, 29);
		contentPane.add(geri);		
		geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Otobus bus = new Otobus();
				bus.setVisible(true);
				dispose();
			}
		});
		
		
		
	}
}
