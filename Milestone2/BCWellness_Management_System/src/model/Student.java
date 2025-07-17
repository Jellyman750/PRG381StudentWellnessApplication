package model;

public class Student extends Person {
    private String studentID;
    private String parentContact;

    public Student(String name, String surname, String email, String studentID, String parentContact) {
        super(name, surname, email);
        this.studentID = studentID;
        this.parentContact = parentContact;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getParentContact() {
        return parentContact;
    }

    public void setParentContact(String parentContact) {
        this.parentContact = parentContact;
    }
}
