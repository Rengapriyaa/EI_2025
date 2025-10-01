package state;

import model.Activity;
import model.TodoList;

public class ActiveState implements TodoListState {

    @Override
    public void addActivity(TodoList list, Activity activity) {
        list.getActivities().add(activity);
        System.out.println("Added activity: " + activity);
    }

    @Override
    public void markCompleted(TodoList list) {
        list.setState(new CompletedState());
        System.out.println("TodoList marked as Completed.");
    }

    @Override
    public void archive(TodoList list) {
        System.out.println("Cannot archive directly from Active. Complete it first.");
    }

    @Override
    public String getName() {
        return "ACTIVE";
    }
}
