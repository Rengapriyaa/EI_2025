package service;

import model.Student;
import java.time.LocalDate;

public class LibraryMembership {
    private String membershipId;
    private Student student;

    public LibraryMembership(String membershipId, Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null for membership");
        }
        this.student = student;
        this.membershipId = membershipId;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public Student getStudent() {
        return student;
    }

    public void returnBook(LocalDate dueDate, LocalDate returnDate) {
        FineCalculator fineCalculator = FineCalculator.getInstance();
        int fine = fineCalculator.calculateFine(dueDate, returnDate);

        System.out.println("Member: " + student.getName() + " (ID: " + membershipId + ")");
        System.out.println("Book Due Date: " + dueDate);
        System.out.println("Book Return Date: " + returnDate);
        System.out.println("Fine: ₹" + fine);
        System.out.println("------------------------------------");
    }

    
}
