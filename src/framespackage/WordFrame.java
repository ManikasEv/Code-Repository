package framespackage;

import connection.SaveClassInDatabase;
import graphicModals.WordModalFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordFrame {
    private String wordData;
    public WordFrame(int userId, String wordData) {
        this.wordData = wordData;
        WordModalFrame wordFrame = new WordModalFrame(1200, 820, "Word Frame");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton footerButton = new JButton("Save");

        footerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSmallWindowAndReturnText(wordFrame, userId); // Pass userId here
            }
        });

        footerPanel.add(footerButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        JTextArea textArea = new JTextArea();
        textArea.setText(wordData);

        JScrollPane scrollPane = new JScrollPane(textArea);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        wordFrame.addComponent(mainPanel, BorderLayout.CENTER);

        wordFrame.pack();
    }

    private void openSmallWindowAndReturnText(JFrame parentFrame, int userId) {
        JFrame smallWindowFrame = new JFrame("Small Window");
        JTextField textField = new JTextField(20);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text2 = textField.getText();
                String windowName = smallWindowFrame.getTitle();
                boolean isValid = text2.endsWith(".doc") || text2.endsWith(".docx");
                if (isValid) {

                    JOptionPane.showMessageDialog(smallWindowFrame, "Succesfully saved with name: " + text2);

                    SaveClassInDatabase.saveData(userId, text2, wordData); // Pass both text2 and wordData

                    smallWindowFrame.dispose();
                } else {

                    JOptionPane.showMessageDialog(smallWindowFrame, "Invalid file format. Please enter a .doc or .docx file path.");
                }
            }
        });


        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(okButton);

        smallWindowFrame.add(panel);

        smallWindowFrame.setSize(300, 100);
        smallWindowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        smallWindowFrame.setLocationRelativeTo(parentFrame);
        smallWindowFrame.setVisible(true);
    }
}