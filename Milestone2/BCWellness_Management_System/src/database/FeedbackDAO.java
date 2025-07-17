/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import model.Feedback;
import java.sql.*;
import java.util.ArrayList; 
/**
 *
 * @author tarina
 */
public class FeedbackDAO {
    private final DBConnection dbConnection;

    //Constructor 
    public FeedbackDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    //Creates the Feedback table if it doesn't exist and populates it with sample data
    public void createFeedback() {

        if (dbConnection.tableExists("Feedback")) {
            System.out.println("Feedback table already exists");
            try {
                String query = "SELECT * FROM Feedback FETCH FIRST 5 ROWS ONLY"; // Derby syntax
                ResultSet result = dbConnection.getConnection().createStatement().executeQuery(query);
                System.out.println("First 5 Feedback entries:");
                boolean hasData = false;
                while (result.next()) {
                    hasData = true;
                    int id = result.getInt("feedbackID");
                    int appointmentID = result.getInt("appointmentID");
                    String studentNumber = result.getString("studentNumber");
                    int rating = result.getInt("rating");
                    String comments = result.getString("comments");
                    Date submissionDate = result.getDate("submission_date");

                    System.out.printf("ID: %d, AppointmentID: %d, Student: %s, Rating: %d, Comments: %s, Date: %s\n",
                            id, appointmentID, studentNumber, rating, comments, submissionDate);
                }
                if (!hasData) {
                    System.out.println("No feedback entries found.");
                }
            } catch (SQLException ex) {
                System.err.println("Error fetching feedback: " + ex.getMessage());
            }
        } else {
            createFeedbackTable();
            populateFeedbackTable();
        }
    }

    private void createFeedbackTable() {
        try {
            String query = "CREATE TABLE Feedback (" +
                    "feedbackID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "appointmentID INT NOT NULL, " +
                    "studentNumber VARCHAR(50) NOT NULL, " +
                    "rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5), " +
                    "comments VARCHAR(500), " +
                    "submission_date DATE NOT NULL, " +
                    "FOREIGN KEY (appointmentID) REFERENCES Appointment(appointmentID))";
            dbConnection.getConnection().createStatement().execute(query);
            System.out.println("Feedback table created.");
        } catch (SQLException ex) {
            System.err.println("Error creating Feedback table: " + ex.getMessage());
        }
    }

    public void populateFeedbackTable() {
        try {
            String insert = "INSERT INTO Feedback (appointmentID, studentNumber, rating, comments, SUBMISSION_DATE) VALUES" +
                    "(1, '1001', 5, 'Great session!', '2025-07-01')," +
                    "(2, '1002', 4, 'Very helpful.', '2025-07-02')," +
                    "(3, '1003', 3, 'It was okay.', '2025-07-03')," +
                    "(4, '1004', 5, 'Excellent!', '2025-07-04')," +
                    "(5, '1005', 2, 'Could be better.', '2025-07-05')," +
                    "(6, '1006', 4, 'Pretty good.', '2025-07-06')," +
                    "(7, '1007', 3, 'Not bad.', '2025-07-07')," +
                    "(8, '1008', 5, 'Loved it!', '2025-07-08')," +
                    "(9, '1009', 1, 'Disappointed.', '2025-07-09')," +
                    "(10, '1010', 2, 'Meh.', '2025-07-10')," +
                    "(11, '1011', 5, 'Amazing experience.', '2025-07-11')," +
                    "(12, '1012', 3, 'Fine.', '2025-07-12')," +
                    "(13, '1013', 4, 'Quite useful.', '2025-07-13')," +
                    "(14, '1014', 4, 'Would recommend.', '2025-07-14')," +
                    "(15, '1015', 5, 'Perfect!', '2025-07-15')";
            dbConnection.getConnection().createStatement().execute(insert);
            System.out.println("Feedback table populated.");
        } catch (SQLException ex) {
            System.err.println("Error populating Feedback table: " + ex.getMessage());
        }
    }


    //inserts a new feedback entry into the feedback table
    public boolean insertFeedback(int appointmentID, String studentNumber, int rating, String comments, java.sql.Date submissionDate) {
        if (studentNumber == null || rating < 1 || rating > 5 || submissionDate == null) {
            System.out.println("Invalid input: studentNumber and submissionDate must be non-null, rating must be between 1 and 5.");
            return false;
        }
        String query = "INSERT INTO Feedback (appointmentID, studentNumber, rating, comments, submission_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, appointmentID);
            ps.setString(2, studentNumber);
            ps.setInt(3, rating);
            ps.setString(4, comments);
            ps.setDate(5, submissionDate);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Updates an existing feedback entry in the Feedback table
    public boolean updateFeedback(int feedbackID, int appointmentID, String studentNumber, int rating, String comments, java.sql.Date submissionDate) {
        if (studentNumber == null || rating < 1 || rating > 5 || submissionDate == null) {
            System.out.println("Invalid input: studentNumber and submissionDate must be non-null, rating must be between 1 and 5.");
            return false;
        }
        String query = "UPDATE Feedback SET appointmentID = ?, studentNumber = ?, rating = ?, comments = ?, submission_date = ? WHERE feedbackID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, appointmentID);
            ps.setString(2, studentNumber);
            ps.setInt(3, rating);
            ps.setString(4, comments);
            ps.setDate(5, submissionDate);
            ps.setInt(6, feedbackID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Delete a feedback entry  by ID
    public boolean deleteFeedback(int id) {
        String query = "DELETE FROM Feedback WHERE feedbackID = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Retrieves all feedback entries from the Feedback table and returns  ArrayList of all Feedback objects
    public ArrayList<Feedback> viewFeedback() {
        ArrayList<Feedback> allFeedback = new ArrayList<>();
        String query = "SELECT * FROM Feedback";
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                int feedbackID = result.getInt("feedbackID");
                int appointmentID = result.getInt("appointmentID");
                String studentNumber = result.getString("studentNumber");
                int rating = result.getInt("rating");
                String comments = result.getString("comments");
                Date submissionDate = result.getDate("submission_date");
                allFeedback.add(new Feedback(feedbackID, appointmentID, Integer.parseInt(studentNumber), rating, comments, submissionDate));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allFeedback;
    }
}
