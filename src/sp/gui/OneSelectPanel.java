package sp.gui;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sp.imageinfo.ImageInfo;

public class OneSelectPanel extends JPanel {
	private JLabel label;
	
	public OneSelectPanel() {
		super();
		
		label = new JLabel();
		add(label);
	}
	
	public void update(List<ImageInfo> list) {
		label.setText(list.get(0).toString());
	}
}
