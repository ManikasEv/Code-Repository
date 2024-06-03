package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegsterToDatabase {
    public static void registerUser(String username, String password, String telephone, String address, String email) {
        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String dbUsername = credentials.getUsername();
        String dbPassword = credentials.getPassword();

        String query = "INSERT INTO users (username, password, telephone, address, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, telephone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);

            preparedStatement.executeUpdate();
            System.out.println("User registered successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection or query failed.");
        }
    }
}
