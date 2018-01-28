DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS topics;
DROP TABLE IF EXISTS students_ratings;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;

CREATE TABLE groups
(
  id             INTEGER PRIMARY KEY NOT NULL,
  group_name     TEXT                NOT NULL,
  srudents_count INT                 NOT NULL,
  srudents_email TEXT                NOT NULL
);


CREATE TABLE students
(
  id          INTEGER PRIMARY KEY NOT NULL,
  first_name  TEXT                NOT NULL,
  last_name   TEXT                NOT NULL,
  middle_name TEXT                NOT NULL,
  course      INT DEFAULT 1       NOT NULL,
  group_id    INT                 NOT NULL,
  CONSTRAINT students_group FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE subjects (
  id     INTEGER PRIMARY KEY,
  name   TEXT NOT NULL,
  credit INT  NOT NULL
);

CREATE TABLE students_ratings
(
  id         INTEGER PRIMARY KEY NOT NULL,
  student_id INT                 NOT NULL,
  subject_id INT                 NOT NULL,
  date       DATE                NOT NULL,
  rating     INT                 NOT NULL,
  CONSTRAINT to_student FOREIGN KEY (student_id) REFERENCES students (id),
  CONSTRAINT to_subject FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

CREATE TABLE topics (
  id         INTEGER PRIMARY KEY,
  topic_name TEXT NOT NULL,
  subject_id INT  NOT NULL,
  CONSTRAINT topic_to_subject FOREIGN KEY (subject_id) REFERENCES subjects (id)
);


CREATE TABLE questions
(
  id            INTEGER PRIMARY KEY    NOT NULL,
  question_text TEXT                   NOT NULL,
  rating        INTEGER                NOT NULL,
  topic_id      INTEGER                NOT NULL,
  answer_hash   VARCHAR(64)            NOT NULL,
  CONSTRAINT question_to_topics FOREIGN KEY (topic_id) REFERENCES topics (id)
);


CREATE TABLE answers
(
  id          INTEGER PRIMARY KEY,
  answer_text TEXT    NOT NULL,
  question_id INTEGER NOT NULL,
  CONSTRAINT answers_to_question FOREIGN KEY (question_id) REFERENCES questions (id)
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
