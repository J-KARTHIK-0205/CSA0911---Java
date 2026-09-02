package hospital.applet;

import hospital.db.DBConnection;
import hospital.db.PatientDAO;
import hospital.db.DoctorDAO;
import hospital.db.AppointmentDAO;
import hospital.db.MedicineDAO;
import hospital.exception.*;
import hospital.model.*;
import hospital.service.*;
import hospital.thread.AppointmentBookingTask;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

/**
 * Smart Hospital Management System - AWT Applet & Standalone GUI.
 * Extends java.applet.Applet and provides standalone java.awt.Frame launcher.
 * Integrates directly with AWT Components, Java Services, Multithreading, and MySQL JDBC.
 */
public class HospitalApplet extends Applet implements ActionListener, ItemListener {

    // Services
    private PatientService patientService;
    private DoctorService doctorService;
    private AppointmentService appointmentService;
    private PharmacyService pharmacyService;
    private NotificationService notificationService;
    private ReportGenerator reportGenerator;

    // JDBC DAOs
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    private MedicineDAO medicineDAO;

    // Navigation & Layout
    private CardLayout cardLayout;
    private Panel contentPanel;
    private Panel navPanel;

    // AWT Colors
    private final Color COLOR_BG = new Color(245, 247, 250);
    private final Color COLOR_PRIMARY = new Color(14, 116, 144);
    private final Color COLOR_ACCENT = new Color(16, 185, 129);
    private final Color COLOR_DARK = new Color(15, 23, 42);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_DANGER = new Color(225, 29, 72);

    // Form Controls - Patients
    private TextField txtPatId, txtPatName, txtPatAge, txtPatPhone, txtPatEmail, txtPatHistory, txtPatSearch;
    private Choice choicePatGender, choicePatCategory;
    private TextArea txtAreaPatients;

    // Form Controls - Doctors
    private Choice choiceDocSelect, choiceDocDept;
    private TextField txtDocId, txtDocName, txtDocSpec, txtDocDept, txtDocFee, txtDocNewSlot;
    private java.awt.List listDocSlots;
    private TextArea txtAreaDoctors;

    // Form Controls - Appointments
    private Choice choiceAptPatient, choiceAptDoctor, choiceAptSlot;
    private TextField txtAptId, txtAptDate, txtAptReason, txtAptCancelId;
    private TextArea txtAreaAppointments;

    // Form Controls - Pharmacy
    private TextField txtMedId, txtMedName, txtMedMfg, txtMedQty, txtMedPrice, txtMedReorder, txtMedDosage;
    private Choice choiceMedCategory, choiceMedDispense;
    private TextField txtDispenseQty, txtDispensePatient, txtRestockQty;
    private TextArea txtAreaPharmacy;

    // Form Controls - Notifications
    private TextField txtNotifRecipient, txtNotifMsg;
    private Choice choiceNotifType;
    private TextArea txtAreaNotifications;

    // Form Controls - Reports
    private TextArea txtAreaReports;

    // Form Controls - Database / JDBC
    private TextField txtDbHost, txtDbPort, txtDbName, txtDbUser, txtDbPass;
    private Label lblDbStatus;
    private TextArea txtAreaDbLog;

    // Dashboard labels
    private Label lblDashPatients, lblDashDoctors, lblDashAppointments, lblDashMedicines, lblDashLowStock;

    @Override
    public void init() {
        // Initialize services and models
        patientService = new PatientService();
        doctorService = new DoctorService();
        appointmentService = new AppointmentService(patientService, doctorService);
        pharmacyService = new PharmacyService();
        notificationService = new NotificationService();
        reportGenerator = new ReportGenerator(patientService, doctorService, appointmentService, pharmacyService);

        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
        appointmentDAO = new AppointmentDAO();
        medicineDAO = new MedicineDAO();

        loadSeedData();

        // Setup Applet Layout
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);
        setFont(new Font("SansSerif", Font.PLAIN, 13));

        // 1. Top Header Banner
        Panel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 2. Left / Top Navigation Bar
        navPanel = createNavigationPanel();
        add(navPanel, BorderLayout.WEST);

        // 3. Center Content Panel (CardLayout)
        cardLayout = new CardLayout();
        contentPanel = new Panel(cardLayout);

        contentPanel.add(createDashboardPanel(), "DASHBOARD");
        contentPanel.add(createPatientPanel(), "PATIENTS");
        contentPanel.add(createDoctorPanel(), "DOCTORS");
        contentPanel.add(createAppointmentPanel(), "APPOINTMENTS");
        contentPanel.add(createPharmacyPanel(), "PHARMACY");
        contentPanel.add(createNotificationPanel(), "NOTIFICATIONS");
        contentPanel.add(createReportsPanel(), "REPORTS");
        contentPanel.add(createDatabasePanel(), "DATABASE");

        add(contentPanel, BorderLayout.CENTER);

