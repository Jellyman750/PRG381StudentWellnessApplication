/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package com.studentWellness.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainDashboard extends javax.swing.JFrame {

    public MainDashboard() {
        initComponents();
        addActionListeners();
    }

    private void addActionListeners() {
        // Appointments
        btnBookAppointment.addActionListener(e -> bookAppointment());
        btnUpdateAppiontement1.addActionListener(e -> updateAppointment());
        BtnCancelAppiontment.addActionListener(e -> cancelAppointment());
        btnClearFields.addActionListener(e -> clearAppointmentFields());
        btnExitAppointments.addActionListener(e -> exitApplication());

        // Counselors
        btnAddCounselor.addActionListener(e -> addCounselor());
        btnUpdateCounselor.addActionListener(e -> updateCounselor());
        brnRemoveCounselor.addActionListener(e -> removeCounselor());
        btnViewAllCouselor.addActionListener(e -> viewAllCounselors());
        btnClearCounselor.addActionListener(e -> clearCounselorFields());
        btnExitCounselor.addActionListener(e -> exitApplication());

        // Feedback
        btnSubmitFeedback.addActionListener(e -> submitFeedback());
        BtnEditFeedback.addActionListener(e -> editFeedback());
        btnDeleteFeedback.addActionListener(e -> deleteFeedback());
        BtnViewFeedback.addActionListener(e -> viewFeedback());
    }

    // Dummy methods (Member 4 can add real logic later)
    private void bookAppointment() {
        if (txtStudentName.getText().isEmpty() || txtCounselorName.getText().isEmpty() ||
                txtDate.getText().isEmpty() || txtTime.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all appointment details.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[]{
                txtStudentName.getText(),
                txtCounselorName.getText(),
                txtDate.getText(),
                txtTime.getText(),
                comboBoxStatus.getSelectedItem().toString()
        });
        JOptionPane.showMessageDialog(this, "Appointment booked (dummy data).");
        clearAppointmentFields();
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
        txtDate.setText("");
        txtTime.setText("");
        comboBoxStatus.setSelectedIndex(0);
    }

    private void addCounselor() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{
                txtName.getText(),
                txtSpecialization.getText(),
                jComboBox1.getSelectedItem().toString()
        });
        JOptionPane.showMessageDialog(this, "Counselor added (dummy data).");
        clearCounselorFields();
    }

    private void updateCounselor() {
        JOptionPane.showMessageDialog(this, "Update Counselor clicked (logic to be added).");
    }

    private void removeCounselor() {
        JOptionPane.showMessageDialog(this, "Remove Counselor clicked (logic to be added).");
    }

    private void viewAllCounselors() {
        JOptionPane.showMessageDialog(this, "View All Counselors clicked (logic to be added).");
    }

    private void clearCounselorFields() {
        txtName.setText("");
        txtSpecialization.setText("");
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

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initialize components
        jTabbedPane2 = new JTabbedPane();
        jTabbedPane2.setFont(new Font("Arial", Font.PLAIN, 16));

        // === APPOINTMENTS TAB ===
        jDesktopPane1 = new JDesktopPane();
        jLabel1 = new JLabel("Student Name:");
        jLabel2 = new JLabel("Counselor Name:");
        jLabel3 = new JLabel("Date:");
        jLabel4 = new JLabel("Time:");
        jLabel5 = new JLabel("Status:");

        // Set white text and consistent font for labels
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
        txtDate = new JTextField();
        txtTime = new JTextField();
        txtStudentName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCounselorName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDate.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTime.setFont(new Font("Arial", Font.PLAIN, 14));

        comboBoxStatus = new JComboBox<>(new String[]{"Scheduled", "Cancelled", "Completed"});
        comboBoxStatus.setFont(new Font("Arial", Font.PLAIN, 14));

        jTable2 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Student Name", "Counselor Name", "Date", "Time", "Status"}
        ));
        jTable2.setFont(new Font("Arial", Font.PLAIN, 12));
        jScrollPane2 = new JScrollPane(jTable2);

        BtnCancelAppiontment = new JButton("Cancel Appointment");
        btnBookAppointment = new JButton("Book Appointment");
        btnUpdateAppiontement1 = new JButton("Update Appointment");
        btnClearFields = new JButton("Clear Fields");
        btnExitAppointments = new JButton("Exit");

        // Style buttons
        Color buttonColor = new Color(100, 149, 237); // Lighter blue
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        for (JButton btn : new JButton[]{btnBookAppointment, btnUpdateAppiontement1, BtnCancelAppiontment, btnClearFields, btnExitAppointments}) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }

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
                                        .addComponent(txtDate, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtTime, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                .addGap(20)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(appLayout.createSequentialGroup()
                                                .addComponent(btnBookAppointment)
                                                .addGap(10)
                                                .addComponent(btnUpdateAppiontement1)
                                                .addGap(10)
                                                .addComponent(BtnCancelAppiontment))
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
                                                .addComponent(txtDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel4)
                                                .addGap(6)
                                                .addComponent(txtTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel5)
                                                .addGap(6)
                                                .addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addGap(15)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBookAppointment)
                                        .addComponent(btnUpdateAppiontement1)
                                        .addComponent(BtnCancelAppiontment))
                                .addGap(15)
                                .addGroup(appLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnClearFields)
                                        .addComponent(btnExitAppointments))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Appointments", jDesktopPane1);

        // === COUNSELORS TAB ===
        panelCounselors = new JPanel();
        labName = new JLabel("Name:");
        txtName = new JTextField();
        labSpecialization = new JLabel("Specialization:");
        txtSpecialization = new JTextField();
        labAvailability = new JLabel("Availability:");
        jComboBox1 = new JComboBox<>(new String[]{"Available", "Not Available"});

        // Set white text and consistent font for labels
        labName.setForeground(Color.WHITE);
        labName.setFont(labelFont);
        labSpecialization.setForeground(Color.WHITE);
        labSpecialization.setFont(labelFont);
        labAvailability.setForeground(Color.WHITE);
        labAvailability.setFont(labelFont);

        txtName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSpecialization.setFont(new Font("Arial", Font.PLAIN, 14));
        jComboBox1.setFont(new Font("Arial", Font.PLAIN, 14));

        jTable1 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Name", "Specialization", "Availability"}
        ));
        jTable1.setFont(new Font("Arial", Font.PLAIN, 12));
        jScrollPane1 = new JScrollPane(jTable1);

        btnAddCounselor = new JButton("Add Counselor");
        btnUpdateCounselor = new JButton("Update Counselor");
        brnRemoveCounselor = new JButton("Remove Counselor");
        btnViewAllCouselor = new JButton("View All");
        btnClearCounselor = new JButton("Clear");
        btnExitCounselor = new JButton("Exit");

        // Style buttons
        for (JButton btn : new JButton[]{btnAddCounselor, btnUpdateCounselor, brnRemoveCounselor, btnViewAllCouselor, btnClearCounselor, btnExitCounselor}) {
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
                                        .addComponent(labName)
                                        .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labSpecialization)
                                        .addComponent(txtSpecialization, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
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
                                                .addComponent(brnRemoveCounselor))
                                        .addGroup(cLayout.createSequentialGroup()
                                                .addComponent(btnViewAllCouselor)
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
                                                .addComponent(labName)
                                                .addGap(6)
                                                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(labSpecialization)
                                                .addGap(6)
                                                .addComponent(txtSpecialization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(labAvailability)
                                                .addGap(6)
                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addGap(15)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddCounselor)
                                        .addComponent(btnUpdateCounselor)
                                        .addComponent(brnRemoveCounselor))
                                .addGap(15)
                                .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnViewAllCouselor)
                                        .addComponent(btnClearCounselor)
                                        .addComponent(btnExitCounselor))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Counselors", panelCounselors);

        // === FEEDBACK TAB ===
        panelFeedback = new JPanel();
        jLabel6 = new JLabel("Select Counselor:");
        jLabel7 = new JLabel("Feedback:");

        // Set white text and consistent font for labels
        jLabel6.setForeground(Color.WHITE);
        jLabel6.setFont(labelFont);
        jLabel7.setForeground(Color.WHITE);
        jLabel7.setFont(labelFont);

        jComboBox2 = new JComboBox<>(new String[]{"Counselor 1", "Counselor 2", "Counselor 3"});
        jComboBox2.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextArea1 = new JTextArea(5, 20);
        jTextArea1.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane3 = new JScrollPane(jTextArea1);

        btnSubmitFeedback = new JButton("Submit");
        BtnEditFeedback = new JButton("Edit");
        btnDeleteFeedback = new JButton("Delete");
        BtnViewFeedback = new JButton("View All");

        // Style buttons
        for (JButton btn : new JButton[]{btnSubmitFeedback, BtnEditFeedback, btnDeleteFeedback, BtnViewFeedback}) {
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
                                        .addComponent(jLabel7)
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(fLayout.createSequentialGroup()
                                                .addComponent(btnSubmitFeedback)
                                                .addGap(10)
                                                .addComponent(BtnEditFeedback)
                                                .addGap(10)
                                                .addComponent(btnDeleteFeedback)
                                                .addGap(10)
                                                .addComponent(BtnViewFeedback)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        fLayout.setVerticalGroup(
                fLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(fLayout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(jLabel6)
                                .addGap(6)
                                .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(15)
                                .addComponent(jLabel7)
                                .addGap(6)
                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addGap(15)
                                .addGroup(fLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSubmitFeedback)
                                        .addComponent(BtnEditFeedback)
                                        .addComponent(btnDeleteFeedback)
                                        .addComponent(BtnViewFeedback))
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Feedback", panelFeedback);

        getContentPane().add(jTabbedPane2);

        // === Apply Dark Blue Background and Styling ===
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
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, labName, labSpecialization, labAvailability;
    private JTextField txtStudentName, txtCounselorName, txtDate, txtTime, txtName, txtSpecialization;
    private JComboBox<String> comboBoxStatus, jComboBox1, jComboBox2;
    private JTable jTable1, jTable2;
    private JScrollPane jScrollPane1, jScrollPane2, jScrollPane3;
    private JTextArea jTextArea1;
    private JButton BtnCancelAppiontment, btnBookAppointment, btnUpdateAppiontement1, btnClearFields, btnExitAppointments;
    private JButton btnAddCounselor, btnUpdateCounselor, brnRemoveCounselor, btnViewAllCouselor, btnClearCounselor, btnExitCounselor;
    private JButton btnSubmitFeedback, BtnEditFeedback, btnDeleteFeedback, BtnViewFeedback;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}
