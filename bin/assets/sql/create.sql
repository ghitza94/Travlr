drop table AIRLINE cascade constraints; 
drop table ADDRESS cascade constraints; 
drop table CITY cascade constraints; 
drop table FRIENDS cascade constraints; 
drop table PREFERENCE cascade constraints; 
drop table TRIP cascade constraints; 
drop table FLIGHT cascade constraints; 
drop table TRIP_FLIGHT cascade constraints; 
drop table USER_INFO cascade constraints; 
drop table USER_PREFERENCE cascade constraints; 
drop table USER_TRIP cascade constraints;
drop table USERS cascade constraints; 
--DROP SEQUENCE users_seq;

CREATE TABLE ADDRESS
  (
    zip_code    INTEGER NOT NULL ,
    country     VARCHAR2 (255) NOT NULL ,
    state       VARCHAR2 (255) ,
    county      VARCHAR2 (255) ,
    locality    VARCHAR2 (255) ,
    street_name VARCHAR2 (255) ,
    street_no   VARCHAR2 (255)
  ) ;
ALTER TABLE ADDRESS ADD CONSTRAINT ADDRESS_PK PRIMARY KEY ( zip_code ) ;

CREATE TABLE AIRLINE
  (
    airline_id   INTEGER NOT NULL ,
    airline_name VARCHAR2 (255) NOT NULL ,
    rating_num	INTEGER ,
    rating_sum	INTEGER
  ) ;
ALTER TABLE AIRLINE ADD CONSTRAINT AIRLINE_PK PRIMARY KEY ( airline_id ) ;

CREATE TABLE AIRLINE_USER
  (
    airline_user_id INTEGER NOT NULL ,
    user_id       INTEGER NOT NULL ,
    rating             INTEGER
  ) ;
ALTER TABLE AIRLINE_USER ADD CONSTRAINT AIRLINE_USER_UQ UNIQUE ( airline_user_id , user_id ) ;

CREATE TABLE CITY
  (
    city_id   INTEGER NOT NULL ,
    city_name VARCHAR2 (255) NOT NULL ,
    country   VARCHAR2 (255) NOT NULL ,
    state     VARCHAR2 (255) ,
    county    VARCHAR2 (255)
  ) ;
ALTER TABLE CITY ADD CONSTRAINT CITY_PK PRIMARY KEY ( city_id ) ;

CREATE TABLE COUNTRY
  ( 
  country_id INTEGER NOT NULL , 
  name VARCHAR2(255)
  ) ;
ALTER TABLE COUNTRY ADD CONSTRAINT COUNTRY_PK PRIMARY KEY ( country_id ) ;

CREATE TABLE FRIENDS
  (    
  user1 INTEGER NOT NULL ,    
  user2 INTEGER NOT NULL  
  ) ;
ALTER TABLE FRIENDS ADD CONSTRAINT FRIENDS_PK PRIMARY KEY ( user1, user2 ) ;

CREATE TABLE PREFERENCE
  (
    preference_id    INTEGER NOT NULL ,
    preference_type  VARCHAR2 (255) NOT NULL ,
    preference_value VARCHAR2 (255) NOT NULL
  ) ;
ALTER TABLE PREFERENCE ADD CONSTRAINT PREFERENCE_PK PRIMARY KEY ( preference_id) ;

CREATE TABLE TRIP
  (
    trip_id             INTEGER NOT NULL ,
    origin_address      INTEGER NOT NULL ,
    destination_address INTEGER NOT NULL ,
    departure_time      DATE NOT NULL ,
    arrival_date        DATE NOT NULL
  ) ;
ALTER TABLE TRIP ADD CONSTRAINT TRIP_PK PRIMARY KEY ( trip_id ) ;

CREATE TABLE FLIGHT
  (
    flight_id INTEGER NOT NULL ,
    src       INTEGER NOT NULL ,
    dst       INTEGER NOT NULL
  ) ;
ALTER TABLE FLIGHT ADD CONSTRAINT FLIGHT_PK PRIMARY KEY ( flight_id ) ;

CREATE TABLE TRIP_FLIGHT
  (
    trip        INTEGER NOT NULL ,
    flight      INTEGER NOT NULL ,
    flight_date DATE ,
    flight_num  INTEGER
  ) ;

