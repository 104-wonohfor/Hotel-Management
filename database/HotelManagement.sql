--CREATE DATABASE HotelManagement;
--go

USE HotelManagement;


CREATE TABLE Account (
    ID NVARCHAR(50) PRIMARY KEY,
    username NVARCHAR(50) UNIQUE,
    password NVARCHAR(50)
);

CREATE TABLE Person (
    ID NVARCHAR(50) PRIMARY KEY FOREIGN KEY REFERENCES Account(ID),
    name NVARCHAR(50) NOT NULL,
    Address NVARCHAR(255) NOT NULL,
    email NVARCHAR(50) NOT NULL,
    phone NVARCHAR(15) NOT NULL UNIQUE,
    accountType NVARCHAR(50) NOT NULL
);

CREATE TABLE Guest (
    ID NVARCHAR(50) PRIMARY KEY FOREIGN KEY REFERENCES Person(ID),
    totalRoomCheckedIn INT NOT NULL
);

CREATE TABLE Manager (
    ID NVARCHAR(50) PRIMARY KEY FOREIGN KEY REFERENCES Person(ID)
);

CREATE TABLE Receptionist (
    ID NVARCHAR(50) PRIMARY KEY FOREIGN KEY REFERENCES Person(ID)
);

CREATE TABLE Room (
    roomNumber INT PRIMARY KEY,
    style NVARCHAR(50) NOT NULL,
    numBeds INT NOT NULL,
    bookingPrice DECIMAL(10, 2) NOT NULL,
    roomStatus NVARCHAR(50) NOT NULL
);

CREATE TABLE RoomGuest (
    roomNumber INT FOREIGN KEY REFERENCES Room(roomNumber) UNIQUE,
    guestName NVARCHAR(255),
);

CREATE TABLE RoomBooking (
    bookingID INT PRIMARY KEY IDENTITY(1,1),
    roomNumber INT FOREIGN KEY REFERENCES Room(roomNumber) NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    bookingStatus NVARCHAR(50) NOT NULL,
    checkIn DATETIME,
    checkOut DATETIME,
    guestID NVARCHAR(50) FOREIGN KEY REFERENCES Guest(ID) NOT NULL
);

