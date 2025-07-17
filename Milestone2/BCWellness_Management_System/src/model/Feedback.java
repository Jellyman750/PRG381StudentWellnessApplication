package model;

import java.sql.Date;

public class Feedback {
    private int feedbackID;
    private int appointmentID;
    private String studentNumber;
    private int rating;
    private String comments;
    private Date submissionDate;

    // Constructor
    public Feedback(int feedbackID, int appointmentID, String studentNumber, int rating, String comments, Date submissionDate) {
        this.feedbackID = feedbackID;
        this.appointmentID = appointmentID;
        this.studentNumber = studentNumber;
        this.rating = rating;
        this.comments = comments;
        this.submissionDate = submissionDate;
    }

    // Getters
    public int getFeedbackID() { return feedbackID; }
    public int getAppointmentID() { return appointmentID; }
    public String getStudentNumber() { return studentNumber; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public Date getSubmissionDate() { return submissionDate; }

    // Setters
    public void setFeedbackID(int feedbackID) { this.feedbackID = feedbackID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComments(String comments) { this.comments = comments; }
    public void setSubmissionDate(Date submissionDate) { this.submissionDate = submissionDate; }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackID=" + feedbackID +
                ", appointmentID=" + appointmentID +
                ", studentNumber=" + studentNumber +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", submissionDate=" + submissionDate +
                '}';
    }
}
