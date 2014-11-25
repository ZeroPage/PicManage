package sp.gui;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.*;

import sp.imageinfo.ImageInfo;

/**
 * @see http://stackoverflow.com/questions/7620579
 * @see http://stackoverflow.com/questions/4176343
 */
public class JGridList extends JScrollPane {
	private final static int ITEM_NUMBER = 25;
	private int thumbSize = 100;
	private int paddingSize = 0;
	private DefaultListModel<ImageInfo> dlm = new DefaultListModel<ImageInfo>();
	private JList<ImageInfo> list = new JList<ImageInfo>(dlm);

	public JGridList(SelectionListener listener) {
		for (int i = 0; i < ITEM_NUMBER; i++) {
			try {
				dlm.addElement(new ImageInfo(new File("data/" + i + ".jpg")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		list.setCellRenderer(new ListRenderer());
		list.addListSelectionListener(new JGridListSelectionListener(listener));
		list.setVisibleRowCount(0);
		
		setViewportView(list);
	}

	private class ListRenderer extends DefaultListCellRenderer {

		public ListRenderer() {
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label =  (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			ImageInfo info = (ImageInfo) value;			
			
			Image image = new ImageIcon(info.getFile().getAbsolutePath()).getImage().getScaledInstance(thumbSize, thumbSize, 1);
			Icon icon = new ImageIcon(image);
			
			label.setBorder(BorderFactory.createEmptyBorder(0, paddingSize, 0, paddingSize));
			label.setIcon(icon);
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			
			return label;
		}
	}

	private class JGridListSelectionListener implements ListSelectionListener {
		private SelectionListener listener;
		
		public JGridListSelectionListener(SelectionListener listener) {
			super();
			this.listener = listener;
		}
		
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				listener.onChange(list.getSelectedValuesList());
			}
		}
	}
}