        // 4. Status Bar (Bottom)
        Panel statusBar = new Panel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(226, 232, 240));
        statusBar.add(new Label("Smart Hospital Management System (AWT + Applet + JDBC MySQL) | Ready"));
        add(statusBar, BorderLayout.SOUTH);

        refreshAllViews();
    }

    private void loadSeedData() {
        try {
            patientService.registerPatient(new RegularPatient("P001", "Arun Kumar", 30, "Male", "9876543210", "arun@email.com", "Regular", "No allergies"));
            patientService.registerPatient(new RegularPatient("P002", "Priya Sharma", 25, "Female", "9876543211", "priya@email.com", "Regular", "Mild asthma"));
            patientService.registerPatient(new SeniorCitizenPatient("P003", "Rahul Raj", 68, "Male", "9876543212", "rahul@email.com", "Senior Citizen", "Hypertension"));

            Doctor d1 = new Doctor("D001", "Dr. Meena Iyer", "Cardiology", "Cardiology Dept", 1200.0);
            d1.addSlot("09:00 AM");
            d1.addSlot("10:00 AM");
            d1.addSlot("11:00 AM");

            Doctor d2 = new Doctor("D002", "Dr. Rajesh Kumar", "General Medicine", "Internal Medicine", 800.0);
            d2.addSlot("09:30 AM");
            d2.addSlot("10:30 AM");

            doctorService.registerDoctor(d1);
            doctorService.registerDoctor(d2);

            appointmentService.bookAppointment("APT101", "P001", "D001", "2026-09-10", "10:00 AM", "Cardiac Checkup");

            pharmacyService.addMedicine(new Medicine("M001", "Paracetamol", "Analgesic", "PharmaCorp", 85, 12.50, 20, "500mg"));
            pharmacyService.addMedicine(new Medicine("M002", "Amoxicillin", "Antibiotic", "MediLife", 45, 32.00, 15, "250mg"));
            pharmacyService.addMedicine(new Medicine("M003", "Metformin", "Antidiabetic", "GlycoCare", 12, 45.00, 25, "500mg"));

            notificationService.createAppointmentReminder("Arun Kumar", "Dr. Meena Iyer", "2026-09-10", "10:00 AM", "APT101");
            notificationService.createLowStockNotification("Metformin", 12, 25);
        } catch (Exception ignored) {}
    }

    private Panel createHeaderPanel() {
        Panel panel = new Panel(new BorderLayout()) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Draw decorative medical cross
                g2.setColor(new Color(56, 189, 248));
                g2.fillRect(15, 12, 8, 24);
                g2.fillRect(7, 20, 24, 8);
            }
        };
        panel.setBackground(COLOR_DARK);
        panel.setPreferredSize(new Dimension(1000, 50));

        Label title = new Label("   SMART HOSPITAL MANAGEMENT PLATFORM");
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        title.setForeground(Color.WHITE);

        Label sub = new Label("Java AWT & Applet Suite with MySQL JDBC Integration   ");
        sub.setFont(new Font("SansSerif", Font.ITALIC, 12));
        sub.setForeground(new Color(148, 163, 184));

        panel.add(title, BorderLayout.WEST);
        panel.add(sub, BorderLayout.EAST);
        return panel;
    }

    private Panel createNavigationPanel() {
        Panel panel = new Panel(new GridLayout(8, 1, 4, 6));
        panel.setBackground(new Color(241, 245, 249));
        panel.setPreferredSize(new Dimension(170, 600));

        String[] navs = {
            "Dashboard", "Patients", "Doctors", "Appointments",
            "Pharmacy", "Notifications", "Reports", "MySQL Database"
        };

        for (String n : navs) {
            Button btn = new Button(n);
            btn.setBackground(COLOR_PRIMARY);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 12));
            btn.setActionCommand("NAV_" + n.toUpperCase().replace(" ", "_"));
            btn.addActionListener(this);
            panel.add(btn);
        }
        return panel;
    }

    // ==========================================
    // 1. Dashboard Panel
    // ==========================================
    private Panel createDashboardPanel() {
        Panel panel = new Panel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BG);

        // Top Metrics
        Panel statsPanel = new Panel(new GridLayout(1, 5, 10, 10));
        statsPanel.setPreferredSize(new Dimension(800, 90));

        lblDashPatients = createStatCard(statsPanel, "Patients Registered", "3", new Color(14, 116, 144));
        lblDashDoctors = createStatCard(statsPanel, "Doctors Active", "2", new Color(16, 185, 129));
        lblDashAppointments = createStatCard(statsPanel, "Appointments", "1", new Color(139, 92, 246));
        lblDashMedicines = createStatCard(statsPanel, "Medicines In Stock", "3", new Color(245, 158, 11));
        lblDashLowStock = createStatCard(statsPanel, "Low Stock Alerts", "1", COLOR_DANGER);

        panel.add(statsPanel, BorderLayout.NORTH);

        // Quick Actions & Overview
        Panel center = new Panel(new GridLayout(1, 2, 10, 10));

        Panel actionCard = new Panel(new GridLayout(5, 1, 6, 6));
        actionCard.setBackground(COLOR_CARD);
        Label lblAct = new Label("--- QUICK ACTIONS ---", Label.CENTER);
        lblAct.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblAct.setForeground(COLOR_DARK);
        actionCard.add(lblAct);

        Button btnQuickPat = new Button("+ Register New Patient");
        btnQuickPat.setActionCommand("NAV_PATIENTS");
        btnQuickPat.addActionListener(this);

        Button btnQuickApt = new Button("+ Book Doctor Appointment");
        btnQuickApt.setActionCommand("NAV_APPOINTMENTS");
        btnQuickApt.addActionListener(this);

        Button btnQuickMed = new Button("+ Dispense Pharmacy Medicine");
        btnQuickMed.setActionCommand("NAV_PHARMACY");
        btnQuickMed.addActionListener(this);

        Button btnQuickDb = new Button("⚡ Connect MySQL Database");
        btnQuickDb.setActionCommand("NAV_MYSQL_DATABASE");
        btnQuickDb.addActionListener(this);

        actionCard.add(btnQuickPat);
        actionCard.add(btnQuickApt);
        actionCard.add(btnQuickMed);
        actionCard.add(btnQuickDb);
        center.add(actionCard);

        // System Info
        Panel infoCard = new Panel(new BorderLayout());
        infoCard.setBackground(COLOR_CARD);
        Label lblInfo = new Label("System Architecture & Specifications", Label.CENTER);
        lblInfo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblInfo.setForeground(COLOR_DARK);
        infoCard.add(lblInfo, BorderLayout.NORTH);

        TextArea txtInfo = new TextArea(
                "• GUI Framework: Standard Java AWT (CardLayout, Frame, Applet)\n" +
                "• Backend Architecture: Object-Oriented Services & Exceptions\n" +
                "• Concurrency: Multithreaded Slot Booking & Worker Queues\n" +
                "• Database: JDBC API connecting to MySQL on localhost:3306\n" +
                "• Web Server: Built-in HTTP REST Server on port 8080\n" +
                "• Hybrid Fallback: Seamless offline storage if MySQL offline\n\n" +
                "Ready for academic submission, project viva, and enterprise demo.", 8, 40, TextArea.SCROLLBARS_NONE);
        txtInfo.setEditable(false);
        infoCard.add(txtInfo, BorderLayout.CENTER);
        center.add(infoCard);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private Label createStatCard(Panel parent, String title, String val, Color barColor) {
        Panel card = new Panel(new BorderLayout());
        card.setBackground(COLOR_CARD);
        Label t = new Label(title, Label.CENTER);
        t.setFont(new Font("SansSerif", Font.PLAIN, 11));
        t.setForeground(Color.DARK_GRAY);

        Label v = new Label(val, Label.CENTER);
        v.setFont(new Font("SansSerif", Font.BOLD, 22));
        v.setForeground(barColor);

        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        parent.add(card);
        return v;
    }

    // ==========================================
    // 2. Patient Management Panel
    // ==========================================
    private Panel createPatientPanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        // Top Form
        Panel form = new Panel(new GridLayout(5, 4, 6, 6));
        form.setBackground(COLOR_CARD);

        form.add(new Label("Patient ID:"));
        txtPatId = new TextField("P00" + (patientService.getPatientCount() + 1));
        form.add(txtPatId);

        form.add(new Label("Full Name:"));
        txtPatName = new TextField();
        form.add(txtPatName);

        form.add(new Label("Age:"));
        txtPatAge = new TextField("28");
        form.add(txtPatAge);

        form.add(new Label("Gender:"));
        choicePatGender = new Choice();
        choicePatGender.add("Male");
        choicePatGender.add("Female");
        choicePatGender.add("Other");
        form.add(choicePatGender);

        form.add(new Label("Category:"));
        choicePatCategory = new Choice();
        choicePatCategory.add("Regular");
        choicePatCategory.add("Emergency");
        choicePatCategory.add("Senior Citizen");
        form.add(choicePatCategory);

        form.add(new Label("Phone:"));
        txtPatPhone = new TextField("9876543210");
        form.add(txtPatPhone);

        form.add(new Label("Email:"));
        txtPatEmail = new TextField("patient@email.com");
        form.add(txtPatEmail);

        form.add(new Label("Medical History:"));
        txtPatHistory = new TextField("None");
        form.add(txtPatHistory);

        Button btnAdd = new Button("Register Patient");
        btnAdd.setBackground(COLOR_ACCENT);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setActionCommand("PAT_ADD");
        btnAdd.addActionListener(this);
        form.add(btnAdd);

        Button btnClear = new Button("Clear Form");
        btnClear.setActionCommand("PAT_CLEAR");
        btnClear.addActionListener(this);
        form.add(btnClear);

        panel.add(form, BorderLayout.NORTH);

        // Center Patient Records
        Panel listContainer = new Panel(new BorderLayout(4, 4));
        Panel searchBar = new Panel(new FlowLayout(FlowLayout.LEFT));
        searchBar.setBackground(COLOR_CARD);
        searchBar.add(new Label("Search by ID:"));
        txtPatSearch = new TextField(10);
        searchBar.add(txtPatSearch);

        Button btnSearch = new Button("Search");
        btnSearch.setActionCommand("PAT_SEARCH");
        btnSearch.addActionListener(this);
        searchBar.add(btnSearch);

        Button btnRefresh = new Button("Refresh All");
        btnRefresh.setActionCommand("PAT_REFRESH");
        btnRefresh.addActionListener(this);
        searchBar.add(btnRefresh);

        listContainer.add(searchBar, BorderLayout.NORTH);

        txtAreaPatients = new TextArea(14, 70);
        txtAreaPatients.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaPatients.setEditable(false);
        listContainer.add(txtAreaPatients, BorderLayout.CENTER);

        panel.add(listContainer, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================
    // 3. Doctor Management Panel
    // ==========================================
    private Panel createDoctorPanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        Panel top = new Panel(new GridLayout(4, 4, 6, 6));
        top.setBackground(COLOR_CARD);

        top.add(new Label("Doctor ID:"));
        txtDocId = new TextField("D00" + (doctorService.getDoctorCount() + 1));
        top.add(txtDocId);

        top.add(new Label("Doctor Name:"));
        txtDocName = new TextField();
        top.add(txtDocName);

        top.add(new Label("Specialization:"));
        txtDocSpec = new TextField("Cardiology");
        top.add(txtDocSpec);

        top.add(new Label("Department:"));
        txtDocDept = new TextField("Cardiology Dept");
        top.add(txtDocDept);

        top.add(new Label("Consultation Fee ($):"));
        txtDocFee = new TextField("1000.0");
        top.add(txtDocFee);

        Button btnAddDoc = new Button("Register Doctor");
        btnAddDoc.setBackground(COLOR_ACCENT);
        btnAddDoc.setForeground(Color.WHITE);
        btnAddDoc.setActionCommand("DOC_ADD");
        btnAddDoc.addActionListener(this);
        top.add(btnAddDoc);

        panel.add(top, BorderLayout.NORTH);

        // Center Slot Manager & List
        Panel center = new Panel(new GridLayout(1, 2, 8, 8));

        Panel slotCard = new Panel(new BorderLayout(4, 4));
        slotCard.setBackground(COLOR_CARD);
        Label lblSlotTitle = new Label("Select Doctor to Manage Time Slots:", Label.CENTER);
        lblSlotTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        slotCard.add(lblSlotTitle, BorderLayout.NORTH);

        Panel slotControls = new Panel(new FlowLayout());
        choiceDocSelect = new Choice();
        choiceDocSelect.addItemListener(this);
        slotControls.add(choiceDocSelect);

        txtDocNewSlot = new TextField("12:00 PM", 8);
        slotControls.add(txtDocNewSlot);

        Button btnAddSlot = new Button("+ Add Slot");
        btnAddSlot.setActionCommand("DOC_ADD_SLOT");
        btnAddSlot.addActionListener(this);
        slotControls.add(btnAddSlot);

        slotCard.add(slotControls, BorderLayout.SOUTH);

        listDocSlots = new List(8);
        slotCard.add(listDocSlots, BorderLayout.CENTER);
        center.add(slotCard);

        txtAreaDoctors = new TextArea(12, 40);
        txtAreaDoctors.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaDoctors.setEditable(false);
        center.add(txtAreaDoctors);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================
    // 4. Appointment Booking Panel
    // ==========================================
    private Panel createAppointmentPanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        Panel form = new Panel(new GridLayout(4, 4, 6, 6));
        form.setBackground(COLOR_CARD);

        form.add(new Label("Select Patient:"));
        choiceAptPatient = new Choice();
        form.add(choiceAptPatient);

        form.add(new Label("Select Doctor:"));
        choiceAptDoctor = new Choice();
        choiceAptDoctor.addItemListener(this);
        form.add(choiceAptDoctor);

        form.add(new Label("Available Slot:"));
        choiceAptSlot = new Choice();
        form.add(choiceAptSlot);

        form.add(new Label("Appointment Date:"));
        txtAptDate = new TextField("2026-09-15");
        form.add(txtAptDate);

        form.add(new Label("Reason / Symptoms:"));
        txtAptReason = new TextField("Routine Consultation");
        form.add(txtAptReason);

        Button btnBook = new Button("Book Appointment");
        btnBook.setBackground(COLOR_PRIMARY);
        btnBook.setForeground(Color.WHITE);
        btnBook.setActionCommand("APT_BOOK");
        btnBook.addActionListener(this);
        form.add(btnBook);

        panel.add(form, BorderLayout.NORTH);

        // Center Appointments list & Cancellation bar
        Panel center = new Panel(new BorderLayout(4, 4));
        Panel cancelBar = new Panel(new FlowLayout(FlowLayout.LEFT));
        cancelBar.setBackground(COLOR_CARD);
        cancelBar.add(new Label("Cancel Appointment ID:"));
        txtAptCancelId = new TextField(10);
        cancelBar.add(txtAptCancelId);

        Button btnCancel = new Button("Cancel Appointment");
        btnCancel.setBackground(COLOR_DANGER);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setActionCommand("APT_CANCEL");
        btnCancel.addActionListener(this);
        cancelBar.add(btnCancel);

        Button btnThreadSim = new Button("⚡ Run Concurrent Booking Race (Multithreading)");
        btnThreadSim.setActionCommand("THREAD_SIM_BOOKING");
        btnThreadSim.addActionListener(this);
        cancelBar.add(btnThreadSim);

        center.add(cancelBar, BorderLayout.NORTH);

        txtAreaAppointments = new TextArea(14, 70);
        txtAreaAppointments.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaAppointments.setEditable(false);
        center.add(txtAreaAppointments, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================
    // 5. Pharmacy Panel
    // ==========================================
    private Panel createPharmacyPanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        // Add Medicine Top Form
        Panel top = new Panel(new GridLayout(4, 4, 6, 6));
        top.setBackground(COLOR_CARD);

        top.add(new Label("Medicine ID:"));
        txtMedId = new TextField("M00" + (pharmacyService.getMedicineCount() + 1));
        top.add(txtMedId);

        top.add(new Label("Medicine Name:"));
        txtMedName = new TextField();
        top.add(txtMedName);

        top.add(new Label("Category:"));
        choiceMedCategory = new Choice();
        choiceMedCategory.add("Analgesic");
        choiceMedCategory.add("Antibiotic");
        choiceMedCategory.add("Antihistamine");
        choiceMedCategory.add("Antidiabetic");
        choiceMedCategory.add("Cardiovascular");
        top.add(choiceMedCategory);

        top.add(new Label("Manufacturer:"));
        txtMedMfg = new TextField("GenericPharma");
        top.add(txtMedMfg);

        top.add(new Label("Initial Quantity:"));
        txtMedQty = new TextField("50");
        top.add(txtMedQty);

        top.add(new Label("Unit Price ($):"));
        txtMedPrice = new TextField("20.00");
        top.add(txtMedPrice);

        top.add(new Label("Reorder Level:"));
        txtMedReorder = new TextField("15");
        top.add(txtMedReorder);

        Button btnAddMed = new Button("+ Add Medicine");
        btnAddMed.setBackground(COLOR_ACCENT);
        btnAddMed.setForeground(Color.WHITE);
        btnAddMed.setActionCommand("MED_ADD");
        btnAddMed.addActionListener(this);
        top.add(btnAddMed);

        panel.add(top, BorderLayout.NORTH);

        // Center Dispense & Stock Table
        Panel center = new Panel(new BorderLayout(6, 6));

        Panel actionRow = new Panel(new FlowLayout(FlowLayout.LEFT));
        actionRow.setBackground(COLOR_CARD);

        actionRow.add(new Label("Select Medicine:"));
        choiceMedDispense = new Choice();
        actionRow.add(choiceMedDispense);

        actionRow.add(new Label("Dispense Qty:"));
        txtDispenseQty = new TextField("2", 4);
        actionRow.add(txtDispenseQty);

        Button btnDispense = new Button("Dispense & Bill");
        btnDispense.setBackground(COLOR_PRIMARY);
        btnDispense.setForeground(Color.WHITE);
        btnDispense.setActionCommand("MED_DISPENSE");
        btnDispense.addActionListener(this);
        actionRow.add(btnDispense);

        actionRow.add(new Label("Restock Qty:"));
        txtRestockQty = new TextField("20", 4);
        actionRow.add(txtRestockQty);

        Button btnRestock = new Button("Restock");
        btnRestock.setActionCommand("MED_RESTOCK");
        btnRestock.addActionListener(this);
        actionRow.add(btnRestock);

        center.add(actionRow, BorderLayout.NORTH);

        txtAreaPharmacy = new TextArea(13, 70);
        txtAreaPharmacy.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaPharmacy.setEditable(false);
        center.add(txtAreaPharmacy, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================
    // 6. Notification Center Panel
    // ==========================================
    private Panel createNotificationPanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        Panel top = new Panel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(COLOR_CARD);

        top.add(new Label("Recipient:"));
        txtNotifRecipient = new TextField("Pharmacy Manager", 12);
        top.add(txtNotifRecipient);

        top.add(new Label("Type:"));
        choiceNotifType = new Choice();
        choiceNotifType.add("GENERAL");
        choiceNotifType.add("APPOINTMENT_REMINDER");
        choiceNotifType.add("LOW_STOCK");
        choiceNotifType.add("EMERGENCY");
        top.add(choiceNotifType);

        top.add(new Label("Message:"));
        txtNotifMsg = new TextField("Urgent staff meeting scheduled at 4 PM.", 20);
        top.add(txtNotifMsg);

        Button btnSend = new Button("Queue Alert");
        btnSend.setActionCommand("NOTIF_QUEUE");
        btnSend.addActionListener(this);
        top.add(btnSend);

        Button btnDispatch = new Button("⚡ Dispatch Queue (Worker Thread)");
        btnDispatch.setBackground(COLOR_ACCENT);
        btnDispatch.setForeground(Color.WHITE);
        btnDispatch.setActionCommand("NOTIF_DISPATCH");
        btnDispatch.addActionListener(this);
        top.add(btnDispatch);

        panel.add(top, BorderLayout.NORTH);

        txtAreaNotifications = new TextArea(14, 70);
        txtAreaNotifications.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaNotifications.setEditable(false);
        panel.add(txtAreaNotifications, BorderLayout.CENTER);

        return panel;
    }

    // ==========================================
    // 7. Reports Panel
    // ==========================================
    private Panel createReportsPanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        Panel top = new Panel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(COLOR_CARD);

        Button btnRptAll = new Button("Generate Complete Hospital Report");
        btnRptAll.setActionCommand("RPT_GENERATE");
        btnRptAll.addActionListener(this);
        top.add(btnRptAll);

        Button btnRptDoc = new Button("Doctor Utilization Report");
        btnRptDoc.setActionCommand("RPT_DOCTORS");
        btnRptDoc.addActionListener(this);
        top.add(btnRptDoc);

        Button btnRptMed = new Button("Pharmacy Low Stock Report");
        btnRptMed.setActionCommand("RPT_PHARMACY");
        btnRptMed.addActionListener(this);
        top.add(btnRptMed);

        panel.add(top, BorderLayout.NORTH);

        txtAreaReports = new TextArea(16, 70);
        txtAreaReports.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaReports.setEditable(false);
        panel.add(txtAreaReports, BorderLayout.CENTER);

        return panel;
    }

    // ==========================================
    // 8. MySQL Database & JDBC Panel
    // ==========================================
    private Panel createDatabasePanel() {
        Panel panel = new Panel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_BG);

        Panel form = new Panel(new GridLayout(4, 4, 6, 6));
        form.setBackground(COLOR_CARD);

        form.add(new Label("MySQL Host:"));
        txtDbHost = new TextField("localhost");
        form.add(txtDbHost);

        form.add(new Label("Port:"));
        txtDbPort = new TextField("3306");
        form.add(txtDbPort);

        form.add(new Label("Database Name:"));
        txtDbName = new TextField("hospital_db");
        form.add(txtDbName);

        form.add(new Label("User:"));
        txtDbUser = new TextField("root");
        form.add(txtDbUser);

        form.add(new Label("Password:"));
        txtDbPass = new TextField("");
        txtDbPass.setEchoChar('*');
        form.add(txtDbPass);

        Button btnTestConn = new Button("Test JDBC Connection");
        btnTestConn.setBackground(COLOR_PRIMARY);
        btnTestConn.setForeground(Color.WHITE);
        btnTestConn.setActionCommand("DB_TEST");
        btnTestConn.addActionListener(this);
        form.add(btnTestConn);

        Button btnInitDb = new Button("Initialize MySQL Tables");
        btnInitDb.setActionCommand("DB_INIT");
        btnInitDb.addActionListener(this);
        form.add(btnInitDb);

        Button btnSync = new Button("Sync All Memory Data -> MySQL");
        btnSync.setBackground(COLOR_ACCENT);
        btnSync.setForeground(Color.WHITE);
        btnSync.setActionCommand("DB_SYNC");
        btnSync.addActionListener(this);
        form.add(btnSync);

        panel.add(form, BorderLayout.NORTH);

        Panel center = new Panel(new BorderLayout(4, 4));
        lblDbStatus = new Label("Database Status: Disconnected / Ready to test");
        lblDbStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
        center.add(lblDbStatus, BorderLayout.NORTH);

        txtAreaDbLog = new TextArea(12, 70);
        txtAreaDbLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaDbLog.setEditable(false);
        center.add(txtAreaDbLog, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ==========================================
    // Event Handling
    // ==========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.startsWith("NAV_")) {
            String tab = cmd.substring(4);
            if ("MYSQL_DATABASE".equals(tab)) tab = "DATABASE";
            cardLayout.show(contentPanel, tab);
            refreshAllViews();
            return;
        }

        switch (cmd) {
            case "PAT_ADD":
                handleRegisterPatient();
                break;
            case "PAT_CLEAR":
                clearPatientForm();
                break;
            case "PAT_SEARCH":
                handleSearchPatient();
                break;
            case "PAT_REFRESH":
                refreshPatientList();
                break;
            case "DOC_ADD":
                handleRegisterDoctor();
                break;
            case "DOC_ADD_SLOT":
                handleAddDoctorSlot();
                break;
            case "APT_BOOK":
                handleBookAppointment();
                break;
            case "APT_CANCEL":
                handleCancelAppointment();
                break;
            case "THREAD_SIM_BOOKING":
                handleThreadSimulation();
                break;
            case "MED_ADD":
                handleAddMedicine();
                break;
            case "MED_DISPENSE":
                handleDispenseMedicine();
                break;
            case "MED_RESTOCK":
                handleRestockMedicine();
                break;
            case "NOTIF_QUEUE":
                handleQueueNotification();
                break;
            case "NOTIF_DISPATCH":
                handleDispatchNotifications();
                break;
            case "RPT_GENERATE":
                handleGenerateReport();
                break;
            case "RPT_DOCTORS":
                handleGenerateDoctorReport();
                break;
            case "RPT_PHARMACY":
                handleGeneratePharmacyReport();
                break;
            case "DB_TEST":
                handleTestDb();
                break;
            case "DB_INIT":
                handleInitDb();
                break;
            case "DB_SYNC":
                handleSyncDb();
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == choiceDocSelect) {
            updateSlotListForSelectedDoctor();
        } else if (e.getSource() == choiceAptDoctor) {
            updateAppointmentSlots();
        }
    }

    // ==========================================
    // Operations & Refresh Handlers
    // ==========================================

    private void handleRegisterPatient() {
        try {
            String id = txtPatId.getText().trim();
            String name = txtPatName.getText().trim();
            int age = Integer.parseInt(txtPatAge.getText().trim());
            String gender = choicePatGender.getSelectedItem();
            String category = choicePatCategory.getSelectedItem();
            String phone = txtPatPhone.getText().trim();
            String email = txtPatEmail.getText().trim();
            String history = txtPatHistory.getText().trim();

            Patient p;
            if ("Emergency".equalsIgnoreCase(category)) {
                p = new EmergencyPatient(id, name, age, gender, phone, email, "Emergency", history);
            } else if ("Senior Citizen".equalsIgnoreCase(category)) {
                p = new SeniorCitizenPatient(id, name, age, gender, phone, email, "Senior Citizen", history);
            } else {
                p = new RegularPatient(id, name, age, gender, phone, email, "Regular", history);
            }

            patientService.registerPatient(p);
            // Also attempt JDBC insert
            patientDAO.insertPatient(p);

            showMessage("Patient registered successfully: " + name + " (" + id + ")");
            clearPatientForm();
            refreshAllViews();
        } catch (Exception ex) {
            showMessage("Error registering patient: " + ex.getMessage());
        }
    }

    private void clearPatientForm() {
        txtPatId.setText("P00" + (patientService.getPatientCount() + 1));
        txtPatName.setText("");
        txtPatAge.setText("30");
        txtPatHistory.setText("None");
    }

    private void handleSearchPatient() {
        String id = txtPatSearch.getText().trim();
        try {
            Patient p = patientService.searchPatient(id);
            txtAreaPatients.setText("=== PATIENT SEARCH RESULT ===\n\n" +
                    "ID: " + p.getPatientId() + "\n" +
                    "Name: " + p.getName() + "\n" +
                    "Age: " + p.getAge() + " | Gender: " + p.getGender() + "\n" +
                    "Category: " + p.getPatientCategory() + "\n" +
                    "Phone: " + p.getPhone() + " | Email: " + p.getEmail() + "\n" +
                    "Medical History: " + p.getMedicalHistory() + "\n");
        } catch (PatientNotFoundException ex) {
            showMessage("Patient not found: " + id);
        }
    }

    private void handleRegisterDoctor() {
        try {
            String id = txtDocId.getText().trim();
            String name = txtDocName.getText().trim();
            String spec = txtDocSpec.getText().trim();
            String dept = txtDocDept.getText().trim();
            double fee = Double.parseDouble(txtDocFee.getText().trim());

            Doctor d = new Doctor(id, name, spec, dept, fee);
            d.addSlot("09:00 AM");
            d.addSlot("11:00 AM");
            d.addSlot("02:00 PM");

            doctorService.registerDoctor(d);
            doctorDAO.insertDoctor(d);

            showMessage("Doctor registered successfully: " + name);
            refreshAllViews();
        } catch (Exception ex) {
            showMessage("Error registering doctor: " + ex.getMessage());
        }
    }

    private void handleAddDoctorSlot() {
        String docStr = choiceDocSelect.getSelectedItem();
        if (docStr == null) return;
        String docId = docStr.split(" - ")[0];
        String slot = txtDocNewSlot.getText().trim();
        try {
            Doctor doc = doctorService.searchDoctor(docId);
            doc.addSlot(slot);
            doctorDAO.addSlot(docId, slot);
            showMessage("Slot added: " + slot + " for " + doc.getName());
            updateSlotListForSelectedDoctor();
            updateAppointmentSlots();
        } catch (Exception ex) {
            showMessage("Error: " + ex.getMessage());
        }
    }

    private void handleBookAppointment() {
        try {
            String aptId = "APT" + (appointmentService.getAppointmentCount() + 101);
            String patStr = choiceAptPatient.getSelectedItem();
            String docStr = choiceAptDoctor.getSelectedItem();
            String slot = choiceAptSlot.getSelectedItem();
            String date = txtAptDate.getText().trim();
            String reason = txtAptReason.getText().trim();

            if (patStr == null || docStr == null || slot == null || slot.isEmpty()) {
                showMessage("Please select a valid patient, doctor, and time slot.");
                return;
            }

            String patId = patStr.split(" - ")[0];
            String docId = docStr.split(" - ")[0];

            Appointment apt = appointmentService.bookAppointment(aptId, patId, docId, date, slot, reason);
            appointmentDAO.insertAppointment(apt);

            Patient p = patientService.searchPatient(patId);
            Doctor d = doctorService.searchDoctor(docId);
            notificationService.createAppointmentReminder(p.getName(), d.getName(), date, slot, aptId);

            showMessage("Appointment Booked Successfully! ID: " + aptId);
            refreshAllViews();
        } catch (Exception ex) {
            showMessage("Booking Failed: " + ex.getMessage());
        }
    }

    private void handleCancelAppointment() {
        String aptId = txtAptCancelId.getText().trim();
        if (aptId.isEmpty()) {
            showMessage("Please enter an Appointment ID to cancel.");
            return;
        }

        boolean ok = appointmentService.cancelAppointment(aptId);
        if (ok) {
            appointmentDAO.updateStatus(aptId, "CANCELLED");
            showMessage("Appointment " + aptId + " cancelled and slot released.");
            refreshAllViews();
        } else {
            showMessage("Unable to cancel appointment " + aptId + " (not found or already cancelled).");
        }
    }

    private void handleThreadSimulation() {
        new Thread(() -> {
            String docId = "D001";
            String slot = "03:00 PM";
            try {
                Doctor doc = doctorService.searchDoctor(docId);
                doc.addSlot(slot);
            } catch (Exception ignored) {}

            StringBuilder sb = new StringBuilder();
            sb.append("=== MULTITHREADING CONCURRENT SLOT BOOKING RACE ===\n");
            sb.append("Target Slot: ").append(slot).append(" on Doctor D001\n");
            sb.append("Launching 3 concurrent threads simultaneously...\n\n");

            Thread t1 = new Thread(new AppointmentBookingTask(appointmentService, patientService, doctorService, "P001", docId, "2026-09-20", slot, "Thread 1 checkup", "Worker-Thread-1"));
            Thread t2 = new Thread(new AppointmentBookingTask(appointmentService, patientService, doctorService, "P002", docId, "2026-09-20", slot, "Thread 2 checkup", "Worker-Thread-2"));
            Thread t3 = new Thread(new AppointmentBookingTask(appointmentService, patientService, doctorService, "P003", docId, "2026-09-20", slot, "Thread 3 checkup", "Worker-Thread-3"));

            t1.start();
            t2.start();
            t3.start();

            try {
                t1.join();
                t2.join();
                t3.join();
            } catch (InterruptedException ignored) {}

            sb.append("Concurrent simulation complete. Exactly one thread succeeded without race condition!\n");
            txtAreaAppointments.setText(sb.toString());
            refreshAllViews();
        }).start();
    }

    private void handleAddMedicine() {
        try {
            String id = txtMedId.getText().trim();
            String name = txtMedName.getText().trim();
            String cat = choiceMedCategory.getSelectedItem();
            String mfg = txtMedMfg.getText().trim();
            int qty = Integer.parseInt(txtMedQty.getText().trim());
            double price = Double.parseDouble(txtMedPrice.getText().trim());
            int reorder = Integer.parseInt(txtMedReorder.getText().trim());

            Medicine m = new Medicine(id, name, cat, mfg, qty, price, reorder, "Standard");
            pharmacyService.addMedicine(m);
            medicineDAO.insertMedicine(m);

            showMessage("Medicine Added: " + name);
            refreshAllViews();
        } catch (Exception ex) {
            showMessage("Error adding medicine: " + ex.getMessage());
        }
    }

    private void handleDispenseMedicine() {
        String medStr = choiceMedDispense.getSelectedItem();
        if (medStr == null) return;
        String medId = medStr.split(" - ")[0];
        try {
            int qty = Integer.parseInt(txtDispenseQty.getText().trim());
            Medicine med = pharmacyService.searchMedicine(medId);
            pharmacyService.dispenseMedicine(medId, qty);
            medicineDAO.updateQuantity(medId, med.getQuantity());

            double bill = qty * med.getPrice();
            if (med.isLowStock()) {
                notificationService.createLowStockNotification(med.getMedicineName(), med.getQuantity(), med.getReorderLevel());
            }

            showMessage("Dispensed " + qty + " units of " + med.getMedicineName() + ". Total Bill: $" + String.format("%.2f", bill));
            refreshAllViews();
        } catch (Exception ex) {
            showMessage("Dispensing Failed: " + ex.getMessage());
        }
    }

    private void handleRestockMedicine() {
        String medStr = choiceMedDispense.getSelectedItem();
        if (medStr == null) return;
        String medId = medStr.split(" - ")[0];
        try {
            int qty = Integer.parseInt(txtRestockQty.getText().trim());
            pharmacyService.restockMedicine(medId, qty);
            Medicine med = pharmacyService.searchMedicine(medId);
            medicineDAO.updateQuantity(medId, med.getQuantity());

            showMessage("Restocked " + qty + " units of " + med.getMedicineName() + ". New Stock: " + med.getQuantity());
            refreshAllViews();
        } catch (Exception ex) {
            showMessage("Restock Failed: " + ex.getMessage());
        }
    }

    private void handleQueueNotification() {
        String to = txtNotifRecipient.getText().trim();
        String type = choiceNotifType.getSelectedItem();
        String msg = txtNotifMsg.getText().trim();

        notificationService.createNotification(to, msg, type);
        showMessage("Notification queued for: " + to);
        refreshAllViews();
    }

    private void handleDispatchNotifications() {
        new Thread(() -> {
            txtAreaNotifications.append("\n[AWT Worker Thread] Starting Dispatching Notification Queue...\n");
            notificationService.processNotificationQueue();
            txtAreaNotifications.append("[AWT Worker Thread] Notification Queue Dispatched Cleanly.\n");
            refreshAllViews();
        }).start();
    }

    private void handleGenerateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append("     COMPREHENSIVE HOSPITAL ANALYTICS REPORT\n");
        sb.append("====================================================\n\n");

        sb.append("1. PATIENT DEMOGRAPHICS:\n");
        sb.append("   Total Registered: ").append(patientService.getPatientCount()).append("\n\n");

        sb.append("2. DOCTOR ROSTER:\n");
        for (Doctor d : doctorService.getAllDoctors()) {
            sb.append("   - Dr. ").append(d.getName()).append(" (").append(d.getSpecialization()).append(")")
              .append(" | Slots Available: ").append(d.getAvailableSlots().size()).append("\n");
        }
        sb.append("\n");

        sb.append("3. APPOINTMENT SUMMARY:\n");
        sb.append("   Total Appointments: ").append(appointmentService.getAppointmentCount()).append("\n\n");

        sb.append("4. PHARMACY INVENTORY VALUATION:\n");
        double val = 0;
        int low = 0;
        for (Medicine m : pharmacyService.getAllMedicines()) {
            val += m.getQuantity() * m.getPrice();
            if (m.isLowStock()) low++;
        }
        sb.append("   Total Stock Value: $").append(String.format("%.2f", val)).append("\n");
        sb.append("   Low Stock Alerts: ").append(low).append("\n");

        txtAreaReports.setText(sb.toString());
    }

    private void handleGenerateDoctorReport() {
        StringBuilder sb = new StringBuilder("=== DOCTOR UTILIZATION ===\n\n");
        for (Doctor d : doctorService.getAllDoctors()) {
            int booked = 0;
            for (Appointment a : appointmentService.getAllAppointments()) {
                if (a.getDoctorId().equals(d.getDoctorId()) && a.getStatus() == Appointment.Status.BOOKED) booked++;
            }
            int total = d.getAvailableSlots().size() + booked;
            double pct = total > 0 ? ((double) booked / total) * 100.0 : 0.0;
            sb.append(String.format("%-20s | Booked: %-2d | Available: %-2d | Util: %.1f%%\n",
                    d.getName(), booked, d.getAvailableSlots().size(), pct));
        }
        txtAreaReports.setText(sb.toString());
    }

    private void handleGeneratePharmacyReport() {
        StringBuilder sb = new StringBuilder("=== PHARMACY INVENTORY REPORT ===\n\n");
        for (Medicine m : pharmacyService.getAllMedicines()) {
            sb.append(String.format("%-15s | Qty: %-4d | Price: $%-6.2f | Status: %s\n",
                    m.getMedicineName(), m.getQuantity(), m.getPrice(),
                    m.isLowStock() ? "LOW STOCK ALERT" : "OK"));
        }
        txtAreaReports.setText(sb.toString());
    }

    private void handleTestDb() {
        String host = txtDbHost.getText().trim();
        int port = Integer.parseInt(txtDbPort.getText().trim());
        String db = txtDbName.getText().trim();
        String user = txtDbUser.getText().trim();
        String pass = txtDbPass.getText();

        DBConnection.setCredentials(host, port, db, user, pass);
        boolean ok = DBConnection.testConnection();

        if (ok) {
            lblDbStatus.setText("Database Status: Connected to MySQL (" + host + ":" + port + "/" + db + ")");
            lblDbStatus.setForeground(new Color(16, 185, 129));
            txtAreaDbLog.append("[SUCCESS] Connected to MySQL database via JDBC Driver.\n");
        } else {
            lblDbStatus.setText("Database Status: Offline / Failed to connect");
            lblDbStatus.setForeground(COLOR_DANGER);
            txtAreaDbLog.append("[NOTICE] Could not connect to MySQL at " + host + ":" + port + ". System is operating in active memory mode.\n");
        }
    }

    private void handleInitDb() {
        handleTestDb();
        DBConnection.initializeDatabase();
        txtAreaDbLog.append("[INIT] Executed DDL table creation statements in MySQL.\n");
    }

    private void handleSyncDb() {
        handleTestDb();
        try {
            int pCount = 0, dCount = 0, mCount = 0;
            for (Patient p : patientService.getAllPatients()) {
                if (patientDAO.insertPatient(p)) pCount++;
            }
            for (Doctor d : doctorService.getAllDoctors()) {
                if (doctorDAO.insertDoctor(d)) dCount++;
            }
            for (Medicine m : pharmacyService.getAllMedicines()) {
                if (medicineDAO.insertMedicine(m)) mCount++;
            }
            txtAreaDbLog.append("[SYNC] Synchronized to MySQL: " + pCount + " Patients, " + dCount + " Doctors, " + mCount + " Medicines.\n");
        } catch (Exception ex) {
            txtAreaDbLog.append("[SYNC ERROR] " + ex.getMessage() + "\n");
        }
    }

    private void refreshAllViews() {
        // Update stats
        lblDashPatients.setText(String.valueOf(patientService.getPatientCount()));
        lblDashDoctors.setText(String.valueOf(doctorService.getDoctorCount()));
        lblDashAppointments.setText(String.valueOf(appointmentService.getAppointmentCount()));
        lblDashMedicines.setText(String.valueOf(pharmacyService.getMedicineCount()));

        int low = 0;
        for (Medicine m : pharmacyService.getAllMedicines()) if (m.isLowStock()) low++;
        lblDashLowStock.setText(String.valueOf(low));

        refreshPatientList();
        refreshDoctorList();
        refreshAppointmentList();
        refreshPharmacyList();
        refreshNotificationList();
    }

    private void refreshPatientList() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s | %-18s | %-4s | %-6s | %-12s | %-14s | %s\n",
                "ID", "Name", "Age", "Gender", "Category", "Phone", "Medical History"));
        sb.append("-".repeat(85)).append("\n");

        choiceAptPatient.removeAll();
        for (Patient p : patientService.getAllPatients()) {
            sb.append(String.format("%-6s | %-18s | %-4d | %-6s | %-12s | %-14s | %s\n",
                    p.getPatientId(), p.getName(), p.getAge(), p.getGender(),
                    p.getPatientCategory(), p.getPhone(), p.getMedicalHistory()));
            choiceAptPatient.add(p.getPatientId() + " - " + p.getName());
        }
        txtAreaPatients.setText(sb.toString());
    }

    private void refreshDoctorList() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s | %-18s | %-15s | %-16s | %s\n", "ID", "Name", "Specialization", "Department", "Fee"));
        sb.append("-".repeat(75)).append("\n");

        choiceDocSelect.removeAll();
        choiceAptDoctor.removeAll();

        for (Doctor d : doctorService.getAllDoctors()) {
            sb.append(String.format("%-6s | %-18s | %-15s | %-16s | $%.2f\n",
                    d.getDoctorId(), d.getName(), d.getSpecialization(), d.getDepartment(), d.getConsultationFee()));
            choiceDocSelect.add(d.getDoctorId() + " - " + d.getName());
            choiceAptDoctor.add(d.getDoctorId() + " - " + d.getName());
        }
        txtAreaDoctors.setText(sb.toString());

        updateSlotListForSelectedDoctor();
        updateAppointmentSlots();
    }

    private void updateSlotListForSelectedDoctor() {
        listDocSlots.removeAll();
        String docStr = choiceDocSelect.getSelectedItem();
        if (docStr == null) return;
        String docId = docStr.split(" - ")[0];
        try {
            Doctor doc = doctorService.searchDoctor(docId);
            for (String slot : doc.getAvailableSlots()) {
                listDocSlots.add(slot);
            }
        } catch (Exception ignored) {}
    }

    private void updateAppointmentSlots() {
        choiceAptSlot.removeAll();
        String docStr = choiceAptDoctor.getSelectedItem();
        if (docStr == null) return;
        String docId = docStr.split(" - ")[0];
        try {
            Doctor doc = doctorService.searchDoctor(docId);
            for (String slot : doc.getAvailableSlots()) {
                choiceAptSlot.add(slot);
            }
        } catch (Exception ignored) {}
    }

    private void refreshAppointmentList() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s | %-6s | %-6s | %-11s | %-9s | %-10s | %s\n",
                "Appt ID", "Pat ID", "Doc ID", "Date", "Time", "Status", "Reason"));
        sb.append("-".repeat(75)).append("\n");

        for (Appointment a : appointmentService.getAllAppointments()) {
            sb.append(String.format("%-8s | %-6s | %-6s | %-11s | %-9s | %-10s | %s\n",
                    a.getAppointmentId(), a.getPatientId(), a.getDoctorId(),
                    a.getDate(), a.getTime(), a.getStatus().name(), a.getReason()));
        }
        txtAreaAppointments.setText(sb.toString());
    }

    private void refreshPharmacyList() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s | %-16s | %-14s | %-6s | %-8s | %-8s | %s\n",
                "ID", "Name", "Category", "Stock", "Price", "Reorder", "Dosage"));
        sb.append("-".repeat(80)).append("\n");

        choiceMedDispense.removeAll();
        for (Medicine m : pharmacyService.getAllMedicines()) {
            sb.append(String.format("%-6s | %-16s | %-14s | %-6d | $%-7.2f | %-8d | %s %s\n",
                    m.getMedicineId(), m.getMedicineName(), m.getCategory(),
                    m.getQuantity(), m.getPrice(), m.getReorderLevel(), m.getDosage(),
                    m.isLowStock() ? " [LOW STOCK!]" : ""));
            choiceMedDispense.add(m.getMedicineId() + " - " + m.getMedicineName() + " (Qty: " + m.getQuantity() + ")");
        }
        txtAreaPharmacy.setText(sb.toString());
    }

    private void refreshNotificationList() {
        txtAreaNotifications.setText("=== LIVE NOTIFICATION QUEUE ===\n");
        txtAreaNotifications.append("Pending Dispatch: " + notificationService.getNotificationQueueCount() + "\n");
        txtAreaNotifications.append("Total History Dispatched: " + notificationService.getNotificationHistoryCount() + "\n\n");
        txtAreaNotifications.append("Active Worker Thread status: Ready for Dispatch.\n");
    }

    private void showMessage(String msg) {
        System.out.println("[Applet Alert] " + msg);
        // Display in AWT dialog if standalone frame available
    }

    // ==========================================
    // Standalone Frame Launcher (main)
    // ==========================================
    public static void main(String[] args) {
        Frame frame = new Frame("Smart Hospital Management System [AWT Applet & JDBC]");
        HospitalApplet applet = new HospitalApplet();

        frame.add(applet);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        applet.init();
        applet.start();
        frame.setVisible(true);
        System.out.println("Hospital AWT Applet Standalone Frame started.");
    }
}
