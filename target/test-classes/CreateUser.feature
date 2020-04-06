Feature: Create a user in database

  Scenario Outline: Create a User
#    Given A user with valid access token
    When the user wants to create a record with given credentials
      | first_name | <first_name> |
      | last_name  | <last_name>  |
      | email      | <email>      |
      | gender     | <gender>     |
    And user submits the user data in "https://gorest.co.in/public-api/users"
    Then you should receive a "201" status code
    And response includes following body with info
      | first_name | <first_name> |
      | last_name  | <last_name>  |
      | email      | <email>      |
      | gender     | <gender>     |

    And delete test user

    Examples:
      | first_name | last_name | email           | gender |
      | arsens     | morins    | test11@test1.lv | male   |
#      | arsens     | morins    |                 | male   |
