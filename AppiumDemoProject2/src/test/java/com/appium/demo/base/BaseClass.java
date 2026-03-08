package com.appium.demo.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import com.appium.demo.utils.ConfigReader;


public class BaseClass {

    protected AppiumDriver driver;

    @BeforeTest
    public void setup() {
        UiAutomator2Options options = new UiAutomator2Options();
        String env = ConfigReader.get("env");

        if (env.equals("local")) {
            // ✅ Physical device — local browser testing
            options.setPlatformName("Android");
            options.setPlatformVersion("16");
            options.setDeviceName("SM-F956U");
            options.setUdid("RFCX60NJVSF");
            options.setNewCommandTimeout(Duration.ofSeconds(60));
            options.withBrowserName("chrome");
            options.setChromedriverExecutable("C:\\chromedriver\\chromedriver.exe");
            options.setNoReset(true);

        } else if (env.equals("local-app")) {
            // ✅ Physical device — local native app testing
            options.setPlatformName("Android");
            options.setDeviceName("SM-F956U");
            options.setUdid("RFCX60NJVSF");
            options.setApp(System.getProperty("user.dir") + "/" + ConfigReader.get("app.path"));
            options.setAppPackage("com.swaglabsmobileapp");
            options.setAppActivity("com.swaglabsmobileapp.MainActivity");
            options.setAutomationName("UIAutomator2");
            options.setNewCommandTimeout(Duration.ofSeconds(60));

        } else {
            // ✅ CI emulator — browser testing
            options.setPlatformName("Android");
            options.setPlatformVersion("10");
            options.setDeviceName("test");
            options.setAvd("test");
            options.setNewCommandTimeout(Duration.ofSeconds(60));
            options.withBrowserName("chrome");
            options.setNoReset(true);
            options.setCapability("appium:chromedriverExecutable", "/usr/local/bin/chromedriver");
            options.setCapability("appium:uiautomator2ServerInstallTimeout", 60000);
        }

        try {
            driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options
            );
            System.out.println(">>> driver created: " + driver);
        } catch (MalformedURLException | URISyntaxException e) {
            System.out.println(">>> setup() FAILED with exception");
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}