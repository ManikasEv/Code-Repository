package framespackage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class DisplayContentOfTheFileFrame {
    public static void displayContent(String content) {
        JFrame frame = new JFrame("File Content");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void displayImage(File imageFile) {
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            BufferedImage image = ImageIO.read(imageFile);
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
