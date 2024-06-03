package graphicModals;

import javax.swing.*;
import java.awt.*;

public class ExcelModalFrame extends JFrame {

    public ExcelModalFrame(int height, int width, String title) {
        super(title);
        setSize(width, height);
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void addComponent(Component component, Object constraints) {
        getContentPane().add(component, constraints);
        getContentPane().revalidate();
    }
}
