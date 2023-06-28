--O(1)?
SELECT MAX(registered) FROM student;
SELECT MIN(registered) FROM student;

--O(n)
SELECT student.firstName, student.lastName, course.title FROM student
JOIN takes ON student.ID = takes.ID
JOIN course ON takes.course_id = course.course_id
WHERE title LIKE 'Introduction to Anthropology';

--O(logn)
SELECT firstName FROM student WHERE firstName LIKE '#FEM#';
