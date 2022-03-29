DROP DATABASE IF EXISTS BADMINTON;

CREATE DATABASE BADMINTON;

USE BADMINTON;
DROP TABLE IF EXISTS Players;

CREATE TABLE Players
(
player_id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
first_name varchar(30),
last_name varchar(30),
weight float,
height float,
dateOfBirth Date,
championship_wins int
);

insert into Players values(null,"Jim","Jam",60.50,160,"1990/12/10", 0),
(null,"John","Jameson",60.50,160,"1970/1/17", 0),
(null,"Michael","Flynn",75.50,170,"2000/9/14", 1),
(null,"Tim","Adejumo",65.50,150,"2002/4/1", 1),
(null,"Nikita","Fedans",68.90,160,"2001/5/12", 2),
(null,"Jimmy","Jammy",60.50,160,"1970/12/10", 2),
(null,"Tim","Jameson",60.50,160,"1960/1/12", 3),
(null,"Michelle","Fly",75.50,170,"2002/9/19", 0),
(null,"Tim","Ada",65.50,150,"2006/2/1", 0),
(null,"Nikki","Federal",68.90,160,"2005/3/12", 0);