CREATE TABLE Service (
    serviceID INT PRIMARY KEY IDENTITY(1,1),
    bookingID INT FOREIGN KEY REFERENCES RoomBooking(bookingID) NOT NULL,
    description NVARCHAR(255) NOT NULL,
    issueAt DATETIME DEFAULT GETDATE(),
    roomNumber INT FOREIGN KEY REFERENCES Room(roomNumber) NOT NULL,
    serviceStatus NVARCHAR(50) NOT NULL,
    serviceAmount DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Invoice (
    InvoiceID INT PRIMARY KEY IDENTITY(1,1),
    bookingID INT FOREIGN KEY REFERENCES RoomBooking(bookingID) NOT NULL,
    totalAmount DECIMAL(10, 2) NOT NULL,
    paymentStatus NVARCHAR(50) NOT NULL
);

CREATE TABLE KitchenService (
    ID INT PRIMARY KEY IDENTITY(1,1),
    description NVARCHAR(255) NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
);

CREATE TABLE Request (
    requestID INT PRIMARY KEY IDENTITY(1,1),
    description NVARCHAR(255) NOT NULL,
);

CREATE TABLE NumberReadRequest (
    staffID NVARCHAR(50) NOT NULL,
    numReadRequest INT NOT NULL,
);


-- Insert into Account
INSERT INTO Account(ID, username, password) 
VALUES 
('G1', 'user1', 'pass1'),
('G2', 'user2', 'pass2'),
('G3', 'user3', 'pass3'),
('R1', 'receptionist01', 'staffPass'),
('R2', 'receptionist02', 'staffPass'),
('M1', 'manager01', 'admin');

-- Insert into Person
INSERT INTO Person(ID, name, Address, email, phone, accountType) 
VALUES 
('G1', 'Tokugawa Ieyasu', '24 Izu, Japan', 'tIeyasu2824@toku.com', '0987654321', 'GUEST'), 
('G2', 'Hojo Hidetada', '24 Berlin, Germany', 'tHdetade343@hfwk.com', '0987654322', 'GUEST'),
('G3', 'Sherlock Holmes', '137 London, England', 'hwjjb628@wq22.com', '0987654323', 'GUEST'),
('R1', 'Minamoto Yoshitsune', '13 Tosa, Japan', 'mYoshitsune2901@hotel.com', '0123456789', 'RECEPTIONIST'), 
('R2', 'Minamoto Yorinaka', '94 Edo, Japan', 'mYorinaka912@hotel.com', '0123456788', 'RECEPTIONIST'), 
('M1', 'Minamoto no Yorimoto', '1204 Sagami, Japan', 'mYorimoto242@hotel.com', '0123456787', 'MANAGER');

-- Insert into Guest
INSERT INTO Guest(ID, totalRoomCheckedIn) 
VALUES 
('G1', 0),
('G2', 0),
('G3', 0);

-- Insert into Manager
INSERT INTO Manager(ID) 
VALUES 
('M1');

-- Insert into Receptionist
INSERT INTO Receptionist(ID) 
VALUES 
('R1'),
('R2');

-- Insert into Room
INSERT INTO Room(roomNumber, style, numBeds, bookingPrice, roomStatus) 
VALUES 
(101, 'STANDARD', 1, 2000.00, 'AVAILABLE'),
(102, 'DELUXE', 1, 3000.00, 'AVAILABLE'),
(103, 'BUSINESS_SUITE', 1, 4000.00, 'AVAILABLE'),
(104, 'STANDARD', 2, 2800.00, 'AVAILABLE'),
(105, 'DELUXE', 1, 3000.00, 'AVAILABLE'),
(106, 'BUSINESS_SUITE', 2, 6000.00, 'AVAILABLE'),
(107, 'STANDARD', 3, 3200.00, 'AVAILABLE'),
(108, 'DELUXE', 1, 3000.00, 'AVAILABLE'),
(201, 'BUSINESS_SUITE', 2, 6000.00, 'AVAILABLE'),
(202, 'STANDARD', 1, 2000.00, 'AVAILABLE'),
(203, 'DELUXE', 2, 5500.00, 'AVAILABLE'),
(204, 'BUSINESS_SUITE', 1, 4200.00, 'AVAILABLE'),
(205, 'STANDARD', 2, 2900.00, 'AVAILABLE'),
(206, 'DELUXE', 1, 3000.00, 'AVAILABLE'),
(207, 'BUSINESS_SUITE', 1, 4000.00, 'AVAILABLE'),
(208, 'STANDARD', 2, 2800.00, 'AVAILABLE'),
(301, 'DELUXE', 3, 6500.00, 'AVAILABLE'),
(302, 'BUSINESS_SUITE', 3, 7300.00, 'AVAILABLE'),
(303, 'STANDARD', 1, 2000.00, 'AVAILABLE'),
(304, 'DELUXE', 2, 5200.00, 'AVAILABLE'),
(305, 'BUSINESS_SUITE', 1, 4000.00, 'AVAILABLE'),
(306, 'STANDARD', 3, 3300.00, 'AVAILABLE'),
(307, 'DELUXE', 1, 3000.00, 'AVAILABLE'),
(308, 'BUSINESS_SUITE', 3, 7200.00, 'AVAILABLE');
/*
-- Insert into RoomGuest
INSERT INTO RoomGuest(roomNumber, guestName)
VALUES
(101, 'Hojo Hidetada'),
(202, 'Sherlock Holmes');


-- Insert into RoomBooking
INSERT INTO RoomBooking(roomNumber, startDate, endDate, bookingStatus, checkIn, checkOut, guestID) 
VALUES 
(101, '2024-05-11', '2024-05-13', 'CHECKED_OUT', '2024-05-11 10:00:00', '2024-05-12 21:00:00', 'G1'),
(101, '2024-05-14', '2024-05-17', 'CHECKED_IN', '2024-05-14 09:00:00', NULL, 'G2'),
(202, '2024-05-11', '2024-05-20', 'CHECKED_IN', '2024-05-11 09:20:00', NULL, 'G3');

-- Insert into Service
INSERT INTO Service(bookingID, description, roomNumber, serviceStatus, serviceAmount)
VALUES 
(1, 'Clean Room', 101, 'COMPLETED', 0),
(1, '2 Pizza', 101, 'CANCELLED', 400.00),
(1, 'Doing Laundry', 101, 'COMPLETED', 180.00);

-- Insert into Invoice
INSERT INTO Invoice(bookingID, totalAmount, paymentStatus)
VALUES 
('1', 4180.00, 'PAID');
*/
-- Insert into KitchenService
INSERT INTO KitchenService(description, Amount)
VALUES 
('Pizza', 200.00),
('Burger', 150.00),
('Pasta', 180.00),
('Pho', 200.00),
('Ramen', 180.00),
('Sushi', 250.00),
('Fried Rice', 150.00),
('Noodles', 180.00),
('Steak', 300.00),
('Soup', 100.00),
('Salad', 100.00),
('Sandwich', 120.00),
('Coffee', 50.00),
('Tea', 40.00),
('Soft Drink', 60.00),
('Juice', 70.00),
('Water', 20.00);
/*
-- Insert into Request
INSERT INTO Request(description)
VALUES
('A new room booking was requested by Tokugawa Ieyasu'),
('ROOM 101 requested a SERVICE'),
('ROOM 101 requested a SERVICE'),
('ROOM 101 requested a SERVICE'),
('A new room booking was requested by Hojo Hidetada');
*/
-- Insert into TrackStaffNumberReadRequest
INSERT INTO NumberReadRequest(staffID, numReadRequest)
VALUES
('R1', 0),
('R2', 0),
('R3', 0);

