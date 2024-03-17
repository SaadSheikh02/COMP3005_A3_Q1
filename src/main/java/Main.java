import java.sql.*;

public class Main {
    static Connection connection;
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:1433/university";
        String user = "postgres";
        String password = "50551591";

        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null){
                System.out.println("Connected to the Database");
                getAllStudents();
            }
            else {
                System.out.println("Failed to connect to the database");
            }

        }
        catch (Exception e){}
    }

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
