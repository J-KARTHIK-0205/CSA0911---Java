package hospital.model;

/**
 * Appointment class representing a patient's appointment with a doctor.
 * Demonstrates encapsulation and basic attributes.
 */
public class Appointment {
    public enum Status {
        BOOKED, CANCELLED, COMPLETED, WAITLISTED
    }

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private Status status;
    private String reason;

    // Constructors
    public Appointment() {
    }

    public Appointment(String appointmentId, String patientId, String doctorId, String date, String time,
                       Status status, String reason) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = status;
        this.reason = reason;
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // Methods
    /**
     * Book the appointment (set status to BOOKED).
     */
    public void book() {
        this.status = Status.BOOKED;
    }

    /**
     * Cancel the appointment (set status to CANCELLED).
     */
    public void cancel() {
        this.status = Status.CANCELLED;
    }

    public void displayAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);
        System.out.println("Reason: " + reason);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }
}