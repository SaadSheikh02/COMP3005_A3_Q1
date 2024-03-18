import java.sql.*;
import java.util.Scanner;

public class Main {
    static Connection connection;
    static Scanner input;
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:1433/university"; // change url accordingly
        String user = "postgres";
        String password = "50551591"; // change password accordingly
        input = new Scanner(System.in);

        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null){
                System.out.println("Connected to the Database");
                while(true){
                    int menuChoice = outputChoices();

                    if(menuChoice == 1){
                        getAllStudents();

                    }
                    else if(menuChoice == 2){
                        // date format: YYYY/MM/DD
                        String f_name = getFirstName();
                        String l_name = getLastName();
                        String s_email = getEmail();
                        Date e_date = Date.valueOf(getEnrollmentDate());
                        addStudent(f_name, l_name, s_email, e_date);
                    }
                    else if(menuChoice == 3){
                        int student_id = getStudentID();
                        String student_email = getNewEmail();
                        updateStudentEmail(student_id, student_email);

                    }
                    else if(menuChoice == 4){
                        int student_id = getStudentID();
                        deleteStudent(student_id);
                    }
                    else if(menuChoice == 5){
                        System.exit(0);
                    }

                }
            }
            else {
                System.out.println("Failed to connect to the database");
            }

        }
        catch (Exception e){}
    }

    private static int outputChoices() {
        System.out.println();
        System.out.println("Options: ");
        System.out.println("1) Get all students");
        System.out.println("2) Add a student");
        System.out.println("3) Update student email");
        System.out.println("4) Delete a student");
        System.out.println("5) Exit the program");
        System.out.println();
        System.out.println("Enter the number of your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    private static String getFirstName() {
        System.out.println("Enter first name: ");
        return input.nextLine();
    }

    private static String getLastName() {
        System.out.println("Enter last name: ");
        return input.nextLine();
    }

    private static String getEmail() {
        System.out.println("Enter student email: ");
        return input.nextLine();
    }

    private static String getEnrollmentDate() {
        System.out.println("Enter enrollment date (YYYY-MM-DD): ");
        return input.nextLine();
    }

    private static int getStudentID() {
        System.out.print("Enter student ID: ");
        int id = input.nextInt();
        input.nextLine();
        return id;
    }

    private static String getNewEmail() {
        System.out.println("Enter student's new email: ");
        return input.nextLine();
    }

    /**
     * deletes a student tuple provided a student_id
     * @param student_id is the id of the student to be removed
     */
    private static void deleteStudent(int student_id) {
        String sql_statement = "DELETE FROM students WHERE student_id = ?";

        try{
            System.out.println("\n BEFORE UPDATE: ");
            getAllStudents();
            System.out.println();

            // Prepared statement to complete the sql statement based on parameters
            PreparedStatement prepStatement = connection.prepareStatement(sql_statement);
            prepStatement.setInt(1, student_id);
            prepStatement.executeUpdate();

            System.out.println("\n AFTER UPDATE: ");
            getAllStudents();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * updates a student's email
     * @param student_id is the id of the student to be updated
     * @param email of the student
     */
    private static void updateStudentEmail(int student_id, String email) {
        String sql_statement = "UPDATE students SET email = ? WHERE student_id = ?";

        try{
            System.out.println("\n BEFORE UPDATE: ");
            getAllStudents();
            System.out.println();

            // Prepared statement to complete the sql statement based on parameters
            PreparedStatement prepStatement = connection.prepareStatement(sql_statement);
            prepStatement.setString(1, email);
            prepStatement.setInt(2, student_id);
            prepStatement.executeUpdate();

            System.out.println("\n AFTER UPDATE: ");
            getAllStudents();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * adds a student record to the students table
     * @param first_name of the student
     * @param last_name of the student
     * @param email of the student
     * @param enrollment_date of the student
     * @throws SQLException
     */
    private static void addStudent(String first_name, String last_name, String email, Date enrollment_date) throws SQLException {
        String sql_statement = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        try {
            // Prepared statement to complete the sql statement based on parameters
            PreparedStatement prepStatement = connection.prepareStatement(sql_statement);
            prepStatement.setString(1, first_name);
            prepStatement.setString(2, last_name);
            prepStatement.setString(3, email);
            // have to use sql Date format instead of Java Date format for insertion
            prepStatement.setDate(4, new java.sql.Date(enrollment_date.getTime()));
            prepStatement.executeUpdate();
            getAllStudents();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * returns all students
     */
    private static void getAllStudents() {
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students;");
            ResultSet resultSet = statement.getResultSet();
            System.out.println("student_id\tfirst_name\tlast_name\temail\tenrolment_date");
            while (resultSet.next()){
                System.out.print("\n" + resultSet.getInt("student_id") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.print(resultSet.getDate("enrollment_date") + "\t");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
