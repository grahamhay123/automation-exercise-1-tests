package org.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IndexPage {

    public final static String webFormHtml = "web-form.html";

    public By webFormHtmlByLinkTextSelector = new By.ByLinkText(webFormHtml);

    WebDriver driver;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "web-form.html")
    public WebElement webFormLinkTextElement;

    public boolean webFormLinkTextElementIsDisplayed() {
        return webFormLinkTextElement.isDisplayed();
    }

    public void clickWebFormLinkTextElement(){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(webFormLinkTextElement)).click();
    }
}
