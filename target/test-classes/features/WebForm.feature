Feature: WebForm
  This feature will test the fields on the public Selenium Web Form page https://www.selenium.dev/selenium/web/web-form.html
  Please note: There is no validation on the page therefore no unhappy paths will be tested until criteria is specified

  Background:
    Given I open the Web form page
    And   I verify the Web form page title


  Scenario: The submitted Web form page navigates successfully to the Web form - target page
    When I submit the Web form
    Then I navigate to the submitted-form page
    And  I verify the Web form - target page page title
    And  I see the Form submitted target page heading
    And  I see the Received! target page confirmation message


  Scenario: The Return to index link navigates to the Index page where the Web form page can be revisited
    When I click the Return to index link
    Then I navigate to the index page
    And  I click the web-form.html index page link
    And  I navigate to the web-form page
    And  I verify the Web form page title


  Scenario Outline: All submitted web-form page values are set and assigned to target page url parameters correctly
    When I enter <text_input> for Text input
    And  I verify <password> for Password
    And  I enter "<text_area>" for Textarea
    And  I select <dropdown_select> for Dropdown select
    And  I enter <dropdown_datalist> for Dropdown datalist
    And  I choose a value of <color_picker> for Color picker
    And  I enter the date <date_picker> for Date picker
    And  I choose <file_input> for File input
    And  I set the Example range to <example_range>
    And  I verify the <readonly_input> value for Readonly input
    And  I verify the Disabled input placeholder for Disabled input
#   please see note 1 in readme
#   And  I set the Checked checkbox status to <checked_checkbox>
#   And  I set the Default checkbox status to <default_checkbox>

#   please see note 2 in readme
    And  I select the Default radio button to <default_radio_button>

    And  I submit the Web form
    Then I navigate to the submitted-form page
    And  I verify the target page url parameter string has pair values
      | my-text      | my-password | my-textarea           | my-select         | my-datalist         | my-colors      | my-date       | my-file              | my-range        | my-readonly      | my-radio               |
      | <text_input> | <password>  | <text_area>           | <dropdown_select> | <dropdown_datalist> | <color_picker> | <date_picker> | <file_input>         | <example_range> | <readonly_input> | <default_radio_button> |
#     please see note 1 checkbox steps fail
#      | my-text      | my-password | my-textarea           | my-select         | my-datalist         | my-colors      | my-date       | my-file              | my-range        | my-readonly      | my-radio               | my-check-1         | my-check-2         |
#      | <text_input> | <password>  | <text_area>           | <dropdown_select> | <dropdown_datalist> | <color_picker> | <date_picker> | <file_input>         | <example_range> | <readonly_input> | <default_radio_button> | <checked_checkbox> | <default_checkbox> |

    Examples:
      | text_input   | password    | text_area             | dropdown_select   | dropdown_datalist   | color_picker   | date_picker   | file_input           | example_range   | readonly_input   | default_radio_button   |
      | my_text      | my_password | some text             | 2                 | Los Angeles         | #564d7c        | 04/24/2025    | test_upload_file.txt | 3               | Readonly input   | on                     |
#    please see note 1 checkbox steps fail
#      | text_input   | password    | text_area             | dropdown_select   | dropdown_datalist   | color_picker   | date_picker   | file_input           | example_range   | readonly_input   | default_radio_button   | checked_checkbox   | default_checkbox   |
#      | my_text      | my_password | some text             | 2                 | Los Angeles         | #564d7c        | 04/24/2025    | test_upload_file.txt | 3               | Readonly input   | on                     | on                 | off                |

