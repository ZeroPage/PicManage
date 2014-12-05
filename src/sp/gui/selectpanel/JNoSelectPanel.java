package sp.gui.selectpanel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JNoSelectPanel extends JPanel {
	private JLabel label;
	
	public JNoSelectPanel() {
		super();
		
		label = new JLabel("select photo from left");
		add(label);
	}
}
