# automation-exercise-1-tests

After cloning this project to run the tests please navigate to the project folder 'automation-exercise-1' in a terminal window.

It uses a Maven build and the tests can be run with - mvn clean test

The project build can also be performed and the feature file run from within IntelliJ (or other IDE).

The maven command to run the tests will generate a Cucumber report here -

automation-exercise-1/target/cucumber-reports.html

The tests currently use a chrome driver so tests need to be performed using a Chrome browser installed until other drivers can be added.

Testing was performed on a macbook pro x86_64 version (not arm64)

Chrome Browser Version 134.0.6998.166 (Official Build) (x86_64)


**Please note:**

Confirmation needed on Checkbox parameter functionality in generated url.

The two checkboxes with name 'my-check' duplicate the same param with the same assignment ‘on’ in the generated url

* if both checkboxes are checked gives the same duplicate assignment '&my-check=on&my-check=on'
* if only one of either checkbox is checked then '&my-check=on' appears once in the generated url so it cannot be determined which one was selected
* if neither checkbox is checked then 'my-check' param is not in the generated url Neither checkbox has a ‘value’ in html but do have different id's 'my-check-1' and 'my-check-2'.

The two test steps below are currently commented out as they are designed to generate url parameters using their unique id as parameter name where

* if both checkboxes are checked gives '&my-check-1=on&my-check-2=on'
* if any one checkbox is selected gives '&my-check-1=on&my-check-2=off' (or '&my-check-1=off&my-check-2=on')
* if neither checkbox is selected gives '&my-check-1=off&my-check-2=off'

however they can be changed to comma separated assignments using id where
* if both checkboxes are checked gives '&my-check=my-check-1,my-check-2'
* if any one checkbox is selected gives '&my-check=my-check-1' (or -2')
* if neither checkbox is selected gives '&my-check='
* also they can be changed if unique 'values' are introduced to the 'my-check' checkbox html to use those values instead of id's '&my-check=uniqueVal1&my-check=uniqueVal2' or '&my-check=uniqueVal1,uniqueVal2'

_And  I set the Checked checkbox status to <checked_checkbox>
And  I set the Default checkbox status to <default_checkbox>_


The selection of either Checked or Default radio button adds &my-radio=on to the url parameters so a false positive pass because it does not indicate which radio was selected

_And I select the Default radio button to <default_radio_button>_


Future work:

1. If time had allowed i would have liked to introduce a class to convert blank cells in the cucumber table such that values from Datatables can be returned as "" instead of null values. This would have allowed blank cells stored in a Map to be compared directly with url parameters in the submitted form whose decoded values from the url are "".
2. I set up an account with lambdatest hoping to test across different platforms as the project has chromedrivers for Chrome for Mac OSX, Windows, Linux. In time drivers can be introduced and tested for Firefox, IE and other operating systems. There is also Cloud Selenium Grid could be used for this purpose.
3. There was no time to add API tests
4. Numerous ambiguities were discovered during testing though the 3 scenarios pass. Ther is likely to be a false positive on the radio button test for url params (see 2.). The checkbox design for url params is also ambiguous as is the labelling of them on the form as Default and Checked (see 1.). Confirmation needed of the presence of my-hidden unassigned in url params. my-disabled not passed in url param list worth noting.
5. More time was needed to handle different ways in which to interpret different platforms behaviour for newlines due to the inclusion of a multiline input field. /r/n on Windows and /n on Mac and Linux. These can cause issues when decoding the url to break out parameters as the browser may have /r/n even though passed /n from the Cucumber datatable