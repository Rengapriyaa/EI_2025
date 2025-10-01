package decorator;

import model.Employee;

public class SeniorityDecorator extends RoleDecorator {

    private double seniorityAllowance;

    public SeniorityDecorator(Employee employee, double allowance) {
        super(employee);
        this.seniorityAllowance = allowance;
    }

    @Override
    public String getRole() {
        return employee.getRole() + " + Seniority";
    }

    @Override
    public double getSalary() {
        return employee.getSalary() + seniorityAllowance;
    }
}
