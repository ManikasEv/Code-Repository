package connection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrepareJsonData {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/officed1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "MAVERIK27";

    public static List<String> getFileNames(int userId, String collectionName) {
        List<String> fileNames = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT datacollection FROM collections WHERE userid = ? AND name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, collectionName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String jsonData = rs.getString("datacollection");
                        if (jsonData != null && !jsonData.isEmpty()) {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            JSONArray filesArray = jsonObject.getJSONArray("files");

                            for (int i = 0; i < filesArray.length(); i++) {
                                JSONObject fileObj = filesArray.getJSONObject(i);
                                String fileName = fileObj.getString("filename");
                                if (fileName != null && !fileName.isEmpty()) {
                                    fileNames.add(fileName);
                                } else {
                                    System.out.println("Null or empty filename in JSON data.");
                                }
                            }
                        } else {
                            System.out.println("Empty JSON data retrieved.");
                        }
                    } else {
                        System.out.println("No data retrieved for given userId and collectionName.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileNames;
    }
}