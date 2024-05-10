USE llmeta;

INSERT INTO member (name, email, password, job)
VALUES ('testuser', 'test@test.com', 'testpw', 'tester');

-- paper
INSERT INTO extraction (member_id, type, meta_data, model_id)
VALUES (1, 'paper', '{title: test title, author: test author}', 1);

-- image
INSERT INTO extraction (member_id, type, meta_data, model_id)
VALUES (1, 'image', '{type: human, age: teenage}', 2);

INSERT INTO evaluation (extraction_id, rate, feedback, created_at)
VALUES (1, 1.3, 'weird model!!', CURRENT_TIMESTAMP);