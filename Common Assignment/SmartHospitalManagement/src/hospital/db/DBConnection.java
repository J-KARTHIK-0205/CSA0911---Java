package hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database Connection Manager using JDBC for MySQL.
 * Provides unified connection retrieval, table initialization, and fallback
 * handling.
 */
public class DBConnection {
    // Database Configuration
    private static String dbHost = "localhost";
    private static int dbPort = 3306;
    private static String dbName = "hospital_db";
    private static String dbUser = "root";
    private static String dbPassword = "pawan1611"; // Default empty for XAMPP / WAMP / standard local MySQL

    private static boolean isDriverLoaded = false;
    private static boolean isDbAvailable = false;

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            // Try MySQL Connector/J 8.x+
            Class.forName("com.mysql.cj.jdbc.Driver");
            isDriverLoaded = true;
            System.out.println("[JDBC] MySQL CJ Driver loaded successfully.");
        } catch (ClassNotFoundException e1) {
            try {
                // Try legacy MySQL Connector/J 5.x
                Class.forName("com.mysql.jdbc.Driver");
                isDriverLoaded = true;
                System.out.println("[JDBC] MySQL Legacy Driver loaded successfully.");
            } catch (ClassNotFoundException e2) {
                System.out.println("[JDBC] MySQL Driver not found on classpath. Running in hybrid/in-memory mode.");
                isDriverLoaded = false;
            }
        }
    }

    /**
     * Get a Connection to the MySQL database.
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (!isDriverLoaded) {
            loadDriver();
        }

        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
        isDbAvailable = true;
        return conn;
    }

    /**
     * Test connection to MySQL.
     * 
     * @return true if successfully connected
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            isDbAvailable = (conn != null && !conn.isClosed());
            return isDbAvailable;
        } catch (Exception e) {
            isDbAvailable = false;
            return false;
        }
    }

    /**
     * Initializes database tables if they do not exist.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // 1. Patients
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS patients (" +
                    "patient_id VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "age INT NOT NULL, " +
                    "gender VARCHAR(10) NOT NULL, " +
                    "phone VARCHAR(20), " +
                    "email VARCHAR(100), " +
                    "category VARCHAR(30) DEFAULT 'Regular', " +
                    "medical_history TEXT)");

            // 2. Doctors
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS doctors (" +
                    "doctor_id VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "specialization VARCHAR(100) NOT NULL, " +
                    "department VARCHAR(100) NOT NULL, " +
                    "consultation_fee DECIMAL(10, 2) NOT NULL DEFAULT 500.00)");

            // 3. Doctor Slots
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS doctor_slots (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "doctor_id VARCHAR(20) NOT NULL, " +
                    "slot_time VARCHAR(20) NOT NULL, " +
                    "is_available BOOLEAN DEFAULT TRUE, " +
                    "UNIQUE KEY unique_doc_slot (doctor_id, slot_time))");

            // 4. Appointments
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS appointments (" +
                    "appointment_id VARCHAR(30) PRIMARY KEY, " +
                    "patient_id VARCHAR(20) NOT NULL, " +
                    "doctor_id VARCHAR(20) NOT NULL, " +
                    "appointment_date VARCHAR(20) NOT NULL, " +
                    "appointment_time VARCHAR(20) NOT NULL, " +
                    "status VARCHAR(20) DEFAULT 'BOOKED', " +
                    "reason TEXT)");

            // 5. Medicines
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS medicines (" +
                    "medicine_id VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "category VARCHAR(50) NOT NULL, " +
                    "manufacturer VARCHAR(100), " +
                    "quantity INT NOT NULL DEFAULT 0, " +
                    "price DECIMAL(10, 2) NOT NULL DEFAULT 0.00, " +
                    "reorder_level INT NOT NULL DEFAULT 10, " +
                    "dosage VARCHAR(50))");

            // 6. Notifications
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS notifications (" +
                    "notification_id VARCHAR(30) PRIMARY KEY, " +
                    "recipient VARCHAR(100) NOT NULL, " +
                    "message TEXT NOT NULL, " +
                    "notification_type VARCHAR(50) NOT NULL, " +
                    "is_sent BOOLEAN DEFAULT FALSE)");

            System.out.println("[JDBC] MySQL Database & Tables initialized successfully.");
        } catch (Exception e) {
            System.out.println(
                    "[JDBC] Note: MySQL auto-init deferred (" + e.getMessage() + "). Services will use memory store.");
        }
    }

    // Getters and Setters for dynamic database settings
    public static void setCredentials(String host, int port, String database, String user, String pass) {
        dbHost = host;
        dbPort = port;
        dbName = database;
        dbUser = user;
        dbPassword = pass;
    }

    public static boolean isDbAvailable() {
        return isDbAvailable;
    }

    public static boolean isDriverLoaded() {
        return isDriverLoaded;
    }

    public static String getDbUrl() {
        return "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void main(String[] args) {
        System.out.println("=== Testing JDBC MySQL Driver & Connection ===");
        System.out.println("Driver Loaded: " + isDriverLoaded());
        System.out.println("Connecting to: " + getDbUrl() + " as " + getDbUser());
        boolean connected = testConnection();
        System.out.println("Connection Status: " + (connected ? "SUCCESS (Connected to MySQL)" : "OFFLINE / Host unreachable"));
        if (connected) {
            initializeDatabase();
        }
    }
}
