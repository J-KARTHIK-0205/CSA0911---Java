package hospital.db;

import hospital.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Medicine Inventory operations using JDBC.
 */
public class MedicineDAO {

    public boolean insertMedicine(Medicine med) {
        String sql = "INSERT INTO medicines (medicine_id, name, category, manufacturer, quantity, price, reorder_level, dosage) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name=?, category=?, manufacturer=?, quantity=?, price=?, reorder_level=?, dosage=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, med.getMedicineId());
            ps.setString(2, med.getMedicineName());
            ps.setString(3, med.getCategory());
            ps.setString(4, med.getManufacturer());
            ps.setInt(5, med.getQuantity());
            ps.setDouble(6, med.getPrice());
            ps.setInt(7, med.getReorderLevel());
            ps.setString(8, med.getDosage());

            ps.setString(9, med.getMedicineName());
            ps.setString(10, med.getCategory());
            ps.setString(11, med.getManufacturer());
            ps.setInt(12, med.getQuantity());
            ps.setDouble(13, med.getPrice());
            ps.setInt(14, med.getReorderLevel());
            ps.setString(15, med.getDosage());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] insertMedicine: " + e.getMessage());
            return false;
        }
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines ORDER BY medicine_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("medicine_id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String mfg = rs.getString("manufacturer");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int reorder = rs.getInt("reorder_level");
                String dosage = rs.getString("dosage");

                list.add(new Medicine(id, name, category, mfg, qty, price, reorder, dosage));
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] getAllMedicines: " + e.getMessage());
        }
        return list;
    }

    public boolean updateQuantity(String medicineId, int quantity) {
        String sql = "UPDATE medicines SET quantity = ? WHERE medicine_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] updateQuantity: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMedicine(String medicineId) {
        String sql = "DELETE FROM medicines WHERE medicine_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[JDBC Error] deleteMedicine: " + e.getMessage());
            return false;
        }
    }
}
