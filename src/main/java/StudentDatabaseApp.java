import java.sql.*;
import java.util.Scanner;

public class StudentDatabaseApp {

    // JDBC URL, username, and password of PostgreSQL server
    private static final String URL = "jdbc:postgresql://localhost:5432/Assignment3.1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static final Scanner scanner = new Scanner(System.in);

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Unable to connect to the database: " + e.getMessage());
            return null;
        }
    }

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

    public static void addStudent() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter enrollment date (YYYY-MM-DD): ");
        String enrollmentDateStr = scanner.nextLine();
        Date enrollmentDate = Date.valueOf(enrollmentDateStr);

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

    public static void updateStudentEmail() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();

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
    public static void deleteStudent() {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();

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
        int choice;
        do {
            System.out.println("\nStudent Database App\nEnter a number (1-5)");
            System.out.println("1. View all students");
            System.out.println("2. Add a student");
            System.out.println("3. Update a student's email");
            System.out.println("4. Delete a student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    getAllStudents();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    updateStudentEmail();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
}
