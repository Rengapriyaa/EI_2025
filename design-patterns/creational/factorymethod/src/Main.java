import factory.model.Student;
import factory.service.LibraryMembership;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            Student student = new Student(
                    "2025CS01",
                    "xyz",
                    "Computer Science",
                    LocalDate.of(2003, 5, 20),
                    5,
                    "9876543210",
                    "xyz@student.tce.edu",
                    "xyz@gmail.com"
            );

            LibraryMembership membership = LibraryMembership.createMembership(student);

            System.out.println("Membership created: " + membership.getMembershipId());
            System.out.println("Linked Student: " + membership.getStudent().getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
