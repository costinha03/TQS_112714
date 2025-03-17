# Created by diogo03 at 13/03/25
Feature: Search for books in the store

  Scenario: User searches for a book and views its details
    Given the user is on the book store homepage
    When the user searches for "Harry Potter"
    Then the user should see "Harry Potter and the Sorcerer's Stone"

