package sp.gui.detail;

import sp.imageinfo.ImageInfo;
import sp.imagemanager.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class JTagEditDialog extends JDialog{
    private JScrollPane tagPane;
    private Box tagBox;
    private java.util.List<JCheckBox> checkBoxList;

    private JPanel buttonPanel;
    private JButton newButton;
    private JButton confirmButton;

    private ImageManager manager;
    private ImageInfo info;

    private Set<String> allTags;

    public JTagEditDialog(ImageManager manager) {
        this.manager = manager;
        checkBoxList = new ArrayList<>();

        setLayout(new BorderLayout());
        setSize(new Dimension(300, 300));
        setResizable(false);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        tagBox = Box.createVerticalBox();
        tagPane = new JScrollPane(tagBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(tagPane, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        newButton = new JButton("New tag...");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Input new tag");

                if (input != null && !allTags.contains(input)) {
                    allTags.add(input);

                    JCheckBox checkBox = new JCheckBox(input);
                    checkBox.setSelected(true);
                    checkBox.setPreferredSize(new Dimension(200, 20));

                    checkBoxList.add(checkBox);
                    tagBox.add(checkBox);

                    tagPane.updateUI();
                }
            }
        });
        buttonPanel.add(newButton, BorderLayout.NORTH);
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<String> selectedTags = new HashSet<>();

                for (JCheckBox checkBox : checkBoxList) {
                    if (checkBox.isSelected()) {
                        selectedTags.add(checkBox.getText());
                    }
                }

                info.setTag(selectedTags);
                JTagEditDialog.this.setVisible(false);
            }
        });
        buttonPanel.add(confirmButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void init(ImageInfo info) {
        this.info = info;

        tagBox.removeAll();
        checkBoxList.clear();

        Set<String> infoTags = info.getTag();
        allTags = manager.getTagSet();

        for (String tag : allTags) {
            JCheckBox checkBox = new JCheckBox(tag);

            if (infoTags.contains(tag)) {
                checkBox.setSelected(true);
            }

            checkBoxList.add(checkBox);
            tagBox.add(checkBox);
        }

        setVisible(true);
    }
}
