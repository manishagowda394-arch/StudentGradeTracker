import java.util.ArrayList;
import java.util.Scanner;

// Stock class
class Stock {
    String name;
    String symbol;
    double price;

    Stock(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }
}

// Portfolio holding class
class Holding {
    Stock stock;
    int quantity;
    double buyPrice;

    Holding(Stock stock, int quantity, double buyPrice) {
        this.stock = stock;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }
}

// Transaction class
class Transaction {
    String type;
    String symbol;
    int quantity;
    double price;

    Transaction(String type, String symbol, int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }
}

// Main class
public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Stock> stocks = new ArrayList<>();
    static ArrayList<Holding> portfolio = new ArrayList<>();
    static ArrayList<Transaction> transactions = new ArrayList<>();

    static double balance = 50000;

    public static void main(String[] args) {

        // Adding sample market stocks
        stocks.add(new Stock("TCS", "TCS", 3500));
        stocks.add(new Stock("Infosys", "INFY", 1800));
        stocks.add(new Stock("Reliance", "RELIANCE", 2900));
        stocks.add(new Stock("Wipro", "WIPRO", 500));

        int choice;

        System.out.println("================================");
        System.out.println("     STOCK TRADING PLATFORM");
        System.out.println("================================");

        do {
            System.out.println("\nCurrent Balance: ₹" + balance);

            System.out.println("\n1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    viewTransactions();
                    break;

                case 6:
                    System.out.println("\nThank you for using the Stock Trading Platform!");
                    break;

                default:
                    System.out.println("\nInvalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }

    // Display market data
    static void viewMarket() {

        System.out.println("\n========== MARKET DATA ==========");

        System.out.printf("%-15s %-15s %-10s%n",
                "Company", "Symbol", "Price");

        for (Stock stock : stocks) {
            System.out.printf("%-15s %-15s ₹%.2f%n",
                    stock.name, stock.symbol, stock.price);
        }
    }

    // Buy stock
    static void buyStock() {

        viewMarket();

        System.out.print("\nEnter stock symbol to buy: ");
        String symbol = sc.next();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        double totalCost = stock.price * quantity;

        if (totalCost > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= totalCost;

        Holding holding = findHolding(symbol);

        if (holding == null) {
            portfolio.add(
                new Holding(stock, quantity, stock.price)
            );
        } else {
            holding.quantity += quantity;
        }

        transactions.add(
            new Transaction("BUY", symbol, quantity, stock.price)
        );

        System.out.println("\nPurchase successful!");
        System.out.println("Stock: " + stock.name);
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Cost: ₹" + totalCost);
    }

    // Sell stock
    static void sellStock() {

        if (portfolio.isEmpty()) {
            System.out.println("\nYour portfolio is empty.");
            return;
        }

        viewPortfolio();

        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = sc.next();

        Holding holding = findHolding(symbol);

        if (holding == null) {
            System.out.println("You do not own this stock!");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (quantity > holding.quantity) {
            System.out.println("You do not have enough shares!");
            return;
        }

        double saleAmount = holding.stock.price * quantity;

        balance += saleAmount;
        holding.quantity -= quantity;

        transactions.add(
            new Transaction("SELL", symbol, quantity, holding.stock.price)
        );

        if (holding.quantity == 0) {
            portfolio.remove(holding);
        }

        System.out.println("\nSale successful!");
        System.out.println("Stock: " + symbol);
        System.out.println("Quantity: " + quantity);
        System.out.println("Amount Received: ₹" + saleAmount);
    }

    // Display portfolio
    static void viewPortfolio() {

        System.out.println("\n========== MY PORTFOLIO ==========");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks in your portfolio.");
            return;
        }

        double totalValue = 0;

        System.out.printf("%-15s %-10s %-15s%n",
                "Stock", "Quantity", "Current Value");

        for (Holding holding : portfolio) {

            double value =
                    holding.stock.price * holding.quantity;

            totalValue += value;

            System.out.printf("%-15s %-10d ₹%-14.2f%n",
                    holding.stock.symbol,
                    holding.quantity,
                    value);
        }

        System.out.println("----------------------------------");
        System.out.printf("Portfolio Value: ₹%.2f%n", totalValue);
        System.out.printf("Cash Balance: ₹%.2f%n", balance);
        System.out.printf("Total Assets: ₹%.2f%n",
                totalValue + balance);
    }

    // Display transactions
    static void viewTransactions() {

        System.out.println("\n========== TRANSACTIONS ==========");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {

            double amount =
                    transaction.price * transaction.quantity;

            System.out.println(
                transaction.type +
                " | " +
                transaction.symbol +
                " | Quantity: " +
                transaction.quantity +
                " | Price: ₹" +
                transaction.price +
                " | Amount: ₹" +
                amount
            );
        }
    }

    // Find stock by symbol
    static Stock findStock(String symbol) {

        for (Stock stock : stocks) {

            if (stock.symbol.equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }

    // Find holding by stock symbol
    static Holding findHolding(String symbol) {

        for (Holding holding : portfolio) {

            if (holding.stock.symbol.equalsIgnoreCase(symbol)) {
                return holding;
            }
        }

        return null;
    }
}