import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "portfolio.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        User user = new User("Sameer", 10000.00);

        // Load existing portfolio if available
        user.loadFromFile(DATA_FILE);

        System.out.println("==================================================");
        System.out.println("   Welcome to CodeAlpha Stock Trading Platform    ");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View Market Quotes");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio & Balance");
            System.out.println("5. Save & Exit");
            System.out.print("Enter choice (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayMarket(market);
                    break;

                case "2":
                    handleBuy(scanner, market, user);
                    break;

                case "3":
                    handleSell(scanner, market, user);
                    break;

                case "4":
                    displayPortfolio(market, user);
                    break;

                case "5":
                    user.saveToFile(DATA_FILE);
                    System.out.println("Portfolio saved to '" + DATA_FILE + "'. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 5.");
            }
        }
        scanner.close();
    }

    private static void displayMarket(Market market) {
        System.out.println("\n----------------- Live Market Data -----------------");
        System.out.printf("%-6s | %-16s | %s\n", "Symbol", "Company", "Price (USD)");
        System.out.println("----------------------------------------------------");
        for (Stock stock : market.getAllStocks()) {
            System.out.println(stock);
        }
        System.out.println("----------------------------------------------------");
    }

    private static void handleBuy(Scanner scanner, Market market, User user) {
        System.out.print("Enter stock ticker to buy: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        Stock stock = market.getStock(symbol);

        if (stock == null) {
            System.out.println("Error: Symbol not recognized.");
            return;
        }

        System.out.printf("Current price of %s is $%.2f. Available Cash: $%.2f\n",
                stock.getSymbol(), stock.getCurrentPrice(), user.getBalance());
        System.out.print("Enter quantity to purchase: ");

        try {
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            if (user.buyStock(stock, quantity)) {
                System.out.printf("Success! Bought %d shares of %s at $%.2f each.\n",
                        quantity, stock.getSymbol(), stock.getCurrentPrice());
            } else {
                System.out.println("Transaction declined: Insufficient funds or invalid quantity.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Quantity must be a valid integer.");
        }
    }

    private static void handleSell(Scanner scanner, Market market, User user) {
        System.out.print("Enter stock ticker to sell: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        Stock stock = market.getStock(symbol);

        if (stock == null) {
            System.out.println("Error: Symbol not recognized.");
            return;
        }

        int currentHolding = user.getPortfolio().getOrDefault(symbol, 0);
        System.out.printf("You currently own %d shares of %s.\n", currentHolding, symbol);
        if (currentHolding == 0) return;

        System.out.print("Enter quantity to sell: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            if (user.sellStock(stock, quantity)) {
                System.out.printf("Success! Sold %d shares of %s at $%.2f each.\n",
                        quantity, stock.getSymbol(), stock.getCurrentPrice());
            } else {
                System.out.println("Transaction declined: Quantity exceeds current holdings.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Quantity must be a valid integer.");
        }
    }

    private static void displayPortfolio(Market market, User user) {
        System.out.println("\n---------------- Account Portfolio ----------------");
        System.out.printf("Account Holder: %s\n", user.getUsername());
        System.out.printf("Cash Balance  : $%.2f\n", user.getBalance());
        System.out.println("Holdings:");

        double totalHoldingsValue = 0.0;
        if (user.getPortfolio().isEmpty()) {
            System.out.println("  (No stocks currently owned)");
        } else {
            for (Map.Entry<String, Integer> entry : user.getPortfolio().entrySet()) {
                Stock stock = market.getStock(entry.getKey());
                double unitPrice = stock != null ? stock.getCurrentPrice() : 0.0;
                double positionValue = unitPrice * entry.getValue();
                totalHoldingsValue += positionValue;

                System.out.printf("  • %-5s: %3d shares @ $%-7.2f | Market Value: $%.2f\n",
                        entry.getKey(), entry.getValue(), unitPrice, positionValue);
            }
        }
        System.out.println("---------------------------------------------------");
        System.out.printf("Total Portfolio Valuation: $%.2f\n", (user.getBalance() + totalHoldingsValue));
        System.out.println("---------------------------------------------------");
    }
}