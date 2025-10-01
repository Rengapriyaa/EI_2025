package decorator;

import model.Employee;

public class BonusDecorator extends RoleDecorator {

    private double bonus;

    public BonusDecorator(Employee employee, double bonus) {
        super(employee);
        this.bonus = bonus;
    }

    @Override
    public String getRole() {
        return employee.getRole() + " + Bonus";
    }

    @Override
    public double getSalary() {
        return employee.getSalary() + bonus;
    }
}
