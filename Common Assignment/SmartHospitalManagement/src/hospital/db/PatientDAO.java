package hospital.db;

import hospital.model.EmergencyPatient;
import hospital.model.Patient;
import hospital.model.RegularPatient;
import hospital.model.SeniorCitizenPatient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Patient operations using JDBC.
 * Demonstrates PreparedStatement, ResultSet, and SQL query execution.
 */
public class PatientDAO {

    public boolean insertPatient(Patient patient) {
        String sql = "INSERT INTO patients (patient_id, name, age, gender, phone, email, category, medical_history) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name=?, age=?, gender=?, phone=?, email=?, category=?, medical_history=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getPatientId());
            ps.setString(2, patient.getName());
            ps.setInt(3, patient.getAge());
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getEmail());
            ps.setString(7, patient.getPatientCategory());
            ps.setString(8, patient.getMedicalHistory());

            // On duplicate update values
            ps.setString(9, patient.getName());
            ps.setInt(10, patient.getAge());
            ps.setString(11, patient.getGender());
            ps.setString(12, patient.getPhone());
            ps.setString(13, patient.getEmail());
            ps.setString(14, patient.getPatientCategory());
            ps.setString(15, patient.getMedicalHistory());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] insertPatient: " + e.getMessage());
            return false;
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("patient_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String category = rs.getString("category");
                String medicalHistory = rs.getString("medical_history");

                Patient p;
                if ("Emergency".equalsIgnoreCase(category)) {
                    p = new EmergencyPatient(id, name, age, gender, phone, email, "Emergency", medicalHistory);
                } else if ("Senior Citizen".equalsIgnoreCase(category) || "Senior".equalsIgnoreCase(category)) {
                    p = new SeniorCitizenPatient(id, name, age, gender, phone, email, "Senior Citizen", medicalHistory);
                } else {
                    p = new RegularPatient(id, name, age, gender, phone, email, "Regular", medicalHistory);
                }
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] getAllPatients: " + e.getMessage());
        }
        return list;
    }

    public Patient getPatientById(String patientId) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String category = rs.getString("category");
                    String medicalHistory = rs.getString("medical_history");

                    if ("Emergency".equalsIgnoreCase(category)) {
                        return new EmergencyPatient(patientId, name, age, gender, phone, email, "Emergency", medicalHistory);
                    } else if ("Senior Citizen".equalsIgnoreCase(category) || "Senior".equalsIgnoreCase(category)) {
                        return new SeniorCitizenPatient(patientId, name, age, gender, phone, email, "Senior Citizen", medicalHistory);
                    } else {
                        return new RegularPatient(patientId, name, age, gender, phone, email, "Regular", medicalHistory);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] getPatientById: " + e.getMessage());
        }
        return null;
    }

    public boolean deletePatient(String patientId) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] deletePatient: " + e.getMessage());
            return false;
        }
    }
}
