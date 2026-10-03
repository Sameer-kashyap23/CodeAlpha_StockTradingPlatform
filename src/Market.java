import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Market {
    private final Map<String, Stock> marketStocks = new LinkedHashMap<>();

    public Market() {
        seedMarketData();
    }

    private void seedMarketData() {
        addStock(new Stock("AAPL", "Apple Inc.", 189.45));
        addStock(new Stock("GOOGL", "Alphabet Inc.", 174.80));
        addStock(new Stock("MSFT", "Microsoft Corp.", 425.20));
        addStock(new Stock("TSLA", "Tesla Inc.", 212.15));
        addStock(new Stock("NVDA", "Nvidia Corp.", 128.90));
    }

    public void addStock(Stock stock) {
        marketStocks.put(stock.getSymbol(), stock);
    }

    public Stock getStock(String symbol) {
        if (symbol == null) return null;
        return marketStocks.get(symbol.toUpperCase());
    }

    public Collection<Stock> getAllStocks() {
        return Collections.unmodifiableCollection(marketStocks.values());
    }
}