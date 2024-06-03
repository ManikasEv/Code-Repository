package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class HandleNewCollection {
    public HandleNewCollection(int userId, String collectionName) {
        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        String sql = "INSERT INTO collections (userid, name, date_added) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, collectionName);
            pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Set current local date

            pstmt.executeUpdate();

            System.out.println("New collection created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
