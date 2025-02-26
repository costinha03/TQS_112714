package TQS.Lab02;

import java.util.ArrayList;
import java.util.List;

public class StockPortfolio {
    private final List<Stock> stocks;
    private final IStockMarketService stockMarket;

    public StockPortfolio(IStockMarketService stockMarket) {
        this.stocks = new ArrayList<>();
        this.stockMarket = stockMarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double total = 0.0;
        for (Stock stock : stocks) {
            total += stockMarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }

    /**
     * @param topN the number of most valuable stocks to return
     * @return a list with the topN most valuable stocks in the portfolio
     */
    public List<Stock> mostValuableStocks(int topN) {
        List<Stock> mostValuable = new ArrayList<>(stocks);
        mostValuable.sort((s1, s2) -> {
            double s1Value = stockMarket.lookUpPrice(s1.getLabel()) * s1.getQuantity();
            double s2Value = stockMarket.lookUpPrice(s2.getLabel()) * s2.getQuantity();
            return Double.compare(s2Value, s1Value);
        });
        return mostValuable.subList(0, Math.min(topN, mostValuable.size()));
    }

}
