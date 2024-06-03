package connection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class prepareContentOfJson {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/officed1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "MAVERIK27";

    public static String getFileContent(int userId, String collectionName, String fileName) {
        String content = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT datacollection FROM collections WHERE userid = ? AND name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, collectionName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String jsonData = rs.getString("datacollection");

                        JSONObject jsonObject = new JSONObject(jsonData);

                        JSONArray filesArray = jsonObject.getJSONArray("files");

                        for (int i = 0; i < filesArray.length(); i++) {
                            JSONObject fileObj = filesArray.getJSONObject(i);
                            String fName = fileObj.getString("filename");
                            if (fName.equals(fileName)) {
                                content = fileObj.getString("filecontent");
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return content;
    }
}
