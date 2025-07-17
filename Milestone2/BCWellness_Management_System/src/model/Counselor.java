package model;

public class Counselor extends Person {
    private int counselorID;
    private String specialization;
    private boolean availability;

    public Counselor(int counselorID, String name, String surname, String email, String specialization, boolean availability) {
        super(name, surname, email);
        this.counselorID = counselorID;
        this.specialization = specialization;
        this.availability = availability;
    }

    public int getCounselorID() {
        return counselorID;
    }

    public void setCounselorID(int counselorID) {
        this.counselorID = counselorID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Counselor{" +
                "counselorID=" + counselorID +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", availability=" + availability +
                '}';
    }
}
