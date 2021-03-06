-- Generated by Oracle SQL Developer Data Modeler 4.0.3.853
--   at:        2015-04-24 09:25:50 EEST
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g




CREATE TABLE Address
  ( address_id INTEGER NOT NULL , city INTEGER NOT NULL
  ) ;
ALTER TABLE Address ADD CONSTRAINT Address_PK PRIMARY KEY ( address_id ) ;

CREATE TABLE Airline
  (
    airline_id   INTEGER NOT NULL ,
    airline_name VARCHAR2 ,
    rating_num   INTEGER ,
    rating_sum   INTEGER
  ) ;
ALTER TABLE Airline ADD CONSTRAINT Airline_PK PRIMARY KEY ( airline_id ) ;

CREATE TABLE Airline_User
  (
    Airline_airline_id INTEGER NOT NULL ,
    User_user_id       INTEGER NOT NULL ,
    rating             INTEGER
  ) ;
ALTER TABLE Airline_User ADD CONSTRAINT Airline_User__UN UNIQUE ( Airline_airline_id , User_user_id ) ;

CREATE TABLE City
  (
    city_id INTEGER NOT NULL ,
    name    VARCHAR2 ,
    country INTEGER NOT NULL
  ) ;
ALTER TABLE City ADD CONSTRAINT City_PK PRIMARY KEY ( city_id ) ;

CREATE TABLE Country
  ( country_id INTEGER NOT NULL , name VARCHAR2
  ) ;
ALTER TABLE Country ADD CONSTRAINT Country_PK PRIMARY KEY ( country_id ) ;

CREATE TABLE Flight
  (
    flight_id INTEGER NOT NULL ,
    src       INTEGER NOT NULL ,
    dst       INTEGER NOT NULL
  ) ;
ALTER TABLE Flight ADD CONSTRAINT Flight_PK PRIMARY KEY ( flight_id ) ;

CREATE TABLE Friends
  ( user1 INTEGER NOT NULL , user2 INTEGER NOT NULL
  ) ;
CREATE UNIQUE INDEX Friends__IDX ON Friends
  (
    user1 ASC , user2 ASC
  )
  ;

CREATE TABLE Trip
  ( trip_id INTEGER NOT NULL
  ) ;
ALTER TABLE Trip ADD CONSTRAINT Trip_PK PRIMARY KEY ( trip_id ) ;

CREATE TABLE Trip_Flight
  (
    trip        INTEGER NOT NULL ,
    flight      INTEGER NOT NULL ,
    flight_date DATE ,
    flight_num  INTEGER
  ) ;

CREATE TABLE "User"
  (
    user_id   INTEGER NOT NULL ,
    username  VARCHAR2 ,
    password  VARCHAR2 ,
    user_info INTEGER NOT NULL
  ) ;
ALTER TABLE "User" ADD CONSTRAINT User_PK PRIMARY KEY ( user_id ) ;

CREATE TABLE User_Info
  (
    user_info_id INTEGER NOT NULL ,
    first_name   VARCHAR2 ,
    last_name    VARCHAR2 ,
    birthdate    DATE ,
    gender       VARCHAR2
  ) ;
ALTER TABLE User_Info ADD CONSTRAINT User_Info_PK PRIMARY KEY ( user_info_id ) ;

CREATE TABLE User_Trip
  ( "user" INTEGER NOT NULL , trip INTEGER NOT NULL
  ) ;

ALTER TABLE Flight ADD CONSTRAINT Address1 FOREIGN KEY ( src ) REFERENCES Address ( address_id ) ;

ALTER TABLE Flight ADD CONSTRAINT Address2 FOREIGN KEY ( dst ) REFERENCES Address ( address_id ) ;

ALTER TABLE Airline_User ADD CONSTRAINT Airline_User_Airline_FK FOREIGN KEY ( Airline_airline_id ) REFERENCES Airline ( airline_id ) ;

ALTER TABLE Airline_User ADD CONSTRAINT Airline_User_User_FK FOREIGN KEY ( User_user_id ) REFERENCES "User" ( user_id ) ;

ALTER TABLE Address ADD CONSTRAINT City_FK FOREIGN KEY ( city ) REFERENCES City ( city_id ) ;

ALTER TABLE City ADD CONSTRAINT Country_FK FOREIGN KEY ( country ) REFERENCES Country ( country_id ) ;

ALTER TABLE User_Trip ADD CONSTRAINT Trip_FK FOREIGN KEY ( trip ) REFERENCES Trip ( trip_id ) ;

ALTER TABLE Trip_Flight ADD CONSTRAINT Trip_Flight_Flight_FK FOREIGN KEY ( flight ) REFERENCES Flight ( flight_id ) ;

ALTER TABLE Trip_Flight ADD CONSTRAINT Trip_Flight_Trip_FK FOREIGN KEY ( trip ) REFERENCES Trip ( trip_id ) ;

ALTER TABLE Friends ADD CONSTRAINT User1_FK FOREIGN KEY ( user1 ) REFERENCES "User" ( user_id ) ;

ALTER TABLE Friends ADD CONSTRAINT User2_FK FOREIGN KEY ( user2 ) REFERENCES "User" ( user_id ) ;

ALTER TABLE User_Trip ADD CONSTRAINT User_FK FOREIGN KEY ( "user" ) REFERENCES "User" ( user_id ) ;

ALTER TABLE "User" ADD CONSTRAINT User_Info_FK FOREIGN KEY ( user_info ) REFERENCES User_Info ( user_info_id ) ;


-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            12
-- CREATE INDEX                             1
-- ALTER TABLE                             22
-- CREATE VIEW                              0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
