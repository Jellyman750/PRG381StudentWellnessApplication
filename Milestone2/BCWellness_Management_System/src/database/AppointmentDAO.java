/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import model.Appointment;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author tarina
 */
public class AppointmentDAO {//Data Access Object for managing Appointment table operations.
    private final DBConnection dbConnection;

    public AppointmentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        
    }
    //Creates the Appointment table if it doesn't exist and populates it with sample data.
    public void createAppointment() {
        if (dbConnection.tableExists("Appointment")) {
            System.out.println("Appointment table already exists");
            try {
                String query = "SELECT * FROM Appointment FETCH FIRST 5 ROWS ONLY";
                ResultSet result = dbConnection.getConnection().createStatement().executeQuery(query);
                System.out.println("First 5 Appointments:");
                boolean hasData = false;
                while (result.next()) {
                    hasData = true;
                    int id = result.getInt("appointmentID");
                    String studentNumber = result.getString("studentNumber");
                    int counselorID = result.getInt("counselorID");
                    Date date = result.getDate("appointmentDate");
                    Time time = result.getTime("appointmentTime");
                    String status = result.getString("status");
                    System.out.println("ID: " + id + ", Student: " + studentNumber + ", Counselor: " + counselorID +
                            ", Date: " + date + ", Time: " + time + ", Status: " + status);
                }
                if (!hasData) {
                    System.out.println("No appointments found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            createAppointmentTable();
            populateAppointmentTable();
        }
    }

    private void createAppointmentTable() {
        try {
            String query = "CREATE TABLE Appointment (" +
                    "appointmentID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "studentNumber VARCHAR(50) NOT NULL, " +
                    "counselorID INT NOT NULL, " +
                    "appointmentDate DATE NOT NULL, " +
                    "appointmentTime TIME NOT NULL, " +
                    "status VARCHAR(20) NOT NULL, " +
                    "FOREIGN KEY (counselorID) REFERENCES Counselor(counselorID)" +
                    ")";
            dbConnection.getConnection().createStatement().execute(query);
            System.out.println("Appointment table created.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateAppointmentTable() {
        try {
            String insert = "INSERT INTO Appointment (studentNumber, counselorID, appointmentDate, appointmentTime, status) VALUES " +
                    "('1001', 1, '2025-07-01', '09:00:00', 'Scheduled')," +
                    "('1002', 2, '2025-07-02', '10:00:00', 'Completed')," +
                    "('1003', 3, '2025-07-03', '11:00:00', 'Cancelled')," +
                    "('1004', 4, '2025-07-04', '12:00:00', 'Scheduled')," +
                    "('1005', 5, '2025-07-05', '13:00:00', 'Completed')," +
                    "('1006', 6, '2025-07-06', '14:00:00', 'Cancelled')," +
                    "('1007', 7, '2025-07-07', '15:00:00', 'Scheduled')," +
                    "('1008', 8, '2025-07-08', '16:00:00', 'Completed')," +
                    "('1009', 9, '2025-07-09', '08:00:00', 'Cancelled')," +
                    "('1010', 10, '2025-07-10', '09:30:00', 'Scheduled')," +
                    "('1011', 11, '2025-07-11', '10:30:00', 'Completed')," +
                    "('1012', 12, '2025-07-12', '11:30:00', 'Cancelled')," +
                    "('1013', 13, '2025-07-13', '12:30:00', 'Scheduled')," +
                    "('1014', 14, '2025-07-14', '13:30:00', 'Completed')," +
                    "('1015', 15, '2025-07-15', '14:30:00', 'Cancelled')";
            dbConnection.getConnection().createStatement().execute(insert);
            System.out.println("Appointment table populated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Retrieves all scheduled appointments and returns ArrayList of Appointment objects with status 'Scheduled'
    public ArrayList<Appointment> getScheduledAppointments() {
        ArrayList<Appointment> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM Appointment WHERE status = 'Scheduled'";
            ResultSet result = dbConnection.getConnection().createStatement().executeQuery(query);
            while (result.next()) {
                int id = result.getInt("appointmentID");
                String studentNumber = result.getString("studentNumber");
                int counselorID = result.getInt("counselorID");
                Date date = result.getDate("appointmentDate");
                Time time = result.getTime("appointmentTime");
                String status = result.getString("status");
                list.add(new Appointment(id, Integer.parseInt(studentNumber), counselorID, date, time, status));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //Inserts a new appointment into the Appointment table.
    public void insertAppointment(int studentNumber, int counselorID, java.sql.Date appointmentDate, java.sql.Time appointmentTime, String status) {
        String query = "INSERT INTO Appointment (studentNumber, counselorID, appointmentDate, appointmentTime, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, studentNumber);
            ps.setInt(2, counselorID);
            ps.setDate(3, appointmentDate);
            ps.setTime(4, appointmentTime);
            ps.setString(5, status);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Updates an existing appointment.
    public void updateAppointment(int appointmentID, int studentNumber, int counselorID, java.sql.Date appointmentDate, java.sql.Time appointmentTime, String status) {
        String query = "UPDATE Appointment SET studentNumber = ?, counselorID = ?, appointmentDate = ?, appointmentTime = ?, status = ? WHERE appointmentID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, studentNumber);
            ps.setInt(2, counselorID);
            ps.setDate(3, appointmentDate);
            ps.setTime(4, appointmentTime);
            ps.setString(5, status);
            ps.setInt(6, appointmentID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Deletes an appointment by ID.
    public void deleteAppointment(int id) {
        String query = "DELETE FROM Appointment WHERE appointmentID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Retrieves all appointments and returns ArrayList of all Appointment objects

    public ArrayList<Appointment> viewAppointment() {
        ArrayList<Appointment> allAppointments = new ArrayList<>();
        try {
            String query = "SELECT * FROM Appointment";
            ResultSet result = dbConnection.getConnection().createStatement().executeQuery(query);
            while (result.next()) {
                int appointmentID = result.getInt("appointmentID");
                String studentNumber = result.getString("studentNumber");
                int counselorID = result.getInt("counselorID");
                Date appointmentDate = result.getDate("appointmentDate");
                Time appointmentTime = result.getTime("appointmentTime");
                String status = result.getString("status");
                Appointment row = new Appointment(appointmentID, Integer.parseInt(studentNumber), counselorID, appointmentDate, appointmentTime, status);
                allAppointments.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allAppointments;
    }
}