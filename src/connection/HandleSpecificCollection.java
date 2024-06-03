package connection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandleSpecificCollection {

    public static void saveFileContent(int userId, String collectionName, String fileName, String fileContent) {
        // Retrieve existing JSON data from the database
        String existingJsonData = getExistingJsonData(userId, collectionName);

        // Create a new JSON object to store the file data
        JSONObject fileObj = new JSONObject();
        fileObj.put("filename", fileName);
        fileObj.put("filecontent", fileContent);

        // Merge the new file object with existing JSON data
        JSONObject mergedData = new JSONObject();
        if (existingJsonData != null && !existingJsonData.isEmpty()) {
            mergedData = new JSONObject(existingJsonData);
        }
        JSONArray fileList;
        if (mergedData.has("files")) {
            fileList = mergedData.getJSONArray("files");
        } else {
            fileList = new JSONArray();
        }
        fileList.put(fileObj);
        mergedData.put("files", fileList);

        // Convert merged data to string
        String newData = mergedData.toString();

        // Save the updated data to the database
        saveDataToDatabase(userId, collectionName, newData);
    }

    private static String getExistingJsonData(int userId, String collectionName) {

        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT datacollection FROM collections WHERE userid = ? AND name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, collectionName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("datacollection");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveDataToDatabase(int userId, String collectionName, String jsonData) {

        Credentials credentials = new Credentials();
        String url = credentials.getUrl();
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE collections SET datacollection = ? WHERE userid = ? AND name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, jsonData);
                pstmt.setInt(2, userId);
                pstmt.setString(3, collectionName);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
