import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSearch;
	private JButton btnAddUser;
	
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 115);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnAddUser = new JButton("Add New User");
		contentPane.add(btnAddUser);
		
		btnSearch = new JButton("Search User");
		contentPane.add(btnSearch);
	}
	
	public void addSearchUserListener(ActionListener log) {
		this.btnSearch.addActionListener(log);
	}
	
	public void addAddNewUserListener(ActionListener log) {
		this.btnAddUser.addActionListener(log);
	}
	
	
}
