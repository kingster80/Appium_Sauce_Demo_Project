package com.appium.demo.stepdefinitions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    public static AppiumDriver driver;

    @Before
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setPlatformVersion("16");
        options.setDeviceName("SM-F956U");
        options.setUdid("RFCX60NJVSF");
        options.setNewCommandTimeout(Duration.ofSeconds(60));
        options.withBrowserName("chrome");
        options.setChromedriverExecutable("C:\\chromedriver\\chromedriver.exe");
        options.setNoReset(true);

        try {
            driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options
            );
            System.out.println(">>> driver created: " + driver);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
