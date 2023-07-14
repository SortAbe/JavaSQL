SELECT student.firstName, student.lastName, course.title FROM student
JOIN takes ON student.ID = takes.ID
JOIN course ON takes.course_id = course.course_id
WHERE title LIKE 'Introduction to Anthropology';

