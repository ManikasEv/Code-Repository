package framespackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connection.LoginFunctionality;
import graphicModals.Colors;
import graphicModals.FontFamilys;
import graphicModals.JFrameModal;

public class LoginFrame {
    private static class ButtonActionListener implements ActionListener {
        private JTextField usernameTextField;
        private JPasswordField passwordField;
        private JFrameModal loginFrame;

        public ButtonActionListener(JTextField usernameTextField, JPasswordField passwordField, JFrameModal loginFrame) {
            this.usernameTextField = usernameTextField;
            this.passwordField = passwordField;
            this.loginFrame = loginFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("Login")) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                System.out.println(username +"\n"+ password);
                new LoginFunctionality(loginFrame, username, password);
            } else if (action.equals("Register")) {
                new RegisterFrame();
            }
        }
    }

    public static JFrameModal CreateWindow() {
        JFrameModal loginFrame = new JFrameModal(300, 600, "Office Application");

        JPanel mainPanelData = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();

        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTextField = new JTextField(15);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 5, 0));
        usernameLabel.setForeground(Colors.SUB_THEME_3);
        usernameLabel.setFont(FontFamilys.BOLD);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        usernamePanel.setBackground(Colors.MAIN_THEME);

        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setBackground(Colors.MAIN_THEME);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 5, 0));
        passwordLabel.setForeground(Colors.SUB_THEME_3);
        passwordLabel.setFont(FontFamilys.BOLD);
        passwordField.setEchoChar('*');
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        mainPanelData.add(usernamePanel);
        mainPanelData.add(passwordPanel);
        mainPanelData.setBackground(Colors.MAIN_THEME);
        loginFrame.addComponent(mainPanelData, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.setBackground(Colors.SUB_THEME_1);
        registerButton.setBackground(Colors.SUB_THEME_1);
        loginButton.setFont(FontFamilys.BOLD);
        registerButton.setFont(FontFamilys.BOLD);

        mainPanelData.add(loginButton);
        mainPanelData.add(registerButton);

        mainPanelData.setLayout(null);

        registerButton.setBounds(150, 125, Math.max(registerButton.getPreferredSize().width, loginButton.getPreferredSize().width), registerButton.getPreferredSize().height);

        loginButton.setBounds(325, 125, Math.max(registerButton.getPreferredSize().width, loginButton.getPreferredSize().width), loginButton.getPreferredSize().height);

        ButtonActionListener buttonActionListener = new ButtonActionListener(usernameTextField, passwordField, loginFrame);
        loginButton.addActionListener(buttonActionListener);
        registerButton.addActionListener(buttonActionListener);

        return loginFrame;
    }
}
