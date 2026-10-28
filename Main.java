package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {


    static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    // Database connection
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // INSERT: Add student details
    public static void insertStudent(
            String name, int age, String gender, String aadhar)
            throws Exception {

        Connection cn = getConnection();
        Statement stmt = cn.createStatement();

        String sql = "INSERT INTO student(stu_name,stu_age,stu_gender,stu_aadhar) "
                + "VALUES('" + name + "', " + age + ", '"
                + gender + "', '" + aadhar + "')";

        stmt.executeUpdate(sql);

        System.out.println("Student inserted successfully!");

        stmt.close();
        cn.close();
    }

    // READ: Display all student details
    public static void displayStudents() throws Exception {

        Connection cn = getConnection();
        Statement stmt = cn.createStatement();

        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM student"
        );

        System.out.println("\n----- Student Details -----");

        while (rs.next()) {
            System.out.println(
                    "ID: " + rs.getInt("stu_id")
                            + ", Name: " + rs.getString("stu_name")
                            + ", Age: " + rs.getInt("stu_age")
                            + ", Gender: " + rs.getString("stu_gender")
                            + ", Aadhaar: " + rs.getString("stu_aadhar")
            );
        }

        rs.close();
        stmt.close();
        cn.close();
    }

    // UPDATE: Update student age using ID
    public static void updateStudent(int id, int age)
            throws Exception {

        Connection cn = getConnection();
        Statement stmt = cn.createStatement();

        String sql = "UPDATE student SET stu_age = "
                + age + " WHERE stu_id = " + id;

        int rows = stmt.executeUpdate(sql);

        System.out.println(rows + " student record(s) updated.");

        stmt.close();
        cn.close();
    }

    // DELETE: Delete student using ID
    public static void deleteStudent(int id) throws Exception {

        Connection cn = getConnection();
        Statement stmt = cn.createStatement();

        String sql = "DELETE FROM student WHERE stu_id = " + id;

        int rows = stmt.executeUpdate(sql);

        System.out.println(rows + " student record(s) deleted.");

        stmt.close();
        cn.close();
    }

    // MAIN METHOD
    public static void main(String[] args) {

        try {

            // 1. INSERT
            insertStudent("Manasa Sriram", 20, "female", "111111");
            insertStudent("Harika", 20, "female", "222222");
            insertStudent("Chaithanya", 20, "female", "333333");
            insertStudent("Anirudh", 21, "male", "444444");
            insertStudent("Aryaveer", 15, "male", "555555");



            // 2. READ
            System.out.println("\nBefore Updating:");
            displayStudents();

            // 3. UPDATE: Change age of student ID 1
            updateStudent(101, 19);

            System.out.println("\nAfter Updating:");
            displayStudents();

            // 4. DELETE: Delete student ID 2
            deleteStudent(105);

            System.out.println("\nAfter Deleting:");
            displayStudents();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}