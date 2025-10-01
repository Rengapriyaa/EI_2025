

import observer.model.Activity;
import observer.model.TodoList;
import observer.Creator;
import utils.LoggerUtil;
import utils.ValidationException;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Main application. Demonstrates adding activities and observer notifications.
 *
 * Notes:
 *  - Uses a scheduled executor to check activities periodically (no while(true) hard-coded).
 *  - Menu loop uses a condition controlled by user input (clean exit).
 */
public class LibraryTodoApp {
    private static final long CHECK_INTERVAL_SECONDS = 15L; // check every 15 seconds (demo)

    public static void main(String[] args) {
        // initialize logging (global)
        LoggerUtil.configureLogging();

        TodoList todoList = new TodoList();

        // create a sample creator (observer)
        Creator creator = new Creator("C001", "Alice", "alice@example.com");

        // Create and add a couple of activities (for demo)
        todoList.createAndAddActivity(
                "A100", "Write report", "Complete the design patterns report",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(0)); // ends today
        todoList.createAndAddActivity(
                "A101", "Upload assignment", "Upload assignment to LMS",
                LocalDate.now(), LocalDate.now().plusDays(1)); // ends tomorrow

        // register creator as observer to one activity
        todoList.registerObserverToActivity("A100", creator);
        todoList.registerObserverToActivity("A101", creator);

        // Scheduled checker: runs periodically to detect ended activities and notify observers.
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "activity-checker");
            t.setDaemon(true); // app can exit when other non-daemon threads complete
            return t;
        });

        final Runnable checkerTask = () -> {
            try {
                todoList.checkActivitiesForEnd();
            } catch (Exception ex) {
                // transient error handling: log, but keep scheduler running
                System.err.println("Transient error during check: " + ex.getMessage());
            }
        };

        scheduler.scheduleAtFixedRate(checkerTask, 0, CHECK_INTERVAL_SECONDS, TimeUnit.SECONDS);

        // Simple menu loop (controlled by user input)
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exitRequested = false;
            while (!exitRequested) {
                System.out.println("\n--- ToDo App Menu ---");
                System.out.println("1. Add Activity");
                System.out.println("2. Register Creator to Activity");
                System.out.println("3. View Activity (basic)");
                System.out.println("4. Exit");
                System.out.print("Choose option: ");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1": {
                        try {
                            System.out.print("Activity id: ");
                            String id = scanner.nextLine().trim();
                            System.out.print("Title: ");
                            String title = scanner.nextLine().trim();
                            System.out.print("Description: ");
                            String desc = scanner.nextLine().trim();
                            System.out.print("Start date (YYYY-MM-DD): ");
                            LocalDate sd = LocalDate.parse(scanner.nextLine().trim());
                            System.out.print("End date (YYYY-MM-DD): ");
                            LocalDate ed = LocalDate.parse(scanner.nextLine().trim());

                            todoList.createAndAddActivity(id, title, desc, sd, ed);
                            System.out.println("Activity added.");
                        } catch (ValidationException ve) {
                            System.err.println("Validation error: " + ve.getMessage());
                        } catch (Exception e) {
                            System.err.println("Failed to add activity: " + e.getMessage());
                        }
                    }
                    case "2": {
                        try {
                            System.out.print("Activity id to register: ");
                            String aid = scanner.nextLine().trim();
                            System.out.print("Creator id (for display): ");
                            String cid = scanner.nextLine().trim();
                            System.out.print("Creator name: ");
                            String cname = scanner.nextLine().trim();
                            System.out.print("Creator contact: ");
                            String contact = scanner.nextLine().trim();

                            Creator c = new Creator(cid, cname, contact);
                            todoList.registerObserverToActivity(aid, c);
                            System.out.println("Creator registered to activity " + aid);
                        } catch (ValidationException ve) {
                            System.err.println("Validation error: " + ve.getMessage());
                        } catch (Exception e) {
                            System.err.println("Failed to register: " + e.getMessage());
                        }
                    }
                    case "3": {
                        System.out.print("Activity id to view: ");
                        String aid = scanner.nextLine().trim();
                        Activity a = todoList.getActivity(aid);
                        if (a == null) {
                            System.out.println("No such activity.");
                        } else {
                            System.out.println(a);
                        }
                    }
                    case "4" :{
                        System.out.println("Exiting... shutting down scheduler.");
                        exitRequested = true;
                    }
                    default: System.out.println("Invalid option. Try again.");
                }
            }
        } finally {
            // shutdown scheduler gracefully
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException ie) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("Application stopped.");
        }
    }
}
