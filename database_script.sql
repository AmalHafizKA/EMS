CREATE TABLE Department (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creationDate DATE,
    head_id INT,
    FOREIGN KEY (head_id) REFERENCES Employee(id)
);

CREATE TABLE Employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dateOfBirth DATE,
    salary DECIMAL(10, 2),
    address VARCHAR(255),
    role VARCHAR(255),
    joiningDate DATE,
    yearlyBonusPercentage DECIMAL(5, 2),
    department_id INT,
    manager_id INT,
    FOREIGN KEY (department_id) REFERENCES Department(id),
    FOREIGN KEY (manager_id) REFERENCES Employee(id)
);
