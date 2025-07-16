/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Appointment;
import model.Counselor;
import model.Feedback;


/**
 *
 * @author tarina
 */
public class DBConnection {
    
    //driver
    private static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    //url
    private static final String URL="jdbc:derby:BC_Wellness;create=true";
    //connection object
    Connection conn;
    
    //constructor
    public DBConnection(){}
    
    
    //connect metod
    public void connect() throws ClassNotFoundException{
        try{
            Class.forName(DRIVER);
            this.conn=DriverManager.getConnection(URL);
            if(this.conn !=null){
                System.out.println("Connected to DB");//debugging
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //test if tables already exist
    public boolean tableExists(String tableName){
        try {
            DatabaseMetaData meta = this.conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"})) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 //CRUD operations
    //create tables
    //Appointment
    public void createAppointment(){
        if (tableExists("Appointment")){
            System.out.println("Appointment table already exists");
        }
        else{
            try{
            String query="CREATE TABLE Appointment (" +
                "appointmentID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                "studentNumber VARCHAR(50) NOT NULL," +
                "counselorID INT NOT NULL, " +
                "appointmentDate DATE NOT NULL," +
                "appointmentTime TIME NOT NULL," +
                "status VARCHAR(20) NOT NULL," + // eg Scheduled, Completed, Cancelled
                "FOREIGN KEY (counselorID) REFERENCES Counselors(counselorID) )";
            this.conn.createStatement().execute(query);
            //populate table
            String insert = "INSERT INTO Appointment (studentNumber, counselorID, appointmentDate, appointmentTime, status) VALUES " +
                    "(1001, 1, '2025-07-01', '09:00:00', 'Scheduled')," +
                    "(1002, 2, '2025-07-02', '10:00:00', 'Completed')," +
                    "(1003, 3, '2025-07-03', '11:00:00', 'Cancelled')," +
                    "(1004, 4, '2025-07-04', '12:00:00', 'Scheduled')," +
                    "(1005, 5, '2025-07-05', '13:00:00', 'Completed')," +
                    "(1006, 6, '2025-07-06', '14:00:00', 'Cancelled')," +
                    "(1007, 7, '2025-07-07', '15:00:00', 'Scheduled')," +
                    "(1008, 8, '2025-07-08', '16:00:00', 'Completed')," +
                    "(1009, 9, '2025-07-09', '08:00:00', 'Cancelled')," +
                    "(1010, 10, '2025-07-10', '09:30:00', 'Scheduled')," +
                    "(1011, 11, '2025-07-11', '10:30:00', 'Completed')," +
                    "(1012, 12, '2025-07-12', '11:30:00', 'Cancelled')," +
                    "(1013, 13, '2025-07-13', '12:30:00', 'Scheduled')," +
                    "(1014, 14, '2025-07-14', '13:30:00', 'Completed')," +
                    "(1015, 15, '2025-07-15', '14:30:00', 'Cancelled')";
            conn.createStatement().execute(insert);
            System.out.println("Appointment table created and populated.");
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    //Counselor
    public void createCounselor(){
        if (tableExists("Counselor")){
            System.out.println("Counselor table already exists");
        }
        else{
            try{
                String query="CREATE TABLE Counselor (" +
                    "counselorID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "counselorName VARCHAR(100) NOT NULL, " +
                    "counselorSurname VARCHAR(100) NOT NULL, " +
                    "counselorEmail VARCHAR(100) NOT NULL, " +
                    "specialisation VARCHAR(100) NOT NULL," +
                    "availability BOOLEAN NOT NULL )"; 
                this.conn.createStatement().execute(query);
                //populate tbl
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
                conn.createStatement().execute(insert); 
                System.out.println("Appointment table created and populated.");//debugging
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            
        }
    }
    //Feedback
    public void createFeedback(){
        if (tableExists("Feedback")){
            System.out.println("Feedback table already exists");
        }
        else{
            try{
            String query=" CREATE TABLE Feedback (" +
                "feedbackID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                "appointmentID INT NOT NULL," +
                "studentNumber VARCHAR(50) NOT NULL, " +
                "rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5), " +
                "comments VARCHAR(500), " +
                "submissionDate DATE NOT NULL ," +
                "FOREIGN KEY (appointment_id) REFERENCES Appointments(id))";
            this.conn.createStatement().execute(query);
            //populate
            String insert = "INSERT INTO Feedback (appointmentID, studentNumber, rating, comments, submissionDate) VALUES " +
                    "(1, 1001, 5, 'Great session!', '2025-07-01')," +
                    "(2, 1002, 4, 'Very helpful.', '2025-07-02')," +
                    "(3, 1003, 3, 'It was okay.', '2025-07-03')," +
                    "(4, 1004, 5, 'Excellent!', '2025-07-04')," +
                    "(5, 1005, 2, 'Could be better.', '2025-07-05')," +
                    "(6, 1006, 4, 'Pretty good.', '2025-07-06')," +
                    "(7, 1007, 3, 'Not bad.', '2025-07-07')," +
                    "(8, 1008, 5, 'Loved it!', '2025-07-08')," +
                    "(9, 1009, 1, 'Disappointed.', '2025-07-09')," +
                    "(10, 1010, 2, 'Meh.', '2025-07-10')," +
                    "(11, 1011, 5, 'Amazing experience.', '2025-07-11')," +
                    "(12, 1012, 3, 'Fine.', '2025-07-12')," +
                    "(13, 1013, 4, 'Quite useful.', '2025-07-13')," +
                    "(14, 1014, 4, 'Would recommend.', '2025-07-14')," +
                    "(15, 1015, 5, 'Perfect!', '2025-07-15')";
            conn.createStatement().execute(insert);
            System.out.println("Feedback table created and populated.");
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    //insert method
    //Appointment
    public void insertAppointment(int studentNumber, int counselorID, java.sql.Date appointmentDate, java.sql.Time appointmentTime, String status) {
        String query = "INSERT INTO Appointment (studentNumber, counselorID, appointmentDate, appointmentTime, status)VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, studentNumber);
            ps.setInt(2, counselorID);
            ps.setDate(3, appointmentDate);
            ps.setTime(4, appointmentTime);
            ps.setString(5, status);
            ps.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
}
    //Counselor
    public void insertCounselor(String name, String surname,String email, String specialisation,boolean availability){
        String query="INSERT INTO Counselor (counselorName, counselorSurname, counselorEmail, specialisation, availability)VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, specialisation);
            ps.setBoolean(5, availability);
            ps.executeUpdate();     
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    //Feedback
    public void insertFeedback(int appointmentID, int studentNumber, int rating, String comments, java.sql.Date submissionDate) {
        String query = "INSERT INTO Feedback (appointmentID, studentNumber, rating, comments, submission_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentID);
            ps.setInt(2, studentNumber);
            ps.setInt(3, rating);
            ps.setString(4, comments);
            ps.setDate(5, submissionDate);
            ps.executeUpdate();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
   //Update
    //Appointment
    public void updateAppointment(int appointmentID, int studentNumber, int counselorID, java.sql.Date appointmentDate, java.sql.Time appointmentTime, String status) {
        String query = "UPDATE Appointment SET studentNumber = ?, counselorID = ?, appointmentDate = ?, appointmentTime = ?, status = ? WHERE appointmentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
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
    //Counselor
    public void updateCounselor(int id,String name, String surname, String email,String specialisation, boolean availability){
        String query= "UPDATE Counselor SET counselorName = ?, counselorSurname = ?, counselorEmail = ?, specialisation = ?, availability = ? WHERE counselorID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, specialisation);
            ps.setBoolean(5, availability);
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //Feedback 
    public void updateFeedback(int feedbackID, int appointmentID, int studentNumber, int rating, String comments, java.sql.Date submissionDate) {
        String query = "UPDATE Feedback SET appointmentID = ?, studentNumber = ?, rating = ?, comments = ?, submissionDate = ? WHERE feedbackID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentID);
            ps.setInt(2, studentNumber);
            ps.setInt(3, rating);
            ps.setString(4, comments);
            ps.setDate(5, submissionDate);
            ps.setInt(6, feedbackID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //DELETE
    //Appointment
    public void deleteAppointment(int id){
        String query= "DELETE FROM Appointment WHERE appointmentID=?";
        try(PreparedStatement ps=conn.prepareStatement(query)){
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //Counselor
    public void deleteCounselor(int id){
        String query= "DELETE FROM Counselor WHERE counselorID=?";
        try(PreparedStatement ps=conn.prepareStatement(query)){
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //Feedback
    public void deleteFeedback(int id){
        String query= "DELETE FROM Feedback WHERE feedbackID=?";
        try(PreparedStatement ps=conn.prepareStatement(query)){
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    //VIEW-- returns data from the database in an ArrayList of an object
    //Appointment--uses appointment object
    public ArrayList<Appointment> viewAppointment(){
        ArrayList<Appointment> allAppointments=new ArrayList<>();
        try {
            String query="SELECT * FROM Appointment";
            ResultSet result=this.conn.createStatement().executeQuery(query);
            while(result.next()){
                int appointmentID=result.getInt("appointmentID");
                int studentNumber=result.getInt("studentNumber");
                int counselorID=result.getInt("counselorID");
                Date appointmentDate=result.getDate("appointmentDate");
                Time appointmentTime=result.getTime("appointmentTime");
                String status=result.getString("status");
                
                Appointment row= new Appointment(appointmentID,studentNumber,counselorID,appointmentDate,appointmentTime,status);
                allAppointments.add(row);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return allAppointments;
    }
    
    //Counselor-uses counselor object
    public ArrayList<Counselor> viewCounselor() {
        ArrayList<Counselor> allCounselors = new ArrayList<>();
        try {
            String query = "SELECT * FROM Counselor";
            ResultSet result = conn.createStatement().executeQuery(query);
            while (result.next()) {
                int id = result.getInt("counselorID");
                String name = result.getString("counselorName");
                String surname = result.getString("counselorSurname");
                String email = result.getString("counselorEmail");
                String specialisation = result.getString("specialisation");
                boolean availability = result.getBoolean("availability");

                Counselor row = new Counselor(id, name, surname, email, specialisation, availability);
                allCounselors.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCounselors;
    }
    //Feedback--uses Feedback object
    public ArrayList<Feedback> viewFeedback() {
        ArrayList<Feedback> allFeedback = new ArrayList<>();
        try {
            String query = "SELECT * FROM Feedback";
            ResultSet result = conn.createStatement().executeQuery(query);
            while (result.next()) {
                int feedbackID = result.getInt("feedbackID");
                int appointmentID = result.getInt("appointment_id");
                int studentNumber = result.getInt("studentNumber");
                int rating = result.getInt("rating");
                String comments = result.getString("comments");
                Date submissionDate = result.getDate("submission_date");

                Feedback row = new Feedback(feedbackID, appointmentID, studentNumber, rating, comments, submissionDate);
                allFeedback.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allFeedback;
    }
    
//Disconnect method-cll when the application is closed-dont know if needed
  public void disconnect() {
    try {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Disconnected from DB");
        }
    } 
    catch (SQLException ex) {
        ex.printStackTrace();
    }}     
}

