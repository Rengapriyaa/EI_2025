package model;

public class Chef implements Employee {
    private double salary;

    public Chef(double salary) {
        this.salary = salary;
    }

    @Override
    public String getRole() {
        return "Chef";
    }

    @Override
    public double getSalary() {
        return salary;
    }
}
