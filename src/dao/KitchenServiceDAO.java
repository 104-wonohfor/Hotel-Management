package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



public class KitchenServiceDAO {
    public TableModel getKitchenServiceList() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"description", "price"}, 0);
        Connection conn = DAO.DAO_DB();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM KitchenService");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNo = rsmd.getColumnCount();

            while (rs.next()) {
                Object[] objects = new Object[colNo];
                for (int i = 1; i < colNo; i++) {
                    objects[i - 1] = rs.getObject(i + 1);
                }
                model.addRow(objects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }
}
