
package gui;

import java.io.*;
import java.util.*;

public class CounselorLogic {
    private static final String COUNSELOR_FILE = "data/counselors.txt";

    public static boolean addCounselor(String name, String specialization, String availability) {
        List<String[]> counselors = readCounselors();

        for (String[] c : counselors) {
            if (c[0].equalsIgnoreCase(name)) {
                return false; // Duplicate name
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNSELOR_FILE, true))) {
            writer.write(name + "," + specialization + "," + availability);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCounselor(String name, String newSpecialization, String newAvailability) {
        List<String[]> counselors = readCounselors();
        boolean updated = false;

        for (String[] c : counselors) {
            if (c[0].equalsIgnoreCase(name)) {
                c[1] = newSpecialization;
                c[2] = newAvailability;
                updated = true;
            }
        }

        if (updated) {
            return writeCounselors(counselors);
        }

        return false;
    }

    public static boolean removeCounselor(String name) {
        List<String[]> counselors = readCounselors();
        boolean removed = counselors.removeIf(c -> c[0].equalsIgnoreCase(name));

        if (removed) {
            return writeCounselors(counselors);
        }

        return false;
    }

    public static List<String[]> viewAllCounselors() {
        return readCounselors();
    }

    private static List<String[]> readCounselors() {
        List<String[]> list = new ArrayList<>();
        File file = new File(COUNSELOR_FILE);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(COUNSELOR_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static boolean writeCounselors(List<String[]> counselors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNSELOR_FILE))) {
            for (String[] c : counselors) {
                writer.write(String.join(",", c));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}