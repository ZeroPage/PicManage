package sp.gui.detail;

import com.drew.metadata.Tag;
import sp.imageinfo.ImageInfo;
import sp.imagemanager.ImageManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Set;

public class JInfoPanel extends JPanel {
    private JScrollPane infoPanel;
    private JTable infoTable;
    private DefaultTableModel tableModel;

    private JPanel memoPanel;
    private JLabel memoTitle;
    private JTextArea memoContent;
    private JButton memoButton;

    private JPanel tagPanel;
    private JLabel tagTitle;
    private JTextArea tagContent;
    private JButton tagButton;

    private JTagEditDialog tagDialog;

    private ImageInfo currentInfo;

    public JInfoPanel(ImageManager manager) {
        tagDialog = new JTagEditDialog(manager);

        setPreferredSize(new Dimension(300, 300));
        setLayout(new BorderLayout());

        initTable();
        initMemo();
        initTag();
    }

    public void update(ImageInfo info) {
        currentInfo = info;

        int count;
        while ((count = tableModel.getRowCount()) != 0) {
            tableModel.removeRow(count - 1);
        }

        Collection<Tag> tags = currentInfo.getMeta();

        for (Tag tag : tags) {
            tableModel.addRow(new String[] {tag.getTagName(), tag.getDescription()});
        }

        Set<String> tagSet = currentInfo.getTag();


        memoContent.setText(currentInfo.getMemo());
        tagContent.setText(tagSet.toString());
    }

    private void initTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Key");
        tableModel.addColumn("Value");
        infoTable = new JTable(tableModel);
        infoTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        infoPanel = new JScrollPane(infoTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(infoPanel, BorderLayout.CENTER);
    }

    private void initMemo() {
        memoPanel = new JPanel();
        memoPanel.setLayout(new BorderLayout());

        memoTitle = new JLabel("Memo");
        memoPanel.add(memoTitle, BorderLayout.NORTH);

        memoContent = new JTextArea();
        memoContent.setEditable(false);
        memoPanel.add(memoContent, BorderLayout.CENTER);

        memoButton = new JButton("Edit");
        memoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Input Memo", currentInfo.getMemo());

                if (input != null) {
                    currentInfo.setMemo(input);
                }

                update(currentInfo);
            }
        });
        memoPanel.add(memoButton, BorderLayout.EAST);

        add(memoPanel, BorderLayout.NORTH);}

    private void initTag() {
        tagPanel = new JPanel();
        tagPanel.setLayout(new BorderLayout());

        tagTitle = new JLabel("Tag(s)");
        tagPanel.add(tagTitle, BorderLayout.NORTH);

        tagContent = new JTextArea();
        tagContent.setEditable(false);
        tagPanel.add(tagContent, BorderLayout.CENTER);

        tagButton = new JButton("Edit");
        tagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tagDialog.init(currentInfo);

                update(currentInfo);
            }
        });
        tagPanel.add(tagButton, BorderLayout.EAST);

        add(tagPanel, BorderLayout.SOUTH);
    }
}
