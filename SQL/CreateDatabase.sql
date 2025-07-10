CREATE TABLE users(
	student_number INT NOT NULL PRIMARY KEY UNIQUE, 
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    password TEXT NOT NULL
) 

