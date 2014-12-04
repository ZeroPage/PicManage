package sp.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.drew.metadata.Tag;
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
		fileChooser.setFileFilter(new FileNameExtensionFilter("Picture Manage", FILE_SUFFIX));

		imageChooser = new JFileChooser();
		imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageChooser.setMultiSelectionEnabled(true);
		imageChooser.setFileFilter(new FileNameExtensionFilter("Image", "JPG"));

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
				fileChooser.showOpenDialog(PicManageGUI.this);

				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null) {
					manager.loadImageList(selectedFile);
					list.setItems(manager.getImageList());
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showSaveDialog(PicManageGUI.this);

				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null) {
					if (!selectedFile.getAbsolutePath().endsWith("." + FILE_SUFFIX)) {
						selectedFile = new File(selectedFile.getAbsolutePath() + "." + FILE_SUFFIX);
					}

					manager.saveImageList(selectedFile);
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

				list.setItems(manager.getImageList());
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
				rightPanel.update(list);
			}
		});

		list.addClickListener(new ImageInfoListener() {
			@Override
			public void action(ImageInfo info) {
				System.out.println(info);
				Collection<Tag> meta = info.getMeta();

				if (meta != null) {
					for (Tag t : meta) {
						System.out.println(t.getTagName() + ": " + t.getDescription());
					}
				}
			}
		});

		list.setItems(manager.getImageList());
		leftPanel.add(list, BorderLayout.CENTER);
	}
}
