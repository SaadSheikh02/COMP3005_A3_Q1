-- COMP 3005 WINTER 2024
-- Student Name: Saad Sheikh
-- Student Number: 101209067

-- Students Table
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
	email TEXT NOT NULL,
    enrollment_date DATE
);