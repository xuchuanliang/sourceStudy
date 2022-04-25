use flyway_test;

CREATE TABLE personV111 (
                        id int(11) NOT NULL AUTO_INCREMENT,
                        first varchar(100) NOT NULL,
                        last varchar(100) NOT NULL,
                        dateofbirth DATE DEFAULT null,
                        placeofbirth varchar(100) not null,
                        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
