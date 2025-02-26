package TQS.Lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class StockPortfolio2Test {
  @Mock
  IStockMarketService market;

  @InjectMocks
  StockPortfolio portfolio;
  @BeforeEach
  public void setUp() {
        portfolio = new StockPortfolio(market);
    }

  @Test
  void testGetTotalValue() {
    when(market.lookUpPrice("AAPL")).thenReturn(100.0);
    when(market.lookUpPrice("MSFT")).thenReturn(50.0);

    Stock appleStock = new Stock("AAPL", 10);
    Stock microsoftStock = new Stock("MSFT", 20);

    portfolio.addStock(appleStock);
    portfolio.addStock(microsoftStock);

    double result = portfolio.totalValue();
    assertEquals(2000.0, result);
    assertThat(result, is(2000.0));
    verify(market, times(2)).lookUpPrice(anyString());

  }

  @Test
    void testGetMostValuableStocks() {
        when(market.lookUpPrice("AAPL")).thenReturn(100.0);
        when(market.lookUpPrice("MSFT")).thenReturn(50.0);

        Stock appleStock = new Stock("AAPL", 10);
        Stock microsoftStock = new Stock("MSFT", 20);

        portfolio.addStock(appleStock);
        portfolio.addStock(microsoftStock);

        var result = portfolio.mostValuableStocks(1);
        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).getLabel());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getLabel(), is("AAPL"));
        verify(market, times(2)).lookUpPrice(anyString());

        var result2 = portfolio.mostValuableStocks(2);
        assertEquals(2, result2.size());
        assertEquals("AAPL", result2.get(0).getLabel());
        assertEquals("MSFT", result2.get(1).getLabel());
        assertThat(result2.size(), is(2));
        assertThat(result2.get(0).getLabel(), is("AAPL"));
        assertThat(result2.get(1).getLabel(), is("MSFT"));
        verify(market, times(4)).lookUpPrice(anyString());

    }

}