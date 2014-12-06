package sp.gui.selectpanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import sp.gui.gridlist.ImageInfoListListener;
import sp.imageinfo.ImageInfo;

public class JOneSelectPanel extends JPanel {
	private JLabel previewLabel;
	private JPanel controlPanel;
	private JButton deleteButton;
	private JPanel infoPanel;
	private JPanel memoPanel;
	private JTextArea memoText;
	private JPanel tagPanel;
	private JTextArea tagText;

	private List<ImageInfoListListener> deleteListeners;
	private ImageInfo currentInfo;

	public static final int THUMB_SIZE = 300;
	
	public JOneSelectPanel() {
		super();

		deleteListeners = new ArrayList<>();

		setLayout(new BorderLayout());

		previewLabel = new JLabel();
		previewLabel.setVerticalAlignment(JLabel.CENTER);
		previewLabel.setHorizontalAlignment(JLabel.CENTER);
		previewLabel.setHorizontalTextPosition(JLabel.CENTER);
		previewLabel.setVerticalTextPosition(JLabel.BOTTOM);
		add(previewLabel, BorderLayout.NORTH);

		controlPanel = new JPanel(new BorderLayout());
		add(controlPanel, BorderLayout.SOUTH);

		deleteButton = new JButton("Remove");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (ImageInfoListListener listener : deleteListeners) {
					List<ImageInfo> tempList = new ArrayList<>();
					tempList.add(currentInfo);
					listener.action(tempList);
				}
			}
		});
		controlPanel.add(deleteButton, BorderLayout.SOUTH);

		infoPanel = new JPanel(new BorderLayout());
		controlPanel.add(infoPanel, BorderLayout.NORTH);

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
		currentInfo = list.get(0);
		
		previewLabel.setText(currentInfo.toString());

		BufferedImage image;
		try {
			image = ImageIO.read(currentInfo.getFile());
		} catch (IOException e) {
			return;
		}

		double ratio = Math.min((double) THUMB_SIZE / image.getWidth(), (double) THUMB_SIZE / image.getHeight());
		int newWidth = (int) (ratio * image.getWidth());
		int newHeight = (int) (ratio * image.getHeight());

		ImageIcon resizeImage = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));

		previewLabel.setIcon(resizeImage);

		memoText.setText(currentInfo.getMemo());
		tagText.setText(currentInfo.getTag().toString());
	}

	public void addRemoveListener(ImageInfoListListener listener) {
		deleteListeners.add(listener);
	}
}
