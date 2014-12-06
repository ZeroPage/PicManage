package sp.gui.gridlist;

import sp.imagemanager.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class JTagSelectDialog extends JDialog {
    private JScrollPane tagPane;
    private Box tagBox;
    private java.util.List<JCheckBox> checkBoxList;

    private JPanel buttonPanel;
    private JButton confirmButton;

    private ImageManager manager;
    ArrayList<String> result;

    public JTagSelectDialog(ImageManager manager) {
        this.manager = manager;
        checkBoxList = new ArrayList<>();
        result = new ArrayList<>();

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

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = new ArrayList<>();

                for (JCheckBox checkBox : checkBoxList) {
                    if (checkBox.isSelected()) {
                        result.add(checkBox.getText());
                    }
                }

                JTagSelectDialog.this.setVisible(false);
            }
        });
        buttonPanel.add(confirmButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void init() {
        tagBox.removeAll();
        checkBoxList.clear();

        Set<String> allTags = manager.getTagSet();

        for (String tag : allTags) {
            JCheckBox checkBox = new JCheckBox(tag);

            checkBoxList.add(checkBox);
            tagBox.add(checkBox);
        }

        setVisible(true);
    }

    public ArrayList<String> getResult() {
        return result;
    }
}
