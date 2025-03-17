# Lab 4: Web Layer Test Automation - Notebook

## Learning Objectives
- Write web-layer tests using Selenium API and automate their execution in JUnit
- Understand best practices for elements location and possible causes of flaky tests
- Apply the Page Object Pattern

## Key Points
- Acceptance tests exercise the UI as if a real user was using the application (black box testing)
- Browser automation is essential for web application acceptance testing
- Several frameworks exist (like Puppeteer), but Selenium WebDriver API is most common for Java
- Page Objects Pattern improves test code readability and maintainability
- Web browser automation helps implement "smoke tests"

## 4.1 WebDriver "Hello World"

### Setup
- Selenium WebDriver provides API to control web browsers programmatically
- Requires driver implementation for specific browser (simplified with WebDriver management)

### Implementation Notes
- Added main dependencies to POM.xml:
  - `org.seleniumhq.selenium:selenium-java` (Selenium 4)
  - `io.github.bonigarcia:selenium-jupiter` (JUnit extension)
  - `org.junit.jupiter:junit-jupiter` (JUnit 5)

### Basic Test Implementation
- Created initial test using example from Garcia's book
- Modified to use custom website scenario
- Added additional navigation to "Slow calculator" link
- Verified correct page navigation with assertions on `driver.getCurrentUrl()`

### Refactoring with Selenium-Jupiter
- Added `@ExtendWith(SeleniumJupiter.class)` annotation
- Used dependency injection to get WebDriver instance
- Removed explicit quit() call as it's handled by the extension

## 4.2 Interactive Test Recording

### Recording with Selenium IDE
- Installed Selenium IDE browser extension
- Created test for BlazeDemo travel booking:
  - Selected departure and destination cities
  - Chose a flight
  - Filled in passenger details
  - Completed purchase
  - Added verification points throughout process

### Test Modification
- Added assertion to verify "BlazeDemo Confirmation" title on confirmation page
- Experimented with deliberately breaking the test to observe failure behavior
- Saved project as .side file and included in git repository

### Export to Java
- Exported test from Selenium IDE to Java
- Refactored generated code to be compatible with JUnit 5
- Ran test programmatically through JUnit

### Selenium-Jupiter Integration
- Refactored to use Selenium-Jupiter extension benefits:
  - Automatic WebDriver dependency injection
  - No need to pre-install WebDriver binaries
  - Automatic WebDriver initialization and closing

## 4.3 Selecting Elements with Locators

### Book Search Test
- Implemented test for searching "Harry Potter" in fictitious bookstore
- Verified results contain "Harry Potter and the Sorcerer's Stone" by J.K. Rowling

### Locator Strategy Analysis
- Reviewed locators used in the test
- Found instances of XPath selectors (less robust)
- Identified ID-based locators (more robust)
- Noted that more stable locators are based on:
  - IDs
  - Data attributes (data-testid)
  - Unique class names

### Test Refactoring
- Created improved version with:
  - Better selectors (e.g., `By.cssSelector("[data-testid=book-search-item]")`)
  - Reduced XPath usage in favor of CSS selectors





