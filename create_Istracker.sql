/*
Purpose: script creates the tables for the Programming iStudentTracker database
Authors: Iara Santos
Date: 01/28/2022
*/

CREATE DATABASE IF NOT EXISTS istracker;

USE istracker;

CREATE TABLE Login
(
loginID INT PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(45) NOT NULL,
password VARCHAR(45) NOT NULL
);

CREATE TABLE Teacher
(
teacherID INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(45) NOT NULL,
last_Name  VARCHAR(45) NOT NULL,
phone VARCHAR(45) NOT NULL,
email      VARCHAR(45)
);

CREATE TABLE Section
(
sectionID INT PRIMARY KEY AUTO_INCREMENT,
teacherID INT,
semester enum('Winter', 'Spring', 'Summer', 'Fall') NOT NULL,
FOREIGN KEY (teacherID) REFERENCES Teacher(teacherID)
);

CREATE TABLE Course
(
CourseID INT PRIMARY KEY AUTO_INCREMENT,
sectionID INT,
title VARCHAR(45) NOT NULL,
year INT,
FOREIGN KEY (sectionID) REFERENCES Section(sectionID)
);

CREATE TABLE Grade
(
gradeID INT PRIMARY KEY AUTO_INCREMENT,
sectionID INT,
letterGrade CHAR(2),
numberGrade NUMERIC(4, 2),
FOREIGN KEY (sectionID) REFERENCES Section(sectionID)
);

CREATE TABLE Parent
(
parentID INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(45) NOT NULL,
last_Name  VARCHAR(45) NOT NULL,
phone VARCHAR(45) NOT NULL,
email      VARCHAR(45)
);

CREATE TABLE Student
(
studentID INT PRIMARY KEY AUTO_INCREMENT,
gradeID INT,
first_name VARCHAR(45) NOT NULL,
last_Name  VARCHAR(45) NOT NULL,
phone VARCHAR(45) NOT NULL,
email      VARCHAR(45),
FOREIGN KEY (gradeID) REFERENCES Grade(gradeID)
);

CREATE TABLE StudentParents
(
parentID INT,
studentID INT,
FOREIGN KEY (parentID) REFERENCES Parent(parentID),
FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

CREATE TABLE ENROLLMENT
(
sectionID INT,
studentID INT,
FOREIGN KEY (sectionID) REFERENCES Section(sectionID),
FOREIGN KEY (studentID) REFERENCES Student(studentID)
);

INSERT INTO Login(email, password)
VALUES
('dumbledore@yahoo.com', '183258'),
('minerva@gmail.com', 'minerva'),
('ssnape@hogwarts.com', 'severus'),
('wolf@hotmail.com', 'remo');

INSERT INTO Teacher(first_name, last_name, phone, email)
VALUES
('Alvo', 'Dumbledore', '9999999999','dumbledore@yahoo.com'),
('Minerva','McGonagall', '8888888888', 'minerva@gmail.com'),
('Severus', 'Snape', '7777777777', 'ssnape@hogwarts.com'),
('Remo', 'Lupin', '6666666666', 'wolf@hotmail.com');

INSERT INTO Section(teacherID, semester)
VALUES
((SELECT teacherID from Teacher WHERE teacherId = 1), 'Winter'),
((SELECT teacherID from Teacher WHERE teacherId = 2), 'Spring'),
((SELECT teacherID from Teacher WHERE teacherId = 3), 'Fall'),
((SELECT teacherID from Teacher WHERE teacherId = 4), 'Summer');

INSERT INTO Course(sectionID, title, year)
VALUES
((SELECT sectionID from Section WHERE sectionID = 1), 'Defence Against the Dark Arts', 2019),
((SELECT sectionID from Section WHERE sectionID = 2), 'Transfiguration', 2019),
((SELECT sectionID from Section WHERE sectionID = 3), 'Potions', 2019),
((SELECT sectionID from Section WHERE sectionID = 4), 'Potions', 2019);

INSERT INTO Grade(sectionID, letterGrade, numberGrade)
VALUES
((SELECT sectionID from Section WHERE sectionID = 1), 'A', 95.25),
((SELECT sectionID from Section WHERE sectionID = 2), 'B', 89.20),
((SELECT sectionID from Section WHERE sectionID = 3), 'C', 84.00),
((SELECT sectionID from Section WHERE sectionID = 4), 'D', 75.25);

INSERT INTO Parent(first_name, last_name, phone, email)
VALUES
('James', 'Potter', '5555555555', 'jpotter@yahoo.com'),
('Lily', 'Potter', '5555555555', 'lpotter@gmail.com'),
('Arthur', 'Weasley', '3333333333', 'redhair@hotmail.com'),
('Molly', 'Weasley', '3333333333', 'redhair@hotmail.com'),
('Mr.', 'Granger', '4444444444', 'grangerfamily@gmail.com'),
('Mrs.', 'Granger', '4444444444', 'grangerfamily@gmail.com');

INSERT INTO Student(gradeID, first_name, last_name, phone, email)
VALUES
((SELECT gradeID from Grade WHERE gradeID = 3), 'Harry', 'Potter', '222222222', 'hpotter@gmail.com'),
((SELECT gradeID from Grade WHERE gradeID = 4), 'Ronny', 'Weasley', '1111111111', 'redhair@hotmail.com'),
((SELECT gradeID from Grade WHERE gradeID = 2),
'Gina', 'Weasley', '1111111111', 'redhair@hotmail.com'),
((SELECT gradeID from Grade WHERE gradeID = 1),
'Hermione', 'Granger', '1231231234', 'grangerfamily@gmail.com');

INSERT INTO StudentParents(parentID, studentID)
VALUES
((SELECT parentID from Parent WHERE parentID = 1), (SELECT studentID from Student WHERE studentID = 1)),
((SELECT parentID from Parent WHERE parentID = 2), (SELECT studentID from Student WHERE studentID = 1)),
((SELECT parentID from Parent WHERE parentID = 3), (SELECT studentID from Student WHERE studentID = 2)),
((SELECT parentID from Parent WHERE parentID = 4), (SELECT studentID from Student WHERE studentID = 2)),
((SELECT parentID from Parent WHERE parentID = 3), (SELECT studentID from Student WHERE studentID = 3)),
((SELECT parentID from Parent WHERE parentID = 4), (SELECT studentID from Student WHERE studentID = 3)),
((SELECT parentID from Parent WHERE parentID = 5), (SELECT studentID from Student WHERE studentID = 4)),
((SELECT parentID from Parent WHERE parentID = 6), (SELECT studentID from Student WHERE studentID = 4));

INSERT INTO Enrollment(sectionID, studentID)
VALUES
((SELECT sectionID from Section WHERE sectionID = 1), (SELECT studentID from Student WHERE studentID = 1)),
((SELECT sectionID from Section WHERE sectionID = 1), (SELECT studentID from Student WHERE studentID = 2)),
((SELECT sectionID from Section WHERE sectionID = 1), (SELECT studentID from Student WHERE studentID = 3)),
((SELECT sectionID from Section WHERE sectionID = 1), (SELECT studentID from Student WHERE studentID = 4));





