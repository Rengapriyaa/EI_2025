package factory.service;

import factory.model.Student;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryMembership {
    private static final Logger LOGGER = Logger.getLogger(LibraryMembership.class.getName());
    private final String membershipId;
    private final Student student;

    private LibraryMembership(Student student) {
        this.student = student;
        this.membershipId = generateMembershipId();
        LOGGER.log(Level.INFO, "Library membership created for student: {0}", student.getRegisterNo());
    }

    public static LibraryMembership createMembership(Student student) {
        if (student == null) {
            LOGGER.severe("Student object is null. Cannot create membership.");
            throw new IllegalArgumentException("Student cannot be null");
        }
        return new LibraryMembership(student);
    }

    private String generateMembershipId() {
        return "LIB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getMembershipId() {
        return membershipId;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "LibraryMembership{" +
                "membershipId='" + membershipId + '\'' +
                ", student=" + student +
                '}';
    }
}
