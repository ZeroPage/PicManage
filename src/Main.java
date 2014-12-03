import sp.gui.PicManageGUI;
import sp.imagemanager.ImageManager;

public class Main {
	public static void main(String[] args) {
		ImageManager manager = new ImageManager();
		PicManageGUI gui = new PicManageGUI(manager);

		gui.setVisible(true);
	}
}
