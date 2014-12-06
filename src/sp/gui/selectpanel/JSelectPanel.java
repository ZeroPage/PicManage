package sp.gui.selectpanel;

import sp.gui.gridlist.ImageInfoListListener;
import sp.imageinfo.ImageInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JSelectPanel extends JPanel {

    private JNoSelectPanel JNoSelectPanel;
    private JOneSelectPanel JOneSelectPanel;
    private JMultiSelectPanel JMultiSelectPanel;

    public JSelectPanel() {
        setPreferredSize(new Dimension(300, 300));
        setLayout(new BorderLayout());

        JNoSelectPanel = new JNoSelectPanel();
        JOneSelectPanel = new JOneSelectPanel();
        JMultiSelectPanel = new JMultiSelectPanel();

        add(JNoSelectPanel, BorderLayout.CENTER);
    }

    public void addRemoveButtonListener(ImageInfoListListener listener) {
        JMultiSelectPanel.addRemoveListener(listener);
    }

    public void update(List<ImageInfo> list) {
        int size = list.size();

        removeAll();

        if (size == 0) {
            add(JNoSelectPanel, BorderLayout.CENTER);
        } else if (size == 1) {
            add(JOneSelectPanel, BorderLayout.CENTER);
            JOneSelectPanel.update(list);
        } else {
            add(JMultiSelectPanel, BorderLayout.CENTER);
            JMultiSelectPanel.update(list);
        }

        updateUI();
    }
}
