
CREATE DATABASE IF NOT EXISTS busticket;


USE busticket;

CREATE TABLE IF NOT EXISTS buses (
    idbuses INT PRIMARY KEY AUTO_INCREMENT,
    busPlate VARCHAR(20) NOT NULL,
    numberOfSeats INT NOT NULL
);

CREATE TABLE IF NOT EXISTS driver (
    iddriver INT PRIMARY KEY AUTO_INCREMENT,
    driverName VARCHAR(255) NOT NULL,
    driverTel VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS departures (
    iddepartures INT PRIMARY KEY AUTO_INCREMENT,
    departure VARCHAR(255) NOT NULL,
    target VARCHAR(255) NOT NULL,
    departureTime DATETIME NOT NULL,
    idbuses INT,
    iddriver INT,
    FOREIGN KEY (idbuses) REFERENCES buses(idbuses),
    FOREIGN KEY (iddriver) REFERENCES driver(iddriver)
);

CREATE TABLE IF NOT EXISTS customers (
    idcustomers INT PRIMARY KEY AUTO_INCREMENT,
    cusName VARCHAR(255) NOT NULL,
    cusGender VARCHAR(10) NOT NULL,
    cusAge INT NOT NULL,
    cusPhone VARCHAR(20) NOT NULL,
    cusSeat INT NOT NULL,
    iddepartures INT,
    FOREIGN KEY (iddepartures) REFERENCES departures(iddepartures)
);
