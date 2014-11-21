package sp.gui;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.*;

import sp.imageinfo.ImageInfo;

/**
 * @see http://stackoverflow.com/questions/7620579
 * @see http://stackoverflow.com/questions/4176343
 */
public class JGridList extends JPanel {

    private static final int N = 5;
    private DefaultListModel<ImageInfo> dlm = new DefaultListModel<ImageInfo>();
    private JList<ImageInfo> list = new JList<ImageInfo>(dlm);

    public JGridList() {
        super(new GridLayout());
        for (int i = 0; i < N * N; i++) {
            //String name = "Cell:" + String.format("%02d", i);
            //dlm.addElement(name);   
        	
        	try {
				dlm.addElement(new ImageInfo(new File("data/" + i + ".jpg")));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(N);
        list.setCellRenderer(new ListRenderer());
        list.addListSelectionListener(new SelectionHandler());
        this.add(list);
    }

    private class ListRenderer extends DefaultListCellRenderer {

        public ListRenderer() {
            this.setBorder(BorderFactory.createLineBorder(Color.red));
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        	JLabel label =  (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        	ImageInfo info = (ImageInfo) value;        	
        	
    	    Image image = new ImageIcon(info.getFile().getAbsolutePath()).getImage().getScaledInstance(50, 50, 1);
        	Icon icon = new ImageIcon(image);
        	
        	label.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));
    	    label.setIcon(icon);
    	    label.setHorizontalTextPosition(JLabel.CENTER);
    	    label.setVerticalTextPosition(JLabel.BOTTOM);
    	    
    	    return label;
        }
    }

    private class SelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                System.out.println(Arrays.toString(list.getSelectedIndices()));
            }
        }
    }
}