CREATE TABLE FIRST_TABLE (somekey integer not null primary key, one_attribute varchar(20) not null, snd_attrib date null);

INSERT INTO FIRST_TABLE VALUES (100, 'HUNDRED', SYSDATE()), (200, 'TWO HUNDRED', NULL), (300, 'THREE HUNDRED', SYSDATE());

CREATE TABLE SECOND_TABLE (sndkey varchar(5) not null primary key, disposable_date date null, a_number decimal(10,2) not null);

INSERT INTO SECOND_TABLE VALUES ('1ST', SYSDATE(), 200.3), ('2ND', SYSDATE(), 0.4), ('3TH', SYSDATE(), -1.0);

CREATE TABLE THIRD_TABLE (somekey integer not null primary key, one_attribute varchar(80) not null, snd_attrib date null);

INSERT INTO THIRD_TABLE (somekey, one_attribute) VALUES (100, 'one hundred');
INSERT INTO THIRD_TABLE (somekey, one_attribute) VALUES (200, 'two hundred');
INSERT INTO THIRD_TABLE (somekey, one_attribute) VALUES (220, 'more than two hundred');

CREATE TABLE FOURTH_TABLE (somekey integer not null primary key, one_attribute varchar(20) not null, snd_attrib date null);

CREATE TABLE FIFTH_TABLE (somekey integer not null primary key identity, one_attribute varchar(20) not null, snd_attrib date null);

INSERT INTO FIFTH_TABLE (one_attribute) values ('attr_5_1'), ('attr_5_2'), ('attr_5_3');

CREATE TABLE SQL_REPORT (name varchar(20) not null primary key, sql varchar(255) not null);

INSERT INTO SQL_REPORT (name, sql) values ('first', 'select * from FIRST_TABLE order by somekey desc');
INSERT INTO SQL_REPORT (name, sql) values ('3rd', 'select * from THIRD_TABLE where somekey > 200');
