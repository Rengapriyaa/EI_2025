import model.Student;
import service.LibraryMembership;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Student student = new Student(
                "2025CS001",
                "Ravi Kumar",
                "Computer Science",
                java.time.LocalDate.of(2003, 5, 14),
                5,
                "9876543210",
                "ravi.kumar@student.tce.edu",
                "ravi.personal@gmail.com"
        );

        LibraryMembership membership = new LibraryMembership("LIB-001", student);

        membership.returnBook(LocalDate.of(2025, 9, 20), LocalDate.of(2025, 9, 27));

        membership.returnBook(LocalDate.of(2025, 9, 25), LocalDate.of(2025, 9, 25));
    }
}
