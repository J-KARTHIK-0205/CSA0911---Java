package hospital.thread;

import hospital.service.*;
import hospital.model.*;

/**
 * Task for simulating concurrent appointment booking.
 * Demonstrates multithreading and synchronization.
 */
public class AppointmentBookingTask implements Runnable {
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final String patientId;
    private final String doctorId;
    private final String date;
    private final String timeSlot;
    private final String reason;
    private final String taskName;

    public AppointmentBookingTask(AppointmentService appointmentService,
                                  PatientService patientService,
                                  DoctorService doctorService,
                                  String patientId,
                                  String doctorId,
                                  String date,
                                  String timeSlot,
                                  String reason,
                                  String taskName) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.reason = reason;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(taskName + " attempting to book appointment for patient " + patientId +
                " with Dr. " + doctorId + " on " + date + " at " + timeSlot);

        try {
            // Validate that patient and doctor exist before attempting to book
            Patient patient = patientService.searchPatient(patientId);
            Doctor doctor = doctorService.searchDoctor(doctorId);

            // Attempt to book the appointment
            String appointmentId = "APT" + System.currentTimeMillis();
            appointmentService.bookAppointment(appointmentId, patientId, doctorId, date, timeSlot, reason);

            System.out.println(taskName + " SUCCESS: Appointment booked with ID " + appointmentId);
        } catch (Exception e) {
            System.out.println(taskName + " FAILED: " + e.getMessage());
        }
    }
}