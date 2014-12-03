package sp.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.*;

import sp.imageinfo.ImageInfo;
import sp.imagemanager.ImageManager;

public class PicManageGUI extends JFrame {
	private JPanel leftPanel;
	private JComboBox<String> comboBox;
	private JGridList list;

	private JSelectPanel rightPanel;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem addMenuItem;
	private JMenuItem loadMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;

	private JFileChooser imageChooser;
	private JFileChooser fileChooser;

	private ImageManager manager;
	public PicManageGUI(ImageManager manager) {
		this.manager = manager;

		setTitle("TEST");
		setSize(600, 600);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(600, 300));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		initChooser();
		initMenu();
		initRight();
		initLeft();
	}

	private void initChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		imageChooser = new JFileChooser();
		imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageChooser.setMultiSelectionEnabled(true);

	}

	private void initMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu("MENU");

		addMenuItem = new JMenuItem("Add...");
		loadMenuItem = new JMenuItem("Load...");
		saveMenuItem = new JMenuItem("Save...");
		exitMenuItem = new JMenuItem("Exit");

		addMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imageChooser.showOpenDialog(PicManageGUI.this);
				File[] selectedFiles = imageChooser.getSelectedFiles();

				for (File file : selectedFiles) {
					manager.addImage(file);
				}

				list.setItems(manager.getImageList());

			}
		});

		loadMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				fileChooser.showOpenDialog(PicManageGUI.this);
				manager.loadImageList(fileChooser.getSelectedFile());
				list.setItems(manager.getImageList());
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
				fileChooser.showOpenDialog(PicManageGUI.this);
				manager.saveImageList(fileChooser.getSelectedFile());
			}
		});

		menu.add(addMenuItem);
		menu.add(loadMenuItem);
		menu.add(saveMenuItem);
		menu.add(exitMenuItem);

		menuBar.add(menu);
		add(menuBar, BorderLayout.NORTH);
	}

	private void initRight() {
		rightPanel = new JSelectPanel();
		add(rightPanel, BorderLayout.EAST);

	}

	private void initLeft() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.CENTER);

		comboBox = new JComboBox<>(new String[] {"sort(not implemented)", "ssoorrtt"});
		leftPanel.add(comboBox, BorderLayout.NORTH);

		list = new JGridList();
		list.addSelectListener(new SelectListener() {
			@Override
			public void onChange(List<ImageInfo> list) {
				rightPanel.update(list);
			}
		});

		list.addClickListener(new ClickListener() {
			@Override
			public void onClick(ImageInfo info) {
				System.out.println(info);
			}
		});

		list.setItems(manager.getImageList());
		leftPanel.add(list, BorderLayout.CENTER);
	}
}
