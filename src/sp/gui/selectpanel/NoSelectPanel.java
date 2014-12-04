package sp.gui.selectpanel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NoSelectPanel extends JPanel {
	private JLabel label;
	
	public NoSelectPanel() {
		super();
		
		label = new JLabel("select photo from left");
		add(label);
	}
}
