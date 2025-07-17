package model;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int appointmentID;
    private int studentNumber;
    private int counselorID;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status;

    // Constructor
    public Appointment(int appointmentID, int studentNumber, int counselorID, Date appointmentDate, Time appointmentTime, String status) {
        this.appointmentID = appointmentID;
        this.studentNumber = studentNumber;
        this.counselorID = counselorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getters
    public int getAppointmentID() { return appointmentID; }
    public int getStudentNumber() { return studentNumber; }
    public int getCounselorID() { return counselorID; }
    public Date getAppointmentDate() { return appointmentDate; }
    public Time getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }

    // Setters
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
    public void setStudentNumber(int studentNumber) { this.studentNumber = studentNumber; }
    public void setCounselorID(int counselorID) { this.counselorID = counselorID; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(Time appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setStatus(String status) { this.status = status; }


    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", studentNumber=" + studentNumber +
                ", counselorID=" + counselorID +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", status='" + status + '\'' +
                '}';
    }
}
