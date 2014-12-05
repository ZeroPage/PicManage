package sp.gui.selectpanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import sp.gui.gridlist.ImageInfoListListener;
import sp.imageinfo.ImageInfo;

public class JMultiSelectPanel extends JPanel {
	private JLabel label;
	private JButton removeButton;

	private List<ImageInfo> selected;

	private MultiSelectPanelRemoveListener removeListener;
	
	public JMultiSelectPanel() {
		super();
		setLayout(new BorderLayout());
		
		label = new JLabel();
		removeButton = new JButton("Remove");

		removeListener = new MultiSelectPanelRemoveListener();
		removeButton.addActionListener(removeListener);

		add(label, BorderLayout.CENTER);
		add(removeButton, BorderLayout.SOUTH);
	}

	public void addRemoveListener(ImageInfoListListener listener) {
		removeListener.addImageInfoListListener(listener);

	}

	public void update(List<ImageInfo> list) {
		selected = list;

		label.setText(selected.size() + " items selected");
	}

	private class MultiSelectPanelRemoveListener implements ActionListener {
		private List<ImageInfoListListener> listeners;

		public MultiSelectPanelRemoveListener() {
			super();
			listeners = new ArrayList<>();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (ImageInfoListListener listener : listeners) {
				listener.action(selected);
			}
		}

		public void addImageInfoListListener(ImageInfoListListener listener) {
			listeners.add(listener);
		}
	}
}
