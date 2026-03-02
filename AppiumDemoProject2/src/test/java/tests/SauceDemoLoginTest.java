package tests;


import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseClass;
import pages.SauceDemoLoginPage;
import utils.ConfigReader;

public class SauceDemoLoginTest extends BaseClass {

    private SauceDemoLoginPage loginPage;
    

    @BeforeMethod
    public void initPage() {
        loginPage = new SauceDemoLoginPage(driver); // ✅ pass inherited driver
    }

    @Test
    public void testSuccessfulLogin() {
        loginPage.navigateTo();                          // loads page + switches to web context
        loginPage.enterUsername(ConfigReader.get("username"));
        loginPage.enterPassword(ConfigReader.get("password"));
        loginPage.clickLoginButton();

        WebElement inventory = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.id("inventory_container")
            ));

        Assert.assertTrue(inventory.isDisplayed(), "Inventory page should be visible after login");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        loginPage.navigateTo();
        loginPage.enterUsername("wrong_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLoginButton();

        Assert.assertEquals(
            loginPage.getErrorMessage(),
            "Epic sadface: Username and password do not match any user in this service"
        );
    }
}