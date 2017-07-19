Feature: SearchResult Screen

#  Background:
#    Given I have a MainActivity
#    When I input keyword "test" and limit "<12>"
#    And I click button search
#    Then I see result Screen

  Scenario : See list result and click item
    Given I have a SearchResult Activity
    And I click any item
    Then I see detail Screen
