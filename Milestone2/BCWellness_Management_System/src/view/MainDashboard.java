/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.AppointmentLogic;
import database.AppointmentDAO;
import database.CounselorDAO;
import database.DBConnection;
import database.FeedbackDAO;
import model.Appointment;
import model.Counselor;
import model.Feedback;
import controller.ComboItem;
import controller.CounselorLogic;
import controller.FeedbackLogic;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class MainDashboard extends javax.swing.JFrame {

    private final DBConnection db = new DBConnection();
    private ArrayList<Counselor> counselorList = new ArrayList<>();
    private ArrayList<Feedback> feedbackList = new ArrayList<>();
    
    private final AppointmentDAO appointmentDAO= new AppointmentDAO(db);
    private final CounselorDAO counselorDAO = new CounselorDAO(db);
    private final FeedbackDAO feedbackDAO = new FeedbackDAO(db);

    private final AppointmentLogic appointmentManager = new AppointmentLogic(appointmentDAO);
    //private final CounselorLogic counselorManager;
    private final FeedbackLogic feedbackManager = new FeedbackLogic();
    
    private int selectedAppointmentID = -1;

    public MainDashboard() {
        initComponents();
        try {
            db.connect();
            counselorDAO.createCounselor();
            appointmentDAO.createAppointment();
            feedbackDAO.createFeedback();
            populateCounselorComboBox();
            addFeedbackTableClickListener();
             loadCounselors();
            appointmentManager.loadScheduledAppointments(tblAppointment, counselorDAO);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database driver not found: " + e.getMessage());
            db.disconnect();
        } catch (RuntimeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
            db.disconnect();
        }
        addActionListeners();
        viewAllCounselors();
        addCounselorTableClickListener();
        appointmentManager.loadScheduledAppointments(tblAppointment, counselorDAO);

        appointmentManager.loadScheduledAppointments(tblAppointment, counselorDAO);
        
        
        tblAppointment.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblAppointment.getSelectedRow() != -1) {
        int selectedRow = tblAppointment.getSelectedRow();

        int appointmentID = (int) tblAppointment.getValueAt(selectedRow, 0);
        String studentNumber = tblAppointment.getValueAt(selectedRow, 1).toString();
        String counselorNameFromTable = tblAppointment.getValueAt(selectedRow, 2).toString();
        Date date = Date.valueOf(tblAppointment.getValueAt(selectedRow, 3).toString());
        Time time = Time.valueOf(tblAppointment.getValueAt(selectedRow, 4).toString());
        String status = tblAppointment.getValueAt(selectedRow, 5).toString();

        txtStudentID.setText(studentNumber);

        comboCounselorName.setSelectedItem(counselorNameFromTable);

        datePicker.getModel().setDate(
            date.toLocalDate().getYear(),
            date.toLocalDate().getMonthValue() - 1,
            date.toLocalDate().getDayOfMonth()
        );
        datePicker.getModel().setSelected(true);
        timeSpinner.setValue(time);
        comboBoxStatus.setSelectedItem(status);
    }
});
    }
    private void loadCounselors(){//loads rhe counselors into the combobox
        try{
            List<Counselor> counselors=counselorDAO.viewCounselor(); 
            for (Counselor c:counselors){
                ComboItem item = new ComboItem(c.getCounselorID(), c.getName() + " " + c.getSurname());
                comboCounselorName.addItem(item);
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Failded to load counselors: "+ex.getMessage());
        }
    }

    private void populateCounselorComboBox() {
        jComboBox2.removeAllItems();
        counselorList = counselorDAO.viewCounselor();
        for (Counselor c : counselorList) {
            jComboBox2.addItem(c.getName() + " " + c.getSurname() + " (ID: " + c.getCounselorID() + ")");
        }
    }

    private void addActionListeners() {
        // Appointments
        btnBookAppointment.addActionListener(e -> {//insert appointment
            try{
            appointmentManager.insertAppointment(txtStudentID, comboCounselorName , datePicker, timeSpinner, comboBoxStatus, jTable1);
            appointmentManager.loadScheduledAppointments(tblAppointment, counselorDAO);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error booking appointment"+ex.getMessage());
            } });
        btnUpdateAppointment1.addActionListener(e -> {//update appointment
        try{
            int selectedRow = tblAppointment.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to update.");
            return;
        }
        int appointmentID = (int) tblAppointment.getValueAt(selectedRow, 0); // get appointment id from table
        appointmentManager.updateAppointment(appointmentID,txtStudentID,comboCounselorName,datePicker,timeSpinner,comboBoxStatus,tblAppointment);
        appointmentManager.loadScheduledAppointments(tblAppointment, counselorDAO);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error updating appointment"+ex.getMessage());
            }
        });
        btnCancelAppointment.addActionListener(e -> {//delete appontment
          try {
            appointmentManager.deleteAppointment(tblAppointment);
                appointmentManager.loadScheduledAppointments(tblAppointment, counselorDAO);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting appointment: " + ex.getMessage());
            }  
        });
        btnViewAllAppointments.addActionListener(e -> {
            try {
                appointmentManager.ViewAllAppointments(tblAppointment,counselorDAO);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage());
            }
             });
        btnClearFields.addActionListener(e -> clearAppointmentFields());
        btnExitAppointments.addActionListener(e -> exitApplication());

        // Counselors
        btnAddCounselor.addActionListener(e -> addCounselor());
        btnUpdateCounselor.addActionListener(e -> updateCounselor());
        btnRemoveCounselor.addActionListener(e -> removeCounselor());
        btnClearCounselor.addActionListener(e -> clearCounselorFields());

        // Feedback
        btnSubmitFeedback.addActionListener(e -> submitFeedback());
        btnEditFeedback.addActionListener(e -> editFeedback());
        btnDeleteFeedback.addActionListener(e -> deleteFeedback());
        btnViewFeedback.addActionListener(e -> viewFeedback());
    }


    private void addCounselorTableClickListener() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow >= 0) {
                    Counselor c = counselorList.get(selectedRow);
                    txtCounselorNameInput.setText(c.getName() + " " + c.getSurname());
                    txtSpecialization.setText(c.getSpecialization());
                    txtCounselorEmail.setText(c.getEmail());
                    jComboBox1.setSelectedItem(c.isAvailable() ? "Available" : "Not Available");
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }


    private void clearAppointmentFields() {
        txtStudentID.setText("");
        comboCounselorName.setSelectedIndex(0);
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
        String specialisation = txtSpecialization.getText();
        boolean availability = jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Available");

        if (counselorDAO.counselorExists(firstName, surname, email)) {
            JOptionPane.showMessageDialog(this, "A counselor with the same name or email already exists.");
            return;
        }

        boolean success = counselorDAO.insertCounselor(firstName, surname, email, specialisation, availability);
        if (success) {
            JOptionPane.showMessageDialog(this, "Counselor added successfully.");
            viewAllCounselors();
            clearCounselorFields();
            populateCounselorComboBox();
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

        Counselor selectedCounselor = counselorList.get(selectedRow);
        int counselorID = selectedCounselor.getCounselorID();

        String nameInput = txtCounselorNameInput.getText().trim();
        String[] parts = nameInput.split(" ", 2);
        String firstName = parts[0];
        String surname = (parts.length > 1) ? parts[1] : "";
        String specialisation = txtSpecialization.getText();
        boolean availability = jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Available");

        boolean success = counselorDAO.updateCounselor(counselorID, firstName, surname, email, specialisation, availability);
        if (success) {
            JOptionPane.showMessageDialog(this, "Counselor updated successfully.");
            viewAllCounselors();
            clearCounselorFields();
            populateCounselorComboBox();
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

        Counselor selectedCounselor = counselorList.get(selectedRow);
        int counselorID = selectedCounselor.getCounselorID();

        ArrayList<Appointment> appointments = counselorDAO.getAppointmentsByCounselor(counselorID);
        ArrayList<Feedback> feedbacks = feedbackDAO.getFeedbackByCounselor(counselorID);

        StringBuilder message = new StringBuilder("This counselor has related data:\n\n");
        if (!appointments.isEmpty()) {
            message.append("Appointments:\n");
            for (Appointment app : appointments) {
                message.append("- ")
                        .append(app.getAppointmentDate())
                        .append(" at ")
                        .append(app.getAppointmentTime())
                        .append(", Student: ")
                        .append(app.getStudentNumber())
                        .append(", Status: ")
                        .append(app.getStatus())
                        .append("\n");
            }
        }

        if (!feedbacks.isEmpty()) {
            message.append("\nFeedback:\n");
            for (Feedback fb : feedbacks) {
                message.append("- Student: ")
                        .append(fb.getStudentNumber())
                        .append(", Comment: ")
                        .append(fb.getComments())
                        .append("\n");
            }
        }

        message.append("\nDo you want to delete the counselor and all related data?");
        int confirm = JOptionPane.showConfirmDialog(this, message.toString(), "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            feedbackDAO.deleteFeedbackByCounselor(counselorID);
            counselorDAO.deleteAppointmentsByCounselor(counselorID);
            boolean success = counselorDAO.deleteCounselor(counselorID);
            if (success) {
                JOptionPane.showMessageDialog(this, "Counselor and all related data deleted.");
                viewAllCounselors();
                clearCounselorFields();
                populateCounselorComboBox();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting counselor.");
            }
        }
    }

    private void viewAllCounselors() {
        try {
            counselorList = counselorDAO.viewCounselor();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

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

    private void submitFeedback() {
        try {
            String selectedCounselor = (String) jComboBox2.getSelectedItem();
            if (selectedCounselor == null) {
                JOptionPane.showMessageDialog(this, "Please select a counselor.");
                return;
            }
            int counselorID = Integer.parseInt(selectedCounselor.substring(selectedCounselor.indexOf("ID: ") + 4, selectedCounselor.indexOf(")")));
            ArrayList<Appointment> appointments = appointmentDAO.getAppointmentsByCounselor(counselorID);
            if (appointments.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No appointments found for this counselor.");
                return;
            }
            int appointmentID = appointments.get(0).getAppointmentID();

            String studentNumber = txtStudentNumber.getText().trim();
            int rating = Integer.parseInt((String) ratingComboBox.getSelectedItem());
            String comments = jTextArea1.getText().trim();
            java.util.Date selectedDate = (java.util.Date) feedbackDatePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a submission date.");
                return;
            }
            java.sql.Date submissionDate = new java.sql.Date(selectedDate.getTime());

            if (studentNumber.isEmpty() || rating < 1 || rating > 5) {
                JOptionPane.showMessageDialog(this, "Please enter a valid student number and rating (1-5).");
                return;
            }

            boolean success = feedbackDAO.insertFeedback(appointmentID, studentNumber, rating, comments, submissionDate);
            if (success) {
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully.");
                viewFeedback();
                clearFeedbackFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error submitting feedback.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid student number and rating.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void editFeedback() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback entry to edit.");
            return;
        }

        try {
            int feedbackID = feedbackList.get(selectedRow).getFeedbackID();
            String selectedCounselor = (String) jComboBox2.getSelectedItem();
            if (selectedCounselor == null) {
                JOptionPane.showMessageDialog(this, "Please select a counselor.");
                return;
            }
            int counselorID = Integer.parseInt(selectedCounselor.substring(selectedCounselor.indexOf("ID: ") + 4, selectedCounselor.indexOf(")")));
            ArrayList<Appointment> appointments = appointmentDAO.getAppointmentsByCounselor(counselorID);
            if (appointments.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No appointments found for this counselor.");
                return;
            }
            int appointmentID = appointments.get(0).getAppointmentID();

            String studentNumber = txtStudentNumber.getText().trim();
            int rating = Integer.parseInt((String) ratingComboBox.getSelectedItem());
            String comments = jTextArea1.getText().trim();
            java.util.Date selectedDate = (java.util.Date) feedbackDatePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a submission date.");
                return;
            }
            java.sql.Date submissionDate = new java.sql.Date(selectedDate.getTime());

            if (studentNumber.isEmpty() || rating < 1 || rating > 5) {
                JOptionPane.showMessageDialog(this, "Please enter a valid student number and rating (1-5).");
                return;
            }

            boolean success = feedbackDAO.updateFeedback(feedbackID, appointmentID, studentNumber, rating, comments, submissionDate);
            if (success) {
                JOptionPane.showMessageDialog(this, "Feedback updated successfully.");
                viewFeedback();
                clearFeedbackFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating feedback.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid student number and rating.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteFeedback() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback entry to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this feedback?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int feedbackID = feedbackList.get(selectedRow).getFeedbackID();
            boolean success = feedbackDAO.deleteFeedback(feedbackID);
            if (success) {
                JOptionPane.showMessageDialog(this, "Feedback deleted successfully.");
                viewFeedback();
                clearFeedbackFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting feedback.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void viewFeedback() {
        try {
            feedbackList = feedbackDAO.viewFeedback();
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);

            for (Feedback f : feedbackList) {
                String counselorName = "Unknown";
                for (Counselor c : counselorList) {
                    ArrayList<Appointment> appointments = appointmentDAO.getAppointmentsByCounselor(c.getCounselorID());
                    for (Appointment a : appointments) {
                        if (a.getAppointmentID() == f.getAppointmentID()) {
                            counselorName = c.getName() + " " + c.getSurname();
                            break;
                        }
                    }
                }
                model.addRow(new Object[]{
                        f.getFeedbackID(),
                        counselorName,
                        f.getStudentNumber(),
                        f.getRating(),
                        f.getComments(),
                        f.getSubmissionDate().toString()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading feedback: " + e.getMessage());
        }
    }

    private void clearFeedbackFields() {
        jComboBox2.setSelectedIndex(-1);
        txtStudentNumber.setText("");
        ratingComboBox.setSelectedIndex(0);
        jTextArea1.setText("");
        feedbackDatePicker.getModel().setValue(null);
    }

  
 private void addFeedbackTableClickListener() {
    jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int selectedRow = jTable3.getSelectedRow();
            if (selectedRow >= 0) {
                Feedback feedback = feedbackList.get(selectedRow);

                for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                    String item = (String) jComboBox2.getItemAt(i);
                    ArrayList<Appointment> appointments = appointmentDAO.getAppointmentsByCounselor(
                            Integer.parseInt(item.substring(item.indexOf("ID: ") + 4, item.indexOf(")")))
                    );
                    for (Appointment a : appointments) {
                        if (a.getAppointmentID() == feedback.getAppointmentID()) {
                            jComboBox2.setSelectedIndex(i);
                            break;
                        }
                    }
                }

                txtStudentNumber.setText(String.valueOf(feedback.getStudentNumber()));
                ratingComboBox.setSelectedItem(String.valueOf(feedback.getRating()));
                jTextArea1.setText(feedback.getComments());

                // FIXED: Properly cast to UtilDateModel before setting the date
                java.sql.Date sqlDate = feedback.getSubmissionDate();
                if (sqlDate != null) {
                    java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                    ((org.jdatepicker.impl.UtilDateModel) feedbackDatePicker.getModel()).setValue(utilDate);
                } else {
                    ((org.jdatepicker.impl.UtilDateModel) feedbackDatePicker.getModel()).setValue(null);
                }
            }
        }
    });
}



    private void clearCounselorFields() {
        txtCounselorNameInput.setText("");
        txtSpecialization.setText("");
        txtCounselorEmail.setText("");
        jComboBox1.setSelectedIndex(0);
    }

    private void filterCounselorTable() {
        String searchText = txtSearchCounselor.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Counselor c : counselorList) {
            String fullName = (c.getName() + " " + c.getSurname()).toLowerCase();
            String specialization = c.getSpecialization().toLowerCase();
            String email = c.getEmail().toLowerCase();

            if (fullName.contains(searchText) || specialization.contains(searchText) || email.contains(searchText)) {
                model.addRow(new Object[]{
                        c.getName() + " " + c.getSurname(),
                        c.getSpecialization(),
                        c.getEmail(),
                        c.isAvailable() ? "Available" : "Not Available"
                });
            }
        }
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            db.disconnect();
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jTabbedPane2 = new JTabbedPane();
        jTabbedPane2.setFont(new Font("Arial", Font.PLAIN, 16));

        // === APPOINTMENTS TAB ===
       jDesktopPane1 = new JDesktopPane();
        jLabel1 = new JLabel("Student ID:");
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

        txtStudentID  = new JTextField();
        comboCounselorName = new JComboBox(); 
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

        txtStudentID.setFont(new Font("Arial", Font.PLAIN, 14));
        comboCounselorName.setFont(new Font("Arial", Font.PLAIN, 14));
        datePicker.setFont(new Font("Arial", Font.PLAIN, 14));
        timeSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBoxStatus = new JComboBox<>(new String[]{"Scheduled", "Cancelled", "Completed"});
        comboBoxStatus.setFont(new Font("Arial", Font.PLAIN, 14));

        DefaultTableModel modelAppointment = new DefaultTableModel(new Object[][]{},new String[]{"ID", "Student Name", "Counselor Name", "Date", "Time", "Status"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // makes all cells read only
            }
        };
        tblAppointment = new JTable(modelAppointment);
        tblAppointment.setFont(new Font("Arial", Font.PLAIN, 12));
        jScrollPane2 = new JScrollPane(tblAppointment);

        btnCancelAppointment = new JButton("Cancel Appointment");
        btnBookAppointment = new JButton("Book Appointment");
        btnUpdateAppointment1 = new JButton("Update Appointment");
        btnViewAllAppointments=new JButton("View All Appointments");
        btnClearFields = new JButton("Clear Fields");
        btnExitAppointments = new JButton("Exit");

        Color buttonColor = new Color(100, 149, 237);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        for (JButton btn : new JButton[]{btnViewAllAppointments,btnBookAppointment, btnUpdateAppointment1, btnCancelAppointment, btnClearFields, btnExitAppointments}) {
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
                                .addComponent(txtStudentID, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(comboCounselorName, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(btnViewAllAppointments)
                                    .addGap(10)
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
                                                .addComponent(txtStudentID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(jLabel2)
                                                .addGap(6)
                                                .addComponent(comboCounselorName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(btnViewAllAppointments)
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

        labSearchCounselor = new JLabel("Search:");
        labSearchCounselor.setForeground(Color.WHITE);
        labSearchCounselor.setFont(new Font("Arial", Font.PLAIN, 14));

        txtSearchCounselor = new JTextField();
        txtSearchCounselor.setFont(new Font("Arial", Font.PLAIN, 14));

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


        for (JButton btn : new JButton[]{btnAddCounselor, btnUpdateCounselor, btnRemoveCounselor, btnViewAllCounselors, btnClearCounselor}) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }

        txtSearchCounselor.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { filterCounselorTable(); }
            public void removeUpdate(DocumentEvent e) { filterCounselorTable(); }
            public void insertUpdate(DocumentEvent e) { filterCounselorTable(); }
        });

        GroupLayout cLayout = new GroupLayout(panelCounselors);
        panelCounselors.setLayout(cLayout);
        cLayout.setAutoCreateGaps(true);
        cLayout.setAutoCreateContainerGaps(true);

        cLayout.setHorizontalGroup(
                cLayout.createSequentialGroup()
                        .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labCounselor)
                                .addComponent(txtCounselorNameInput, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labSpecialization)
                                .addComponent(txtSpecialization, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labCounselorEmail)
                                .addComponent(txtCounselorEmail, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labAvailability)
                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addGap(25) // To center the button ( (160 - 110) / 2 = 25 )
                                .addComponent(btnClearCounselor, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(labSearchCounselor)
                                .addComponent(txtSearchCounselor, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                .addGroup(cLayout.createSequentialGroup()
                                        .addComponent(btnAddCounselor)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnUpdateCounselor)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnRemoveCounselor)
                                )
                        )
        );


        cLayout.setVerticalGroup(
                cLayout.createSequentialGroup()
                        .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(cLayout.createSequentialGroup()
                                        .addComponent(labCounselor)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCounselorNameInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labSpecialization)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSpecialization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labCounselorEmail)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCounselorEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labAvailability)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(15)
                                        .addComponent(btnClearCounselor, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                )
                                .addGroup(cLayout.createSequentialGroup()
                                        .addComponent(labSearchCounselor)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSearchCounselor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(cLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnAddCounselor)
                                                .addComponent(btnUpdateCounselor)
                                                .addComponent(btnRemoveCounselor))

                                )
                        )
        );

        jTabbedPane2.addTab("Counselors", panelCounselors);

        // === FEEDBACK TAB ===
        panelFeedback = new JPanel();
        jLabel6 = new JLabel("Select Counselor:");
        jLabel7 = new JLabel("Feedback:");
        jLabel8 = new JLabel("Student Number:");
        jLabel9 = new JLabel("Submission Date:");
        jLabel10 = new JLabel("Rating:");

        jLabel6.setForeground(Color.WHITE);
        jLabel6.setFont(labelFont);
        jLabel7.setForeground(Color.WHITE);
        jLabel7.setFont(labelFont);
        jLabel8.setForeground(Color.WHITE);
        jLabel8.setFont(labelFont);
        jLabel9.setForeground(Color.WHITE);
        jLabel9.setFont(labelFont);
        jLabel10.setForeground(Color.WHITE);
        jLabel10.setFont(labelFont);

        jComboBox2 = new JComboBox<>();
        jComboBox2.setFont(new Font("Arial", Font.PLAIN, 14));
        txtStudentNumber = new JTextField();
        txtStudentNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        UtilDateModel feedbackDateModel = new UtilDateModel();
        JDatePanelImpl feedbackDatePanel = new JDatePanelImpl(feedbackDateModel, p);
        feedbackDatePicker = new JDatePickerImpl(feedbackDatePanel, new org.jdatepicker.impl.DateComponentFormatter());
        feedbackDatePicker.setFont(new Font("Arial", Font.PLAIN, 14));
        ratingComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        ratingComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextArea1 = new JTextArea(5, 20);
        jTextArea1.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane3 = new JScrollPane(jTextArea1);

        jTable3 = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Feedback ID", "Counselor", "Student Number", "Rating", "Comments", "Submission Date"}
        ));
        jTable3.setFont(new Font("Arial", Font.PLAIN, 12));
        jScrollPane4 = new JScrollPane(jTable3);

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

        JPanel feedbackDatePanelWrapper = new JPanel();
        feedbackDatePanelWrapper.setLayout(new BoxLayout(feedbackDatePanelWrapper, BoxLayout.Y_AXIS));
        feedbackDatePanelWrapper.add(feedbackDatePicker);
        feedbackDatePanelWrapper.setPreferredSize(new Dimension(160, 30));

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
                .addComponent(jLabel10)
                .addComponent(ratingComboBox, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9)
                .addComponent(feedbackDatePanelWrapper, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7)
                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
            .addGap(20)
            .addGroup(fLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
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
            .addGroup(fLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(fLayout.createSequentialGroup()
                    .addComponent(jLabel6)
                    .addGap(6)
                    .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(jLabel8)
                    .addGap(6)
                    .addComponent(txtStudentNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(jLabel10)
                    .addGap(6)
                    .addComponent(ratingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(jLabel9)
                    .addGap(6)
                    .addComponent(feedbackDatePanelWrapper, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(jLabel7)
                    .addGap(6)
                    .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(fLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btnSubmitFeedback, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnEditFeedback, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDeleteFeedback, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnViewFeedback, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
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
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, jLabel10;
    private JLabel labCounselor, labSpecialization, labCounselorEmail, labAvailability, labSearchCounselor;
    private JTextField txtStudentID, txtCounselorNameInput, txtSpecialization, txtCounselorEmail, txtStudentNumber, txtSearchCounselor;
    private JComboBox<ComboItem> comboCounselorName;
    private JDatePickerImpl datePicker, feedbackDatePicker;
    private JSpinner timeSpinner;
    private JComboBox<String> comboBoxStatus, jComboBox1, jComboBox2, ratingComboBox;
    private JTable jTable1, tblAppointment, jTable3;
    private JScrollPane jScrollPane1, jScrollPane2, jScrollPane3, jScrollPane4;
    private JTextArea jTextArea1;
    private JButton btnCancelAppointment, btnBookAppointment, btnUpdateAppointment1, btnClearFields, btnExitAppointments,btnViewAllAppointments;
    private JButton btnAddCounselor, btnUpdateCounselor, btnRemoveCounselor, btnViewAllCounselors, btnClearCounselor, btnExitCounselor;
    private JButton btnSubmitFeedback, btnEditFeedback, btnDeleteFeedback, btnViewFeedback;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}