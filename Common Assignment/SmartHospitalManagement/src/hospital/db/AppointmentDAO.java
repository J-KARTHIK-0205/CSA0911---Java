package hospital.db;

import hospital.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Appointment operations using JDBC.
 */
public class AppointmentDAO {

    public boolean insertAppointment(Appointment apt) {
        String sql = "INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_date, appointment_time, status, reason) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE status=?, reason=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, apt.getAppointmentId());
            ps.setString(2, apt.getPatientId());
            ps.setString(3, apt.getDoctorId());
            ps.setString(4, apt.getDate());
            ps.setString(5, apt.getTime());
            ps.setString(6, apt.getStatus().name());
            ps.setString(7, apt.getReason());

            ps.setString(8, apt.getStatus().name());
            ps.setString(9, apt.getReason());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] insertAppointment: " + e.getMessage());
            return false;
        }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("appointment_id");
                String pId = rs.getString("patient_id");
                String dId = rs.getString("doctor_id");
                String date = rs.getString("appointment_date");
                String time = rs.getString("appointment_time");
                String statusStr = rs.getString("status");
                String reason = rs.getString("reason");

                Appointment.Status status = Appointment.Status.BOOKED;
                try {
                    status = Appointment.Status.valueOf(statusStr.toUpperCase());
                } catch (Exception ignored) {}

                Appointment apt = new Appointment(id, pId, dId, date, time, status, reason);
                list.add(apt);
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] getAllAppointments: " + e.getMessage());
        }
        return list;
    }

    public boolean updateStatus(String appointmentId, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] updateStatus: " + e.getMessage());
            return false;
        }
    }
}
