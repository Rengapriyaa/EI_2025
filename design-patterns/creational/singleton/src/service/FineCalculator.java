package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {

    private static final FineCalculator instance = new FineCalculator();

    private static final int FINE_PER_DAY = 5; 
    private static final int MAX_FINE = 200;  

    private FineCalculator() {}

    public static FineCalculator getInstance() {
        return instance;
    }

    public int calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
            return 0;
        }

        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        int fine = (int) daysLate * FINE_PER_DAY;

        return Math.min(fine, MAX_FINE); 
    }
}
