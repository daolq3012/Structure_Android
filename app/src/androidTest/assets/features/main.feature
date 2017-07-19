Feature: MainScreen
  Perform search on keyword and limit number are inputted

  @ScenarioId("FUNCTIONAL.AUTH.SCN.001") @UserStory("MyApp-135") @search-scenarios
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

  Scenario: Input keyword and limit number in correct format
    Given I have a MainActivity
    When I input keyword "test" and limit "<12>"
    And I click button search
    Then I see result Screen
