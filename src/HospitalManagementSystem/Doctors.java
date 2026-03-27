
package HospitalManagementSystem;

import java.sql.*;

public class Doctors {

    private Connection connection;

    public Doctors(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM doctors").executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("specialization"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM doctors WHERE id=?");
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
}