DROP ALL OBJECTS;

CREATE TABLE be_roles (
  name VARCHAR_IGNORECASE(20) PRIMARY KEY
);

CREATE TABLE auth_users (
  userid INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR_IGNORECASE(250) NOT NULL,
  password VARCHAR_IGNORECASE(250) NOT NULL,
  accountNonExpired BOOLEAN NOT NULL DEFAULT TRUE,
  accountNonLocked BOOLEAN NOT NULL DEFAULT TRUE,
  credentialsNonExpired BOOLEAN NOT NULL DEFAULT TRUE,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  deleted BOOLEAN DEFAULT FALSE,
  createdDate TIMESTAMP,
  deletedDate TIMESTAMP
);

CREATE TABLE user_roles (
  userId INT NOT NULL,
  role VARCHAR_IGNORECASE(20) NOT NULL,
  foreign key (userId) references auth_users(userId),
  foreign key (role) references be_roles(name)
);

ALTER TABLE user_roles ADD PRIMARY KEY (userId, role);

CREATE TABLE accounts (
  accountId INT AUTO_INCREMENT  PRIMARY KEY,
  userId INT NOT NULL,
  comment VARCHAR_IGNORECASE(255) NOT NULL,
  isDeleted BOOLEAN,
  openedDate TIMESTAMP,
  closedDate TIMESTAMP,
  foreign key (userId) references auth_users(userId)
);

CREATE TABLE operations (
  operationId INT AUTO_INCREMENT  PRIMARY KEY,
  accountId INT NOT NULL,
  difference decimal (20,2) NOT NULL,
  isConfirmed BOOLEAN,
  proceededDate TIMESTAMP,
  foreign key (accountId) references accounts(accountId)
);

INSERT INTO be_roles (name) VALUES ( 'ADMIN' ),( 'USER' );

INSERT INTO auth_users (username,password,enabled, deleted,createdDate,deletedDate)
VALUES ('administrator','$2a$10$6TsPGbR2TgcuCe/DZCPkNOOYFeYqclmq4tSC9uvvc77t/vteja5nq',true,false,'1900-01-01 00:00:00.0','1900-01-01 00:00:00.0'), --123
('user1','$2a$10$6TsPGbR2TgcuCe/DZCPkNOOYFeYqclmq4tSC9uvvc77t/vteja5nq',true,false,'1900-01-01 00:00:00.0','1900-01-01 00:00:00.0'); --123

INSERT INTO user_roles (userId, role) VALUES ( 1,'ADMIN' ),( 2,'USER' );

INSERT INTO accounts (userId,comment,isDeleted,openedDate,closedDate)
VALUES ( 1,'acc one',false,'1900-01-01 00:00:00.0',NULL ),
 ( 1,'acc two',false,'2000-01-01 00:00:00.0',NULL ),
 ( 2,'acc three',false,'2000-01-01 00:00:00.0',NULL );

INSERT INTO operations (accountId,difference,isConfirmed,proceededDate)
VALUES ( 1,1000.01,true,'2010-01-01 00:00:00.0'),
       ( 2,-500.01,true,'2010-07-07 00:00:00.0' ),
       ( 2,10000,true,'2010-03-02 00:00:00.0' );