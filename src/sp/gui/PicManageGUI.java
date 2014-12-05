package sp.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.drew.imaging.jpeg.JpegProcessingException;
import sp.gui.gridlist.ImageInfoListener;
import sp.gui.gridlist.JGridList;
import sp.gui.gridlist.ImageInfoListListener;
import sp.gui.selectpanel.JSelectPanel;
import sp.imageinfo.ImageInfo;
import sp.imagemanager.ImageManager;

public class PicManageGUI extends JFrame {
	public static final String FILE_SUFFIX = "pm";

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
	private List<ImageInfo> infoList;
	public PicManageGUI(ImageManager manager) {
		this.manager = manager;

		setTitle("PicManager");
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
		fileChooser.setFileFilter(new FileNameExtensionFilter("Picture Manage (*.pm)", FILE_SUFFIX));

		imageChooser = new JFileChooser();
		imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageChooser.setMultiSelectionEnabled(true);
		imageChooser.setFileFilter(new FileNameExtensionFilter("Image (*.jpg)", "JPG"));

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
					try {
						manager.addImage(file);
					} catch (JpegProcessingException | IOException e1) {
						JOptionPane.showMessageDialog(PicManageGUI.this, "Failed to open " + file.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}

				updateLeft();

			}
		});

		loadMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(PicManageGUI.this);


				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					try {
						manager.loadImageList(selectedFile);
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(PicManageGUI.this, "Deprecated *.pm file.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					} catch (IOException | JpegProcessingException e1) {
						JOptionPane.showMessageDialog(PicManageGUI.this, "Failed to load.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					updateLeft();
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showSaveDialog(PicManageGUI.this);


				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					if (!selectedFile.getAbsolutePath().endsWith("." + FILE_SUFFIX)) {
						selectedFile = new File(selectedFile.getAbsolutePath() + "." + FILE_SUFFIX);
					}

					try {
						manager.saveImageList(selectedFile);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(PicManageGUI.this, "Failed to save.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
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

		rightPanel.addRemoveButtonListener(new ImageInfoListListener() {
			@Override
			public void action(List<ImageInfo> infoList) {
				for (ImageInfo info : infoList) {
					manager.deleteImage(info);
				}

				updateLeft();
			}
		});

		add(rightPanel, BorderLayout.EAST);
	}

	private void initLeft() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.CENTER);

		comboBox = new JComboBox<>(new String[] {"sort(not implemented)", "ssoorrtt"});
		leftPanel.add(comboBox, BorderLayout.NORTH);

		list = new JGridList();
		list.addSelectListener(new ImageInfoListListener() {
			@Override
			public void action(List<ImageInfo> list) {
				updateRight(list);
			}
		});

		list.addClickListener(new ImageInfoListener() {
			@Override
			public void action(ImageInfo info) {
				System.out.println(info);

				new DetailViewGUI(PicManageGUI.this, infoList, infoList.indexOf(info)).setVisible(true);
			}
		});

		updateLeft();
		leftPanel.add(list, BorderLayout.CENTER);
	}

	private void updateLeft() {
		infoList = manager.getImageList();
		list.setItems(infoList);
	}

	private void updateRight(List<ImageInfo> list) {
		rightPanel.update(list);
	}
}
