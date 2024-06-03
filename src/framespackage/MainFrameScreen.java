package framespackage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import connection.ExcelData;
import connection.FileChoserFunction;
import connection.LibraryItems;
import connection.PdfData; // Import PdfData class
import connection.WordData;
import graphicModals.Colors;
import graphicModals.FontFamilys;
import graphicModals.JFrameModalMainScreen;

public class MainFrameScreen {

    // ActionListener for Library button
    public static class LibraryButtonListener implements ActionListener {
        private int userId;

        public LibraryButtonListener(int userId) {
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<LibraryItems> libraryItems = LibraryItems.getLibraryItems(userId);
            new LibraryFrame(userId, libraryItems);
        }
    }

    // ActionListener for Choose File button
    static class ChooseFileButtonListener implements ActionListener {
        private int userId;

        public ChooseFileButtonListener(int userId) {
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedFilePath = FileChoserFunction.chooseFile();
            if (selectedFilePath != null) {
                JOptionPane.showMessageDialog(null, "Selected file: " + selectedFilePath);
                if (selectedFilePath.endsWith(".pdf")) {
                    String pdfData = PdfData.readPdfData(selectedFilePath); // Read PDF data
                    SwingUtilities.invokeLater(() -> new PdfFrame(userId, pdfData)); // Pass data to PdfFrame
                } else if (selectedFilePath.endsWith(".xls") || selectedFilePath.endsWith(".xlsx")) {
                    List<List<String>> excelData = ExcelData.readExcelData(selectedFilePath);
                    SwingUtilities.invokeLater(() -> new ExcelFrame(userId, excelData, selectedFilePath));
                } else if (selectedFilePath.endsWith(".doc") || selectedFilePath.endsWith(".docx")) {
                    String wordData = WordData.readWordData(selectedFilePath);
                    SwingUtilities.invokeLater(() -> new WordFrame(userId, wordData));
                } else {
                    JOptionPane.showMessageDialog(null, "Unsupported file type selected");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No file selected");
            }
        }
    }

    static class CollectionButtonListener implements ActionListener {
        private int userId;

        public CollectionButtonListener(int userId) {
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new CollectionFrame(userId);
        }
    }

    public static JFrameModalMainScreen CreateMainFrame(int userId) {
        JFrameModalMainScreen MainFrame = new JFrameModalMainScreen(300, 600, "Office Application");

        JPanel mainPanel = new JPanel(new GridBagLayout());

        mainPanel.setBackground(Colors.MAIN_THEME);

        JButton libraryButton = new JButton("Library");
        JButton newFile = new JButton("Choose File");
        JButton collectionButton = new JButton("Collection");
        libraryButton.setBackground(Colors.SUB_THEME_1);
        newFile.setBackground(Colors.SUB_THEME_1);
        collectionButton.setBackground(Colors.SUB_THEME_1);

        libraryButton.setFont(FontFamilys.BOLD);
        newFile.setFont(FontFamilys.BOLD);
        collectionButton.setFont(FontFamilys.BOLD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(libraryButton, gbc);

        gbc.gridx++;
        mainPanel.add(newFile, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(collectionButton, gbc);

        libraryButton.addActionListener(new LibraryButtonListener(userId));

        newFile.addActionListener(new ChooseFileButtonListener(userId));

        collectionButton.addActionListener(new CollectionButtonListener(userId));

        MainFrame.addComponent(mainPanel, BorderLayout.CENTER);
        return MainFrame;
    }
}
