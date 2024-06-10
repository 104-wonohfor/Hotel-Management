package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class RequestDAO {

    public void insertRequest(String description) {
        String sql = "INSERT INTO Request(description) VALUES(?)";

        try (Connection conn = DAO.DAO_DB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TableModel getRequestTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"description"}, 0);
        String sql = "SELECT description FROM Request ORDER BY requestID DESC";

        try (Connection conn = DAO.DAO_DB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
    
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
    
            model.setDataVector(data, columnNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return model;
    }

    // trả về requestID của request cuối cùng
    public int getLastRequestID() {
        String sql = "SELECT TOP 1 requestID FROM Request ORDER BY requestID DESC";
    
        try (Connection conn = DAO.DAO_DB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("requestID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return -1;
    }
}