CREATE TABLE USERS
  (
    user_id   INTEGER NOT NULL ,
    username  VARCHAR2(100) ,
    password  VARCHAR2(100) ,
    user_info INTEGER NOT NULL
  ) ;
ALTER TABLE USERS ADD CONSTRAINT USER_PK PRIMARY KEY ( user_id ) ;

CREATE SEQUENCE users_seq;

CREATE OR REPLACE TRIGGER trigger_users_incr
BEFORE INSERT ON USERS
FOR EACH ROW
	BEGIN
		-- :new.user_id := users_seq.NEXTVAL;
		SELECT users_seq.NEXTVAL
		INTO :new.user_id
		FROM DUAL;
	END;
 /

CREATE TABLE USER_INFO
  (
    user_info_id INTEGER NOT NULL ,
    first_name   VARCHAR2 (255) NOT NULL ,
    middle_name  VARCHAR2 (255) ,
    last_name    VARCHAR2 (255) ,
    birthdate    DATE ,
    gender       VARCHAR2 (10) NOT NULL,
    email        VARCHAR2 (255) NOT NULL ,
    address      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_PK PRIMARY KEY ( user_info_id );

CREATE TABLE USER_PREFERENCE
  (
    user_preference_id INTEGER NOT NULL ,
    user_id            INTEGER NOT NULL ,
    preference_id      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_PK PRIMARY KEY (user_preference_id ) ;

CREATE TABLE USER_TRIP
  (
    user_trip_id INTEGER NOT NULL ,
    user_id      INTEGER NOT NULL ,
    trip_id      INTEGER NOT NULL
  ) ;
ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_PK PRIMARY KEY ( user_trip_id );

ALTER TABLE FRIENDS ADD CONSTRAINT FRIENDS1_USER_FK FOREIGN KEY ( user1 ) REFERENCES USERS ( user_id ) ;

ALTER TABLE FRIENDS ADD CONSTRAINT FRIENDS2_USER_FK FOREIGN KEY ( user2 ) REFERENCES USERS ( user_id ) ;

ALTER TABLE TRIP ADD CONSTRAINT TRIP_DESTINATION_ADDRESS_FK FOREIGN KEY ( destination_address ) REFERENCES ADDRESS ( zip_code ) ;

ALTER TABLE TRIP ADD CONSTRAINT TRIP_ORIGIN_ADDRESS_FK FOREIGN KEY ( origin_address ) REFERENCES ADDRESS ( zip_code ) ;

ALTER TABLE FLIGHT ADD CONSTRAINT ADDRESS1 FOREIGN KEY ( src ) REFERENCES ADDRESS ( zip_code ) ;

ALTER TABLE FLIGHT ADD CONSTRAINT ADDRESS2 FOREIGN KEY ( dst ) REFERENCES ADDRESS ( zip_code ) ;

ALTER TABLE USER_INFO ADD CONSTRAINT USER_INFO_ADDRESS_FK FOREIGN KEY ( address ) REFERENCES ADDRESS ( zip_code ) ;

ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_PREFERENCE_FK FOREIGN KEY ( preference_id ) REFERENCES PREFERENCE ( preference_id ) ;

ALTER TABLE USER_PREFERENCE ADD CONSTRAINT USER_PREFERENCE_USER_FK FOREIGN KEY ( user_id ) REFERENCES USERS ( user_id ) ;

ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_TRIP_FK FOREIGN KEY ( trip_id )
REFERENCES TRIP ( trip_id ) ON
DELETE CASCADE ;

ALTER TABLE USER_TRIP ADD CONSTRAINT USER_TRIP_USER_FK FOREIGN KEY ( user_id )
REFERENCES USERS ( user_id ) ;

ALTER TABLE USERS ADD CONSTRAINT USER_USER_INFO_FK FOREIGN KEY ( user_info )
REFERENCES USER_INFO ( user_info_id ) ;

/*
BEGIN
  FOR c IN (SELECT TABLE_NAME FROM USER_TABLES) 
  LOOP
    EXECUTE IMMEDIATE ('DROP TABLE '||c.table_name||' CASCADE CONSTRAINTS');
  END LOOP;
END;
*/