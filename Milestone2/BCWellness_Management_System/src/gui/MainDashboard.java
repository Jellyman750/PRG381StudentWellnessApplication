/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import database.AppointmentDAO;
import database.CounselorDAO;
import database.DBConnection;
import database.FeedbackDAO;
import model.Appointment;
import model.Counselor;
import model.Feedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;





public class MainDashboard extends javax.swing.JFrame {

    private final AppointmentLogic appointmentManager = new AppointmentLogic();
    private final CounselorLogic counselorManager = new CounselorLogic();
    private final FeedbackLogic feedbackManager = new FeedbackLogic();
    private final DBConnection db = new DBConnection();

    private ArrayList<Counselor> counselorList = new ArrayList<>();


    //create dao objects
    AppointmentDAO appointmentDAO= new AppointmentDAO(db);
    CounselorDAO counselorDAO = new CounselorDAO(db);
    FeedbackDAO feedbackDAO = new FeedbackDAO(db);

    public MainDashboard() {
        initComponents();
        try {
            db.connect();
            counselorDAO.createCounselor();
            appointmentDAO.createAppointment();
            feedbackDAO.createFeedback();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
        addActionListeners();
        addCounselorTableClickListener();
        loadScheduledAppointments();

        jTabbedPane2.addChangeListener(e -> {
            int selectedTab = jTabbedPane2.getSelectedIndex();
            String selectedTitle = jTabbedPane2.getTitleAt(selectedTab);

            if (selectedTitle.equals("Appointments")) {
                loadScheduledAppointments();
            } else if (selectedTitle.equals("Counselors")) {
                viewAllCounselors();
            }
        });


    }

    private void addActionListeners() {
        // Appointments
        btnBookAppointment.addActionListener(e -> {
            int studentNumber = Integer.parseInt(txtStudentName.getText());
            int counselorID = Integer.parseInt(txtCounselorName.getText());
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date.");
                return;
            }
            java.sql.Date date = new java.sql.Date(selectedDate.getTime());

            java.util.Date selectedTime = (java.util.Date) timeSpinner.getValue();
            java.sql.Time time = new java.sql.Time(selectedTime.getTime());

            String status = comboBoxStatus.getSelectedItem().toString();

            appointmentDAO.insertAppointment(studentNumber, counselorID, date, time, status);
            JOptionPane.showMessageDialog(this, "Appointment booked successfully.");
        });

        btnUpdateAppointment1.addActionListener(e -> updateAppointment());
        btnCancelAppointment.addActionListener(e -> cancelAppointment());
        btnClearFields.addActionListener(e -> clearAppointmentFields());
        btnExitAppointments.addActionListener(e -> exitApplication());

        // Counselors
        btnAddCounselor.addActionListener(e -> addCounselor());
        btnUpdateCounselor.addActionListener(e -> updateCounselor());
        btnRemoveCounselor.addActionListener(e -> removeCounselor());
        btnViewAllCounselors.addActionListener(e -> viewAllCounselors());
        btnClearCounselor.addActionListener(e -> clearCounselorFields());
        btnExitCounselor.addActionListener(e -> exitApplication());

        // Feedback
        btnSubmitFeedback.addActionListener(e -> submitFeedback());
        btnEditFeedback.addActionListener(e -> editFeedback());
        btnDeleteFeedback.addActionListener(e -> deleteFeedback());
        btnViewFeedback.addActionListener(e -> viewFeedback());
    }

