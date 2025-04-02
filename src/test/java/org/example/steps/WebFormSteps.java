package org.example.steps;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang.SystemUtils;
import org.example.page.IndexPage;
import org.example.page.SubmittedFormPage;
import org.example.page.WebFormPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebFormSteps {
    WebDriver driver;

    WebFormPage webFormPage;
    SubmittedFormPage submittedFormPage;
    IndexPage indexPage;

    @BeforeAll
    public static void setupFeature() throws IOException {
        // 64-bit Chrome driver has been used for testing on a Macbook Intel Core i7 with x86-64 architecture
        // 64-bit - Mac (OSX and Arm), Windows and Linux are supported drivers
        // Further work is to test in different browsers across different OS using a cloud service
        String chromeDriverPackage;
        final String systemArchitecture = System.getProperty("os.arch");
        final String projectPath = new File("./").getCanonicalPath();

        if ((SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_MAC)) {
            if(systemArchitecture.equals("x86_64")) {
                // Tested using this driver
                chromeDriverPackage =  "/chromedriver_mac_x64/chromedriver";
            } else {
                chromeDriverPackage = "/chromedriver_mac_arm64/chromedriver";
            }
        } else if (SystemUtils.IS_OS_WINDOWS) {
            chromeDriverPackage = "/chromedriver_win64/chromedriver.exe";
        } else if (SystemUtils.IS_OS_LINUX) {
            chromeDriverPackage = "/chromedriver_linux64/chromedriver";
        } else {
            throw new IllegalArgumentException("Unknown architecture " + systemArchitecture + " or operating system");
        }

        final String chromeDriverPath = projectPath + "/src/main/resources/drivers" + chromeDriverPackage;

        File file = new File(chromeDriverPath);
        final String chromeDriverFilePath = file.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
    }

    @Before
    public void setUpScenario() {
        driver = new ChromeDriver();

        webFormPage = new WebFormPage(driver);
        submittedFormPage = new SubmittedFormPage(driver);
        indexPage = new IndexPage(driver);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Given("^I open the Web form page$")
    public void INavigateToThePage() {
        final String webForm = "web-form.html";
        webFormPage.openWebFormPage();
        assertTrue("Expected the current page url to contain " + webForm, driver.getCurrentUrl().contains(webForm));
    }

    @And("^I verify the (.*) page title$")
    public void IVerifyThePageTitle(String expectedPageTitle) {
        assertEquals("Expected the page title to be " + expectedPageTitle, expectedPageTitle, webFormPage.pageTitle());
    }

    @When("^I submit the Web form$")
    public void ISubmitTheWebForm() {
        webFormPage.clickSubmitButton();

        // explicit wait - to wait for the submitted-form heading to be visible so url for page can be obtained
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(submittedFormPage.headingElementByCssSelector));
    }

    @Then("^I navigate to the (.*) page$")
    public void INavigateToThePage(String expectedUrlPageName) {
        assertTrue("Expected current page url to contain " + expectedUrlPageName, driver.getCurrentUrl().contains(expectedUrlPageName));
    }

    @And("^I see the (.*) target page heading$")
    public void ISeeThePageHeading(String expectedPageHeading) {
        assertEquals("Expected the page heading to be " + expectedPageHeading, expectedPageHeading, submittedFormPage.heading());
        assertTrue("The page heading " + expectedPageHeading + " is not displayed", submittedFormPage.headingDisplayed());
    }

    @And("^I see the (.*) target page confirmation message$")
    public void ISeeTheTargetPageConfirmationMessage(String expectedConfirmationMessage) {
        assertEquals("Expected the target page confirmation message to be " + expectedConfirmationMessage, expectedConfirmationMessage, submittedFormPage.confirmationMessage());
        assertTrue("The target page confirmation message " + expectedConfirmationMessage + " is not displayed", submittedFormPage.confirmationMessageDisplayed());
    }

    @When("^I enter (.*) for Text input$")
    public void IEnterForTextInput(String textInput) {
        webFormPage.setTextInput(textInput);
    }

    @And("^I verify (.*) for Password$")
    public void IEnterForPassword(String password) {
        webFormPage.setPassword(password);
        assertTrue("Expected the disabled input placeholder to be disabled", webFormPage.isPasswordType());
    }

    @And("I enter {string} for Textarea")
    public void IEnterForTextarea(String textArea) {
        webFormPage.setTextArea(textArea);
    }

    @When("^I verify the (.*) placeholder for Disabled input$")
    public void IVerifyThePlaceholderForDisabledInput(String expectedDisabledInput) {
        assertEquals("Expected the disabled input placeholder to be " + expectedDisabledInput, expectedDisabledInput, webFormPage.disabledInput());
        assertTrue("Expected the disabled input placeholder to be disabled", webFormPage.isDisabled());
    }

    @When("^I verify the (.*) value for Readonly input$")
    public void IVerifyTheValueForReadonlyInput(String expectedReadOnlyInput) {
        assertEquals("Expected the read only input value to be " + expectedReadOnlyInput, expectedReadOnlyInput, webFormPage.readonlyInput());
        assertTrue("Expected the read only input value to be read only", webFormPage.isReadonly());
    }

    @And("^I select (.*) for Dropdown select$")
    public void ISelectForDropdownSelect(String dropdownSelectValue) {
        if (!dropdownSelectValue.isBlank()) {
            webFormPage.selectDropDownValue(dropdownSelectValue);
        }
    }

    @And("^I enter (.*) for Dropdown datalist$")
    public void IEnterForDropdownDatalist(String dropdownDatalist) {
        webFormPage.selectDropDownDatalist(dropdownDatalist);
    }

    @And("^I choose a value of (.*) for Color picker$")
    public void IChooseAValueForColorPicker(String colorPicker) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.getElementsByName('my-colors')[0].value='" + colorPicker + "';");
    }

    @And("^I enter the date (.*) for Date picker$")
    public void IEnterTheDateForDatePicker(String datePicker) {
        webFormPage.enterDatalistDate(datePicker);
    }

    @And("^I choose (.*) for File input$")
    public void IChooseForFileInput(String expectedFileInput) throws IOException {
        File uploadFile = webFormPage.findUploadFile(expectedFileInput);
        assertTrue("The file " + expectedFileInput + " was not found in your /src/test/resources folder", uploadFile.exists());

        webFormPage.uploadFile(uploadFile);
    }

    @And("^I set the Example range to (.*)$")
    public void ISetTheExampleRange(String exampleRange) {
        ((JavascriptExecutor)driver).executeScript("$(arguments[0]).val(" + exampleRange + ").change()", webFormPage.exampleRangeElement);
    }

    @And("^I select the (Checked|Default) radio button to (on|off)$")
    public void ISelectTheRadioButtonTo(String radioButton, String selectedStatus) {
        webFormPage.selectDefaultOrCheckedRadiobutton(radioButton, selectedStatus);
    }

    @And("^I set the (Checked|Default) checkbox status to (on|off)$")
    public void ISetTheCheckboxStatus(String checkbox, String checkedStatus) {
        webFormPage.selectDefaultOrCheckedCheckbox(checkbox, checkedStatus);
    }

    @And("^I verify the target page url parameter string has pair values$")
    public void IVerifyTheTargetPageUrlParameterStringHasPairValues(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> expectedUrlParameterMap = data.get(0);

        String url = driver.getCurrentUrl();
        Map<String, String> urlParameterMap = getUrlParameters(url, StandardCharsets.UTF_8);

        assertTrue("The target page url " + url + " does not contain the correct values", urlParameterMap.entrySet().containsAll(expectedUrlParameterMap.entrySet()));
    }

    @When("^I click the Return to index link$")
    public void IClickTheReturnToIndexLink() {
        webFormPage.clickReturnToIndexLinkTextElement();
        // explicit wait - to wait for the index page web-form.html link to be visible
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(indexPage.webFormHtmlByLinkTextSelector));
    }

    @When("^I click the web-form.html index page link$")
    public void IClickTheWebFormHtmlLink() {
        indexPage.clickWebFormLinkTextElement();
        // explicit wait - to wait for the web form page Return to index kink to be visible
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(webFormPage.returnToIndexByLinkTextSelector));
    }

    public static Map<String, String> getUrlParameters(String url, Charset charset) {
        return Arrays.stream(url.split("\\?")[1].split("&"))
                .map(pair -> pair.split("=", 2))
                .collect(Collectors.toMap(
                        pair -> URLDecoder.decode(pair[0], charset),
                        pair -> pair.length > 1 ? URLDecoder.decode(pair[1], charset) : null)
                );
    }
}