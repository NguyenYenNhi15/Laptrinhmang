import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainCotrol {
	private MainFrame mainFrame;
	
	public MainCotrol(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		mainFrame.addSearchUserListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				SearchUserFrame searchUserFrame = new SearchUserFrame();
				SearchUserControl searchUserControl = new SearchUserControl(searchUserFrame);
				searchUserFrame.setVisible(true);
				
			}
		});
		
		mainFrame.addAddNewUserListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				AddNewUserFrame addNewUserFrame = new AddNewUserFrame();
				AddNewUserControl addNewUserControl = new AddNewUserControl(addNewUserFrame);
				
				addNewUserFrame.setVisible(true);
				
			}
		});
		
	}
}
