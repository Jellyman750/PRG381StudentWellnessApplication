package model;

public class Counselor {
    private int counselorID;
    private String name;
    private String surname;
    private String email;
    private String specialization;
    private boolean availability;

    // Constructor
    public Counselor(int counselorID, String name, String surname, String email, String specialization, boolean availability) {
        this.counselorID = counselorID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.specialization = specialization;
        this.availability = availability;
    }

    // Getters
    public int getCounselorID() { return counselorID; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return availability; }

    // Setters
    public void setCounselorID(int counselorID) { this.counselorID = counselorID; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setEmail(String email) { this.email = email; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    @Override
    public String toString() {
        return "Counselor{" +
                "counselorID=" + counselorID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", availability=" + availability +
                '}';
    }
}
