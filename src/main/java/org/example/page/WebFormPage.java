package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;

public class WebFormPage {

    public final static String exampleRangeElementLocator = "input[type='range'][name='my-range']";

    public By exampleRangeElementByCssSelector = new By.ByCssSelector(exampleRangeElementLocator);

    public final static String returnToIndex = "Return to index";

    public By returnToIndexByLinkTextSelector = new By.ByLinkText(returnToIndex);

    WebDriver driver;

    public WebFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "my-text-id")
    public WebElement textInputElement;

    @FindBy(name = "my-password")
    public WebElement passwordElement;

    @FindBy(name = "my-textarea")
    public WebElement textAreaElement;

    @FindBy(name = "my-disabled")
    public WebElement disabledInputElement;

    @FindBy(name = "my-readonly")
    public WebElement readonlyInputElement;

    @FindBy(name = "my-select")
    public WebElement dropdownSelectElement;

    @FindBy(name = "my-datalist")
    public WebElement dropdownDatalistElement;

    @FindBy(name = "my-colors")
    public WebElement colorPickerElement;

    @FindBy(name = "my-date")
    public WebElement datePickerElement;

    @FindBy(name = "my-file")
    public WebElement fileInputElement;

    @FindBy(css = exampleRangeElementLocator)
    public WebElement exampleRangeElement;

    @FindBy(css = "input[name='my-radio'][id='my-radio-1']")
    public WebElement checkedRadioButtonElement;

    @FindBy(css = "input[name='my-radio'][id='my-radio-2']")
    public WebElement defaultRadioButtonElement;

    @FindBy(name = "input[name='my-check'][id='my-check-1']")
    public WebElement checkedCheckboxElement;

    @FindBy(name = "input[name='my-check'][id='my-check-2']")
    public WebElement defaultCheckboxElement;

    @FindBy(linkText = "Return to index")
    public WebElement returnToIndexLinkTextElement;

    @FindBy(css = ".btn.btn-outline-primary.mt-3")
    public WebElement submitButtonElement;


    public void setTextInput(String textInput){
        textInputElement.sendKeys(textInput);
    }

    public void setPassword(String password){
        passwordElement.sendKeys(password);
    }

    public boolean isPasswordType(){
        return passwordElement.getDomAttribute("type").equals("password");
    }

    public void setTextArea(String textArea){
        textAreaElement.sendKeys(textArea);
    }

    public String disabledInput(){
        return disabledInputElement.getDomAttribute("placeholder");
    }

    public boolean isDisabled(){
        return disabledInputElement.getDomAttribute("disabled").equals("true");
    }

    public String readonlyInput(){
        return readonlyInputElement.getDomAttribute("value");
    }

    public boolean isReadonly(){
        return readonlyInputElement.getDomAttribute("readonly").equals("true");
    }

    public void clickSubmitButton(){
        submitButtonElement.click();
    }

    public String pageTitle() {
        return driver.getTitle();
    }

    public void selectDropDownValue(String dropdownSelectValue){
        new Select(dropdownSelectElement).selectByValue(dropdownSelectValue);
    }

    public void selectDropDownDatalist(String dropdownDatalist){
        dropdownDatalistElement.sendKeys(dropdownDatalist);
    }

    public void enterDatalistDate(String dropdownDatalistDate){
        datePickerElement.sendKeys(dropdownDatalistDate);
    }

    public File findUploadFile(String expectedFileInput) throws IOException {
        String projectPath = new File("./").getCanonicalPath();
        // suffix with user home
        // upload file {projectPath}/src/test/resources/{expectedFileInput}
        return new File(projectPath + "/src/test/resources/" + expectedFileInput);
    }

    public void uploadFile(File uploadFile) {
        fileInputElement.sendKeys(uploadFile.getAbsolutePath());
    }

    public void selectDefaultOrCheckedCheckbox(String checkbox, String checkedStatus) {
        WebElement checkboxElement = checkbox.equals("Checked") ? checkedCheckboxElement : defaultCheckboxElement;
        if(checkedStatus.equals("on")) {
            if(!checkboxElement.isSelected()) {
                checkboxElement.click();
            }
        } else {
            if(checkboxElement.isSelected()) {
                checkboxElement.click();
            }
        }
    }

    public void selectDefaultOrCheckedRadiobutton(String radioButton, String selectedStatus) {
        WebElement radioButtonElement = radioButton.equals("Checked") ? checkedRadioButtonElement : defaultRadioButtonElement;
        if(selectedStatus.equals("on")) {
            if(!radioButtonElement.isSelected()) {
                radioButtonElement.click();
            }
        } else {
            if(radioButtonElement.isSelected()) {
                radioButtonElement.click();
            }
        }
    }

    public void clickReturnToIndexLinkTextElement(){
        returnToIndexLinkTextElement.click();
    }
}
