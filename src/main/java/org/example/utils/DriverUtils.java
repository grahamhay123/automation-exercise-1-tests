package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverUtils {
    public static WebDriver setWebDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(); // Selenium 4+ auto-detects driver paths
                break;
            case "firefox":
                driver = new FirefoxDriver(); // Selenium 4+ auto-detects driver paths
                break;
            case "edge":
                driver = new EdgeDriver(); // Selenium 4+ auto-detects driver paths
                break;
            case "safari":
                driver = new SafariDriver(); // Selenium 4+ auto-detects driver paths
                break;
           default:
                throw new IllegalArgumentException("Unsupported browser detected: " + browser);
        }

        driver.manage().window().maximize();

        return driver;
    }

}
