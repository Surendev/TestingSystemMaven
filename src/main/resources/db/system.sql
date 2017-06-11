DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS students;
CREATE TABLE students
(
  id                 INTEGER PRIMARY KEY NOT NULL,
  first_name         TEXT                NOT NULL,
  last_name          TEXT                NOT NULL,
  middle_name        TEXT                NOT NULL,
  course             INT     DEFAULT 1 NOT NULL,
  "group"            TEXT    DEFAULT '' NOT NULL
);


CREATE TABLE answers
(
  id          INTEGER PRIMARY KEY  AUTOINCREMENT,
  text        TEXT    NOT NULL,
  to_question INTEGER NOT NULL,
  CONSTRAINT answers_to_question FOREIGN KEY (to_question) REFERENCES questions (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE questions
(
  id       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  question TEXT                              NOT NULL,
  rating   INTEGER                           NOT NULL,
  topic    NVARCHAR(50)                      NOT NULL,
  answer   VARCHAR(64)                       NOT NULL
);
CREATE INDEX questions_rating
  ON questions (rating);



INSERT INTO students (id, first_name, last_name, middle_name, course, "group")
VALUES (1, 'Սուրեն', 'Շառյան', 'Անդրանիկի', 3, 'Հ420-2');
INSERT INTO students (id, first_name, last_name, middle_name, course, "group")
VALUES (2, 'Արտյոմ', 'Գիշյան', 'Արմենի', 3, 'Հ420-2' );
INSERT INTO students (id, first_name, last_name, middle_name, course, "group" )
VALUES (3, 'Կորյուն', 'Գրիգորյան', 'Հրաչիկի', 3, 'Հ420-2');
INSERT INTO students (id, first_name, last_name, middle_name, course, "group"  )
VALUES (4, 'Կարեն', 'Սարգսյան', 'Հրաչյայի', 3, 'Հ420-2');
INSERT INTO students (id, first_name, last_name, middle_name, course, "group"  )
VALUES (5, 'Ալեքսան', 'Պողոսյան', 'Կամոյի', 3, 'Հ420-2');
