import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;
    private String notes;

    public Expense(String description, double amount, String category, String notes) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = LocalDate.now();
        this.notes = notes;
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return String.format("Date: %s | Category: %s | Description: %s | Amount: ₹%.2f | Notes: %s",
                date, category, description, amount, notes);
    }
} 