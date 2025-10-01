package app;

import model.*;
import decorator.*;
import util.*;

public class RestaurantApp {

    public static void main(String[] args) {

        boolean continueRunning = true;

        while (continueRunning) {
            try {
                System.out.println("\n--- Restaurant Employee Menu ---");
                System.out.println("1. Chef");
                System.out.println("2. Waiter");
                System.out.println("3. Cashier");
                System.out.println("4. Exit");

                int choice = InputUtil.getInt("Select role: ", 1, 4);

                if (choice == 4) {
                    continueRunning = false;
                    LoggerUtil.info("Exiting application. Goodbye!");
                    continue;
                }

                double baseSalary = InputUtil.getDouble("Enter base salary: ", 1000, 100000);

                Employee emp;
				switch (choice) {
					case 1:
						emp = new Chef(baseSalary);
						break;
					case 2:
						emp = new Waiter(baseSalary);
						break;
					case 3:
						emp = new Cashier(baseSalary);
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + choice);
				}

                boolean addBonus = InputUtil.getInt("Add bonus? (1=Yes, 0=No): ", 0, 1) == 1;
                if (addBonus) {
                    double bonus = InputUtil.getDouble("Enter bonus: ", 100, 5000);
                    emp = new BonusDecorator(emp, bonus);
                }

                boolean addSeniority = InputUtil.getInt("Add seniority allowance? (1=Yes, 0=No): ", 0, 1) == 1;
                if (addSeniority) {
                    double allowance = InputUtil.getDouble("Enter seniority allowance: ", 100, 5000);
                    emp = new SeniorityDecorator(emp, allowance);
                }

                LoggerUtil.info("Final Role: " + emp.getRole());
                LoggerUtil.info("Final Salary: " + emp.getSalary());

            } catch (Exception e) {
                LoggerUtil.error("Error occurred in main loop", e);
            }
        }
    }
}
