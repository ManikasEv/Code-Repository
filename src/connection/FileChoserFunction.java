package connection;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileChoserFunction {

    public static String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new CustomFileFilter());

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    static class CustomFileFilter extends FileFilter {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String fileName = file.getName().toLowerCase();
            return fileName.endsWith(".pdf") ||
                    fileName.endsWith(".doc") ||
                    fileName.endsWith(".docx") ||
                    fileName.endsWith(".xls") ||
                    fileName.endsWith(".xlsx");
        }

        @Override
        public String getDescription() {
            return "PDF, Word, and Excel files (*.pdf, *.doc, *.docx, *.xls, *.xlsx)";
        }
    }
}
