package sp.gui.selectpanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sp.imageinfo.ImageInfo;

public class JOneSelectPanel extends JPanel {
	private JLabel label;
	private JLabel hintLabel;
	public static final int THUMB_SIZE = 300;
	
	public JOneSelectPanel() {
		super();
		setLayout(new BorderLayout());
		
		label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);

		hintLabel = new JLabel("double click and check the console");
		
		add(label, BorderLayout.CENTER);
		add(hintLabel, BorderLayout.SOUTH);
	}
	
	public void update(List<ImageInfo> list) {
		ImageInfo item = list.get(0);
		
		label.setText(item.toString());


		BufferedImage image;
		try {
			image = ImageIO.read(item.getFile());
		} catch (IOException e) {
			return;
		}

		double ratio = Math.min((double) THUMB_SIZE / image.getWidth(), (double) THUMB_SIZE / image.getHeight());
		int newWidth = (int) (ratio * image.getWidth());
		int newHeight = (int) (ratio * image.getHeight());

		ImageIcon resizeImage = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));

		label.setIcon(resizeImage);
	}
}
