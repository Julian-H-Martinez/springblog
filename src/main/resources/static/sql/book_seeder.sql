USE springblog_db;

DROP TABLE books;
DROP TABLE authors;

INSERT INTO authors(name) VALUES ('Victor Hugo'), ('George Orwell'), ('John Steinbeck');

INSERT INTO books(author_id, title) VALUES (1, 'Notre-Dame de Paris'), (2, 'Animal Farm'), (3, 'Of Mice and Men');

INSERT INTO books(title, author_id) VALUES ('Les Miserables', 1);

INSERT INTO genres(name) VALUES ('fiction'), ('non-fiction'), ('satire'), ('feel-good'), ('tragedy'), ('has rabbits');

INSERT INTO books_genres(book_id, genre_id) VALUES (1, 1), (1, 5), (2, 1), (2, 3), (2, 6), (3, 1), (3, 5), (3, 6), (4, 1), (4, 5);

INSERT INTO genres(id, name) VALUES (99, 'ranch dressing');