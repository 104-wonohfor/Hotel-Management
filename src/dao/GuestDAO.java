package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.Guest;

import java.sql.ResultSet;


public class GuestDAO {
    public void insertGuest(Guest guest){
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO Guest(ID, totalRoomCheckedIn) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, guest.getID());
            statement.setInt(2, guest.getTotalRoomCheckedIn());
            statement.executeUpdate();
            System.out.println("Guest inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void updateGuest(Guest guest){ 
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE Guest SET totalRoomCheckedIn = ? WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, guest.getTotalRoomCheckedIn());
            statement.setString(2, guest.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Guest getGuestByID(String ID){
        Guest guest = null;
        try {
            guest = new Guest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Connection conn = DAO.DAO_DB();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Guest WHERE ID = ?");
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                guest.setID(rs.getString("ID"));
                guest.setTotalRoomCheckedIn(rs.getInt("totalRoomCheckedIn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guest;
    }

    public void deleteGuestByID(String ID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "DELETE FROM Guest WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            statement.executeUpdate();
            System.out.println("Guest deleted");
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }
}
