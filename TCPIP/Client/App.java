package Client;

public class App {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		MainCotrol mainControl = new MainCotrol(mainFrame);
		mainFrame.setVisible(true);
	}

}
