package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import model.Service;
import types.ServiceStatus;

public class ServiceDAO {
    public void insertService(Service service) {
        Connection conn = DAO.DAO_DB();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Service (bookingID, description, roomNumber, serviceStatus, serviceAmount) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, service.getBookingID());
            ps.setString(2, service.getDescription());
            ps.setInt(3, service.getRoomNumber());
            ps.setString(4, service.getServiceStatus().toString());
            ps.setDouble(5, service.getServiceAmount());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateServiceStatus(int serviceID, ServiceStatus serviceStatus) {
        Connection conn = DAO.DAO_DB();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Service SET serviceStatus = ? WHERE serviceID = ?");
            ps.setString(1, serviceStatus.toString());
            ps.setInt(2, serviceID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Service> getAllService(String Status) {
        Connection conn = DAO.DAO_DB();
        List<Service> services = new ArrayList<>();

        String sql = "SELECT * FROM Service";
        
        if (Status.equals("REQUESTED") || Status.equals("CONFIRMED") || Status.equals("COMPLETED") || Status.equals("CANCELLED")) {
            sql += " WHERE serviceStatus = '" + Status + "'";
        } else if (!Status.equals("All")) {
            return services;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Service service = new Service();
                service.setServiceID(rs.getInt("serviceID"));
                service.setBookingID(rs.getInt("bookingID"));
                service.setDescription(rs.getString("description"));
                service.setIssueAt(new Date(rs.getTimestamp("issueAt").getTime()));
                service.setRoomNumber(rs.getInt("roomNumber"));
                service.setServiceStatus(ServiceStatus.fromString(rs.getString("serviceStatus")));
                service.setServiceAmount(rs.getDouble("serviceAmount"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public List<Service> getCompledteServicesByBookingID(int bookingID) {
        Connection conn = DAO.DAO_DB();
        List<Service> services = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Service WHERE bookingID = ? AND serviceStatus = 'COMPLETED'");
            ps.setInt(1, bookingID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Service service = new Service();
                service.setServiceID(rs.getInt("serviceID"));
                service.setBookingID(rs.getInt("bookingID"));
                service.setDescription(rs.getString("description"));
                service.setIssueAt(new Date(rs.getTimestamp("issueAt").getTime()));
                service.setRoomNumber(rs.getInt("roomNumber"));
                service.setServiceStatus(ServiceStatus.fromString(rs.getString("serviceStatus")));
                service.setServiceAmount(rs.getDouble("serviceAmount"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    //get service by a list bookingID
    public List<Service> getServiceByBookingID(List<Integer> bookingIDs) {
        Connection conn = DAO.DAO_DB();
        List<Service> services = new ArrayList<>();

        try {
            for (int bookingID : bookingIDs) {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Service WHERE bookingID = ?");
                ps.setInt(1, bookingID);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Service service = new Service();
                    service.setServiceID(rs.getInt("serviceID"));
                    service.setBookingID(rs.getInt("bookingID"));
                    service.setDescription(rs.getString("description"));
                    service.setIssueAt(new Date(rs.getTimestamp("issueAt").getTime()));
                    service.setRoomNumber(rs.getInt("roomNumber"));
                    service.setServiceStatus(ServiceStatus.fromString(rs.getString("serviceStatus")));
                    service.setServiceAmount(rs.getDouble("serviceAmount"));
                    services.add(service);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
