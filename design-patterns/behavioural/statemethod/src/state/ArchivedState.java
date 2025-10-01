package state;

import model.Activity;
import model.TodoList;

public class ArchivedState implements TodoListState {

    @Override
    public void addActivity(TodoList list, Activity activity) {
        System.out.println("Cannot add activity. TodoList is Archived.");
    }

    @Override
    public void markCompleted(TodoList list) {
        System.out.println("Archived TodoList cannot be marked Completed.");
    }

    @Override
    public void archive(TodoList list) {
        System.out.println("TodoList is already Archived.");
    }

    @Override
    public String getName() {
        return "ARCHIVED";
    }
}
