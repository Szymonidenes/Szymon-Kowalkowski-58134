CREATE TABLE employee (
  id INT AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(50),
  lastname VARCHAR(50),
  street VARCHAR(100),
  zipcode VARCHAR(10),
  city VARCHAR(50),
  photo LONGBLOB
);