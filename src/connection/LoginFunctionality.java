package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import framespackage.MainFrameScreen;
import graphicModals.JFrameModal;

public class LoginFunctionality {
    private JFrameModal loginFrame;

    public LoginFunctionality(JFrameModal loginFrame, String username, String password) {
        this.loginFrame = loginFrame;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Credentials credentials = new Credentials();
            String url = credentials.getUrl();
            String dbUsername = credentials.getUsername();
            String dbPassword = credentials.getPassword();

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT id FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                System.out.println("Login Successful");
                loginFrame.setVisible(false);
                MainFrameScreen.CreateMainFrame(userId);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginFrame, "Database connection or query failed.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
