SELECT firstName, lastName FROM student WHERE credits = 0;
SELECT lastName, gender FROM student WHERE credits = 3 AND firstName = '#FEM#';
SELECT firstName, gender FROM student WHERE credits = 3 AND firstName = '#MAL#';
SELECT * FROM student WHERE credits = 3 AND firstName = '#FEM#';
SELECT ID FROM student WHERE credits = 3 AND lastName = '#LAS#';
SELECT firstName, dept_name FROM student WHERE credits = 3 AND lastName = '#LAS#';
SELECT registered, lastName FROM student WHERE credits = 6 OR credits = 9;
SELECT firstName, lastName FROM student WHERE credits = 12 OR credits = 15 AND credits < 18;
SELECT lastName, firstName FROM student WHERE credits BETWEEN 24 AND 26;
SELECT firstName, credits FROM student WHERE ID = 1;
SELECT firstName, dept_name FROM student WHERE ID = 1000000;
SELECT firstName FROM student WHERE firstName LIKE '#FEM#';


SELECT firstName, lastName FROM instructor WHERE salary < 81000;
SELECT firstName, dept_name FROM instructor WHERE dept_name = '#DEP#';
SELECT firstName, gender FROM instructor WHERE ID < 10;
SELECT * FROM instructor WHERE salary = 100000 AND firstName = '#FEM#';
SELECT ID FROM instructor WHERE salary = 70000 AND lastName = '#LAS#';
SELECT ID, firstName FROM instructor WHERE salary = 90000 AND lastName = '#LAS#';
SELECT lastName, salary FROM instructor WHERE firstName LIKE '' AND firstName LIKE '#MAL#';
SELECT firstName, gender FROM instructor WHERE salary = 90001 OR salary = 90002;
SELECT lastName, firstName FROM instructor WHERE salary = 100001 OR salary = 100002 AND salary < 100003;
SELECT firstName, dept_name FROM instructor WHERE salary BETWEEN 80000 AND 70000;
SELECT dept_name, gen FROM instructor WHERE ID = 1;
SELECT firstName, lastName FROM instructor WHERE ID = 100000;

SELECT addr1, city FROM sAddress WHERE ID < 1000;
SELECT city, state FROM sAddress WHERE city = '#CIT#';
SELECT zip, addr2 FROM sAddress WHERE zip = 3000;
SELECT * FROM sAddress WHERE city = '#CIT#' AND state = '#STA#';
SELECT ID FROM sAddress WHERE zip = 4000 AND state = '#STA#';
SELECT addr1, addr2 FROM sAddress WHERE state = '#STA#' AND city = '#CIT#';
SELECT addr1, city FROM sAddress WHERE city LIKE 'eta' AND state LIKE '#STA#';
SELECT addr2, zip, city, state FROM sAddress WHERE zip = 3002 OR zip = 3001;
SELECT city, state FROM sAddress WHERE zip = 3003 OR zip = 2002 AND zip < 5000;
SELECT state, zip FROM sAddress WHERE zip BETWEEN 6000 AND 7000;
SELECT addr2, zip FROM sAddress WHERE ID = 1;
SELECT zip, city FROM sAddress WHERE ID = 9000;

SELECT email, phone FROM sContact WHERE phone LIKE '##' AND email LIKE '##';
SELECT ID, email FROM sContact WHERE email LIKE '##' AND phone LIKE '##';
SELECT phone, email FROM sContact WHERE email LIKE '##' OR email LIKE '##';
SELECT ID, phone FROM sContact WHERE email LIKE '##' AND phone LIKE '##' OR email like '##';
SELECT phone, email FROM sContact WHERE email LIKE '##' AND phone LIKE '##' OR ID < 10000;
SELECT phone, email FROM sContact WHERE ID BETWEEN 1000 AND 5000 AND email like '##';
