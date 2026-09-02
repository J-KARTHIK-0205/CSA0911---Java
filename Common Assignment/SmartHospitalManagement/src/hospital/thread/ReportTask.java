package hospital.thread;

import hospital.service.*;
import hospital.model.*;

/**
 * Task for generating reports periodically.
 * Demonstrates multithreading for report generation.
 */
public class ReportTask implements Runnable {
    private final ReportGenerator reportGenerator;
    private final boolean runContinuously;
    private volatile boolean running = true;

    public ReportTask(ReportGenerator reportGenerator, boolean runContinuously) {
        this.reportGenerator = reportGenerator;
        this.runContinuously = runContinuously;
    }

    public void stopRunning() {
        this.running = false;
    }

    @Override
    public void run() {
        int reportCount = 0;

        while (running) {
            try {
                System.out.println("\n=== Generating Reports (Report Task) ===");

                // Generate different types of reports
                if (reportCount % 3 == 0) {
                    reportGenerator.generatePatientVisitReport();
                } else if (reportCount % 3 == 1) {
                    reportGenerator.generatePharmacyInventoryReport();
                } else {
                    reportGenerator.generateDoctorUtilizationReport();
                }

                reportCount++;

                // If not running continuously, break after a few reports
                if (!runContinuously && reportCount >= 3) {
                    break;
                }

                // Wait for a bit before generating next set of reports
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Report task interrupted.");
                break;
            } catch (Exception e) {
                System.out.println("Error in report task: " + e.getMessage());
            }
        }

        System.out.println("Report task stopped.");
    }
}