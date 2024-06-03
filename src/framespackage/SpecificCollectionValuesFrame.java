package framespackage;

import connection.*;
import graphicModals.Colors;
import graphicModals.JFrameModal;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class SpecificCollectionValuesFrame {
    private JFrameModal specificFrame;
    private JPanel fileButtonPanel;

    public SpecificCollectionValuesFrame(int userId, String collectionName) {
        specificFrame = new JFrameModal(400, 600, "Specific Collection Values");

        JButton newFileButton = new JButton("New File");
        JButton deleteCollectionButton = new JButton("Delete Collection");

        newFileButton.addActionListener(e -> handleNewFile(userId, collectionName));
        deleteCollectionButton.addActionListener(e -> handleDelete(userId, collectionName));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(newFileButton);
        buttonPanel.add(deleteCollectionButton);

        specificFrame.addComponent(buttonPanel, BorderLayout.SOUTH);

        fileButtonPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        List<String> fileNames = PrepareJsonData.getFileNames(userId, collectionName);
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (fileName != null) {
                    addButton(fileName, userId, collectionName);
                } else {
                    System.out.println("Null filename found.");
                }
            }
        } else {
            System.out.println("No file names retrieved.");
        }

        specificFrame.addComponent(fileButtonPanel, BorderLayout.CENTER);

        specificFrame.setVisible(true);
    }

    private void addButton(String fileName, int userId, String collectionName) {
        JButton button = new JButton(fileName);

        button.setPreferredSize(new Dimension(100, 50));

        button.setBorder(BorderFactory.createLineBorder(Colors.MAIN_THEME));
        button.setBackground(Colors.SUB_THEME_1);
        button.setForeground(Colors.MAIN_THEME);

        button.addActionListener(e -> {
            String content = prepareContentOfJson.getFileContent(userId, collectionName, fileName);
            DisplayContentOfTheFileFrame.displayContent(content);
            specificFrame.dispose();
        });

        fileButtonPanel.add(button);
    }

    private void handleNewFile(int userId, String collectionName) {

        JDialog fileNameDialog = new JDialog(specificFrame, "Enter File Name", true);
        fileNameDialog.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Enter file name:");
        JTextField nameField = new JTextField(20);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {

            String fileName = nameField.getText();

            String filePath = FileChoserFunction.chooseFile();

            String fileContent = readFileContent(filePath);

            HandleSpecificCollection.saveFileContent(userId, collectionName, fileName, fileContent);

            fileNameDialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(okButton);

        fileNameDialog.add(panel, BorderLayout.CENTER);

        fileNameDialog.setSize(300, 150);
        fileNameDialog.setLocationRelativeTo(specificFrame);
        fileNameDialog.setVisible(true);
    }

    private String readFileContent(String filePath) { //διάβασμα του αρχείου για αποθήκευση
        String fileContent = null;

        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);

            if (filePath.toLowerCase().endsWith(".docx")) {
                XWPFDocument docx = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
                fileContent = extractor.getText();
                extractor.close();
                specificFrame.setVisible(false);
            } else if (filePath.toLowerCase().endsWith(".pdf")) {
                PDDocument pdfDoc = PDDocument.load(file);
                PDFTextStripper stripper = new PDFTextStripper();
                fileContent = stripper.getText(pdfDoc);
                pdfDoc.close();
                specificFrame.setVisible(false);
            }  else if (filePath.toLowerCase().endsWith(".xls") || filePath.toLowerCase().endsWith(".xlsx")) {
                Workbook workbook = WorkbookFactory.create(fis);
                StringBuilder sb = new StringBuilder();
                for (Sheet sheet : workbook) {
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    sb.append(cell.getStringCellValue()).append("\t");
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        sb.append(cell.getDateCellValue()).append("\t");
                                    } else {
                                        sb.append(cell.getNumericCellValue()).append("\t");
                                    }
                                    break;
                                case BOOLEAN:
                                    sb.append(cell.getBooleanCellValue()).append("\t");
                                    break;
                                case FORMULA:
                                    DataFormatter formatter = new DataFormatter();
                                    sb.append(formatter.formatCellValue(cell)).append("\t");
                                    break;
                                default:
                                    sb.append("\t");
                            }
                        }
                        sb.append("\n");
                    }
                }
                fileContent = sb.toString();
                specificFrame.setVisible(false);
                workbook.close();
            } else {
                fileContent = "Unsupported file type";
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    private void handleDelete(int userId, String collectionName) {
        HandleDelete.deleteCollection(userId, collectionName);
        specificFrame.dispose();
    }
}
