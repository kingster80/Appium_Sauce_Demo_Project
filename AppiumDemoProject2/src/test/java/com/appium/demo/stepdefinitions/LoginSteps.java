package com.appium.demo.stepdefinitions;

import com.appium.demo.base.BaseClass;
import com.appium.demo.pages.SauceDemoLoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class LoginSteps {

    private SauceDemoLoginPage loginPage;

    @Before                                        // ✅ Cucumber @Before not TestNG
    public void initPage() {
        loginPage = new SauceDemoLoginPage(Hooks.driver); // ✅ get driver from Hooks
    }

    @Given("I am on the Sauce Demo login page")
    public void i_am_on_the_sauce_demo_login_page() {
        loginPage.navigateTo();
    }

    @When("I enter username {string}")
    public void i_enter_username(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("I should see the inventory page")
    public void i_should_see_the_inventory_page() {
        WebElement inventory = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.id("inventory_container")
            ));
        Assert.assertTrue(inventory.isDisplayed(), "Inventory page should be visible");
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMessage) {
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage);
    }
}