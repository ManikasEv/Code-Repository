package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleDelete {
    public static void deleteCollection(int userId, String collectionName) {
        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        String sql = "DELETE FROM collections WHERE userid = ? AND name = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, collectionName);

            pstmt.executeUpdate();

            System.out.println("Collection deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
