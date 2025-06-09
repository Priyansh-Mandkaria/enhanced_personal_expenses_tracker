import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private List<Expense> expenses;
    private List<Budget> budgets;
    private Set<String> categories;
    private static final String EXPENSES_FILE = "expenses.dat";
    private static final String BUDGETS_FILE = "budgets.dat";
    private static final String CATEGORIES_FILE = "categories.dat";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        budgets = new ArrayList<>();
        categories = new HashSet<>(Arrays.asList(
            "Food", "Transport", "Shopping", "Bills", "Entertainment", "Health", "Education", "Other"
        ));
        loadData();
    }

    private void loadData() {
        try {
            loadExpenses();
            loadBudgets();
            loadCategories();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing data found. Starting fresh!");
        }
    }

    private void loadExpenses() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXPENSES_FILE))) {
            expenses = (List<Expense>) ois.readObject();
        }
    }

    private void loadBudgets() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BUDGETS_FILE))) {
            budgets = (List<Budget>) ois.readObject();
        }
    }

    private void loadCategories() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CATEGORIES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                categories.add(line.trim());
            }
        }
    }

    private void saveData() {
        try {
            saveExpenses();
            saveBudgets();
            saveCategories();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void saveExpenses() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXPENSES_FILE))) {
            oos.writeObject(expenses);
        }
    }

    private void saveBudgets() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BUDGETS_FILE))) {
            oos.writeObject(budgets);
        }
    }

    private void saveCategories() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CATEGORIES_FILE))) {
            for (String category : categories) {
                writer.write(category);
                writer.newLine();
            }
        }
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveData();
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
        saveData();
    }

    public void addCategory(String category) {
        categories.add(category);
        saveData();
    }

    public Set<String> getCategories() {
        return new HashSet<>(categories);
    }

    public double getTotalExpenses() {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getMonthlyExpenses(YearMonth month) {
        return expenses.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(month))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, Double> getCategoryTotals() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                    Expense::getCategory,
                    Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public Map<String, Double> getMonthlyCategoryTotals(YearMonth month) {
        return expenses.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(month))
                .collect(Collectors.groupingBy(
                    Expense::getCategory,
                    Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }

    public List<Budget> getBudgets() {
        return new ArrayList<>(budgets);
    }

    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        System.out.println("\nExpense History:");
        System.out.println("----------------");
        expenses.stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .forEach(System.out::println);
    }

    public void displayCategoryBreakdown() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        System.out.println("\nCategory-wise Breakdown:");
        System.out.println("----------------------");
        getCategoryTotals().forEach((category, total) ->
            System.out.printf("%s: ₹%.2f%n", category, total));
    }

    public void displayMonthlyAnalysis(YearMonth month) {
        double totalExpenses = getMonthlyExpenses(month);
        Map<String, Double> categoryTotals = getMonthlyCategoryTotals(month);
        Map<String, Double> budgetTotals = budgets.stream()
                .filter(b -> b.getMonth().equals(month))
                .collect(Collectors.toMap(
                    Budget::getCategory,
                    Budget::getAmount,
                    Double::sum
                ));

        System.out.printf("\nMonthly Analysis for %s:%n", month);
        System.out.println("------------------------");
        System.out.printf("Total Expenses: ₹%.2f%n", totalExpenses);

        System.out.println("\nCategory-wise Analysis:");
        System.out.println("---------------------");
        categoryTotals.forEach((category, amount) -> {
            double budget = budgetTotals.getOrDefault(category, 0.0);
            double percentage = budget > 0 ? (amount / budget) * 100 : 0;
            System.out.printf("%s:%n", category);
            System.out.printf("  Spent: ₹%.2f%n", amount);
            System.out.printf("  Budget: ₹%.2f%n", budget);
            System.out.printf("  Usage: %.1f%%%n", percentage);
        });
    }

    public void exportToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Date,Category,Description,Amount,Notes");
            expenses.forEach(expense -> writer.printf("%s,%s,%s,%.2f,%s%n",
                expense.getDate(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getNotes()));
            System.out.println("Data exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }
} 