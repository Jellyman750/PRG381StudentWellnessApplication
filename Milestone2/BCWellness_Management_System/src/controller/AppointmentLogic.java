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

        Date sqlDate = new Date(utilDate.getTime());
        if (sqlDate.before(new Date(System.currentTimeMillis()))) {//makes sure the date is valid
            return "Appointment date cannot be in the past.";
        }
        // Check for double booking
        int counselorID = ((ComboItem) counselorItem).getId();
        Time sqlTime = new Time(utilTime.getTime());
        if (isDoubleBooked(counselorID, sqlDate, sqlTime)) {
            return "This counselor is already booked for the selected date and time.";
    }
        return null; // valid
    }
    
    //refreshed table when table is changed
    
    
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
    public void insertAppointment(JTextField txtStudentID, JComboBox<ComboItem> comboCounselor,JDatePickerImpl datePicker, JSpinner timeSpinner, 
                JComboBox<String> comboStatus, JTable appointmentTable){
        try {
            //gets inputs
            String studentNum = txtStudentID.getText().trim();
            ComboItem counselorItem = (ComboItem) comboCounselor.getSelectedItem();
            java.util.Date utilDate = (java.util.Date) datePicker.getModel().getValue();
            java.util.Date utilTime = (java.util.Date) timeSpinner.getValue();
            comboStatus.setSelectedIndex(0);
            String status = comboStatus.getSelectedItem().toString();
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
            appointmentDAO.insertAppointment(studentID, counselorID, date, time, status);
            JOptionPane.showMessageDialog(null, "Appointment added");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error checking availability: " + e.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
        }
    }

    //update appointment
    public void updateAppointment(int appointmentID,JTextField txtStudentID, JComboBox<ComboItem> comboCounselor,JDatePickerImpl datePicker, JSpinner timeSpinner, 
                JComboBox<String> comboStatus, JTable appointmentTable){
        try{
        //get inputs
        String studentNum=txtStudentID.getText().trim();
        ComboItem counselorItem=(ComboItem) comboCounselor.getSelectedItem();
        java.util.Date utilDate=(java.util.Date) datePicker.getModel().getValue();
        java.util.Date utilTime=(java.util.Date) timeSpinner.getValue();
        String status=comboStatus.getSelectedItem().toString();
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
        appointmentDAO.updateAppointment(appointmentID,studentID, counselorID, date, time, status);
        JOptionPane.showMessageDialog(null, "Appointment updated");
        }
        catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error checking availability: " + e.getMessage());
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
    }
    }
    
    public boolean deleteAppointment(JTable table){
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
        appointmentDAO.deleteAppointment(appointmentID);
        return true;
    }
    
    
}