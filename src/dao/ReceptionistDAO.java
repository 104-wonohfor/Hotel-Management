package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.Receptionist;

public class ReceptionistDAO {
    public void insertReceptionist(Receptionist receptionist){
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO Receptionist(ID) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, receptionist.getID());
            statement.executeUpdate();
            System.out.println("Receptionist inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void deleteReceptionistByID(String ID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "DELETE FROM Receptionist WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            statement.executeUpdate();
            System.out.println("Receptionist deleted");
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }
}
