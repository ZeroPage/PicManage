package sp.gui.selectpanel;

import sp.gui.gridlist.ImageInfoListListener;
import sp.imageinfo.ImageInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JSelectPanel extends JPanel {

    private NoSelectPanel noSelectPanel;
    private OneSelectPanel oneSelectPanel;
    private MultiSelectPanel multiSelectPanel;

    public JSelectPanel() {
        setPreferredSize(new Dimension(300, 300));

        noSelectPanel = new NoSelectPanel();
        oneSelectPanel = new OneSelectPanel();
        multiSelectPanel = new MultiSelectPanel();

        add(noSelectPanel);
    }

    public void addRemoveButtonListener(ImageInfoListListener listener) {
        multiSelectPanel.addRemoveListener(listener);
    }

    public void update(List<ImageInfo> list) {
        int size = list.size();

        removeAll();

        if (size == 0) {
            add(noSelectPanel);
        } else if (size == 1) {
            add(oneSelectPanel);
            oneSelectPanel.update(list);
        } else {
            add(multiSelectPanel);
            multiSelectPanel.update(list);
        }

        updateUI();
    }
}
