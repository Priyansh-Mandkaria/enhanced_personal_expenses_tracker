import java.io.Serializable;
import java.time.YearMonth;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private YearMonth month;
    private double amount;
    private String category;

    public Budget(YearMonth month, double amount, String category) {
        this.month = month;
        this.amount = amount;
        this.category = category;
    }

    public YearMonth getMonth() {
        return month;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("Month: %s | Category: %s | Budget: ₹%.2f",
                month, category, amount);
    }
} 