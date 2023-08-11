

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SearchUserFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JLabel lbUser;
	
	
	public SearchUserFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		txtSearch = new JTextField();
		contentPane.add(txtSearch, BorderLayout.NORTH);
		txtSearch.setColumns(10);
		
		btnSearch = new JButton("Search User");
		
		contentPane.add(btnSearch, BorderLayout.SOUTH);
		
		
		
		lbUser = new JLabel("Nhap de search");
		lbUser.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbUser, BorderLayout.CENTER);
	}
	
	public String getSearchQuery() {
		return txtSearch.getText();
	}
	
	public void showUser(String user) {
		this.lbUser.setText(user);
	}
	
	public void addSearchListener(ActionListener ac) {
		this.btnSearch.addActionListener(ac);
	}

}
