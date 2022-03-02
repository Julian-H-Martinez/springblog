USE springblog_db;

-- DROP TABLE posts;
-- DROP TABLE users;

# INSERT INTO users (id, first_name, last_name, username, email, password)
# VALUES (1, 'Julian', 'Martinez', 'julianMartinez', 'julian@mail.com', 'password'),
#        (2, 'Jessica', 'Martinez', 'jessicaMartinez', 'jessica@mail.com', 'password'),
#        (3, 'Bubba', 'Martinez', 'bubbaMartinez', 'bubba@mail.com', 'password'),
#        (4, 'Bam', 'Martinez', 'bamMartinez', 'bam@mail.com', 'password');

INSERT INTO users(id, username, email, password)
VALUES(1, 'bruce', 'bruce@lee.com', 'password'),
       (2, 'bob', 'bob@marley.com', 'password');

INSERT INTO posts(user_id, title, body)
VALUES (1, 'Titans Are The Best', 'I dare you to argue your team'),
       (1, 'Cowboys Cannot Win The Big Game', 'They constantly prove that they are not built for big time games.'),
       (2, 'Cowboys are #1', 'From Dak to Shultz we can not be beat'),
       (2, 'Drop Shipping Daily', 'Learn from the greats on how to drop daily and efficiently');

