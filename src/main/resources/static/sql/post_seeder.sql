USE springblog_db;

DROP TABLE posts;
DROP TABLE users;

INSERT INTO users (id, first_name, last_name, username, email, password)
VALUES (1, 'Julian', 'Martinez', 'julianMartinez', 'julian@mail.com', 'password'),
       (2, 'Jessica', 'Martinez', 'jessicaMartinez', 'jessica@mail.com', 'password'),
       (3, 'Bubba', 'Martinez', 'bubbaMartinez', 'bubba@mail.com', 'password'),
       (4, 'Bam', 'Martinez', 'bamMartinez', 'bam@mail.com', 'password');

INSERT INTO posts(user_id, title, body)
VALUES (1, 'Titans Are The Best', 'I dare you to argue your team'),
       (1, 'Cowboys Cannot Win The Big Game', 'They constantly prove that they are not built for big time games.'),
       (2, 'Cowboys are #1', 'From Dak to Shultz we can not be beat'),
       (3, 'Drop Shipping Daily', 'Learn from the greats on how to drop daily and efficiently');
-- insert into books(author_id, title) VALUES (1, 'Notre-Dame de Paris'), (2, 'Animal Farm'), (3, 'Of Mice and Men');
--
-- insert into books(title, author_id) VALUES ('Les Miserables', 1);
--
-- insert into genres(name) VALUES ('fiction'), ('non-fiction'), ('satire'), ('feel-good'), ('tragedy'), ('has rabbits');
--
-- insert into books_genres(book_id, genre_id) VALUES (1, 1), (1, 5), (2, 1), (2, 3), (2, 6), (3, 1), (3, 5), (3, 6), (4, 1), (4, 5);

