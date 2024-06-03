package framespackage;

import connection.SaveClassInDatabase;
import graphicModals.ExcelModalFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ExcelFrame {
    private List<List<String>> excelData;
    public ExcelFrame(int userId, List<List<String>> excelData,String selectedFilePath) {
        this.excelData = excelData;
        ExcelModalFrame excelFrame = new ExcelModalFrame(600, 300, "Excel Frame");

        DefaultTableModel tableModel = new DefaultTableModel();
        if (!excelData.isEmpty()) {
            List<String> header = excelData.get(0);
            for (String column : header) {
                tableModel.addColumn(column);
            }
            for (int i = 1; i < excelData.size(); i++) {
                tableModel.addRow(excelData.get(i).toArray());
            }
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        excelFrame.setLayout(new BorderLayout());
        excelFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton footerButton = new JButton("Save");

        footerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSmallWindowAndReturnText(excelFrame, userId); // Pass userId here
            }
        });

        footerPanel.add(footerButton);

        excelFrame.add(footerPanel, BorderLayout.SOUTH);

        excelFrame.setVisible(true);
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
                boolean isValid = text2.endsWith(".xls") || text2.endsWith(".xlsx");
                if (isValid) {
                    JOptionPane.showMessageDialog(smallWindowFrame, "Succesfully saved with name: " + text2);
                    SaveClassInDatabase.saveData(userId, text2, excelData.toString()); // Pass both text2 and excelData
                    smallWindowFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(smallWindowFrame, "Invalid file format. Please enter a .xls or .xlsx file path.");
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
