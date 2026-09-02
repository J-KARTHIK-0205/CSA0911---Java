# Smart Hospital Management System

A comprehensive, enterprise-ready Java Hospital Management System featuring **Java AWT Applet GUI**, a **Modern Web Application GUI**, **Multithreading Concurrency**, and **MySQL Database Integration with JDBC**.

---

## 🚀 Key Interfaces & Execution Modes

| Interface Mode | Technology | How to Run |
|---|---|---|
| **Modern Web GUI** | HTML5, CSS3, JavaScript, Java HTTP Server | Run `run-web.bat` or `http://localhost:8080/` |
| **Java AWT Applet & Standalone GUI** | `java.awt.*`, `java.applet.Applet` | Run `run-applet.bat` or `java hospital.applet.HospitalApplet` |
| **AppletViewer Mode** | HTML `<applet>` tag | `appletviewer applet.html` |
| **Console CLI System** | Core Java, Menu-driven | `java hospital.main.HospitalManagementSystem` |
| **Master Launcher** | Batch Interactive Menu | Run `run-all.bat` |

---

## 🏥 Core Modules & Features

1. **Patient Management & EHR**:
   - Inheritance hierarchy: `Patient` (Base), `RegularPatient`, `EmergencyPatient`, `SeniorCitizenPatient`.
   - Registration, search by ID, category filtering, medical history records.
   - Validation & Custom Exceptions: `InvalidPatientException`, `PatientNotFoundException`.

2. **Doctor & Slot Roster**:
   - Doctor profiles, consultation fee rates, specialization & department.
   - Dynamic time-slot scheduler, slot locking, and utilization metrics.

3. **Clinical Appointment Booking**:
   - Safe slot reservations preventing double-booking race conditions.
   - Cancellation workflow with automatic doctor slot release and waitlist triggering.
   - Exceptions: `InvalidAppointmentException`, `DuplicateAppointmentException`.

4. **Pharmacy & Medicine Inventory**:
   - Stock quantity tracking, unit pricing, reorder thresholds, dosage information.
   - Dispensing with automated real-time bill and receipt calculation.
   - Low-stock and out-of-stock alarms (`OutOfStockException`, `LowStockNotification`).

5. **Live Notifications & Concurrency**:
   - Thread-safe Producer-Consumer notification queue.
   - Background worker thread dispatcher simulating SMS and clinical alerts.

6. **MySQL Database Integration with JDBC**:
   - JDBC Connection Manager (`DBConnection.java`).
   - Data Access Objects (`PatientDAO`, `DoctorDAO`, `AppointmentDAO`, `MedicineDAO`).
   - Full SQL DDL schema provided in `schema.sql`.
   - Automatic fallback to in-memory store if MySQL server is offline.

7. **Multithreading & Concurrency Sandbox**:
   - Live visual simulation of multiple patient threads competing for a single appointment slot.
   - Demonstrates synchronization, mutex locking, and race condition prevention.

---

## 📦 How to Build & Run

### 1. Run the Web Platform (Recommended)
```bash
# Double-click or run:
run-web.bat

# Or with PowerShell:
.\run-web.ps1
```
Open your browser at **`http://localhost:8080`**.

### 2. Run the Java AWT Applet GUI
```bash
# Double-click or run:
run-applet.bat

# Or directly with java:
java -cp out hospital.applet.HospitalApplet
```

### 3. Compile manually with JDK
```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName)
```

---

## 🗄️ MySQL Database Setup (Optional)

1. Ensure MySQL is running on `localhost:3306` (e.g. via XAMPP, WAMP, or MySQL Server).
2. Import the schema:
```sql
mysql -u root -p < schema.sql
```
3. The application will connect using `jdbc:mysql://localhost:3306/hospital_db` (user: `root`, password: `""`).