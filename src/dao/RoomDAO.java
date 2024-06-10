package dao;

import model.Room;
import types.RoomStyle;
import types.RoomStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> findAvailableRooms(String style, int beds) {
        List<Room> availableRooms = new ArrayList<>();
        Connection conn = DAO.DAO_DB();
    
        try {
            String sql = "SELECT * FROM Room WHERE style = ? AND numBeds = ? AND roomStatus != 'NOT_AVAILABLE'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, style);
            statement.setInt(2, beds);
            System.out.println("Executing SQL statement: " + sql.replaceFirst("\\?", "'" + style + "'").replaceFirst("\\?", String.valueOf(beds)));
            ResultSet result = statement.executeQuery();

    
            while (result.next()) {
                Room room = new Room();
                room.setRoomNumber(result.getString("roomNumber"));
                room.setStyle(RoomStyle.fromString(result.getString("style")));
                room.setNumBeds(result.getInt("numBeds"));
                room.setBookingPrice(result.getDouble("bookingPrice"));
                room.setRoomStatus(RoomStatus.fromString(result.getString("roomStatus")));
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return availableRooms;
    }

    // order can be "Price", "Room Number"
    public List<Room> getRoomByFilter(boolean standard, boolean deluxe, boolean business, 
                                      boolean oneBed, boolean twoBeds, boolean threeBeds,
                                      boolean available, boolean occupied, boolean notAvailable,
                                      String column, boolean ascending) {
        List<Room> rooms = new ArrayList<>();
        Connection conn = DAO.DAO_DB();

        try {
            String sql = "SELECT * FROM Room WHERE style IN (";
            if (standard) {
                sql += "'STANDARD',";
            }
            if (deluxe) {
                sql += "'DELUXE',";
            }
            if (business) {
                sql += "'BUSINESS_SUITE',";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ") AND numBeds IN (";
            if (oneBed) {
                sql += "1,";
            }
            if (twoBeds) {
                sql += "2,";
            }
            if (threeBeds) {
                sql += "3,";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ") AND roomStatus IN (";
            if (available) {
                sql += "'AVAILABLE',";
            }
            if (occupied) {
                sql += "'OCCUPIED',";
            }
            if (notAvailable) {
                sql += "'NOT_AVAILABLE',";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ") ORDER BY ";

            if (column.equals("Price")) {
                sql += "bookingPrice";
            } else if (column.equals("Room Number")) {
                sql += "roomNumber";
            }

            if (ascending) {
                sql += " ASC";
            } else {
                sql += " DESC";
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            System.out.println("Executing SQL statement: " + sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Room room = new Room();
                room.setRoomNumber(result.getString("roomNumber"));
                room.setStyle(RoomStyle.fromString(result.getString("style")));
                room.setNumBeds(result.getInt("numBeds"));
                room.setBookingPrice(result.getDouble("bookingPrice"));
                room.setRoomStatus(RoomStatus.fromString(result.getString("roomStatus")));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }


    public Room getRoomByRoomNumber(int roomNumber) {
        Room room = new RoomGuestDAO().getRoomGuestByRoomNumber(roomNumber);
        Connection conn = DAO.DAO_DB();
    
        try {
            String sql = "SELECT * FROM Room WHERE roomNumber = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomNumber);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                room.setRoomNumber(result.getString("roomNumber"));
                room.setStyle(RoomStyle.fromString(result.getString("style")));
                room.setNumBeds(result.getInt("numBeds"));
                room.setBookingPrice(result.getDouble("bookingPrice"));
                room.setRoomStatus(RoomStatus.fromString(result.getString("roomStatus")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }     
        return room;
    }

    public void updateRoom(Room room) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE Room SET style = ?, numBeds = ?, bookingPrice = ?, roomStatus = ? WHERE roomNumber = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, room.getStyle().toString());
            statement.setInt(2, room.getNumBeds());
            statement.setDouble(3, room.getBookingPrice());
            statement.setString(4, room.getRoomStatus().toString());
            statement.setString(5, room.getRoomNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRoom(Room room) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO Room (roomNumber, style, numBeds, bookingPrice, roomStatus) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, room.getRoomNumber());
            statement.setString(2, room.getStyle().toString());
            statement.setInt(3, room.getNumBeds());
            statement.setDouble(4, room.getBookingPrice());
            statement.setString(5, room.getRoomStatus().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoomByRoomNumber(int roomNumber) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "DELETE FROM Room WHERE roomNumber = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
