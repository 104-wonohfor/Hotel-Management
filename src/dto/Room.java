package dto;


import types.RoomStatus;
import types.RoomStyle;

import java.util.List;


public class Room {
    private String roomNumber;
    private RoomStyle style;
    private RoomStatus roomStatus;
    private double bookingPrice;
    private int numBeds;
    List<String> guestName;

    public Room() {
    }

    public Room(String roomNumber, RoomStyle style, RoomStatus roomStatus, double bookingPrice, int numBeds) {
        this.roomNumber = roomNumber;
        this.style = style;
        this.roomStatus = roomStatus;
        this.bookingPrice = bookingPrice;
        this.numBeds = numBeds;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomStyle getStyle() {
        return style;
    }

    public void setStyle(RoomStyle style) {
        this.style = style;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public List<String> getGuestName() {
        return guestName;
    }

    public void setGuestName(List<String> guestName) {
        this.guestName = guestName;
    }
}
