USE llmeta;

INSERT INTO member (name, email, password, job)
VALUES ('test@test.com', 'testpw', 'testuser', 'tester');

-- paper
INSERT INTO extraction (member_id, type, meta_data, created_at, model_id)
VALUES (1, 'paper', '{title: test title, author: test author}', CURRENT_TIMESTAMP, 1);

-- image
INSERT INTO extraction (member_id, type, meta_data, created_at, model_id)
VALUES (1, 'image', '{type: human, age: teenage}', CURRENT_TIMESTAMP, 2);

INSERT INTO evaluation (extraction_id, rate, feedback, created_at)
VALUES (1, 1.3, 'weird model!!', CURRENT_TIMESTAMP);