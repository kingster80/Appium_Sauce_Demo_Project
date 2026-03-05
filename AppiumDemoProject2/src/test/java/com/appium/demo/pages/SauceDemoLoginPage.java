package com.appium.demo.pages;

import java.util.Set;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import com.appium.demo.utils.ConfigReader;

public class SauceDemoLoginPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public SauceDemoLoginPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateTo() {
        driver.get(ConfigReader.get("url"));
        switchToWebContext();
    }

    private void switchToWebContext() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            Set<String> contexts = ((SupportsContextSwitching) driver).getContextHandles();
            System.out.println(">>> Available contexts: " + contexts);
            return contexts.size() > 1;
        });

        SupportsContextSwitching contextDriver = (SupportsContextSwitching) driver;
        for (String context : contextDriver.getContextHandles()) {
            if (context.contains("WEBVIEW")) {
                contextDriver.context(context);
                System.out.println(">>> Switched to context: " + context);
                break;
            }
        }
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }
}