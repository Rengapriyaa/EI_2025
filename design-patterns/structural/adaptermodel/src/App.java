import adapter.AadharToVoterIDAdapter;
import model.Aadhar;
import model.Person;
import model.VoterID;
import util.LoggerUtil;

import java.util.Scanner;

public class App{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                LoggerUtil.log("Enter person name (or type 'exit' to quit):");
                String name = scanner.nextLine();
                if ("exit".equalsIgnoreCase(name)) break;

                LoggerUtil.log("Enter age:");
                int age = Integer.parseInt(scanner.nextLine());

                LoggerUtil.log("Enter 12-digit Aadhar Number:");
                String aadharNum = scanner.nextLine();

                Person person = new Person(name, age);
                Aadhar aadhar = new Aadhar(person, aadharNum);

                VoterID voterID = AadharToVoterIDAdapter.convert(aadhar);
                if (voterID != null) voterID.showVoterIDDetails();

            } catch (NumberFormatException nfe) {
                LoggerUtil.log("Invalid input for age. Please enter a valid number.");
            } catch (IllegalArgumentException iae) {
                LoggerUtil.log("Validation Error: " + iae.getMessage());
            } catch (Exception e) {
                LoggerUtil.log("Unexpected Error: " + e.getMessage());
            }
        }

        scanner.close();
        LoggerUtil.log("Application terminated.");
    }
}
