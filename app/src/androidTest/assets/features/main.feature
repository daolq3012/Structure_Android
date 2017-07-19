Feature: MainScreen
  Perform search on keyword and limit number are inputted

  Scenario Outline: Input keyword and limit number in wrong format
    Given I have a MainActivity
    When I input keyword "<keyword>" and limit "<limit>"
    And I click button search
    Then I should see <error>

    Examples:
      | keyword | limit | error         |
      |         |       | empty_empty   |
      | shit    |       | keyword_empty |
      |         | 123   | empty_limit   |
      | shit    | 123   | keyword_limit |
      | shit    | 13    | keyword_none  |
      | abc     | 123   | none_limit    |

  Scenario Outline: Input keyword and limit number in correct format
    Given I have a MainActivity
    When I input keyword "<keyword>" and limit "<limit>"
    And I click button search
    Then I goto result Screen

    Examples:
      | keyword | limit |
      | test    | 12    |