package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import dto.Room;


public class RoomGuestDAO {
    public void insertRoomGuest(Room room){
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO RoomGuest(roomNumber, guestName) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, room.getRoomNumber());
    
            for (String guestName : room.getGuestName()) {
                statement.setString(2, guestName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Room getRoomGuestByRoomNumber(int roomNumber){
        Room room = new Room();
        List<String> guestNames = new ArrayList<>();
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM RoomGuest WHERE roomNumber = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomNumber);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                room.setRoomNumber(result.getString("roomNumber"));
                guestNames.add(result.getString("guestName"));
            }
            room.setGuestName(guestNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    public void deleteRoomGuestByRoomNumber(int roomNumber){
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "DELETE FROM RoomGuest WHERE roomNumber = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}