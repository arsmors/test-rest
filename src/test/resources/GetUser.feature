Feature: Get a user from database

  Scenario Outline: Get a User
    When the user wants to get a record of existing user id "14217"
    Then you should receive a "200" status code
    And response includes following body with info
      | first_name | <first_name> |
      | last_name  | <last_name>  |
      | email      | <email>      |
      | gender     | <gender>     |

    Examples:
      | first_name | last_name | email           | gender |
      | donald1     | duck1   | testtest1@test.com | male   |