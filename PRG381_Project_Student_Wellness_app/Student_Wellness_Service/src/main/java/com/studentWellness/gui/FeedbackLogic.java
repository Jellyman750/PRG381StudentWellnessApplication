package com.studentWellness.gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackLogic {
    private static final String FEEDBACK_FILE = "feedbacks.csv";

    public static boolean submitFeedback(String counselor, String studentNumber, String date, String feedbackText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            writer.write(counselor + "," + studentNumber + "," + date + "," + feedbackText.replace(",", " ") + "\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving feedback: " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> getAllFeedbacks() {
        List<String[]> feedbacks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",", 4);
                feedbacks.add(details);
            }
        } catch (IOException e) {
            System.out.println("Error loading feedbacks: " + e.getMessage());
        }
        return feedbacks;
    }

    public static boolean editFeedback(int index, String newText) {
        List<String[]> feedbacks = getAllFeedbacks();
        if (index >= 0 && index < feedbacks.size()) {
            feedbacks.get(index)[3] = newText.replace(",", " ");
            return overwriteFeedbackFile(feedbacks);
        }
        return false;
    }

    public static boolean deleteFeedback(int index) {
        List<String[]> feedbacks = getAllFeedbacks();
        if (index >= 0 && index < feedbacks.size()) {
            feedbacks.remove(index);
            return overwriteFeedbackFile(feedbacks);
        }
        return false;
    }

    private static boolean overwriteFeedbackFile(List<String[]> feedbacks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE))) {
            for (String[] fb : feedbacks) {
                writer.write(String.join(",", fb) + "\n");
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error updating feedback file: " + e.getMessage());
            return false;
        }
    }
}