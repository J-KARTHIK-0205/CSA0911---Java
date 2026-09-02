package hospital.model;

/**
 * AppointmentReminder extends Notification.
 * Demonstrates inheritance and method overriding (polymorphism).
 */
public class AppointmentReminder extends Notification {
    // Additional fields specific to appointment reminders
    private String patientName;
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private String appointmentId;

    // Constructors
    public AppointmentReminder() {
        super();
    }

    public AppointmentReminder(String notificationId, String recipient, String message, String notificationType,
                               String patientName, String doctorName, String appointmentDate, String appointmentTime,
                               String appointmentId) {
        super(notificationId, recipient, message, notificationType);
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentId = appointmentId;
    }

    // Getters and Setters for additional fields
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Override to send appointment reminder in a specific format.
     * This demonstrates runtime polymorphism.
     */
    @Override
    public void sendNotification() {
        System.out.println("\n=== APPOINTMENT REMINDER ===");
        System.out.println("Patient: " + patientName);
        System.out.println("Doctor: " + doctorName);
        System.out.println("Date: " + appointmentDate);
        System.out.println("Time: " + appointmentTime);
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("============================");
    }
}