package dao;

import model.RoomBooking;
import types.BookingStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.ResultSetMetaData;



public class RoomBookingDAO {

    // Check if room is available for booking. Không check RoomBooking nào có bookingStatus = 'CHECKED_OUT'
    public boolean isRoomAvailable(String roomNumber, Date startDate, Date endDate) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM RoomBooking WHERE roomNumber = ? AND ((startDate <= ? AND endDate >= ?) OR (startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?)) AND bookingStatus != 'CHECKED_OUT'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, roomNumber);
            statement.setDate(2, new java.sql.Date(startDate.getTime()));
            statement.setDate(3, new java.sql.Date(startDate.getTime()));
            statement.setDate(4, new java.sql.Date(endDate.getTime()));
            statement.setDate(5, new java.sql.Date(endDate.getTime()));
            statement.setDate(6, new java.sql.Date(startDate.getTime()));
            statement.setDate(7, new java.sql.Date(endDate.getTime()));
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return true;
    }

    public boolean createBooking(RoomBooking roomBooking) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO RoomBooking (roomNumber, startDate, endDate, checkIn, checkOut, bookingStatus, guestID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomBooking.getRoomNumber());
            statement.setDate(2, new java.sql.Date(roomBooking.getStartDate().getTime()));
            statement.setDate(3, new java.sql.Date(roomBooking.getEndDate().getTime()));

            if (roomBooking.getCheckIn() != null) {
                statement.setDate(4, new java.sql.Date(roomBooking.getCheckIn().getTime()));
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }
            
            if (roomBooking.getCheckOut() != null) {
                statement.setDate(5, new java.sql.Date(roomBooking.getCheckOut().getTime()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            statement.setString(6, roomBooking.getBookingStatus().toString());
            statement.setString(7, roomBooking.getGuestID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }

    public RoomBooking getRoomBookingByBookingID(int bookingID) {
        RoomBooking roomBooking = new RoomBooking();
        Connection conn = DAO.DAO_DB();
    
        try {
            String sql = "SELECT * FROM RoomBooking WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookingID);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                roomBooking.setRoomNumber(result.getInt("roomNumber"));
                roomBooking.setStartDate(result.getDate("startDate"));
                roomBooking.setEndDate(result.getDate("endDate"));
                roomBooking.setCheckIn(result.getTimestamp("checkIn"));
                roomBooking.setCheckOut(result.getTimestamp("checkOut"));
                roomBooking.setBookingStatus(BookingStatus.fromString(result.getString("bookingStatus")));
                roomBooking.setGuestID(result.getString("guestID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomBooking;
    }

    public List<RoomBooking> getRoomBookingByGuestID(String guestID) {
        Connection conn = DAO.DAO_DB();
        List<RoomBooking> roomBookingList = new Vector<RoomBooking>();
    
        try {
            String sql = "SELECT * FROM RoomBooking WHERE guestID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, guestID);
            ResultSet result = statement.executeQuery();
    
            while (result.next()) {
                RoomBooking roomBooking = new RoomBooking();
                roomBooking.setRoomNumber(result.getInt("roomNumber"));
                roomBooking.setStartDate(result.getDate("startDate"));
                roomBooking.setEndDate(result.getDate("endDate"));
                roomBooking.setCheckIn(result.getDate("checkIn"));
                roomBooking.setCheckOut(result.getDate("checkOut"));
                roomBooking.setBookingStatus(BookingStatus.fromString(result.getString("bookingStatus")));
                roomBooking.setGuestID(result.getString("guestID"));
                roomBookingList.add(roomBooking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomBookingList;
    }

    public List<RoomBooking> getCheckInRoomOfGuestID(String guestID) {
        Connection conn = DAO.DAO_DB();
        List<RoomBooking> roomBookingList = new Vector<RoomBooking>();
    
        try {
            String sql = "SELECT * FROM RoomBooking WHERE guestID = ? AND bookingStatus = 'CHECKED_IN'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, guestID);
            ResultSet result = statement.executeQuery();
    
            while (result.next()) {
                RoomBooking roomBooking = new RoomBooking();
                roomBooking.setRoomNumber(result.getInt("roomNumber"));
                roomBooking.setStartDate(result.getDate("startDate"));
                roomBooking.setEndDate(result.getDate("endDate"));
                if (result.getTimestamp("checkIn") != null) {
                    roomBooking.setCheckIn(new Date(result.getTimestamp("checkIn").getTime()));
                }
                if (result.getTimestamp("checkOut") != null) {
                    roomBooking.setCheckOut(new Date(result.getTimestamp("checkOut").getTime()));
                }
                roomBooking.setBookingStatus(BookingStatus.fromString(result.getString("bookingStatus")));
                roomBooking.setGuestID(result.getString("guestID"));
                roomBookingList.add(roomBooking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomBookingList;
    }

    public TableModel getRoomBooking(String status) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"bookingID","roomNumber", "startDate", "endDate", "bookingStatus", "checkIn", "checkOut", "guestID"}, 0); 
        String sql = "SELECT * FROM RoomBooking";
        if ("Only Requested".equals(status)) {
            sql += " WHERE bookingStatus = 'REQUESTED'";
        }
        if ("Only Confirmed".equals(status)) {
            sql += " WHERE bookingStatus = 'CONFIRMED'";
        }
        if ("Only Checked In".equals(status)) {
            sql += " WHERE bookingStatus = 'CHECKED_IN'";
        }
    
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

    public TableModel getRoomBooking(String status, String guestID) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"bookingID","roomNumber", "startDate", "endDate", "bookingStatus", "checkIn", "checkOut", "guestID"}, 0); 
        String sql = "SELECT * FROM RoomBooking";
        if ("Only Requested".equals(status)) {
            sql += " WHERE bookingStatus = 'REQUESTED'";
        }
        if ("Only Confirmed".equals(status)) {
            sql += " WHERE bookingStatus = 'CONFIRMED'";
        }
        if ("Only Checked In".equals(status)) {
            sql += " WHERE bookingStatus = 'CHECKED_IN'";
        }

        sql += " AND guestID = '" + guestID + "'";

        System.out.println(sql);
    
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

    public void updateBookingStatus(int bookingID, String status) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE RoomBooking SET bookingStatus = ? WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, bookingID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void updateBookingStatus(int bookingID, RoomBooking roomBooking) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE RoomBooking SET bookingStatus = ?, checkIn = ? WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, roomBooking.getBookingStatus().toString());
            statement.setTimestamp(2, new java.sql.Timestamp(roomBooking.getCheckIn().getTime()));
            statement.setInt(3, bookingID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public boolean checkGuestCheckIn(String guestID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM RoomBooking WHERE guestID = ? AND bookingStatus = 'CHECKED_IN'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, guestID);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get bookingID of checked-in room from roomNumber
    public String getBookingID(int roomNumber) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT bookingID FROM RoomBooking WHERE roomNumber = ? AND bookingStatus = 'CHECKED_IN'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomNumber);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                return result.getString("bookingID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get GuestID from bookingID
    public String getGuestID(int bookingID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT guestID FROM RoomBooking WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookingID);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                return result.getString("guestID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // load RoomBooking data from bookingStatus = 'CHECKED_IN'
    public List<RoomBooking> loadRoomBookingCheckedIn(String userID) {
        Connection conn = DAO.DAO_DB();
        List<RoomBooking> roomBookingList = new ArrayList<>();
    
        try {
            String sql = "SELECT * FROM RoomBooking WHERE bookingStatus = 'CHECKED_IN'";

            if (!userID.equals("All")){
                sql += " AND guestID = ?";
            }
            PreparedStatement statement = conn.prepareStatement(sql);

            if (!userID.equals("All")){
                statement.setString(1, userID);
            }

            ResultSet result = statement.executeQuery();
    
            while (result.next()) {
                RoomBooking roomBooking = new RoomBooking();
                roomBooking.setRoomNumber(result.getInt("roomNumber"));
                roomBooking.setStartDate(result.getDate("startDate"));
                roomBooking.setEndDate(result.getDate("endDate"));
                roomBooking.setCheckIn(result.getDate("checkIn"));
                roomBooking.setCheckOut(result.getDate("checkOut"));
                roomBooking.setBookingStatus(BookingStatus.fromString(result.getString("bookingStatus")));
                roomBooking.setGuestID(result.getString("guestID"));
                roomBookingList.add(roomBooking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomBookingList;
    }

    public void updateRoomBooking(int bookingID, RoomBooking roomBooking) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE RoomBooking SET roomNumber = ?, startDate = ?, endDate = ?, checkIn = ?, checkOut = ?, bookingStatus = ?, guestID = ? WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, roomBooking.getRoomNumber());
            statement.setTimestamp(2, new java.sql.Timestamp(roomBooking.getStartDate().getTime()));
            statement.setTimestamp(3, new java.sql.Timestamp(roomBooking.getEndDate().getTime()));
            statement.setTimestamp(4, new java.sql.Timestamp(roomBooking.getCheckIn().getTime()));
            statement.setTimestamp(5, new java.sql.Timestamp(roomBooking.getCheckOut().getTime()));
            statement.setString(6, roomBooking.getBookingStatus().toString());
            statement.setString(7, roomBooking.getGuestID());
            statement.setInt(8, bookingID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get bookingID from guestID
    public List<Integer> getBookingID(String guestID) {
        Connection conn = DAO.DAO_DB();
        List<Integer> bookingIDList = new ArrayList<>();
        try {
            String sql = "SELECT bookingID FROM RoomBooking WHERE guestID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, guestID);
            ResultSet result = statement.executeQuery();
    
            while (result.next()) {
                bookingIDList.add(result.getInt("bookingID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingIDList;
    }
}