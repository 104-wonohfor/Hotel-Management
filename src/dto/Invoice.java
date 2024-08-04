package dto;

import types.PaymentStatus;


public class Invoice {
    private int invoiceID;
    private int bookingID;
    private double totalAmount;
    private PaymentStatus paymentStatus;

    public Invoice() {
    }

    public Invoice(int invoiceID, int bookingID, double totalAmount, PaymentStatus paymentStatus) {
        this.invoiceID = invoiceID;
        this.bookingID = bookingID;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
