import java.sql.*;

public class Main {
    static Connection connection;
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:1433/university"; // change url accordingly
        String user = "postgres";
        String password = "50551591"; // change password accordingly

        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null){
                System.out.println("Connected to the Database");
                getAllStudents();
                // date format: YYYY/MM/DD
                addStudent("Saad", "Sheikh", "saad1662002@gmail.com", Date.valueOf("2021-09-08"));
                updateStudentEmail(4, "saad2773113@gmail.com");
                deleteStudent(7);
            }
            else {
                System.out.println("Failed to connect to the database");
            }

        }
        catch (Exception e){}
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
