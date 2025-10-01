package factory.model;

import java.time.LocalDate;
import java.util.Objects;

public class Student {
    private final String registerNo;
    private final String name;
    private final String department;
    private final LocalDate dateOfBirth;
    private final int semester;
    private final String phoneNo;
    private final String studentEmail;
    private final String personalEmail;

    public Student(String registerNo, String name, String department, LocalDate dateOfBirth,
                   int semester, String phoneNo, String studentEmail, String personalEmail) {

        if (registerNo == null) {
            throw new IllegalArgumentException("Register number cannot be null or blank");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (semester <= 0) {
            throw new IllegalArgumentException("Semester must be a positive number");
        }
        if (!studentEmail.endsWith("@student.tce.edu")) {
            throw new IllegalArgumentException("Student email must be a university domain");
        }

        this.registerNo = registerNo;
        this.name = name;
        this.department = department;
        this.dateOfBirth = dateOfBirth;
        this.semester = semester;
        this.phoneNo = phoneNo;
        this.studentEmail = studentEmail;
        this.personalEmail = personalEmail;
    }

    public String getRegisterNo() { return registerNo; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public int getSemester() { return semester; }
    public String getPhoneNo() { return phoneNo; }
    public String getStudentEmail() { return studentEmail; }
    public String getPersonalEmail() { return personalEmail; }

    @Override
    public String toString() {
        return "Student{" +
                "registerNo='" + registerNo + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", semester=" + semester +
                ", phoneNo='" + phoneNo + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(registerNo, student.registerNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerNo);
    }
}
