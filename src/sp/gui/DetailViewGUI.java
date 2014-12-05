package sp.gui;

import sp.imageinfo.ImageInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class DetailViewGUI extends JDialog {
    private JLabel imageLabel;

    private JPanel buttonsPanel;
    private JButton leftButton;
    private JButton rightButton;

    private JPanel detailPanel;

    private List<ImageInfo> infoList;
    private int currentIndex;
    private BufferedImage image;
    private boolean resized;

    public DetailViewGUI(JFrame parent, List<ImageInfo> list, int index) {
        super(parent);

        infoList = list;
        currentIndex = index;
        resized = true;

        setTitle("Details");
        setSize(800, 600);
        setModal(true);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(600, 300));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);
        add(imageLabel, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        leftButton = new JButton("<");
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(currentIndex - 1);
            }
        });
        rightButton = new JButton(">");
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(currentIndex + 1);
            }
        });
        buttonsPanel.add(leftButton);
        buttonsPanel.add(rightButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        detailPanel = new JPanel();
        detailPanel.setPreferredSize(new Dimension(300, 300));
        add(detailPanel, BorderLayout.EAST);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                resized = true;

            }
        });


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (resized) {
                    updateImage();
                    resized = false;
                }
            }
        });

        selectImage(index);
    }

    private void selectImage(int selectIndex) {
        selectIndex = selectIndex % infoList.size();

        try {
            image = ImageIO.read(infoList.get(selectIndex).getFile());
        } catch (IOException e) {
            return;
        }

        updateImage();

        currentIndex = selectIndex;
    }

    private void updateImage() {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        int labelWidth = imageLabel.getWidth();
        int labelHeight = imageLabel.getHeight();


        //System.out.println(labelWidth);
        //System.out.println(labelHeight);

        if (labelWidth == 0 || labelHeight == 0) {
            labelWidth = 100;
            labelHeight = 100;
        }


        double widthRatio = (double) labelWidth / imageWidth;
        double heightRatio = (double) labelHeight / imageHeight;

        double finalRatio = Math.min(Math.min(widthRatio, heightRatio), 1.0);

        int finalWidth = (int) (imageWidth * finalRatio);
        int finalHeight = (int) (imageHeight * finalRatio);

        Image newImage = image.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(newImage));
    }
}
