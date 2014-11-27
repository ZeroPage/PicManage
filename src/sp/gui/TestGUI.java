package sp.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sp.imageinfo.ImageInfo;

public class TestGUI extends JFrame {
	private JPanel leftPanel;
	private JComboBox<String> comboBox;
	private JGridList list;
	
	private JPanel rightPanel;
	private NoSelectPanel noSelectPanel;
	private OneSelectPanel oneSelectPanel;
	private MultiSelectPanel multiSelectPanel;
	
	public TestGUI() {
		setTitle("TEST");
		setSize(600, 600);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(600, 300));
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(300, 300));
		add(rightPanel, BorderLayout.EAST);
		
		noSelectPanel = new NoSelectPanel();
		oneSelectPanel = new OneSelectPanel();
		multiSelectPanel = new MultiSelectPanel();
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.CENTER);
		
		comboBox = new JComboBox<String>(new String[] {"sort(not implemented)", "ssoorrtt"});
		leftPanel.add(comboBox, BorderLayout.NORTH);
		
		list = new JGridList(new MySelectionListener());
		leftPanel.add(list, BorderLayout.CENTER);
			

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class MySelectionListener implements SelectionListener {
		@Override
		public void onChange(List<ImageInfo> list) {
			int size = list.size();
			
			rightPanel.removeAll();
			
			if (size == 0) {
				rightPanel.add(noSelectPanel);
			} else if (size == 1) {
				rightPanel.add(oneSelectPanel);
				oneSelectPanel.update(list);
			} else {
				rightPanel.add(multiSelectPanel);
				multiSelectPanel.update(list);
			}
			
			rightPanel.updateUI();
		}
	}
}
