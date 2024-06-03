package framespackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import connection.LibraryItems;

public class LibraryFrame extends JFrame {

    public LibraryFrame(int userId, List<LibraryItems> libraryItems) {
        setTitle("Library Items");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Name", "Date Added"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (LibraryItems item : libraryItems) {
            Object[] row = {item.getName(), item.getDateAdded()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    String fileName = table.getValueAt(row, 0).toString();
                    handleRowClick(userId, fileName, libraryItems.get(row).getDatavalues());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);

        setVisible(true);
    }

    private void handleRowClick(int userId, String fileName, byte[] fileData) {
        System.out.println("Attempting to open file: " + fileName);
        if (fileName.endsWith(".pdf")) {
            new PdfFrame(userId, new String(fileData));
        } else if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
            new WordFrame(userId, new String(fileData));
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
        } else {
            JOptionPane.showMessageDialog(this, "Unsupported file type: " + fileName);
        }
    }
}
