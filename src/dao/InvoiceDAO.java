package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Invoice;
import types.PaymentStatus;


public class InvoiceDAO {
    public void createInvoice(Invoice invoice) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO Invoice(bookingID, totalAmount, paymentStatus) VALUES(?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, invoice.getBookingID());
            statement.setDouble(2, invoice.getTotalAmount());
            statement.setString(3, invoice.getPaymentStatus().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInvoice(Invoice invoice) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE Invoice SET totalAmount = ?, paymentStatus = ? WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, invoice.getTotalAmount());
            statement.setString(2, invoice.getPaymentStatus().toString());
            statement.setInt(3, invoice.getBookingID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Invoice getInvoiceByBookingID(int bookingID) {
        Connection conn = DAO.DAO_DB();
        Invoice invoice = new Invoice();
        try {
            String sql = "SELECT * FROM Invoice WHERE bookingID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookingID);
            statement.executeQuery();
            if (statement.getResultSet().next()) {
                invoice.setInvoiceID(statement.getResultSet().getInt("invoiceID"));
                invoice.setBookingID(statement.getResultSet().getInt("bookingID"));
                invoice.setTotalAmount(statement.getResultSet().getDouble("totalAmount"));
                invoice.setPaymentStatus(PaymentStatus.fromString(statement.getResultSet().getString("paymentStatus")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }
}
