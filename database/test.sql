USE HotelManagement

SELECT * FROM Person 

SELECT * FROM Account

SELECT * FROM Guest

SELECT * FROM Manager

SELECT * FROM Receptionist

SELECT * FROM Room

SELECT * FROM RoomGuest

SELECT * FROM RoomBooking

SELECT * FROM Invoice

SELECT * FROM Service

SELECT * FROM KitchenService

SELECT * FROM Request

SELECT * FROM NumberReadRequest

SELECT * FROM Room WHERE roomStatus = 'OCCUPIED'

SELECT * FROM Room WHERE style IN ('STANDARD','DELUXE','BUSINESS') AND numBeds IN (1,2,3) AND roomStatus IN ('AVAILABLE','OCCUPIED','NOT_AVAILABLE')

SELECT * FROM RoomBooking WHERE bookingStatus = 'CHECKED_OUT';

DELETE FROM RoomGuest WHERE roomNumber = 101

DELETE FROM Account WHERE ID = 'G7'

UPDATE Room SET roomStatus = 'AVAILABLE' WHERE roomNumber = 202

UPDATE RoomBooking SET bookingStatus = 'CONFIRMED', checkIn = NULL WHERE bookingID = 4

UPDATE Guest SET totalRoomCheckedIn = 0 WHERE ID = 'G10'

UPDATE RoomGuest SET guestName = 'Geoffrey Hinton' WHERE roomNumber = 106

UPDATE Service SET issuedAt = 'Do Laundry' WHERE serviceID = 3


INSERT INTO Invoice(bookingID, totalAmount, paymentStatus)
VALUES
(3, 18000, 'UNPAID'),
(2, 6000, 'UNPAID'),
(4, 6000, 'UNPAID'),
(6, 31000, 'UNPAID'),
(8, 24000,'UNPAID');


UPDATE Service SET issueAt = '2024-05-12 10:00:00' WHERE serviceID = 3;

UPDATE Service SET serviceAmount = 400 WHERE serviceID = 2;

UPDATE Service SET serviceStatus = 'REQUESTED' WHERE serviceID = 9;





