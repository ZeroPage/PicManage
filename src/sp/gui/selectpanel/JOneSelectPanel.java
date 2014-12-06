package sp.gui.selectpanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import sp.imageinfo.ImageInfo;

public class JOneSelectPanel extends JPanel {
	private JLabel previewLabel;
	private JPanel infoPanel;
	private JPanel memoPanel;
	private JTextArea memoText;
	private JPanel tagPanel;
	private JTextArea tagText;
	public static final int THUMB_SIZE = 300;
	
	public JOneSelectPanel() {
		super();
		setLayout(new BorderLayout());

		previewLabel = new JLabel();
		previewLabel.setVerticalAlignment(JLabel.CENTER);
		previewLabel.setHorizontalAlignment(JLabel.CENTER);
		previewLabel.setHorizontalTextPosition(JLabel.CENTER);
		previewLabel.setVerticalTextPosition(JLabel.BOTTOM);
		add(previewLabel, BorderLayout.NORTH);

		infoPanel = new JPanel(new BorderLayout());
		add(infoPanel, BorderLayout.SOUTH);

		memoPanel = new JPanel(new BorderLayout());
		memoPanel.add(new JLabel("Memo"), BorderLayout.NORTH);
		memoText = new JTextArea();
		memoText.setEditable(false);
		memoPanel.add(memoText, BorderLayout.SOUTH);
		infoPanel.add(memoPanel, BorderLayout.NORTH);

		tagPanel = new JPanel(new BorderLayout());
		tagPanel.add(new JLabel("Tag(s)"), BorderLayout.NORTH);
		tagText = new JTextArea();
		tagText.setEditable(false);
		tagPanel.add(tagText, BorderLayout.SOUTH);
		infoPanel.add(tagPanel, BorderLayout.SOUTH);
	}
	
	public void update(List<ImageInfo> list) {
		ImageInfo item = list.get(0);
		
		previewLabel.setText(item.toString());

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

		previewLabel.setIcon(resizeImage);

		memoText.setText(item.getMemo());
		tagText.setText(item.getTag().toString());
	}
}
