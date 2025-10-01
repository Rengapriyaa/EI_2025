package state;

import model.Activity;
import model.TodoList;

/**
 * Abstract State for TodoList
 */
public interface TodoListState {
    void addActivity(TodoList list, Activity activity);
    void markCompleted(TodoList list);
    void archive(TodoList list);
    String getName();
}
