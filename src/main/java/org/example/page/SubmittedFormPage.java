package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SubmittedFormPage {

    public final static String headingElementLocator = "h1[class=display-6]";

    public By headingElementByCssSelector = new By.ByCssSelector(headingElementLocator);

    WebDriver driver;

    public SubmittedFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = headingElementLocator)
    public WebElement headingElement;

    @FindBy(css = "p[class=lead][id=message]")
    public WebElement confirmationMessageElement;

    public String pageTitle() {
        return driver.getTitle();
    }

    public String heading() {
        return headingElement.getText();
    }

    public boolean headingDisplayed() {
        return headingElement.isDisplayed();
    }

    public String confirmationMessage() {
        return confirmationMessageElement.getText();
    }

    public boolean confirmationMessageDisplayed() {
        return confirmationMessageElement.isDisplayed();
    }
}

