# Advanced Expense Tracker

A feature-rich command-line expense tracking application written in Java that helps users manage their personal finances effectively.

## Features

- 💰 Track daily expenses with categories
- 📊 Monthly budget planning and tracking
- 📈 Detailed expense analytics and insights
- 📁 Data persistence (saves all data automatically)
- 📤 Export data to CSV format
- 🏷️ Custom category management
- 📱 User-friendly console interface
- 💾 Automatic data backup

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Basic knowledge of using command-line applications

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/expense-tracker.git
cd expense-tracker
```

2. Compile the Java files:
```bash
javac Expense.java Budget.java ExpenseManager.java ExpenseTracker.java
```

3. Run the application:
```bash
java ExpenseTracker
```

## Usage

The application provides a simple menu-driven interface:

1. **Add Expense**: Record a new expense with description, amount, category, and optional notes
2. **Set Monthly Budget**: Define monthly budgets for different categories
3. **View All Expenses**: See a chronological list of all recorded expenses
4. **View Category Breakdown**: Get a summary of expenses by category
5. **View Monthly Analysis**: Analyze expenses and budget usage for any month
6. **Add New Category**: Create custom expense categories
7. **Export Data**: Save your expense data to a CSV file
8. **Exit**: Close the application

## Data Storage

The application automatically saves all data in three files:
- `expenses.dat`: Stores all expense records
- `budgets.dat`: Stores monthly budget information
- `categories.dat`: Stores custom expense categories

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Built with Java
- Uses Java's built-in serialization for data persistence
- Implements modern Java features like Streams API and Lambda expressions 