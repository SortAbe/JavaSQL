SELECT student.firstName, student.lastName, course.title FROM student
JOIN takes ON student.ID = takes.ID
JOIN course ON takes.course_id = course.course_id
WHERE title LIKE '#DEP#';

SELECT student.firstName, student.lastName FROM student
JOIN sAddress ON student.ID = sAddress.ID
WHERE city LIKE '#CIT#';

SELECT * FROM student
JOIN sContact ON student.ID = sContact.ID
WHERE phone LIKE '888';

SELECT instuctor.firstName, instuctor.lastName, course.title FROM student
JOIN teaches ON instuctor.ID = teaches.ID
JOIN course ON teaches.course_id = course.course_id
WHERE title LIKE '#DEP#';

SELECT course.title, course.credits FROM course
JOIN class ON course.course_id = class.course_id
WHERE semester LIKE 'Winter';
