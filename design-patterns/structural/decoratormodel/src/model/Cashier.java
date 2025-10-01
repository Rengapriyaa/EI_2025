package model;

public class Cashier implements Employee {
    private double salary;

    public Cashier(double salary) {
        this.salary = salary;
    }

    @Override
    public String getRole() {
        return "Cashier";
    }

    @Override
    public double getSalary() {
        return salary;
    }
}
