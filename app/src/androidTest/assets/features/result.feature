Feature: Search User GitHub
     Perform search on keyword and limit number are inputted

     Scenario Outline: Input keyword and limit number in wrong format
        Given I have a MainActivity
        When I input keyword <keyword>
        And I input limit number <limit>
        Then I should see error on the <view>

     Examples:
        | keyword | limit | view    |
        | shit    | 123   | keyword |

     Scenario Outline: Input keyword and limit number in correct format
        Given I have a MainActivity
        When I input keyword <keyword>
        And I input limit number <limit>
        Then I should <see> Result Screen

     Examples:
        | keyword | limit | see   |
        | test    | 12    | true  |
