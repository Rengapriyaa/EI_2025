

import model.Activity;
import model.TodoList;

import java.time.LocalDate;

public class TodoListApp {
    public static void main(String[] args) {
        TodoList todoList = new TodoList("T001");

        System.out.println("Initial State: " + todoList.getStateName());

        // Add activity in Active state
        Activity a1 = new Activity("A1", "Finish homework", "Math exercises",
                LocalDate.now(), LocalDate.now().plusDays(1));
        todoList.addActivity(a1);

        // Move to Completed
        todoList.markCompleted();
        System.out.println("Current State: " + todoList.getStateName());

        // Try adding after Completed
        Activity a2 = new Activity("A2", "Go shopping", "Buy groceries",
                LocalDate.now(), LocalDate.now().plusDays(2));
        todoList.addActivity(a2);

        // Archive
        todoList.archive();
        System.out.println("Current State: " + todoList.getStateName());

        // Try operations after Archived
        todoList.addActivity(a2);
        todoList.markCompleted();
    }
}
