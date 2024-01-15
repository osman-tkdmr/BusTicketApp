package javaproje;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class Entery extends JFrame {

	private JPanel contentPane;

	
	public Entery(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 71, 880, 479);
		
		contentPane.add(tabbedPane);
		departure departure = new departure();
		bus bus = new bus();
		user user =new user();
		driver driver = new driver();
		
		tabbedPane.addTab("Yolcu ", null, user.getPanel(), null);
		tabbedPane.addTab("Otobüs ", null, bus.getPanel(), null);
		tabbedPane.addTab("Şoför ", null, driver.getPanel(), null);
		tabbedPane.addTab("Sefer ", null, departure.getPanel(), null);
		
				
	}
}
