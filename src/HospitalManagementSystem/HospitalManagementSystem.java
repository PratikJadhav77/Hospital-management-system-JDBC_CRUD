
package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Patients patients = new Patients(connection, scanner);
            Doctors doctors = new Doctors(connection);

            while (true) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Update Patient");
                System.out.println("4. Delete Patient");
                System.out.println("5. View Doctors");
                System.out.println("6. Book Appointment");
                System.out.println("7. View Appointments");
                System.out.println("8. Update Appointment");
                System.out.println("9. Delete Appointment");
                System.out.println("10. Exit");
                System.out.print("Enter Choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: patients.addPatient(); break;
                    case 2: patients.viewPatients(); break;
                    case 3: patients.updatePatient(); break;
                    case 4: patients.deletePatient(); break;
                    case 5: doctors.viewDoctors(); break;
                    case 6: bookAppointment(patients, doctors, connection, scanner); break;
                    case 7: viewAppointments(connection); break;
                    case 8: updateAppointment(connection, scanner); break;
                    case 9: deleteAppointment(connection, scanner); break;
                    case 10:
                    	System.out.println("Thank you!");
                        return;

                    default: System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // BOOK
    public static void bookAppointment(Patients patients, Doctors doctors, Connection connection, Scanner scanner) {

        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();

        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.next();

        if (patients.getPatientById(patientId) && doctors.getDoctorById(doctorId)) {

            if (checkDoctorAvailability(doctorId, date, connection)) {

                String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";

                try {
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setString(3, date);

                    ps.executeUpdate();
                    System.out.println("✅ Appointment Booked!");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("❌ Doctor not available!");
            }

        } else {
            System.out.println("❌ Invalid Patient or Doctor!");
        }
    }

    // VIEW
    public static void viewAppointments(Connection connection) {
        String query = "SELECT * FROM appointments";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getInt("patient_id") + " | " +
                        rs.getInt("doctor_id") + " | " +
                        rs.getString("appointment_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public static void updateAppointment(Connection connection, Scanner scanner) {
        System.out.print("Enter Appointment ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter New Date: ");
        String date = scanner.next();

        try {
            String query = "UPDATE appointments SET appointment_date=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, date);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("✅ Updated!");
            else System.out.println("❌ Not found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteAppointment(Connection connection, Scanner scanner) {
        System.out.print("Enter Appointment ID: ");
        int id = scanner.nextInt();

        try {
            String query = "DELETE FROM appointments WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("✅ Deleted!");
            else System.out.println("❌ Not found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String date, Connection connection) {

        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorId);
            ps.setString(2, date);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}