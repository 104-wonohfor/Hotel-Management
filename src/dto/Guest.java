package dto;

import com.rabbitmq.client.*;

import java.util.ArrayList;
import java.util.List;

import dao.RoomBookingDAO;

public class Guest extends Person {
    private int totalRoomCheckedIn=0;
    private List<RoomBooking> roomBookingList;

    public int getTotalRoomCheckedIn() {
        return totalRoomCheckedIn;
    }

    public void setTotalRoomCheckedIn(int totalRoomCheckedIn) {
        this.totalRoomCheckedIn = totalRoomCheckedIn;
    }

    public List<RoomBooking> getRoomBookingList() {
        return roomBookingList;
    }

    public void setRoomBookingList(List<RoomBooking> roomBookingList) {
        this.roomBookingList = roomBookingList;
    }

    private static final String EXCHANGE_NAME = "notifications";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public Guest() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    }

    public Guest(int totalRoomCheckedIn, List<RoomBooking> roomBooKingList)
    {
        this.totalRoomCheckedIn = totalRoomCheckedIn;
        this.roomBookingList = roomBooKingList;

        factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bookRoom() throws Exception {
        String message = "bookRoom";
        channel.basicPublish(EXCHANGE_NAME, "bookRoom", null, message.getBytes("UTF-8"));
    }
    
    public void callService() throws Exception {
        String message = "callService";
        channel.basicPublish(EXCHANGE_NAME, "callService", null, message.getBytes("UTF-8"));
    }
}
