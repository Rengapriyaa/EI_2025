package model;

import state.ActiveState;
import state.TodoListState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import observer.Creator;

public class TodoList {
    private final String id;
    private final List<Activity> activities = new ArrayList<>();
    private TodoListState state;

    public TodoList(String id) {
        this.id = id;
        this.state = new ActiveState(); // default state
    }

    public void addActivity(Activity activity) {
        state.addActivity(this, activity);
    }

    public void markCompleted() {
        state.markCompleted(this);
    }

    public void archive() {
        state.archive(this);
    }

    public String getStateName() {
        return state.getName();
    }

    // package-private for state classes
    public void setState(TodoListState newState) {
        this.state = newState;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id='" + id + '\'' +
                ", state=" + state.getName() +
                ", activities=" + activities +
                '}';
    }

}
