package sp.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class TestGUI extends JFrame {
	public TestGUI() {
		setTitle("TEST");
		setSize(600, 600);
		setResizable(false);
		setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		leftPanel.setSize(300, 300);
		leftPanel.setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.WEST);
		
		JComboBox<String> comboBox = new JComboBox<String>(new String[] {"a", "b"});
		comboBox.setPreferredSize(new Dimension(300, 50));
		leftPanel.add(comboBox, BorderLayout.NORTH);
		
		JGridList list = new JGridList();
		leftPanel.add(list, BorderLayout.CENTER);
		
		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		add(rightPanel, BorderLayout.EAST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
