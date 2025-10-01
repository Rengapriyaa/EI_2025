package observer;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete observer representing the creator (owner) of activities.
 */
public class Creator implements Observer {
    private static final Logger LOGGER = Logger.getLogger(Creator.class.getName());

    private final String creatorId;
    private final String name;
    private final String contact; 

    public Creator(String creatorId, String name, String contact) {
        this.creatorId = Objects.requireNonNull(creatorId, "creatorId cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.contact = Objects.requireNonNull(contact, "contact cannot be null");
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public void notify(String message) {
        
        LOGGER.log(Level.INFO, "Notify {0} ({1}): {2}", new Object[] { name, contact, message });
    }

    @Override
    public String toString() {
        return "Creator{" + "creatorId='" + creatorId + '\'' + ", name='" + name + '\'' + ", contact='" + contact + '\'' + '}';
    }
}
