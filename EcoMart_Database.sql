CREATE DATABASE EcoMartPrtal;
USE EcoMartPortal;

CREATE TABLE LoginData (
    Name VARCHAR(50),
    Email VARCHAR(50),
    Mobile VARCHAR(15),
    Username VARCHAR(30) PRIMARY KEY,
    Password VARCHAR(30),
    Address VARCHAR(100),
    RewardPoints INT DEFAULT 0
);

CREATE TABLE OrderHistory (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(30),
    Products TEXT,
    TotalAmount DOUBLE,
    RewardPoints INT
);