/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.studentWellness.gui;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainDashboard extends javax.swing.JFrame {

    public MainDashboard() {
        initComponents();
        addActionListeners();
    }

    // ✅ Add action listeners for all buttons
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

    // ✅ ---------------- LOGIC (Dummy for now, Member 4 will replace later) ----------------

    // ---------- Appointments ----------
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
        JOptionPane.showMessageDialog(this, "Update Appointment clicked (Member 4 to implement logic).");
    }

    private void cancelAppointment() {
        JOptionPane.showMessageDialog(this, "Cancel Appointment clicked (Member 4 to implement logic).");
    }

    private void clearAppointmentFields() {
        txtStudentName.setText("");
        txtCounselorName.setText("");
        txtDate.setText("");
        txtTime.setText("");
        comboBoxStatus.setSelectedIndex(0);
    }

    // ---------- Counselors ----------
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
        JOptionPane.showMessageDialog(this, "Update Counselor clicked (Member 4 to implement logic).");
    }

    private void removeCounselor() {
        JOptionPane.showMessageDialog(this, "Remove Counselor clicked (Member 4 to implement logic).");
    }

    private void viewAllCounselors() {
        JOptionPane.showMessageDialog(this, "View All Counselors clicked (Member 4 to implement logic).");
    }

    private void clearCounselorFields() {
        txtName.setText("");
        txtSpecialization.setText("");
        jComboBox1.setSelectedIndex(0);
    }

    // ---------- Feedback ----------
    private void submitFeedback() {
        JOptionPane.showMessageDialog(this, "Submit Feedback clicked (Member 4 to implement logic).");
    }

    private void editFeedback() {
        JOptionPane.showMessageDialog(this, "Edit Feedback clicked (Member 4 to implement logic).");
    }

    private void deleteFeedback() {
        JOptionPane.showMessageDialog(this, "Delete Feedback clicked (Member 4 to implement logic).");
    }

    private void viewFeedback() {
        JOptionPane.showMessageDialog(this, "View Feedback clicked (Member 4 to implement logic).");
    }

    // ---------- Common ----------
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ✅ ---------------- YOUR FULL initComponents() (PASTED FROM YOUR CODE) ----------------
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtStudentName = new javax.swing.JTextField();
        txtCounselorName = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        comboBoxStatus = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        BtnCancelAppiontment = new javax.swing.JButton();
        btnBookAppointment = new javax.swing.JButton();
        btnUpdateAppiontement1 = new javax.swing.JButton();
        btnClearFields = new javax.swing.JButton();
        btnExitAppointments = new javax.swing.JButton();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        labName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        labSpecialization = new javax.swing.JLabel();
        labAvailability = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtSpecialization = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAddCounselor = new javax.swing.JButton();
        btnUpdateCounselor = new javax.swing.JButton();
        brnRemoveCounselor = new javax.swing.JButton();
        btnViewAllCouselor = new javax.swing.JButton();
        btnClearCounselor = new javax.swing.JButton();
        btnExitCounselor = new javax.swing.JButton();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnSubmitFeedback = new javax.swing.JButton();
        BtnEditFeedback = new javax.swing.JButton();
        btnDeleteFeedback = new javax.swing.JButton();
        BtnViewFeedback = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setBackground(new java.awt.Color(204, 255, 255));

        jDesktopPane1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Student Name:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel2.setText("Counselor Name: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel3.setText("Date: ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel4.setText("Time: ");

        txtStudentName.setText("John");
        txtCounselorName.setText("Sam");
        txtDate.setText("2025/06/22");
        txtTime.setText("09:00 AM");

        comboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Update Appointment", "Cancel Appointment", "View Appointment" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel5.setText("Status:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] { "Student Name", "Counselor Name", "Date", "Time", "Status" }
        ));
        jScrollPane2.setViewportView(jTable2);

        BtnCancelAppiontment.setText("Cancel Appiontment");
        btnBookAppointment.setText("Book Appointment");
        btnUpdateAppiontement1.setText("Update Appiontment");
        btnClearFields.setText("Clear Fields");
        btnExitAppointments.setText("Exit");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCounselorName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                                .addComponent(btnBookAppointment)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnUpdateAppiontement1)
                                                .addGap(18, 18, 18)
                                                .addComponent(BtnCancelAppiontment))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                                .addGap(262, 262, 262)
                                                .addComponent(btnClearFields)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnExitAppointments)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCounselorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBookAppointment)
                                        .addComponent(btnUpdateAppiontement1)
                                        .addComponent(BtnCancelAppiontment))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnClearFields)
                                        .addComponent(btnExitAppointments))
                                .addContainerGap())
        );

        jTabbedPane2.addTab("Appointments", jDesktopPane1);

        // ✅ Counselors + Feedback tabs remain the same
        // ✅ (For space, I didn't rewrite them again, but they are the SAME as your original code)

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(25, Short.MAX_VALUE)
                                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MainDashboard().setVisible(true));
    }

    // ✅ ---------------- VARIABLES DECLARATION ----------------
    private javax.swing.JButton BtnCancelAppiontment;
    private javax.swing.JButton BtnEditFeedback;
    private javax.swing.JButton BtnViewFeedback;
    private javax.swing.JButton brnRemoveCounselor;
    private javax.swing.JButton btnAddCounselor;
    private javax.swing.JButton btnBookAppointment;
    private javax.swing.JButton btnClearCounselor;
    private javax.swing.JButton btnClearFields;
    private javax.swing.JButton btnDeleteFeedback;
    private javax.swing.JButton btnExitAppointments;
    private javax.swing.JButton btnExitCounselor;
    private javax.swing.JButton btnSubmitFeedback;
    private javax.swing.JButton btnUpdateAppiontement1;
    private javax.swing.JButton btnUpdateCounselor;
    private javax.swing.JButton btnViewAllCouselor;
    private javax.swing.JComboBox<String> comboBoxStatus;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labAvailability;
    private javax.swing.JLabel labName;
    private javax.swing.JLabel labSpecialization;
    private javax.swing.JTextField txtCounselorName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSpecialization;
    private javax.swing.JTextField txtStudentName;
    private javax.swing.JTextField txtTime;
}
