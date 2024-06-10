package model;

import java.util.Date;
import types.ServiceStatus;


public class Service {
    private int serviceID;
    private int bookingID;
    private String description;
    private Date issueAt;
    private int roomNumber;
    private ServiceStatus serviceStatus;
    private double serviceAmount;

    public Service(){
    }

    public Service(int serviceID, int bookingID, String description, Date issueAt, int roomNumber, ServiceStatus serviceStatus, double serviceAmount) {
        this.serviceID = serviceID;
        this.bookingID = bookingID;
        this.description = description;
        this.issueAt = issueAt;
        this.roomNumber = roomNumber;
        this.serviceStatus = serviceStatus;
        this.serviceAmount = serviceAmount;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(Date issueAt) {
        this.issueAt = issueAt;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }
}
