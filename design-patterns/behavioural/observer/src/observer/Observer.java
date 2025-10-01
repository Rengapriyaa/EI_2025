package observer;

/**
 * Observer interface for receiving notifications.
 */
public interface Observer {
    /**
     * Called when a subject wants to notify observers.
     * @param message 
     */
    void notify(String message);
}
