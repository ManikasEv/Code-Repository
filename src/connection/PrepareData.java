package connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrepareData {
    public static List<String> getCollections(int userId) {
        List<String> collections = new ArrayList<>();
        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        String sql = "SELECT name FROM collections WHERE userid = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                collections.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collections;
    }
}
