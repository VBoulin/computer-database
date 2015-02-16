drop table if exists user;

CREATE TABLE user 
( id integer not null auto_increment
, username 			varchar(50) unique
, password	varchar(50)
, enabled 			boolean
, accountNonExpired 			boolean
, credentialsNonExpired 			boolean
, accountNonLocked 			boolean
, role 				varchar(50)
, constraint pk_user primary key (id));

INSERT INTO user VALUES (1, 'user', 'password', TRUE, TRUE, TRUE, TRUE, 'ROLE_ADMIN');
INSERT INTO user VALUES (2, 'user2', 'password2', TRUE, TRUE, TRUE, TRUE, 'ROLE_USER');