
package controller;

import database.CounselorDAO;
import database.FeedbackDAO;
import model.Appointment;
import model.Feedback;
import java.util.ArrayList;

public class CounselorLogic {

    private final CounselorDAO counselorDAO;
    private final FeedbackDAO feedbackDAO;

    public CounselorLogic(CounselorDAO counselorDAO, FeedbackDAO feedbackDAO) {
        this.counselorDAO = counselorDAO;
        this.feedbackDAO = feedbackDAO;
    }


    public String addCounselor(String nameInput, String email, String specialization, String availability) {
        if (nameInput.trim().isEmpty() || email.trim().isEmpty() || specialization.trim().isEmpty()) {
            return "Please fill in all counselor details.";
        }

        if (!isValidEmail(email)) {
            return "Please enter a valid email address.";
        }

        String[] parts = nameInput.trim().split(" ", 2);
        String firstName = parts[0];
        String surname = (parts.length > 1) ? parts[1] : "";

        boolean isAvailable = availability.equalsIgnoreCase("Available");

        if (counselorDAO.counselorExists(firstName, surname, email)) {
            return "A counselor with the same name or email already exists.";
        }

        boolean success = counselorDAO.insertCounselor(firstName, surname, email, specialization, isAvailable);

        if (success) {
            return "SUCCESS";
        } else {
            return "Error adding counselor.";
        }
    }
    public String updateCounselor(int counselorID, String nameInput, String email, String specialization, String availability) {
        if (nameInput.trim().isEmpty() || email.trim().isEmpty() || specialization.trim().isEmpty()) {
            return "Please fill in all counselor details.";
        }

        if (!isValidEmail(email)) {
            return "Please enter a valid email address.";
        }

        String[] parts = nameInput.trim().split(" ", 2);
        String firstName = parts[0];
        String surname = (parts.length > 1) ? parts[1] : "";

        boolean isAvailable = availability.equalsIgnoreCase("Available");

        boolean success = counselorDAO.updateCounselor(counselorID, firstName, surname, email, specialization, isAvailable);

        if (success) {
            return "SUCCESS";
        } else {
            return "Error updating counselor.";
        }
    }

    public String getCounselorRelatedDataMessage(int counselorID) {
        ArrayList<Appointment> appointments = counselorDAO.getAppointmentsByCounselor(counselorID);
        ArrayList<Feedback> feedbacks = feedbackDAO.getFeedbackByCounselor(counselorID);

        if (appointments.isEmpty() && feedbacks.isEmpty()) {
            return "No related data found. Do you want to delete this counselor?";
        }

        StringBuilder message = new StringBuilder("This counselor has related data:\n\n");

        if (!appointments.isEmpty()) {
            message.append("Appointments:\n");
            for (Appointment app : appointments) {
                message.append("- ")
                        .append(app.getAppointmentDate())
                        .append(" at ")
                        .append(app.getAppointmentTime())
                        .append(", Student: ")
                        .append(app.getStudentNumber())
                        .append(", Status: ")
                        .append(app.getStatus())
                        .append("\n");
            }
        }

        if (!feedbacks.isEmpty()) {
            message.append("\nFeedback:\n");
            for (Feedback fb : feedbacks) {
                message.append("- Student: ")
                        .append(fb.getStudentNumber())
                        .append(", Comment: ")
                        .append(fb.getComments())
                        .append("\n");
            }
        }

        message.append("\nDo you want to delete the counselor and all related data?");
        return message.toString();
    }

    public boolean deleteCounselorAndRelatedData(int counselorID) {
        feedbackDAO.deleteFeedbackByCounselor(counselorID);
        counselorDAO.deleteAppointmentsByCounselor(counselorID);
        return counselorDAO.deleteCounselor(counselorID);
    }



    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }



}
