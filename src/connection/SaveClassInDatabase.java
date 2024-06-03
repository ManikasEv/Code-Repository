package connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SaveClassInDatabase {

    public static void saveData(int userId, String name, String data) {
        // Get database credentials from the Credentials class
        Credentials credentials = new Credentials();
        String dbUrl = credentials.getUrl();
        String dbUser = credentials.getUsername();
        String dbPassword = credentials.getPassword();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            String sql = "INSERT INTO data (user_id, names, datavalues, date_added) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, data);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));

            System.out.println("Saving data to the database:");
            System.out.println("User ID: " + userId);
            System.out.println("Name: " + name);
            System.out.println("Data: " + data);
            System.out.println("Date Added: " + LocalDate.now());

            stmt.executeUpdate();
            System.out.println("Data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
