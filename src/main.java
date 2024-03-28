import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static boolean isAuthenticated = false;
    public static String accountType = "GUEST";

public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/Flight-Tracker";
    String username = "root";
    String password = "Jb50055007";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(url, username, password);
        Statement statement = conn.createStatement();
    } catch (Exception e) {
        System.out.println("An error occurred connecting to the SQL database.");
    }
    System.out.println("Successfully Connected to SQL Database!");

    System.out.println("### --> Welcome to the Flight Tracker <-- ###");

    System.out.println("Would you like to continue with an account? (y/n)");
    Scanner kb = new Scanner(System.in);
    String authResponse = kb.nextLine();

    boolean wantsToAuthenticate = authResponse.equalsIgnoreCase("y"); // If not, User is guest.
    int action = 0;
    if (wantsToAuthenticate) {
        System.out.println("Here are the different account types: ");
        System.out.println("\t1. Airline Admin");
        System.out.println("\t2. Airport Admin");
        System.out.println("\t3. System Admin");
        System.out.println("\t4. Continue as Public User");
        System.out.println("\nPlease enter the digit corresponding to your choice: ");
        int accountInput = kb.nextInt();

        switch (accountInput) {
            case 1:
                accountType = "AIRLINE_ADMIN";
                System.out.println("\n\nWelcome Airline Admin User!");
                System.out.println("\nWhat would you like to do?");
                System.out.println("\t1. Register a Non Private Flight");
                System.out.println("\t2. View Flights");

                System.out.println("\t--> Please enter the digit for your choice:");
                action = kb.nextInt();
                runAirlineTask(kb, action);
                break;
            case 2:
                accountType = "AIRPORT_ADMIN";
                System.out.println("\n\nWelcome Airport Admin User!");
                System.out.println("\nWhat would you like to do?");
                System.out.println("\t1. Register a Private Flight");
                System.out.println("\t2. View Flights");

                System.out.println("\t\t--> Please enter the digit for your choice:");
                action = kb.nextInt();
                runAirportTask(kb, action);
                break;
            case 3:
                accountType = "SYSTEM_ADMIN";
                System.out.println("\n\nWelcome System Admin User!");
                System.out.println("\nWhat would you like to do?");
                System.out.println("\t1. Add an Airport");

                System.out.println("\t\t--> Please enter the digit for your choice:");
                action = kb.nextInt();
                runSystemAdminTask(kb, action);
                break;
            default:
                accountType = "GUEST";
                System.out.println("\n\nWelcome to our Flight Tracker System!");

                System.out.println("\nWhat would you like to do?");
                System.out.println("\t1. View Flights");
                System.out.println("\t--> Please enter the digit for your choice:");
                action = kb.nextInt();
                runPublicUserTask(kb, action);
                break;
        }
    }
    else { // Public User.
        accountType = "GUEST";
        System.out.println("\n\nWelcome to our Flight Tracker System!");

        System.out.println("\nWhat would you like to do?");
        System.out.println("\t1. View Flights");
        System.out.println("\t--> Please enter the digit for your choice:");
        action = kb.nextInt();
        runPublicUserTask(kb, action);
    }
}
}