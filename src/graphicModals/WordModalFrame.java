package graphicModals;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class WordModalFrame extends JFrame{
    public WordModalFrame(int height, int width, String title) {
        super(title);
        setSize(width, height);
        setLayout(new BorderLayout());
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void addComponent(Component component, Object constraints) {
        getContentPane().add(component, constraints);
        getContentPane().revalidate();
    }
}
