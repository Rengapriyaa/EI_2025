package util;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt(String prompt, int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                if (input < min || input > max) {
                    throw new IllegalArgumentException("Input must be between " + min + " and " + max);
                }
                return input;
            } catch (Exception e) {
                LoggerUtil.error("Invalid input. Try again.", e);
            }
        }
    }

    public static double getDouble(String prompt, double min, double max) {
        double input;
        while (true) {
            try {
                System.out.print(prompt);
                input = Double.parseDouble(scanner.nextLine());
                if (input < min || input > max) {
                    throw new IllegalArgumentException("Input must be between " + min + " and " + max);
                }
                return input;
            } catch (Exception e) {
                LoggerUtil.error("Invalid input. Try again.", e);
            }
        }
    }
}
