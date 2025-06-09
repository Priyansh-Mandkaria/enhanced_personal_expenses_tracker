import java.time.YearMonth;
import java.util.Scanner;
import java.util.Set;

public class ExpenseTracker {
    private static ExpenseManager expenseManager = new ExpenseManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        displayWelcome();

        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    addBudget();
                    break;
                case 3:
                    expenseManager.displayExpenses();
                    break;
                case 4:
                    expenseManager.displayCategoryBreakdown();
                    break;
                case 5:
                    displayMonthlyAnalysis();
                    break;
                case 6:
                    addCategory();
                    break;
                case 7:
                    exportData();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using Expense Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayWelcome() {
        System.out.println("\n" +
            "╔══════════════════════════════════════════════════════════╗\n" +
            "║                                                          ║\n" +
            "║               Welcome to Expense Tracker                  ║\n" +
            "║                                                          ║\n" +
            "╚══════════════════════════════════════════════════════════╝\n");
    }

    private static void displayMenu() {
        System.out.println("\n=== Expense Tracker Menu ===");
        System.out.println("1. Add Expense");
        System.out.println("2. Set Monthly Budget");
        System.out.println("3. View All Expenses");
        System.out.println("4. View Category Breakdown");
        System.out.println("5. View Monthly Analysis");
        System.out.println("6. Add New Category");
        System.out.println("7. Export Data to CSV");
        System.out.println("8. Exit");
    }

    private static void addExpense() {
        System.out.println("\nEnter Expense Details:");
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Description: ");
        String description = scanner.nextLine();
        
        double amount = getDoubleInput("Amount: ₹");
        
        String category = selectCategory();
        
        System.out.print("Notes (optional): ");
        String notes = scanner.nextLine();

        Expense expense = new Expense(description, amount, category, notes);
        expenseManager.addExpense(expense);
        System.out.println("\n✓ Expense added successfully!");
    }

    private static void addBudget() {
        System.out.println("\nSet Monthly Budget:");
        int year = getIntInput("Year: ");
        int month = getIntInput("Month (1-12): ");
        YearMonth yearMonth = YearMonth.of(year, month);
        
        String category = selectCategory();
        
        double amount = getDoubleInput("Budget Amount: ₹");

        Budget budget = new Budget(yearMonth, amount, category);
        expenseManager.addBudget(budget);
        System.out.println("\n✓ Budget set successfully!");
    }

    private static void displayMonthlyAnalysis() {
        int year = getIntInput("Enter year: ");
        int month = getIntInput("Enter month (1-12): ");
        YearMonth yearMonth = YearMonth.of(year, month);
        expenseManager.displayMonthlyAnalysis(yearMonth);
    }

    private static void addCategory() {
        System.out.print("\nEnter new category name: ");
        String category = scanner.nextLine();
        expenseManager.addCategory(category);
        System.out.println("\n✓ Category added successfully!");
    }

    private static void exportData() {
        System.out.print("\nEnter filename for export (e.g., expenses.csv): ");
        String filename = scanner.nextLine();
        expenseManager.exportToCSV(filename);
    }

    private static String selectCategory() {
        Set<String> categories = expenseManager.getCategories();
        System.out.println("\nAvailable Categories:");
        int i = 1;
        for (String category : categories) {
            System.out.printf("%d. %s%n", i++, category);
        }
        
        int choice;
        do {
            choice = getIntInput("\nSelect category number: ");
        } while (choice < 1 || choice > categories.size());
        
        return categories.toArray(new String[0])[choice - 1];
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
} 