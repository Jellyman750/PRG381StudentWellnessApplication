/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import model.Counselor;
import java.sql.*;
import java.util.ArrayList;
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
            return;
        }
        try {
            String query = "CREATE TABLE Counselor (" +
                    "counselorID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "counselorName VARCHAR(100) NOT NULL, " +
                    "counselorSurname VARCHAR(100) NOT NULL, " +
                    "counselorEmail VARCHAR(100) NOT NULL, " +
                    "specialisation VARCHAR(100) NOT NULL, " +
                    "availability BOOLEAN NOT NULL)";
            try (Statement stmt = dbConnection.getConnection().createStatement()) {
                stmt.execute(query);
            }
            populateCounselorTable();
            System.out.println("Counselor table created and populated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Populates the Counselor table with sample data.
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
            try (Statement stmt = dbConnection.getConnection().createStatement()) {
                stmt.execute(insert);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
}

