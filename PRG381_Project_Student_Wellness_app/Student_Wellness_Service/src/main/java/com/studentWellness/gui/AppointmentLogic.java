package com.studentWellness.logic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class AppointmentLogic {

    public static boolean counselorExists(JTable counselorTable, String counselorName) {
        for (int i = 0; i < counselorTable.getRowCount(); i++) {
            if (counselorTable.getValueAt(i, 0).toString().equalsIgnoreCase(counselorName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDoubleBooked(String counselorName, String date, String time, File file) throws IOException {
        if (!file.exists()) return false;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4 &&
                parts[1].equalsIgnoreCase(counselorName) &&
                parts[2].equalsIgnoreCase(date) &&
                parts[3].equalsIgnoreCase(time)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    public static void bookAppointment(JTextField txtStudentName, JTextField txtCounselorName, JTextField txtDate,
                                       JTextField txtTime, JComboBox<String> comboBoxStatus, JTable jTable2, JTable jTable1) {
        String studentName = txtStudentName.getText();
        String counselorName = txtCounselorName.getText();
        String date = txtDate.getText();
        String time = txtTime.getText();
        String status = comboBoxStatus.getSelectedItem().toString();

        if (studentName.isEmpty() || counselorName.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all appointment details.");
            return;
        }

        if (!counselorExists(jTable1, counselorName)) {
            JOptionPane.showMessageDialog(null, "Counselor does not exist.");
            return;
        }

        File file = new File("appointments.csv");

        try {
            if (isDoubleBooked(counselorName, date, time, file)) {
                JOptionPane.showMessageDialog(null, "This counselor is already booked at the specified date and time.");
                return;
            }

            FileWriter fw = new FileWriter(file, true);
            fw.write(String.join(",", studentName, counselorName, date, time, status) + "\n");
            fw.close();

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.addRow(new Object[]{studentName, counselorName, date, time, status});
            JOptionPane.showMessageDialog(null, "Appointment successfully booked.");

            txtStudentName.setText("");
            txtCounselorName.setText("");
            txtDate.setText("");
            txtTime.setText("");
            comboBoxStatus.setSelectedIndex(0);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error writing appointment to file: " + ex.getMessage());
        }
    }
}