    private void loadScheduledAppointments() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        ArrayList<Appointment> appointments = appointmentDAO.getScheduledAppointments();

        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                    a.getStudentNumber(),
                    a.getCounselorID(),
                    a.getAppointmentDate().toString(),
                    a.getAppointmentTime().toString(),
                    a.getStatus()
            });
        }
    }

    private void loadCounselorsToTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing data

        ArrayList<Counselor> counselors = counselorDAO.viewCounselor();

        for (Counselor c : counselors) {
            model.addRow(new Object[]{
                    c.getName() + " " + c.getSurname(),
                    c.getSpecialization(),
                    c.getEmail(),
                    c.isAvailable() ? "Available" : "Not Available"
            });
        }
    }

    private void addCounselorTableClickListener() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTable1.getSelectedRow();

                if (selectedRow >= 0) {
                    // Get values from the selected row
                    String name = jTable1.getValueAt(selectedRow, 0).toString();
                    String specialization = jTable1.getValueAt(selectedRow, 1).toString();
                    String email = jTable1.getValueAt(selectedRow, 2).toString();
                    String availability = jTable1.getValueAt(selectedRow, 3).toString();

                    // Set the input fields
                    txtCounselorNameInput.setText(name);
                    txtSpecialization.setText(specialization);
                    txtCounselorEmail.setText(email);
                    jComboBox1.setSelectedItem(availability);
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }


    // Dummy methods for UI
    private void bookAppointment() {
        if (txtStudentName.getText().isEmpty() || txtCounselorName.getText().isEmpty() ||
                datePicker.getModel().getValue() == null || timeSpinner.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all appointment details.");
            return;
        }

        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        java.util.Date selectedTime = (java.util.Date) timeSpinner.getValue();
        java.sql.Time sqlTime = new java.sql.Time(selectedTime.getTime());

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[]{
                txtStudentName.getText(),
                txtCounselorName.getText(),
                sqlDate.toString(),
                sqlTime.toString(),
                comboBoxStatus.getSelectedItem().toString()
        });

        JOptionPane.showMessageDialog(this, "Appointment booked (dummy data).");
        clearAppointmentFields();
    }

    private void loadAppointments() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear existing data

        for (Appointment appt : appointmentDAO.viewAppointment()) {
            model.addRow(new Object[]{
                    appt.getStudentNumber(),
                    appt.getCounselorID(),
                    appt.getAppointmentDate().toString(),
                    appt.getAppointmentTime().toString(),
                    appt.getStatus()
            });
        }
    }

    private void updateAppointment() {
        JOptionPane.showMessageDialog(this, "Update Appointment clicked (logic to be added).");
    }

    private void cancelAppointment() {
        JOptionPane.showMessageDialog(this, "Cancel Appointment clicked (logic to be added).");
    }

    private void clearAppointmentFields() {
        txtStudentName.setText("");
        txtCounselorName.setText("");
        datePicker.getModel().setValue(null);
        timeSpinner.setValue(new java.util.Date());
        comboBoxStatus.setSelectedIndex(0);
    }

    private void addCounselor() {
        if (txtCounselorNameInput.getText().trim().isEmpty() ||
                txtCounselorEmail.getText().trim().isEmpty() ||
                txtSpecialization.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all counselor details.");
            return;
        }

        String email = txtCounselorEmail.getText().trim();

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }


        String nameInput = txtCounselorNameInput.getText().trim();
        String[] parts = nameInput.split(" ", 2);
        String firstName = parts[0];
        String surname = (parts.length > 1) ? parts[1] : "";
        String email = txtCounselorEmail.getText();
        String specialisation = txtSpecialization.getText();
        boolean availability = jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Available");

        // Use CounselorDAO directly
        boolean success = counselorDAO.insertCounselor(firstName, surname, email, specialisation, availability);

        if (success) {
            JOptionPane.showMessageDialog(this, "Counselor added successfully.");
            viewAllCounselors(); // Refresh table
            clearCounselorFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding counselor.");
        }
    }



    private void updateCounselor() {
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a counselor to update.");
            return;
        }

        if (txtCounselorNameInput.getText().trim().isEmpty() ||
                txtCounselorEmail.getText().trim().isEmpty() ||
                txtSpecialization.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all counselor details.");
            return;
        }

        String email = txtCounselorEmail.getText().trim();

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }


        // Get the selected Counselor object from the list
        Counselor selectedCounselor = counselorList.get(selectedRow);
        int counselorID = selectedCounselor.getCounselorID();

        String nameInput = txtCounselorNameInput.getText().trim();
        String[] parts = nameInput.split(" ", 2);
        String firstName = parts[0];
        String surname = (parts.length > 1) ? parts[1] : "";
        String email = txtCounselorEmail.getText();
        String specialisation = txtSpecialization.getText();
        boolean availability = jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Available");

        boolean success = counselorDAO.updateCounselor(counselorID, firstName, surname, email, specialisation, availability);

        if (success) {
            JOptionPane.showMessageDialog(this, "Counselor updated successfully.");
            viewAllCounselors(); // Refresh the table and list
            clearCounselorFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating counselor.");
        }
    }


    private void removeCounselor() {
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a counselor to remove.");
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove this counselor?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Get counselorID from the list
        Counselor selectedCounselor = counselorList.get(selectedRow);
        int counselorID = selectedCounselor.getCounselorID();

        boolean success = counselorDAO.deleteCounselor(counselorID);

        if (success) {
            JOptionPane.showMessageDialog(this, "Counselor removed successfully.");
            viewAllCounselors(); // Refresh table and list
        } else {
            JOptionPane.showMessageDialog(this, "Error removing counselor.");
        }
    }


    private void viewAllCounselors() {
        try {
            counselorList = counselorDAO.viewCounselor(); // Load counselors from DB

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear table

            for (Counselor c : counselorList) {
                model.addRow(new Object[]{
                        c.getName() + " " + c.getSurname(),
                        c.getSpecialization(),
                        c.getEmail(),
                        c.isAvailable() ? "Available" : "Not Available"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading counselors: " + e.getMessage());
        }
    }




    private void clearCounselorFields() {
        txtCounselorNameInput.setText("");  // ✅ Correct field
        txtSpecialization.setText("");
        txtCounselorEmail.setText("");
        jComboBox1.setSelectedIndex(0);
    }

    private void submitFeedback() {
        JOptionPane.showMessageDialog(this, "Submit Feedback clicked (logic to be added).");
    }

    private void editFeedback() {
        JOptionPane.showMessageDialog(this, "Edit Feedback clicked (logic to be added).");
    }

    private void deleteFeedback() {
        JOptionPane.showMessageDialog(this, "Delete Feedback clicked (logic to be added).");
    }

    private void viewFeedback() {
        JOptionPane.showMessageDialog(this, "View Feedback clicked (logic to be added).");
    }

    private void viewAppointments() {
        JOptionPane.showMessageDialog(this, "View Appointments (logic to be added).");
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jTabbedPane2 = new JTabbedPane();
        jTabbedPane2.setFont(new Font("Arial", Font.PLAIN, 16));

        // === APPOINTMENTS TAB ===
        jDesktopPane1 = new JDesktopPane();
        jLabel1 = new JLabel("Student Name:");
        jLabel2 = new JLabel("Counselor Name:");
        jLabel3 = new JLabel("Date:");
        jLabel4 = new JLabel("Time:");
        jLabel5 = new JLabel("Status:");

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setFont(labelFont);
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setFont(labelFont);
        jLabel3.setForeground(Color.WHITE);
        jLabel3.setFont(labelFont);
        jLabel4.setForeground(Color.WHITE);
        jLabel4.setFont(labelFont);
        jLabel5.setForeground(Color.WHITE);
        jLabel5.setFont(labelFont);

        txtStudentName = new JTextField();
        txtCounselorName = new JTextField();
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new org.jdatepicker.impl.DateComponentFormatter());

        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        Dimension fieldSize = new Dimension(160, 30);

        txtStudentName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCounselorName.setFont(new Font("Arial", Font.PLAIN, 14));
        datePicker.setFont(new Font("Arial", Font.PLAIN, 14));
        timeSpinner.setFont(new Font("Arial", Font.PLAIN, 14));



        comboBoxStatus = new JComboBox<>(new String[]{"Scheduled", "Cancelled", "Completed"});
        comboBoxStatus.setFont(new Font("Arial", Font.PLAIN, 14));

        jTable2 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Student Name", "Counselor Name", "Date", "Time", "Status"}
        ));
        jTable2.setFont(new Font("Arial", Font.PLAIN, 12));
        jScrollPane2 = new JScrollPane(jTable2);

        btnCancelAppointment = new JButton("Cancel Appointment");
        btnBookAppointment = new JButton("Book Appointment");
        btnUpdateAppointment1 = new JButton("Update Appointment");
        btnClearFields = new JButton("Clear Fields");
        btnExitAppointments = new JButton("Exit");

        Color buttonColor = new Color(100, 149, 237);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        for (JButton btn : new JButton[]{btnBookAppointment, btnUpdateAppointment1, btnCancelAppointment, btnClearFields, btnExitAppointments}) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }

        JPanel datePanelWrapper = new JPanel();
        datePanelWrapper.setLayout(new BoxLayout(datePanelWrapper, BoxLayout.Y_AXIS));
        datePanelWrapper.add(datePicker);
        datePanelWrapper.setPreferredSize(new Dimension(160, 30));

        timeSpinner.setPreferredSize(new Dimension(160, 30));
        JPanel timePanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        timePanelWrapper.add(timeSpinner);
        timePanelWrapper.setPreferredSize(new Dimension(160, 30));



        GroupLayout appLayout = new GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(appLayout);
        appLayout.setHorizontalGroup(
                appLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appLayout.createSequentialGroup()
                                .addGap(20)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(txtStudentName, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCounselorName, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(datePanelWrapper, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(timePanelWrapper, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                .addGap(20)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(appLayout.createSequentialGroup()
                                                .addComponent(btnBookAppointment)
                                                .addGap(10)
                                                .addComponent(btnUpdateAppointment1)
                                                .addGap(10)
                                                .addComponent(btnCancelAppointment))
                                        .addGroup(appLayout.createSequentialGroup()
                                                .addGap(240)
                                                .addComponent(btnClearFields)
                                                .addGap(10)
                                                .addComponent(btnExitAppointments)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        appLayout.setVerticalGroup(
                appLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appLayout.createSequentialGroup()
                                .addGap(20)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(appLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(6)
                                                .addComponent(txtStudentName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel2)
                                                .addGap(6)
                                                .addComponent(txtCounselorName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel3)
                                                .addGap(6)
                                                .addComponent(datePanelWrapper, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel4)
                                                .addGap(6)
                                                .addComponent(timePanelWrapper, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel5)
                                                .addGap(6)
                                                .addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addGap(15)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBookAppointment)
                                        .addComponent(btnUpdateAppointment1)
                                        .addComponent(btnCancelAppointment))
                                .addGap(15)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnClearFields)
                                        .addComponent(btnExitAppointments))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Appointments", jDesktopPane1);

        // === COUNSELORS TAB ===
        panelCounselors = new JPanel();
        labCounselor = new JLabel("Counselor:");
        txtSpecialization = new JTextField();
        labSpecialization = new JLabel("Specialization:");
        txtCounselorEmail = new JTextField();
        labCounselorEmail = new JLabel("Email:");
        labAvailability = new JLabel("Availability:");
        txtCounselorNameInput = new JTextField();
        txtCounselorNameInput.setFont(new Font("Arial", Font.PLAIN, 14));
        jComboBox1 = new JComboBox<>(new String[]{"Available", "Not Available"});

        labCounselor.setForeground(Color.WHITE);
        labCounselor.setFont(labelFont);
        labSpecialization.setForeground(Color.WHITE);
        labSpecialization.setFont(labelFont);
        labCounselorEmail.setForeground(Color.WHITE);
        labCounselorEmail.setFont(labelFont);
        labAvailability.setForeground(Color.WHITE);
        labAvailability.setFont(labelFont);

        txtSpecialization.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCounselorEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCounselorName.setFont(new Font("Arial", Font.PLAIN, 14));
        jComboBox1.setFont(new Font("Arial", Font.PLAIN, 14));

        jTable1 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Counselor", "Specialization", "Email", "Availability"}
        ));
        jTable1.setFont(new Font("Arial", Font.PLAIN, 12));
        jScrollPane1 = new JScrollPane(jTable1);

        btnAddCounselor = new JButton("Add Counselor");
        btnUpdateCounselor = new JButton("Update Counselor");
        btnRemoveCounselor = new JButton("Remove Counselor");
        btnViewAllCounselors = new JButton("View All");
        btnClearCounselor = new JButton("Clear");
        btnExitCounselor = new JButton("Exit");

        for (JButton btn : new JButton[]{btnAddCounselor, btnUpdateCounselor, btnRemoveCounselor, btnViewAllCounselors, btnClearCounselor, btnExitCounselor}) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }

        GroupLayout cLayout = new GroupLayout(panelCounselors);
        panelCounselors.setLayout(cLayout);
        cLayout.setHorizontalGroup(
                cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cLayout.createSequentialGroup()
                                .addGap(20)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labCounselor)
                                        .addComponent(txtCounselorNameInput, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labSpecialization)
                                        .addComponent(txtSpecialization, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labCounselorEmail)
                                        .addComponent(txtCounselorEmail, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labAvailability)
                                        .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                .addGap(20)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(cLayout.createSequentialGroup()
                                                .addComponent(btnAddCounselor)
                                                .addGap(10)
                                                .addComponent(btnUpdateCounselor)
                                                .addGap(10)
                                                .addComponent(btnRemoveCounselor))
                                        .addGroup(cLayout.createSequentialGroup()
                                                .addComponent(btnViewAllCounselors)
                                                .addGap(10)
                                                .addComponent(btnClearCounselor)
                                                .addGap(10)
                                                .addComponent(btnExitCounselor)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        cLayout.setVerticalGroup(
                cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cLayout.createSequentialGroup()
                                .addGap(20)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(cLayout.createSequentialGroup()
                                                .addComponent(labCounselor)
                                                .addGap(6)
                                                .addComponent(txtCounselorNameInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(labSpecialization)
                                                .addGap(6)
                                                .addComponent(txtSpecialization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(labCounselorEmail)
                                                .addGap(6)
                                                .addComponent(txtCounselorEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(labAvailability)
                                                .addGap(6)
                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addGap(15)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddCounselor)
                                        .addComponent(btnUpdateCounselor)
                                        .addComponent(btnRemoveCounselor))
                                .addGap(15)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnViewAllCounselors)
                                        .addComponent(btnClearCounselor)
                                        .addComponent(btnExitCounselor))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Counselors", panelCounselors);

        // === FEEDBACK TAB ===
        panelFeedback = new JPanel();
        jLabel6 = new JLabel("Select Counselor:");
        jLabel7 = new JLabel("Feedback:");
        jLabel8 = new JLabel("Student Number:");
        jLabel9 = new JLabel("Submission Date:");

        jLabel6.setForeground(Color.WHITE);
        jLabel6.setFont(labelFont);
        jLabel7.setForeground(Color.WHITE);
        jLabel7.setFont(labelFont);
        jLabel8.setForeground(Color.WHITE);
        jLabel8.setFont(labelFont);
        jLabel9.setForeground(Color.WHITE);
        jLabel9.setFont(labelFont);

        jComboBox2 = new JComboBox<>(new String[]{"Counselor 1", "Counselor 2", "Counselor 3"});
        jComboBox2.setFont(new Font("Arial", Font.PLAIN, 14));
        txtStudentNumber = new JTextField();
        txtStudentNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSubmissionDate = new JTextField();
        txtSubmissionDate.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextArea1 = new JTextArea(5, 20);
        jTextArea1.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane3 = new JScrollPane(jTextArea1);

        btnSubmitFeedback = new JButton("Submit");
        btnEditFeedback = new JButton("Edit");
        btnDeleteFeedback = new JButton("Delete");
        btnViewFeedback = new JButton("View All");

        for (JButton btn : new JButton[]{btnSubmitFeedback, btnEditFeedback, btnDeleteFeedback, btnViewFeedback}) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }

        GroupLayout fLayout = new GroupLayout(panelFeedback);
        panelFeedback.setLayout(fLayout);
        fLayout.setHorizontalGroup(
                fLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(fLayout.createSequentialGroup()
                                .addGap(20)
                                .addGroup(fLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtStudentNumber, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtSubmissionDate, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(fLayout.createSequentialGroup()
                                                .addComponent(btnSubmitFeedback)
                                                .addGap(10)
                                                .addComponent(btnEditFeedback)
                                                .addGap(10)
                                                .addComponent(btnDeleteFeedback)
                                                .addGap(10)
                                                .addComponent(btnViewFeedback)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        fLayout.setVerticalGroup(
                fLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(fLayout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(jLabel6)
                                .addGap(6)
                                .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(jLabel8)
                                .addGap(6)
                                .addComponent(txtStudentNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(jLabel9)
                                .addGap(6)
                                .addComponent(txtSubmissionDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(jLabel7)
                                .addGap(6)
                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addGap(15)
                                .addGroup(fLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSubmitFeedback)
                                        .addComponent(btnEditFeedback)
                                        .addComponent(btnDeleteFeedback)
                                        .addComponent(btnViewFeedback))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Feedback", panelFeedback);

        getContentPane().add(jTabbedPane2);

        Color darkBlue = new Color(0, 51, 102);
        jDesktopPane1.setBackground(darkBlue);
        panelCounselors.setBackground(darkBlue);
        panelFeedback.setBackground(darkBlue);
        jTabbedPane2.setBackground(darkBlue);
        jTabbedPane2.setForeground(Color.WHITE);

        setTitle("Student Wellness Dashboard");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // === VARIABLES ===
    private JTabbedPane jTabbedPane2;
    private JDesktopPane jDesktopPane1;
    private JPanel panelCounselors, panelFeedback;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, labCounselor, labSpecialization, labCounselorEmail, labAvailability;
    private JTextField txtStudentName,txtCounselorNameInput, txtCounselorName, txtSpecialization, txtCounselorEmail, txtStudentNumber, txtSubmissionDate;
    private JDatePickerImpl datePicker;
    private JSpinner timeSpinner;
    private JComboBox<String> comboBoxStatus, jComboBox1, jComboBox2;
    private JTable jTable1, jTable2;
    private JScrollPane jScrollPane1, jScrollPane2, jScrollPane3;
    private JTextArea jTextArea1;
    private JButton btnCancelAppointment, btnBookAppointment, btnUpdateAppointment1, btnClearFields, btnExitAppointments;
    private JButton btnAddCounselor, btnUpdateCounselor, btnRemoveCounselor, btnViewAllCounselors, btnClearCounselor, btnExitCounselor;
    private JButton btnSubmitFeedback, btnEditFeedback, btnDeleteFeedback, btnViewFeedback;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}