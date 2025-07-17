/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import model.Counselor;
import java.sql.*;
import java.util.ArrayList;

import model.Appointment;
/**
 *
 * @author tarina
 */
    //Data Access Object for managing Counselor table operations
public class CounselorDAO {
    private final DBConnection dbConnection;
     //Constructor to initialize the DAO with a database connection.
    public CounselorDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Creates the Counselor table if it doesn't exist and populates it with sample data.
    public void createCounselor() {
        if (dbConnection.tableExists("Counselor")) {
            System.out.println("Counselor table already exists");
            try {
                String query = "SELECT * FROM Counselor FETCH FIRST 5 ROWS ONLY"; // Derby syntax
                ResultSet result = dbConnection.getConnection().createStatement().executeQuery(query);
                System.out.println("First 5 Counselors:");
                boolean hasData = false;
                while (result.next()) {
                    hasData = true;
                    int id = result.getInt("counselorID");
                    String name = result.getString("counselorName");
                    String surname = result.getString("counselorSurname");
                    String email = result.getString("counselorEmail");
                    String spec = result.getString("specialisation");
                    boolean available = result.getBoolean("availability");

                    System.out.printf("ID: %d, Name: %s %s, Email: %s, Specialisation: %s, Available: %b\n",
                            id, name, surname, email, spec, available);
                }
                if (!hasData) {
                    System.out.println("No counselors found.");
                }
            } catch (SQLException ex) {
                System.err.println("Error fetching counselors: " + ex.getMessage());
            }
        } else {
            createCounselorTable();
            populateCounselorTable();
        }
    }


    private void createCounselorTable() {
        try {
            String query = "CREATE TABLE Counselor (" +
                    "counselorID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "counselorName VARCHAR(100) NOT NULL, " +
                    "counselorSurname VARCHAR(100) NOT NULL, " +
                    "counselorEmail VARCHAR(100) NOT NULL, " +
                    "specialisation VARCHAR(100) NOT NULL, " +
                    "availability BOOLEAN NOT NULL)";
            dbConnection.getConnection().createStatement().execute(query);
            System.out.println("Counselor table created.");
        } catch (SQLException ex) {
            System.err.println("Error creating Counselor table: " + ex.getMessage());
        }
    }

    private void populateCounselorTable() {
        try {
            String insert = "INSERT INTO Counselor (counselorName, counselorSurname, counselorEmail, specialisation, availability) VALUES " +
                    "('John', 'Smith', 'john1@wellness.org', 'Therapy', TRUE)," +
                    "('Jane', 'Doe', 'jane2@wellness.org', 'Academic', FALSE)," +
                    "('Mike', 'Brown', 'mike3@wellness.org', 'Therapy', TRUE)," +
                    "('Anna', 'White', 'anna4@wellness.org', 'Academic', TRUE)," +
                    "('Paul', 'Black', 'paul5@wellness.org', 'Therapy', FALSE)," +
                    "('Laura', 'Green', 'laura6@wellness.org', 'Academic', TRUE)," +
                    "('Tom', 'Lee', 'tom7@wellness.org', 'Therapy', TRUE)," +
                    "('Emily', 'Taylor', 'emily8@wellness.org', 'Academic', FALSE)," +
                    "('Daniel', 'Hall', 'daniel9@wellness.org', 'Therapy', TRUE)," +
                    "('Grace', 'Adams', 'grace10@wellness.org', 'Academic', TRUE)," +
                    "('Sam', 'King', 'sam11@wellness.org', 'Therapy', TRUE)," +
                    "('Lucy', 'Scott', 'lucy12@wellness.org', 'Academic', FALSE)," +
                    "('Jack', 'Evans', 'jack13@wellness.org', 'Therapy', TRUE)," +
                    "('Olivia', 'Parker', 'olivia14@wellness.org', 'Academic', TRUE)," +
                    "('Ben', 'Turner', 'ben15@wellness.org', 'Therapy', FALSE)";
            dbConnection.getConnection().createStatement().execute(insert);
            System.out.println("Counselor table populated.");
        } catch (SQLException ex) {
            System.err.println("Error populating Counselor table: " + ex.getMessage());
        }
    }


    //inserts a new counselor into the counselor table
    public boolean insertCounselor(String name, String surname, String email, String specialisation, boolean availability) {
        if (name == null || surname == null || email == null || specialisation == null) {
            System.out.println("Invalid input: All fields must be non-null.");
            return false;
        }
        String query = "INSERT INTO Counselor (counselorName, counselorSurname, counselorEmail, specialisation, availability) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, specialisation);
            ps.setBoolean(5, availability);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //updates an existing counselor in the Counselor table
    public boolean updateCounselor(int id, String name, String surname, String email, String specialisation, boolean availability) {
        if (name == null || surname == null || email == null || specialisation == null) {
            System.out.println("Invalid input: All fields must be non-null.");
            return false;
        }
        String query = "UPDATE Counselor SET counselorName = ?, counselorSurname = ?, counselorEmail = ?, specialisation = ?, availability = ? WHERE counselorID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, specialisation);
            ps.setBoolean(5, availability);
            ps.setInt(6, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //deletes a counselor from the counselor table by ID
    public boolean deleteCounselor(int id) {
        String query = "DELETE FROM Counselor WHERE counselorID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean counselorExists(String name, String surname, String email) {
        String query = "SELECT COUNT(*) FROM Counselor WHERE counselorName = ? AND counselorSurname = ? OR counselorEmail = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if any match found
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    //Retrieves all counselors from the counselor table and return ArrayList of all Counselor objects
    public ArrayList<Counselor> viewCounselor() {
        ArrayList<Counselor> allCounselors = new ArrayList<>();
        String query = "SELECT * FROM Counselor";
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                int id = result.getInt("counselorID");
                String name = result.getString("counselorName");
                String surname = result.getString("counselorSurname");
                String email = result.getString("counselorEmail");
                String specialisation = result.getString("specialisation");
                boolean availability = result.getBoolean("availability");
                allCounselors.add(new Counselor(id, name, surname, email, specialisation, availability));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCounselors;
    }
    public ArrayList<Appointment> getAppointmentsByCounselor(int counselorID) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Appointment WHERE CounselorID = ?";
            PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, counselorID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("AppointmentID"),
                        rs.getInt("StudentNumber"),
                        rs.getInt("CounselorID"),
                        rs.getDate("AppointmentDate"),
                        rs.getTime("AppointmentTime"),
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public void deleteAppointmentsByCounselor(int counselorID) {
        try {
            String sql = "DELETE FROM Appointment WHERE CounselorID = ?";
            PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, counselorID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//method that gets the counselors name and surname from the ID
    public Counselor getCounselorByID(int id) {
        String query = "SELECT * FROM Counselor WHERE CounselorID = ?";
    try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Counselor(
                rs.getInt("CounselorID"),
                rs.getString("counselorName"),
                rs.getString("counselorSurname"),
                rs.getString("counselorEmail"),
                rs.getString("specialisation"),
                rs.getBoolean("availability")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
    }

}

