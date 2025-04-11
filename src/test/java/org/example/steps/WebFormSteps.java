package org.example.steps;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.page.IndexPage;
import org.example.page.SubmittedFormPage;
import org.example.page.WebFormPage;
import org.example.utils.DriverUtils;
import org.openqa.selenium.WebDriver;
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

    @Before
    public void setUpScenario() {
        driver = DriverUtils.setWebDriver("safari");

        webFormPage = new WebFormPage(driver);
        submittedFormPage = new SubmittedFormPage(driver);
        indexPage = new IndexPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("^I visit the Web form page$")
    public void IVisitTheWebFormPage() {
        webFormPage.openWebFormPage();
    }

    @And("^I verify the (.*) page title$")
    public void IVerifyThePageTitle(String expectedPageTitle) {
        assertEquals("Expected the page title to be " + expectedPageTitle, expectedPageTitle, webFormPage.pageTitle());
    }

    @When("^I submit the Web form$")
    public void ISubmitTheWebForm() {
        webFormPage.clickSubmitButton();
    }

    @Then("^I navigate to the (.*) page$")
    public void INavigateToThePage(String expectedPageTitle) {
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.titleIs(expectedPageTitle));
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
        webFormPage.chooseColorPickerValue(colorPicker);
    }

    @And("^I enter the date (.*) for Date picker$")
    public void IEnterTheDateForDatePicker(String datePicker) {
        webFormPage.enterDatePickerDate(datePicker);
    }

    @And("^I choose (.*) for File input$")
    public void IChooseForFileInput(String expectedFileInput) throws IOException {
        File uploadFile = webFormPage.findUploadFile(expectedFileInput);
        assertTrue("The file " + expectedFileInput + " was not found in your /src/test/resources folder", uploadFile.exists());

        webFormPage.uploadFile(uploadFile);
    }

    @And("^I set the Example range to (.*)$")
    public void ISetTheExampleRange(String exampleRange) {
        webFormPage.setExampleRange(exampleRange);
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
    }

    @When("^I click the web-form.html index page link$")
    public void IClickTheWebFormHtmlLink() {
        indexPage.clickWebFormLinkTextElement();
    }

    public static Map<String, String> getUrlParameters(String url, Charset charset) {
        return Arrays.stream(url.split("\\?")[1].split("&"))
                .map(pair -> pair.split("=", 2))
                .collect(Collectors.toMap(
                        pair -> URLDecoder.decode(pair[0], charset),
                        pair -> pair.length > 1 ? URLDecoder.decode(pair[1], charset) : "")
                );
    }
}