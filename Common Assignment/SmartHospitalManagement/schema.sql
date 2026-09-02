-- =======================================================
-- Smart Hospital Management System Database Schema (MySQL)
-- =======================================================

CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- 1. Patients Table
CREATE TABLE IF NOT EXISTS patients (
    patient_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    category VARCHAR(30) DEFAULT 'Regular',
    medical_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Doctors Table
CREATE TABLE IF NOT EXISTS doctors (
    doctor_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    consultation_fee DECIMAL(10, 2) NOT NULL DEFAULT 500.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Doctor Available Time Slots Table
CREATE TABLE IF NOT EXISTS doctor_slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(20) NOT NULL,
    slot_time VARCHAR(20) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
    UNIQUE KEY unique_doc_slot (doctor_id, slot_time)
);

-- 4. Appointments Table
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id VARCHAR(30) PRIMARY KEY,
    patient_id VARCHAR(20) NOT NULL,
    doctor_id VARCHAR(20) NOT NULL,
    appointment_date VARCHAR(20) NOT NULL,
    appointment_time VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'BOOKED',
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);

-- 5. Pharmacy & Medicine Inventory Table
CREATE TABLE IF NOT EXISTS medicines (
    medicine_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    manufacturer VARCHAR(100),
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    reorder_level INT NOT NULL DEFAULT 10,
    dosage VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 6. Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
    notification_id VARCHAR(30) PRIMARY KEY,
    recipient VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    is_sent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =======================================================
-- Initial Seed Data
-- =======================================================

INSERT INTO patients (patient_id, name, age, gender, phone, email, category, medical_history) VALUES
('P001', 'Arun Kumar', 30, 'Male', '9876543210', 'arun@email.com', 'Regular', 'No known allergies'),
('P002', 'Priya Sharma', 25, 'Female', '9876543211', 'priya@email.com', 'Regular', 'Mild asthma'),
('P003', 'Rahul Raj', 68, 'Male', '9876543212', 'rahul@email.com', 'Senior Citizen', 'Hypertension & Type 2 Diabetes'),
('P004', 'Vikram Singh', 42, 'Male', '9876543213', 'vikram@email.com', 'Emergency', 'Severe chest trauma, ICU care'),
('P005', 'Sneha Patel', 29, 'Female', '9876543214', 'sneha@email.com', 'Regular', 'Seasonal allergies')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO doctors (doctor_id, name, specialization, department, consultation_fee) VALUES
('D001', 'Dr. Meena Iyer', 'Cardiology', 'Cardiology Dept', 1200.00),
('D002', 'Dr. Rajesh Kumar', 'General Medicine', 'Internal Medicine', 800.00),
('D003', 'Dr. Anitha Suresh', 'Pediatrics', 'Child Healthcare', 950.00),
('D004', 'Dr. Siddharth Sen', 'Orthopedics', 'Bone & Joint Clinic', 1100.00)
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO doctor_slots (doctor_id, slot_time, is_available) VALUES
('D001', '09:00 AM', TRUE),
('D001', '10:00 AM', FALSE),
('D001', '11:00 AM', TRUE),
('D001', '02:00 PM', TRUE),
('D002', '09:30 AM', TRUE),
('D002', '10:30 AM', TRUE),
('D003', '10:00 AM', TRUE),
('D003', '11:00 AM', TRUE),
('D004', '09:00 AM', TRUE),
('D004', '11:00 AM', TRUE)
ON DUPLICATE KEY UPDATE is_available=VALUES(is_available);

INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_date, appointment_time, status, reason) VALUES
('APT101', 'P001', 'D001', '2026-09-10', '10:00 AM', 'BOOKED', 'Annual Cardiac Evaluation'),
('APT102', 'P002', 'D003', '2026-09-10', '11:00 AM', 'BOOKED', 'Asthma checkup'),
('APT103', 'P003', 'D002', '2026-09-11', '09:30 AM', 'BOOKED', 'BP monitoring & prescription review')
ON DUPLICATE KEY UPDATE status=VALUES(status);

INSERT INTO medicines (medicine_id, name, category, manufacturer, quantity, price, reorder_level, dosage) VALUES
('M001', 'Paracetamol', 'Analgesic', 'PharmaCorp', 85, 12.50, 20, '500mg'),
('M002', 'Amoxicillin', 'Antibiotic', 'MediLife', 45, 32.00, 15, '250mg'),
('M003', 'Cetirizine', 'Antihistamine', 'HealthPlus', 120, 18.00, 25, '10mg'),
('M004', 'Metformin', 'Antidiabetic', 'GlycoCare', 12, 45.00, 25, '500mg'),
('M005', 'Atorvastatin', 'Cardiovascular', 'CardioPharma', 0, 60.00, 10, '20mg'),
('M006', 'Ibuprofen', 'Anti-inflammatory', 'BioHealth', 65, 22.00, 15, '400mg'),
('M007', 'Azithromycin', 'Antibiotic', 'MediLife', 28, 55.00, 10, '500mg')
ON DUPLICATE KEY UPDATE quantity=VALUES(quantity);
