package observer.model;

import observer.Observer;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Subject in Observer pattern: Activity.
 * Notifies registered observers when it ends.
 */
public class Activity {
    private static final Logger LOGGER = Logger.getLogger(Activity.class.getName());

    public enum Status { PENDING, IN_PROGRESS, COMPLETED, ENDED, NOTIFIED }

    private final String id;
    private final String title;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private volatile Status status;

   
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    public Activity(String id,
                    String title,
                    String description,
                    LocalDate startDate,
                    LocalDate endDate) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.title = validateText(title, "title");
        this.description = description == null ? "" : description;
        this.startDate = Objects.requireNonNull(startDate, "startDate cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "endDate cannot be null");

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }

        this.status = computeInitialStatus(startDate, endDate);
        LOGGER.log(Level.FINE, "Created activity {0} with status {1}", new Object[]{id, status});
    }

    private static String validateText(String s, String fieldName) {
        Objects.requireNonNull(s, fieldName + " cannot be null");
        if (s.isEmpty()) throw new IllegalArgumentException(fieldName + " cannot be blank");
        return s.trim();
    }

    private Status computeInitialStatus(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(start)) return Status.PENDING;
        if (!today.isAfter(end)) return Status.IN_PROGRESS;
        return Status.ENDED;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Status getStatus() { return status; }

    public void addObserver(Observer observer) {
        if (observer == null) return;
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void checkAndNotifyIfEnded() {
        try {
            LocalDate today = LocalDate.now();
            if ((status == Status.IN_PROGRESS || status == Status.PENDING) && !today.isBefore(endDate)) {
                status = Status.ENDED;
                String message = String.format("Activity '%s' (id=%s) ended on %s", title, id, endDate);
                notifyObservers(message);
                status = Status.NOTIFIED;
                LOGGER.log(Level.INFO, "Activity {0} ended and observers notified.", id);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error while checking activity " + id + ": " + ex.getMessage(), ex);
        }
    }

    private void notifyObservers(String message) {
        for (Observer ob : observers) {
            try {
                ob.notify(message);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to notify observer: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public String toString() {
        return "Activity{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + '}';
    }
}
