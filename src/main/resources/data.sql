DROP TABLE IF EXISTS employees;

CREATE TABLE employees(
   id INT AUTO_INCREMENT PRIMARY KEY,
   first_name VARCHAR(250) NOT NULL,
   last_name VARCHAR(250) NOT NULL,
   email VARCHAR(250) NOT NULL,
   role VARCHAR(250) NOT NULL DEFAULT 'User',
   password VARCHAR(250) NOT NULL
);

INSERT INTO employees(first_name,last_name,email,role,password) VALUES
('Laurent','Gina','laurent@gmail.com','User','laurent'),
('john','KABORE','john@gmail.com','User','john')