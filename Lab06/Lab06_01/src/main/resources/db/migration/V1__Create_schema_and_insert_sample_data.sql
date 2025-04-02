CREATE TABLE IF NOT EXISTS employees (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
                                         department VARCHAR(255) NOT NULL
    );


INSERT INTO employees (name, department) VALUES
                                             ('John Doe', 'IT'),
                                             ('Jane Smith', 'HR'),
                                             ('Tom Brown', 'Finance');