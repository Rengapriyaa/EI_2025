package observer.model;

import observer.Observer;
import utils.ValidationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages a collection of activities. Acts as the registry for subjects.
 * Thread-safe and optimized for concurrent reads/writes.
 */
public class TodoList {
    private static final Logger LOGGER = Logger.getLogger(TodoList.class.getName());

    private final ConcurrentMap<String, Activity> activities = new ConcurrentHashMap<>();

    public void addActivity(Activity activity) {
        Objects.requireNonNull(activity, "activity cannot be null");
        if (activities.putIfAbsent(activity.getId(), activity) != null) {
            throw new ValidationException("Activity with id already exists: " + activity.getId());
        }
        LOGGER.log(Level.INFO, "Added activity: {0}", activity.getId());
    }
    public void addActivities(Collection<Activity> activityCollection) {
        Objects.requireNonNull(activityCollection, "activityCollection cannot be null");
        for (Activity a : activityCollection) {
            addActivity(a); 
        }
    }

    public void registerObserverToActivity(String activityId, Observer observer) {
        Activity a = activities.get(activityId);
        if (a == null) {
            throw new ValidationException("No activity found with id: " + activityId);
        }
        a.addObserver(observer);
        LOGGER.log(Level.INFO, "Observer registered to activity {0}", activityId);
    }

    public void checkActivitiesForEnd() {
        for (Activity activity : activities.values()) {
            activity.checkAndNotifyIfEnded();
        }
    }

    public void createAndAddActivity(String id, String title, String description,
                                     LocalDate startDate, LocalDate endDate) {
        if (id == null || id.isEmpty()) throw new ValidationException("id required");
        Activity a = new Activity(id, title, description, startDate, endDate);
        addActivity(a);
    }

    public Activity getActivity(String id) {
        return activities.get(id);
    }
}
