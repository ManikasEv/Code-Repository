package connection;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordData {

    public static String readWordData(String filePath) {
        String text = "";
        try {
            if (filePath.endsWith(".doc")) {
                FileInputStream fis = new FileInputStream(filePath);
                HWPFDocument document = new HWPFDocument(fis);
                WordExtractor extractor = new WordExtractor(document);
                text = extractor.getText();
                extractor.close();
                fis.close();
            } else if (filePath.endsWith(".docx")) {
                FileInputStream fis = new FileInputStream(filePath);
                XWPFDocument document = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                text = extractor.getText();
                extractor.close();
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
