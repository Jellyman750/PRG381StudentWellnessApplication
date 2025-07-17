package controller;

//imports
import database.AppointmentDAO;
import database.CounselorDAO;
import java.io.IOException;
import model.Appointment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePickerImpl;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import controller.ComboItem;
import java.util.Calendar;
import model.Counselor;

public class AppointmentLogic {
    
    private final AppointmentDAO appointmentDAO;
    public AppointmentLogic(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

//checks if the counselor is double booked
    public boolean isDoubleBooked(int counselorID, Date date, Time time) throws IOException {
        ArrayList<Appointment> appointments = appointmentDAO.viewAppointment();
        for (Appointment appt : appointments) {
            if (appt.getCounselorID() == counselorID &&
                appt.getAppointmentDate().equals(date) &&
                appt.getAppointmentTime().equals(time)) {
                return true;
            }
        }
        return false;
   
    }
    //checks if inputs are valid
    private String validateInput(String studentStr, Object counselorItem, java.util.Date utilDate, java.util.Date utilTime)throws IOException {
        if (studentStr.isEmpty() || counselorItem == null || utilDate == null || utilTime == null) { //makes sure fields are not empty
            return "All fields must be filled.";
        }
        try {
            Integer.parseInt(studentStr);//makes sure studentnumber is an int
        } catch (NumberFormatException e) {
            return "Student ID must be numeric.";
        }
        // Combine date and time into one Date object
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(utilDate);

            java.util.Calendar timeCal = java.util.Calendar.getInstance();
            timeCal.setTime(utilTime);
            int hour = timeCal.get(Calendar.HOUR_OF_DAY);
            int minute = timeCal.get(Calendar.MINUTE);
            if (hour < 8 || (hour >= 17 && minute > 0)) {// Checks if time is outside allowed range
                return "Appointment time must be between 08:00 and 17:00.";
            }

            calendar.set(java.util.Calendar.HOUR_OF_DAY, timeCal.get(java.util.Calendar.HOUR_OF_DAY));
            calendar.set(java.util.Calendar.MINUTE, timeCal.get(java.util.Calendar.MINUTE));
            calendar.set(java.util.Calendar.SECOND, 0);
            calendar.set(java.util.Calendar.MILLISECOND, 0);

            java.util.Date combinedDateTime = calendar.getTime();
            java.util.Date now = new java.util.Date();

            if (combinedDateTime.before(now)) {
                return "Appointment must be scheduled for a future time.";
            }
        // Check for double booking
        int counselorID = ((ComboItem) counselorItem).getId();
        Date sqlDate = new Date(utilDate.getTime());
        Time sqlTime = new Time(utilTime.getTime());
        if (isDoubleBooked(counselorID, sqlDate, sqlTime)) {
            return "This counselor is already booked for the selected date and time.";
    }
        return null; // valid
    }
    
    //displays all the appointments  
    public void ViewAllAppointments(JTable table,CounselorDAO counselorDAO){
        ArrayList<Appointment> appointments=appointmentDAO.viewAppointment();
        DefaultTableModel model= (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for(Appointment a: appointments){
            Counselor c = counselorDAO.getCounselorByID(a.getCounselorID());
            String counselorName = c != null ? c.getName() + " " + c.getSurname() : "Unknown";
            model.addRow(new Object[]{
                a.getAppointmentID(),
                a.getStudentNumber(),
                counselorName,
                a.getAppointmentDate(),
                a.getAppointmentTime(),
                a.getStatus()
            });
        }
    }
    public void loadScheduledAppointments(JTable table, CounselorDAO counselorDAO) {
        ArrayList<Appointment> appointments = appointmentDAO.getScheduledAppointments();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Appointment a : appointments) {
            Counselor c = counselorDAO.getCounselorByID(a.getCounselorID());
            String counselorName = c != null ? c.getName() + " " + c.getSurname() : "Unknown";

            model.addRow(new Object[]{
                    a.getAppointmentID(),
                    a.getStudentNumber(),
                    counselorName,
                    a.getAppointmentDate().toString(),
                    a.getAppointmentTime().toString(),
                    a.getStatus()
            });
        }
    }
    
    //inserts into database table
    public void insertAppointment(JTextField txtStudentID, JComboBox<ComboItem> comboCounselor,JDatePickerImpl datePicker, JSpinner timeSpinner, JTable appointmentTable){
        try {
            //gets inputs
            String studentNum = txtStudentID.getText().trim();
            ComboItem counselorItem = (ComboItem) comboCounselor.getSelectedItem();
            java.util.Date utilDate = (java.util.Date) datePicker.getModel().getValue();
            java.util.Date utilTime = (java.util.Date) timeSpinner.getValue();
            //validates inputs
            String error = validateInput(studentNum, counselorItem, utilDate, utilTime);
            if (error != null) {
                JOptionPane.showMessageDialog(null, error);
                return;
            }
            int studentID = Integer.parseInt(studentNum);
            int counselorID = counselorItem.getId();
            Date date = new Date(utilDate.getTime());
            Time time = new Time(utilTime.getTime());
            //inserts into db
            appointmentDAO.insertAppointment(studentID, counselorID, date, time);
            JOptionPane.showMessageDialog(null, "Appointment added");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error checking availability: " + e.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
        }
    }

    //update appointment
    public void updateAppointment(int appointmentID,JTextField txtStudentID, JComboBox<ComboItem> comboCounselor,JDatePickerImpl datePicker, JSpinner timeSpinner,JTable appointmentTable){
        try{
        //get inputs
        String studentNum=txtStudentID.getText().trim();
        ComboItem counselorItem=(ComboItem) comboCounselor.getSelectedItem();
        java.util.Date utilDate=(java.util.Date) datePicker.getModel().getValue();
        java.util.Date utilTime=(java.util.Date) timeSpinner.getValue();
        //validate inputs
        String error= validateInput(studentNum,counselorItem,utilDate,utilTime);
        if(error!=null){
            JOptionPane.showMessageDialog(null,error);
            return;
        }
        int studentID=Integer.parseInt(studentNum);
        int counselorID=counselorItem.getId();
        Date date=new Date(utilDate.getTime());
        Time time= new Time(utilTime.getTime());
        
        //update database
        appointmentDAO.updateAppointment(appointmentID,studentID, counselorID, date, time);
        JOptionPane.showMessageDialog(null, "Appointment updated");
        }
        catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error checking availability: " + e.getMessage());
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
    }
    }
    //cancels appointment
    public boolean cancelAppointment(JTable table){
        //get he selected row
        int selectedRow=table.getSelectedRow();
        if (selectedRow==-1){
            JOptionPane.showMessageDialog(null,"Please select an appointment to cancel");
            return false;
        }
        int confirm=JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel this appointment?");
        if (confirm!=JOptionPane.YES_OPTION){
            return false;
        }
        int appointmentID=(int) table.getValueAt(selectedRow,0);
        appointmentDAO.cancelAppointment(appointmentID);
        return true;
    }
     //changes status to completed when the appointment date and time has passed
    public void markPastAppointmentsAsCompleted() {
        ArrayList<Appointment> appointments = appointmentDAO.viewAppointment();
        java.util.Date now = new java.util.Date();

        for (Appointment a : appointments) {
            // check only scheduled appointments
            if ("Scheduled".equalsIgnoreCase(a.getStatus())) {
                // combine date and time
                java.util.Date appointmentDateTime = new java.util.Date(
                        a.getAppointmentDate().getTime() + a.getAppointmentTime().getTime()
                );
                if (appointmentDateTime.before(now)) {
                    appointmentDAO.updateAppointmentStatus(a.getAppointmentID());// updates status
                }
            }
        }
    }
    
    
}