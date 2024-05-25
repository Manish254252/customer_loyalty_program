package in.stackroute;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoyaltyProgram program = new LoyaltyProgram();

        while (true) {
            System.out.println("\nCustomer Loyalty Program");
            System.out.println("1. Register Customer");
            System.out.println("2. Add Transaction");
            System.out.println("3. Add Reward");
            System.out.println("4. Redeem Reward");
            System.out.println("5. View Customer Info");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    program.registerCustomer(name, email, phone);
                    break;
                case 2:
                    System.out.print("Enter customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter transaction amount: ");
                    double amount = scanner.nextDouble();
                    program.addTransaction(customerId, amount);
                    break;
                case 3:
                    System.out.print("Enter reward ID: ");
                    int rewardId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter reward description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter points required: ");
                    int pointsRequired = scanner.nextInt();
                    program.addReward(rewardId, description, pointsRequired);
                    break;
                case 4:
                    System.out.print("Enter customer ID: ");
                    customerId = scanner.nextInt();
                    System.out.print("Enter reward ID: ");
                    rewardId = scanner.nextInt();
                    program.redeemReward(customerId, rewardId);
                    break;
                case 5:
                    System.out.print("Enter customer ID: ");
                    customerId = scanner.nextInt();
                    program.viewCustomerInfo(customerId);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
