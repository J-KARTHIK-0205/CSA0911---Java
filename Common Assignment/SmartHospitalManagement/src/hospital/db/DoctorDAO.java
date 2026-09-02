package hospital.db;

import hospital.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Doctor & Slots operations using JDBC.
 */
public class DoctorDAO {

    public boolean insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (doctor_id, name, specialization, department, consultation_fee) " +
                     "VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name=?, specialization=?, department=?, consultation_fee=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doctor.getDoctorId());
            ps.setString(2, doctor.getName());
            ps.setString(3, doctor.getSpecialization());
            ps.setString(4, doctor.getDepartment());
            ps.setDouble(5, doctor.getConsultationFee());

            ps.setString(6, doctor.getName());
            ps.setString(7, doctor.getSpecialization());
            ps.setString(8, doctor.getDepartment());
            ps.setDouble(9, doctor.getConsultationFee());

            ps.executeUpdate();

            // Insert slots
            if (doctor.getAvailableSlots() != null) {
                for (String slot : doctor.getAvailableSlots()) {
                    addSlot(doctor.getDoctorId(), slot);
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] insertDoctor: " + e.getMessage());
            return false;
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY doctor_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("doctor_id");
                String name = rs.getString("name");
                String spec = rs.getString("specialization");
                String dept = rs.getString("department");
                double fee = rs.getDouble("consultation_fee");

                Doctor d = new Doctor(id, name, spec, dept, fee);
                d.setAvailableSlots(getSlotsForDoctor(id));
                list.add(d);
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] getAllDoctors: " + e.getMessage());
        }
        return list;
    }

    public List<String> getSlotsForDoctor(String doctorId) {
        List<String> slots = new ArrayList<>();
        String sql = "SELECT slot_time FROM doctor_slots WHERE doctor_id = ? AND is_available = TRUE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    slots.add(rs.getString("slot_time"));
                }
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] getSlotsForDoctor: " + e.getMessage());
        }
        return slots;
    }

    public boolean addSlot(String doctorId, String slotTime) {
        String sql = "INSERT INTO doctor_slots (doctor_id, slot_time, is_available) VALUES (?, ?, TRUE) " +
                     "ON DUPLICATE KEY UPDATE is_available = TRUE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            ps.setString(2, slotTime);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] addSlot: " + e.getMessage());
            return false;
        }
    }

    public boolean setSlotAvailability(String doctorId, String slotTime, boolean isAvailable) {
        String sql = "UPDATE doctor_slots SET is_available = ? WHERE doctor_id = ? AND slot_time = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isAvailable);
            ps.setString(2, doctorId);
            ps.setString(3, slotTime);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] setSlotAvailability: " + e.getMessage());
            return false;
        }
    }
}
