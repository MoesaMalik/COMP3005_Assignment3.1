import java.sql.*;

/**
 * A simple Java application to interact with a PostgreSQL student database.
 */
public class StudentDatabaseApp {

    // JDBC URL, username, and password of PostgreSQL server
    private static final String URL = "jdbc:postgresql://localhost:5432/Assignment3.1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    /**
     * Establishes a connection to the PostgreSQL database.
     * @return A connection object if successful, null otherwise.
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Unable to connect to the database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all students from the database and prints their information.
     */
    public static void getAllStudents() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("student_id") + ", " +
                                rs.getString("first_name") + ", " +
                                rs.getString("last_name") + ", " +
                                rs.getString("email") + ", " +
                                rs.getDate("enrollment_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
    }

    /**
     * Adds a new student to the database.
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param email The email of the student.
     * @param enrollmentDate The enrollment date of the student.
     */
    public static void addStudent(String firstName, String lastName, String email, Date enrollmentDate) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setDate(4, enrollmentDate);
            stmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    /**
     * Updates a student's email in the database.
     * @param studentId The ID of the student whose email is to be updated.
     * @param newEmail The new email address.
     */
    public static void updateStudentEmail(int studentId, String newEmail) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE students SET email = ? WHERE student_id = ?")) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
            System.out.println("Email updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
        }
    }

    /**
     * Deletes a student from the database.
     * @param studentId The ID of the student to be deleted.
     */
    public static void deleteStudent(int studentId) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM students WHERE student_id = ?")) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Test the functions
        System.out.println("All students:");
        getAllStudents();
        addStudent("Alice", "Wonderland", "alice@example.com", Date.valueOf("2024-03-16"));
        updateStudentEmail(1, "john.doe.new@example.com");
        deleteStudent(2);
    }
}
