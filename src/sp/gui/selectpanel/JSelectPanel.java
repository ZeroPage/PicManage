package sp.gui.selectpanel;

import sp.gui.gridlist.ImageInfoListListener;
import sp.imageinfo.ImageInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JSelectPanel extends JPanel {

    private JNoSelectPanel noSelectPanel;
    private JOneSelectPanel oneSelectPanel;
    private JMultiSelectPanel multiSelectPanel;

    public JSelectPanel() {
        setPreferredSize(new Dimension(300, 300));
        setLayout(new BorderLayout());

        noSelectPanel = new JNoSelectPanel();
        oneSelectPanel = new JOneSelectPanel();
        multiSelectPanel = new JMultiSelectPanel();

        add(noSelectPanel, BorderLayout.CENTER);
    }

    public void addRemoveButtonListener(ImageInfoListListener listener) {
        multiSelectPanel.addRemoveListener(listener);
        oneSelectPanel.addRemoveListener(listener);
    }

    public void update(List<ImageInfo> list) {
        int size = list.size();

        removeAll();

        if (size == 0) {
            add(noSelectPanel, BorderLayout.CENTER);
        } else if (size == 1) {
            add(oneSelectPanel, BorderLayout.CENTER);
            oneSelectPanel.update(list);
        } else {
            add(multiSelectPanel, BorderLayout.CENTER);
            multiSelectPanel.update(list);
        }

        updateUI();
    }
}
