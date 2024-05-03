USE llmeta;

DROP TABLE IF EXISTS evaluation;
DROP TABLE IF EXISTS extraction;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    password VARCHAR(100) NOT NULL ,
    job VARCHAR(100) NOT NULL,
    UNIQUE KEY email_unique (email)
);

CREATE TABLE extraction (
    extraction_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    type VARCHAR(100) NOT NULL,
    file_name VARCHAR(255),
    meta_data VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    model_id INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

CREATE TABLE evaluation (
    evaluation_id INT PRIMARY KEY AUTO_INCREMENT,
    extraction_id INT NOT NULL,
    rate DOUBLE NOT NULL,
    feedback VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (extraction_id) REFERENCES extraction(extraction_id)
);