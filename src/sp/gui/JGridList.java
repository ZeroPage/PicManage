package sp.gui;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.*;

import sp.imageinfo.ImageInfo;

/**
 * @see http://stackoverflow.com/questions/7620579
 * @see http://stackoverflow.com/questions/4176343
 */
public class JGridList extends JScrollPane {
	// private final static int ITEM_NUMBER = 25;
	private int thumbSize = 100;
	private int paddingSize = 0;
	private DefaultListModel<ImageInfo> dlm = new DefaultListModel<>();
	private JList<ImageInfo> list = new JList<>(dlm);

	private Map<ImageInfo, Icon> thumbnails;

	public JGridList() {
		/*
		for (int i = 0; i < ITEM_NUMBER; i++) {
			try {
				dlm.addElement(new ImageInfo(new File("data/" + i + ".jpg")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		list.setCellRenderer(new ListRenderer());
		list.setVisibleRowCount(0);

		thumbnails = new HashMap<>();
		
		setViewportView(list);
	}

	public void addSelectListener(SelectListener listener) {
		list.addListSelectionListener(new JGridListSelectionListener(listener));
	}

	public void addClickListener(ClickListener listener) {
		list.addMouseListener(new JGridListMouseListener(listener));
	}

	public void setItems(List<ImageInfo> infoList) {
		dlm.clear();
		thumbnails.clear();

		for (ImageInfo info : infoList) {
			dlm.addElement(info);

			Image image = new ImageIcon(info.getFile().getAbsolutePath()).getImage().getScaledInstance(thumbSize, thumbSize, 1);
			thumbnails.put(info, new ImageIcon(image));
		}


	}

	private class ListRenderer extends DefaultListCellRenderer {

		public ListRenderer() {
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label =  (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			Icon icon = thumbnails.get(value);

			label.setBorder(BorderFactory.createEmptyBorder(0, paddingSize, 0, paddingSize));
			label.setIcon(icon);
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			
			return label;
		}
	}

	private class JGridListSelectionListener implements ListSelectionListener {
		private SelectListener listener;
		
		public JGridListSelectionListener(SelectListener listener) {
			super();
			this.listener = listener;
		}
		
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				if (listener != null) {
					listener.onChange(list.getSelectedValuesList());
				}
			}
		}
	}

	private class JGridListMouseListener extends MouseAdapter {
		private ClickListener listener;

		public JGridListMouseListener(ClickListener listener) {
			super();
			this.listener = listener;
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2 && listener != null && list.getSelectedValuesList().size() == 1f) {
				listener.onClick(list.getSelectedValue());
			}
		}
	}
}