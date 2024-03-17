# Student Database Application

This Java application, `StudentDatabaseApp`, is designed to interact with a PostgreSQL database containing student information. It allows users to perform basic CRUD (Create, Read, Update, Delete) operations on student records. Below is a detailed guide on how to use this application effectively.

## Prerequisites
Before running this application, ensure you have the following:

- JDK installed on your system.
- PostgreSQL installed and running on your local machine or network.


## Setting Up
1. **Clone the Repository**: Clone the repository to your local machine using the following command:
   ```bash
   git clone <repository_url>
   ```
   If you don't have Git installed, you can download the source code files directly from the repository.


2. **Import Database**: Make sure the PostgreSQL database is set up. You can import the database schema using SQL scripts provided separately. You can find the SQL scripts in the `database` directory of this repository. To import the database schema, follow these steps:
    - Open your PostgreSQL database management tool (e.g., pgAdmin).
    - Create a new database.
    - Open the SQL script file (e.g., `schema.sql`) using a text editor.
    - Execute the SQL commands in the script to create the necessary tables and schema in your PostgreSQL database.

   ```sql
   -- Create the students table
   CREATE TABLE students (
       student_id SERIAL PRIMARY KEY,
       first_name TEXT NOT NULL,
       last_name TEXT NOT NULL,
       email TEXT NOT NULL UNIQUE,
       enrollment_date DATE
   );

   -- Insert the initial data
   INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
   ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
   ('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
   ('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');
   ```

3. **Configure Database Connection**: Open the `StudentDatabaseApp.java` file in your preferred IDE and update the following constants according to your PostgreSQL server configuration:
    - `URL`: The JDBC URL for connecting to your PostgreSQL server. It should be in the format `jdbc:postgresql://<hostname>:<port>/<database_name>`.
    - `USER`: Your PostgreSQL username.
    - `PASSWORD`: Your PostgreSQL password.

Once you've completed these steps, your environment should be set up and ready to use the `StudentDatabaseApp` application.

## Functionality

#### 1. `getAllStudents()`
Retrieves all students from the database and prints their information.

#### 2. `addStudent(String firstName, String lastName, String email, Date enrollmentDate)`
Adds a new student to the database with the provided details.

#### 3. `updateStudentEmail(int studentId, String newEmail)`
Updates the email address of a student identified by their ID.

#### 4. `deleteStudent(int studentId)`
Deletes a student from the database based on their ID.

### Example
Here's an example of how to use the application:

```java
public static void main(String[] args) {
    // Display all students
    System.out.println("All students:");
    getAllStudents();
    
    // Add a new student
    addStudent("Alice", "Wonderland", "alice@example.com", Date.valueOf("2024-03-16"));
    
    // Update email of a student
    updateStudentEmail(1, "john.doe@gmail.com");
    
    // Delete a student
    deleteStudent(2);
}
