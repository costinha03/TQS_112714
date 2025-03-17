# BDD with Cucumber Framework Lab Notes

## Overview
> This lab focuses on Behavior-Driven Development using Cucumber for Java projects, teaching how to create executable specifications before writing code.

## Key Concepts
- **Cucumber** - Framework for running automated tests written in Gherkin
- **Gherkin** - Business-readable DSL for writing test scenarios
- **BDD** - Behavior-Driven Development methodology
- **.feature files** - Where Gherkin scenarios are stored

## Lab Structure

### 5.0 Introduction
> Covers learning objectives and key concepts of BDD with Cucumber

* Learning objectives include:
  * Writing descriptive test scenarios with Gherkin DSL
  * Automating tests with Cucumber for Java and JUnit

* Key points:
  * "Executable specifications" concept
  * Gherkin language for non-technical readability
  * Step definitions mapping Gherkin to Java code
  * Integration with user stories

### 5.1 Getting Started (Calculator)
> Implements tests for a Reverse Polish Notation calculator

**Steps:**
1. Create Java project with Cucumber dependencies
   * Required dependencies:
   ```
   cucumber-java
   cucumber-junit-platform-engine
   junit-platform-suite
   junit-jupiter
   ```

2. Create `.feature` file in `src/test/resources/mypackage/`
   * Contains Gherkin scenarios for calculator operations

3. Implement test steps
   * Two files needed:
     * One to activate Cucumber engine
     * One with step definitions

4. Use Cucumber expressions instead of regex
   * Example: `@When("I add {int} and {int}")` instead of `@When("^I add (\\d+) and (\\d+)$")`

5. Add more test scenarios (multiply, invalid operations)

### 5.2 Passing Data to Tests
> Creates tests for a Library class managing Books

**Tasks:**
* Write feature file for book search functionality
* Implement search options (by author, category)
* Use Cucumber expressions for step definitions
* Handle dates with `ParameterType` configuration
  * Example: `@When("the customer searches for books published between {iso8601Date} and {iso8601Date}")`
* Use DataTables for defining test data

### 5.3 Web Automation (Online Library)
> Combines Cucumber with Selenium WebDriver for web testing

**Tasks:**
* Build on previous lab's online library example
* Develop scenarios for testing search functionality
* Implement test automation using:
  * Cucumber
  * JUnit Jupiter
  * Selenium WebDriver

## Tips for Success
* Write features before implementing step definitions
* Use Cucumber expressions rather than regex
* Consider AI assistance for writing feature files
* Follow the "executable specifications" approach - write tests first

## Resources
* Cucumber School: video lessons and guided exercises
* Boni García's book: "Cucumber in a Nutshell" section