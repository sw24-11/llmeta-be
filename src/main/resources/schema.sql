USE llmeta;

CREATE TABLE member (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    password VARCHAR(100) NOT NULL ,
    job VARCHAR(100) NOT NULL,
    UNIQUE KEY email_unique (email)
);

CREATE TABLE model (
    model_id INT PRIMARY KEY AUTO_INCREMENT,
    model_name VARCHAR(100) NOT NULL,
    model_spec VARCHAR(1000) NOT NULL
);

CREATE TABLE extraction (
    extraction_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    is_file BOOLEAN NOT NULL,
    link VARCHAR(255),
    file_name VARCHAR(255),
    file_key VARCHAR(255),
    file_url VARCHAR(255),
    meta_data VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    model_id INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    FOREIGN KEY (model_id) REFERENCES model(model_id)
);

CREATE TABLE evaluation (
    evaluation_id INT PRIMARY KEY AUTO_INCREMENT,
    extraction_id INT NOT NULL,
    rate DOUBLE NOT NULL,
    feedback VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (extraction_id) REFERENCES extraction(extraction_id)
);