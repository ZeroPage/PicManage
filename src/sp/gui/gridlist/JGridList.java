package sp.gui.gridlist;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import sp.imageinfo.ImageInfo;

/**
 * http://stackoverflow.com/questions/7620579
 * http://stackoverflow.com/questions/4176343
 */
public class JGridList extends JScrollPane {
	// private final static int ITEM_NUMBER = 25;
	private int thumbSize = 200;
	private int paddingSize = 20;
	private DefaultListModel<ImageInfo> dlm = new DefaultListModel<>();
	private JList<ImageInfo> list = new JList<>(dlm);

	private JGridListClickListener clickListener;
	private JGridListSelectionListener selectionListener;

	private Map<ImageInfo, Icon> thumbnails;

	public JGridList() {
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		list.setCellRenderer(new ListRenderer());
		list.setVisibleRowCount(0);

		clickListener = new JGridListClickListener();
		selectionListener = new JGridListSelectionListener();

		list.addListSelectionListener(selectionListener);
		list.addMouseListener(clickListener);

		thumbnails = new HashMap<>();
		
		setViewportView(list);
	}

	public void addSelectListener(ImageInfoListListener listener) {
		selectionListener.addImageInfoListListener(listener);
	}

	public void addClickListener(ImageInfoListener listener) {
		clickListener.addImageInfoListener(listener);
	}

	public void setItems(List<ImageInfo> infoList) {
		dlm.clear();
		thumbnails.clear();

		for (ImageInfo info : infoList) {
			dlm.addElement(info);

			BufferedImage image;
			try {
				image = ImageIO.read(info.getFile());
			} catch (IOException e) {
				continue;
			}

			double ratio = Math.min((double) thumbSize / image.getWidth(), (double) thumbSize / image.getHeight());
			int newWidth = (int) (ratio * image.getWidth());
			int newHeight = (int) (ratio * image.getHeight());

			ImageIcon resizeImage = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST));
			thumbnails.put(info, resizeImage);
		}


	}

	private class ListRenderer extends DefaultListCellRenderer {

		public ListRenderer() {
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label =  (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			Icon icon = thumbnails.getOrDefault(value, new ImageIcon());

			label.setBorder(BorderFactory.createEmptyBorder(paddingSize, paddingSize, paddingSize, paddingSize));
			label.setIcon(icon);

			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setPreferredSize(new Dimension(thumbSize + paddingSize * 2, thumbSize + paddingSize));
			
			return label;
		}
	}

	private class JGridListSelectionListener implements ListSelectionListener {
		private List<ImageInfoListListener> listeners;
		
		public JGridListSelectionListener() {
			super();
			listeners = new ArrayList<>();
		}
		
		public void valueChanged(ListSelectionEvent e) {
			for (ImageInfoListListener listener : listeners) {
				if (!e.getValueIsAdjusting()) {
					listener.action(list.getSelectedValuesList());
				}
			}
		}

		public void addImageInfoListListener(ImageInfoListListener listener) {
			listeners.add(listener);
		}
	}

	private class JGridListClickListener extends MouseAdapter {
		private List<ImageInfoListener> listeners;

		public JGridListClickListener() {
			super();
			listeners = new ArrayList<>();
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			for (ImageInfoListener listener : listeners) {
				if (event.getClickCount() == 2 && list.getSelectedValuesList().size() == 1) {
					listener.action(list.getSelectedValue());
				}
			}
		}

		public void addImageInfoListener(ImageInfoListener listener) {
			listeners.add(listener);
		}
	}
}