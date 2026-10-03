public class Stock {
    private final String symbol;
    private final String companyName;
    private double currentPrice;

    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol.toUpperCase();
        this.companyName = companyName;
        this.currentPrice = currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-16s | $%.2f", symbol, companyName, currentPrice);
    }
}