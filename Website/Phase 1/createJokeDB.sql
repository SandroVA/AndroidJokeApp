create database jokes; 
use jokes; 
  
  
  
DROP TABLE IF EXISTS jokes; 
CREATE TABLE jokes 
( 
    _id             INTEGER PRIMARY KEY AUTO_INCREMENT, 
    category        VARCHAR(20), 
    s_category      VARCHAR(20), 
    joketype        INTEGER, 
    s_description   VARCHAR(20), 
    question_text   VARCHAR(50), 
    answer_text     VARCHAR(50), 
    monologue_text  VARCHAR(250), 
    rating          INTEGER, 
    comments        VARCHAR(50), 
    source          VARCHAR(150), 
    release_status  TINYINT, 
    create_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    modify_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP

); 
  
DROP TABLE IF EXISTS category; 
CREATE TABLE category 
( 
    _id         INTEGER PRIMARY KEY AUTO_INCREMENT, 
    category    VARCHAR(20), 
    s_category  VARCHAR(20), 
    num_jokes   INTEGER
); 
  
DROP TABLE IF EXISTS users; 
CREATE TABLE users 
( 
    _id         INTEGER PRIMARY KEY AUTO_INCREMENT, 
    username    VARCHAR(20), 
    password    VARCHAR(20), 
    first_name  VARCHAR(20), 
    last_name   VARCHAR(20), 
    userlevel   INTEGER, 
    create_date DATETIME, 
    modify_date DATETIME 
);