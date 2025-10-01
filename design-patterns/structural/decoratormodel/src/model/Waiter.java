package model;

public class Waiter implements Employee {
    private double salary;

    public Waiter(double salary) {
        this.salary = salary;
    }

    @Override
    public String getRole() {
        return "Waiter";
    }

    @Override
    public double getSalary() {
        return salary;
    }
}
