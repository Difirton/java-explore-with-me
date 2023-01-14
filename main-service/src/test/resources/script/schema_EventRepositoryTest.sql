INSERT INTO users (email, name)
VALUES ('test1@mail.ru', 'test_1'),
       ('test2@mail.ru', 'test_2'),
       ('test3@mail.ru', 'test_3'),
       ('test4@mail.ru', 'test_4');
INSERT INTO events (title, annotation, description, initiator_id, state, likes_rating)
VALUES ('Test 1', 'test1 annot', 'test1 description', 1, 'PENDING', 2),
       ('Test 2', 'test2 annot', 'test2 description', 1, 'PENDING', 0),
       ('Test 3', 'test3 annot', 'test3 description', 2, 'PENDING', 1);
INSERT INTO likes (reviewer_id, event_id, is_like)
VALUES (2, 1, true),
       (2, 2, true),
       (3, 3, false);
