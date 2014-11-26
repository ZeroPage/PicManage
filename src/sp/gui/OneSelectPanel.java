package sp.gui;

import java.awt.Image;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sp.imageinfo.ImageInfo;

public class OneSelectPanel extends JPanel {
	private JLabel label;
	
	public OneSelectPanel() {
		super();
		
		label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		
		add(label);
	}
	
	public void update(List<ImageInfo> list) {
		ImageInfo item = list.get(0);
		
		label.setText(item.toString());
		
		Image image = new ImageIcon(item.getFile().getAbsolutePath()).getImage().getScaledInstance(200, 200, 1);
		Icon icon = new ImageIcon(image);
		label.setIcon(icon);
	}
}
