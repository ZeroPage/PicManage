package sp.gui;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @see http://stackoverflow.com/questions/7620579
 * @see http://stackoverflow.com/questions/4176343
 */
public class JGridList extends JPanel {

    private static final int N = 5;
    private DefaultListModel dlm = new DefaultListModel();
    private JList list = new JList(dlm);

    public JGridList() {
        super(new GridLayout());
        for (int i = 0; i < N * N; i++) {
            String name = "Cell:" + String.format("%02d", i);
            dlm.addElement(name);            
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
        public Component getListCellRendererComponent(JList list, Object
            value, int index, boolean isSelected, boolean cellHasFocus) {
            JComponent jc =  (JComponent) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
            jc.setBorder(BorderFactory.createEmptyBorder(N, N, N, N));
            return jc;
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

    private void display() {
        JFrame f = new JFrame("ListPanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}