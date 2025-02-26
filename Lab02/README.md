# Lab: Unit Tests with Dependency Mocking

## 2.1 Stocks Portfolio

- This section focuses on testing the `StockPortfolio#totalValue()` method.
- Mocking is used to replace the remote stock market service (`IStockMarketService`).
- The test follows these steps:
  1. Mock the stock market service.
  2. Inject the mock into `StockPortfolio`.
  3. Define expected return values using `when...thenReturn`.
  4. Run the test and assert the expected results.
  5. Verify mock interactions.
- Additional tests add more mock data and analyze Mockito warnings.
- Alternative assertion libraries like Hamcrest or AssertJ are recommended for readability.
- A new method, `mostValuableStocks(int topN)`, is implemented and tested for correctness.

## 2.2 Conversion Method Behavior

- Focuses on mocking an HTTP client for `ProductFinderService#findProductDetails(id)`.
- The HTTP client isn't implemented yet, so its behavior is mocked.
- Tests include:
  - Ensuring `findProductDetails(3)` returns a correctly parsed `Product`.
  - Ensuring `findProductDetails(300)` handles missing products properly.
- Parsing logic is implemented to convert JSON API responses into Java objects.

## 2.3 Connect to Remote Resource and Integration Tests

- This section transitions from mock-based unit tests to real integration tests.
- A real HTTP client is implemented for `ISimpleHttpClient`.
- Integration tests (`ProductFinderServiceIT`) are created and executed.
- The Maven `failsafe` plugin is configured to handle integration tests.
- The test verifies correct API responses and behavior without mocks.

### Differences Between Maven Commands

| Command | Description |
|---------|-------------|
| `mvn test` | Runs unit tests using the `surefire` plugin. |
| `mvn package` | Compiles and packages the code, running unit tests automatically. |
| `mvn package -DskipTests=true` | Packages the code but skips all tests. |
| `mvn failsafe:integration-test` | Runs integration tests using the `failsafe` plugin. |
| `mvn install` | Compiles, tests, and installs the package in the local repository. |

- `surefire` is used for unit tests, while `failsafe` handles integration tests.
- Skipping tests (`-DskipTests=true`) is useful for faster builds.
- Integration tests must be explicitly run using `failsafe:integration-test`.