package database;

import model.Appointment;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author tarina
 */
public class AppointmentDAO { // Data Access Object for managing Appointment table operations.
    private final DBConnection dbConnection;

    public AppointmentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Creates the Appointment table if it doesn't exist and populates it with sample data.
    public void createAppointment() {
        try {
            if (dbConnection.tableExists("Appointment")) {
                System.out.println("Appointment table already exists");
                String query = "SELECT * FROM Appointment FETCH FIRST 5 ROWS ONLY";
                try (ResultSet result = dbConnection.getConnection().createStatement().executeQuery(query)) {
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
                }
            } else {
                createAppointmentTable();
                populateAppointmentTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createAppointmentTable() throws SQLException {
        String query = "CREATE TABLE Appointment (" +
                "appointmentID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "studentNumber VARCHAR(50) NOT NULL, " +
                "counselorID INT NOT NULL, " +
                "appointmentDate DATE NOT NULL, " +
                "appointmentTime TIME NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "FOREIGN KEY (counselorID) REFERENCES Counselor(counselorID)" +
                ")";
        try (Statement stmt = dbConnection.getConnection().createStatement()) {
            stmt.execute(query);
            System.out.println("Appointment table created.");
        }
    }

    private void populateAppointmentTable() throws SQLException {
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
        try (Statement stmt = dbConnection.getConnection().createStatement()) {
            stmt.execute(insert);
            System.out.println("Appointment table populated.");
        }
    }

    // Retrieves all scheduled appointments and returns ArrayList of Appointment objects with status 'Scheduled'
    public ArrayList<Appointment> getScheduledAppointments() {
        ArrayList<Appointment> list = new ArrayList<>();
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM Appointment WHERE status = 'Scheduled'")) {
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

    // Inserts a new appointment into the Appointment table and returns success status
    public boolean insertAppointment(int studentNumber, int counselorID, java.sql.Date appointmentDate, java.sql.Time appointmentTime) {
        String query = "INSERT INTO Appointment (studentNumber, counselorID, appointmentDate, appointmentTime, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, studentNumber);
            ps.setInt(2, counselorID);
            ps.setDate(3, appointmentDate);
            ps.setTime(4, appointmentTime);
            ps.setString(5, "Scheduled");
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Updates an existing appointment and returns success status
    public boolean updateAppointment(int appointmentID, int studentNumber, int counselorID, java.sql.Date appointmentDate, java.sql.Time appointmentTime) {
        String query = "UPDATE Appointment SET studentNumber = ?, counselorID = ?, appointmentDate = ?, appointmentTime = ? WHERE appointmentID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, studentNumber);
            ps.setInt(2, counselorID);
            ps.setDate(3, appointmentDate);
            ps.setTime(4, appointmentTime);
            ps.setInt(5, appointmentID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Changes the status of the appointment to cancelled
    public boolean cancelAppointment(int appointmentID) {
        String query = "UPDATE Appointment SET status=? WHERE appointmentID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, "Cancelled");
            ps.setInt(2,appointmentID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Retrieves all appointments and returns ArrayList of all Appointment objects
    public ArrayList<Appointment> viewAppointment() {
        ArrayList<Appointment> allAppointments = new ArrayList<>();
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet result = stmt.executeQuery("SELECT * FROM Appointment")) {
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

    // Retrieves appointments by counselor ID
    public ArrayList<Appointment> getAppointmentsByCounselor(int counselorID) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(
                "SELECT * FROM Appointment WHERE counselorID = ?")) {
            ps.setInt(1, counselorID);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    int appointmentID = result.getInt("appointmentID");
                    String studentNumber = result.getString("studentNumber");
                    int counselorIDResult = result.getInt("counselorID");
                    Date appointmentDate = result.getDate("appointmentDate");
                    Time appointmentTime = result.getTime("appointmentTime");
                    String status = result.getString("status");
                    appointments.add(new Appointment(appointmentID, Integer.parseInt(studentNumber), counselorIDResult, appointmentDate, appointmentTime, status));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return appointments;
    }
    // updates appointment status to completed
    public void updateAppointmentStatus(int appointmentID) {
    try {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement(
            "UPDATE Appointment SET status = ? WHERE appointmentID = ?"
        );
        ps.setString(1, "Completed");
        ps.setInt(2, appointmentID);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}