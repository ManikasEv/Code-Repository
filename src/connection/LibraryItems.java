package connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryItems {

    private String name;
    private Date dateAdded;
    private byte[] datavalues;

    public LibraryItems(String name, Date dateAdded, byte[] datavalues) {
        this.name = name;
        this.dateAdded = dateAdded;
        this.datavalues = datavalues;
    }

    public String getName() {
        return name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public byte[] getDatavalues() {
        return datavalues;
    }

    public static List<LibraryItems> getLibraryItems(int userId) {
        List<LibraryItems> libraryItems = new ArrayList<>();

        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        String query = "SELECT names, date_added, datavalues FROM Data WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String name = rs.getString("names");
                    Date dateAdded = rs.getDate("date_added");
                    byte[] datavalues = rs.getBytes("datavalues");

                    libraryItems.add(new LibraryItems(name, dateAdded, datavalues));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libraryItems;
    }
}
