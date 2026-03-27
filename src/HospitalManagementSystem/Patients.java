//package HospitalManagementSystem;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class Patients {
//
//    private Connection connection;
//    private Scanner scanner;
//
//    public Patients(Connection connection, Scanner scanner) {
//        this.connection = connection;
//        this.scanner = scanner;
//    }
//
//    public void addPatient() {
//        System.out.print("Enter Name: ");
//        String name = scanner.next();
//
//        System.out.print("Enter Age: ");
//        int age = scanner.nextInt();
//
//        System.out.print("Enter Gender: ");
//        String gender = scanner.next();
//
//        try {
//            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
//            PreparedStatement ps = connection.prepareStatement(query);
//
//            ps.setString(1, name);
//            ps.setInt(2, age);
//            ps.setString(3, gender);
//
//            ps.executeUpdate();
//            System.out.println("✅ Patient Added!");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void viewPatients() {
//        String query = "SELECT * FROM patients";
//
//        try {
//            PreparedStatement ps = connection.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                System.out.println(
//                        rs.getInt("id") + " | " +
//                        rs.getString("name") + " | " +
//                        rs.getInt("age") + " | " +
//                        rs.getString("gender"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean getPatientById(int id) {
//        String query = "SELECT * FROM patients WHERE id=?";
//
//        try {
//            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, id);
//
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//}




package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patients {

    private Connection connection;
    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Age: ");
        int age = scanner.nextInt();

        System.out.print("Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            ps.executeUpdate();
            System.out.println("✅ Added");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM patients").executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatient() {
        System.out.print("ID: ");
        int id = scanner.nextInt();

        System.out.print("New Name: ");
        String name = scanner.next();

        System.out.print("New Age: ");
        int age = scanner.nextInt();

        System.out.print("New Gender: ");
        String gender = scanner.next();

        try {
            String query = "UPDATE patients SET name=?, age=?, gender=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("✅ Updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient() {
        System.out.print("ID: ");
        int id = scanner.nextInt();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM patients WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("✅ Deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM patients WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}