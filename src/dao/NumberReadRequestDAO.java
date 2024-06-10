package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class NumberReadRequestDAO {
    public int getNumberReadRequest(String ID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT numReadRequest FROM NumberReadRequest WHERE staffID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                return result.getInt("numReadRequest");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Update numReadRequest của ID
    public void updateNumberReadRequest(String ID, int numReadRequest) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE NumberReadRequest SET numReadRequest = ? WHERE staffID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, numReadRequest);
            statement.setString(2, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // insert ID và numReadRequest(mặc định khi thêm là 0) vào bảng NumberReadRequest
    public void insertNumberReadRequest(String ID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO NumberReadRequest(staffID, numReadRequest) VALUES(?, 0)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
