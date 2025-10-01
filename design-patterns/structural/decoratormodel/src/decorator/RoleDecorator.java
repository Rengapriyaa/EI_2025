package decorator;

import model.Employee;

public abstract class RoleDecorator implements Employee {
    protected Employee employee;

    public RoleDecorator(Employee employee) {
        this.employee = employee;
    }

    @Override
    public abstract String getRole();

    @Override
    public abstract double getSalary();
}
