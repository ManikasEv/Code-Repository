package framespackage;

import connection.SaveClassInDatabase;
import graphicModals.PdfModalFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PdfFrame {
    private String pdfData;

    public PdfFrame(int userId, String pdfData) {
        this.pdfData = pdfData;
        PdfModalFrame pdfFrame = new PdfModalFrame(600, 300, "Pdf Frame");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea(pdfData);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.setPreferredSize(new Dimension(600, 300));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton footerButton = new JButton("Save");

        footerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSmallWindowAndReturnText(pdfFrame, userId); // Pass userId here
            }
        });

        footerPanel.add(footerButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        pdfFrame.add(mainPanel);

        pdfFrame.pack();
    }

    private void openSmallWindowAndReturnText(JFrame parentFrame, int userId) {

        JFrame smallWindowFrame = new JFrame("Insert name with .pdf");

        JTextField textField = new JTextField(20);

        JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String text2 = textField.getText();

                String windowName = smallWindowFrame.getTitle();

                boolean isValid = text2.endsWith(".pdf");
                if (isValid) {

                    JOptionPane.showMessageDialog(smallWindowFrame, "Succesfully saved with name: " + text2);

                    SaveClassInDatabase.saveData(userId, text2, pdfData); // Pass both text2 and pdfData

                    smallWindowFrame.dispose();
                } else {

                    JOptionPane.showMessageDialog(smallWindowFrame, "Invalid file format. Please enter a .pdf file path.");
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
