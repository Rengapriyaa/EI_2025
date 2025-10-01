package state;

import model.Activity;
import model.TodoList;

public class CompletedState implements TodoListState {

    @Override
    public void addActivity(TodoList list, Activity activity) {
        System.out.println("Cannot add activity. TodoList is already Completed.");
    }

    @Override
    public void markCompleted(TodoList list) {
        System.out.println("TodoList is already Completed.");
    }

    @Override
    public void archive(TodoList list) {
        list.setState(new ArchivedState());
        System.out.println("TodoList archived.");
    }

    @Override
    public String getName() {
        return "COMPLETED";
    }
}
