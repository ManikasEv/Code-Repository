package framespackage;

import javax.swing.*;

public class NameOfTheFile {
    public static void createSmallWindow() {
        // Create the frame
        JFrame frame = new JFrame("Small Window");

        JTextField textField = new JTextField(20);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String text = textField.getText();
            JOptionPane.showMessageDialog(frame, "Text entered: " + text);
        });

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(okButton);

        frame.add(panel);

        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
