package graphicModals;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class JFrameModal extends JFrame {

    public JFrameModal(int height, int width, String title) {
        super(title);
        setSize(width, height);
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addComponent(Component component, Object constraints) {
        getContentPane().add(component, constraints);
        getContentPane().revalidate();
    }
}
