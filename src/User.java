import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String username;
    private double balance;
    private final Map<String, Integer> portfolio = new HashMap<>(); // Symbol -> Quantity

    public User(String username, double initialBalance) {
        this.username = username;
        this.balance = initialBalance;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getPortfolio() {
        return Collections.unmodifiableMap(portfolio);
    }

    public boolean buyStock(Stock stock, int quantity) {
        if (stock == null || quantity <= 0) return false;
        double totalCost = stock.getCurrentPrice() * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
            return true;
        }
        return false;
    }

    public boolean sellStock(Stock stock, int quantity) {
        if (stock == null || quantity <= 0) return false;
        int currentHolding = portfolio.getOrDefault(stock.getSymbol(), 0);
        if (currentHolding >= quantity) {
            double proceeds = stock.getCurrentPrice() * quantity;
            balance += proceeds;
            if (currentHolding == quantity) {
                portfolio.remove(stock.getSymbol());
            } else {
                portfolio.put(stock.getSymbol(), currentHolding - quantity);
            }
            return true;
        }
        return false;
    }

    public void saveToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(username + "," + balance);
            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error saving portfolio data: " + e.getMessage());
        }
    }

    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine();
            if (header != null && !header.isBlank()) {
                String[] parts = header.split(",");
                this.balance = Double.parseDouble(parts[1].trim());
            }
            portfolio.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                portfolio.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading portfolio data: " + e.getMessage());
        }
    }
}