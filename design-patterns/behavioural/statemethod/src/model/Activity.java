package model;

import java.time.LocalDate;
import java.util.Objects;

public class Activity {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Activity(String id, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.description = description == null ? "" : description;
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
