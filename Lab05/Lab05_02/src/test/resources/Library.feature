Feature: Book Search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
  Background:
    Given the following books exist:
      | Title              | Author          | Published        |
      | One good book      | Anonymous       | 2013-03-14       |
      | Some other book    | Tim Tomson      | 2014-08-23       |
      | How to cook a dino | Fred Flintstone | 2012-01-01       |
  Scenario: Search books by publication year
    When the customer searches for books published between 14 March 2013 and 23 August 2014
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'One good book'

  Scenario: Search books by author
    When the customer searches for books written by 'Tim Tomson'
    Then 1 book should have been found
    And Book 1 should have the title 'Some other book'

  Scenario: Search books by title
    When the customer searches for a book with title 'One good book'
    Then 1 book should have been found
    And Book 1 should have the title 'One good book'