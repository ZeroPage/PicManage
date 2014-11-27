package sp.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NoSelectPanel extends JPanel {
	private JLabel label;
	
	public NoSelectPanel() {
		super();
		
		label = new JLabel("select photo from left");
		add(label);
	}
}
