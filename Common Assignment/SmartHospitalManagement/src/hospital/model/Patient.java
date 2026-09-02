package hospital.model;

/**
 * Patient class representing a hospital patient.
 * Demonstrates encapsulation, inheritance (to be extended), and basic attributes.
 */
public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private String patientCategory;
    private String medicalHistory;

    // Constructors
    public Patient() {
    }

    public Patient(String patientId, String name, int age, String gender, String phone, String email,
                   String patientCategory, String medicalHistory) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.patientCategory = patientCategory;
        this.medicalHistory = medicalHistory;
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    // Methods
    public void displayPatient() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Category: " + patientCategory);
        System.out.println("Medical History: " + medicalHistory);
    }

    public void updatePatient(String name, int age, String gender, String phone, String email,
                              String patientCategory, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.patientCategory = patientCategory;
        this.medicalHistory = medicalHistory;
    }

    public void bookAppointment() {
        // To be implemented in service layer
        System.out.println("Appointment booked for patient: " + name);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", patientCategory='" + patientCategory + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}