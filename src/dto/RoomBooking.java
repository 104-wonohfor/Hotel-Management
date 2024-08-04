package dto;

import java.util.Date;
import types.BookingStatus;

public class RoomBooking {
    private int roomNumber;
    private Date startDate;
    private Date endDate;
    private BookingStatus bookingStatus;
    private Date checkIn;
    private Date checkOut;
    private String guestID;

    public RoomBooking() {
    }

    public RoomBooking(int roomNumber, Date startDate, Date endDate, BookingStatus bookingStatus, Date checkIn, Date checkOut, String guestID) {
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestID = guestID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }   

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }
}
