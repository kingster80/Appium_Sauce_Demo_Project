# AppiumSauceDemoProject

An Android mobile test automation framework built with **Java**, **Appium**, **TestNG**, and **Cucumber BDD**, demonstrating the Page Object Model (POM) design pattern against both the [Sauce Demo](https://www.saucedemo.com) web application in Chrome and the **Swag Labs native Android app**.

---

## 🛠 Tech Stack

| Tool | Version |
|---|---|
| Java | 17+ |
| Appium Java Client | 9.3.0 |
| Selenium | 4.25.0 |
| TestNG | 7.12.0 |
| Cucumber Java | 7.15.0 |
| Cucumber TestNG | 7.15.0 |
| Appium Server | 2.x |
| UiAutomator2 Driver | Latest |
| ChromeDriver | 145 |

---

## 📁 Project Structure

```
src/
└── test/
    ├── java/
    │   ├── base/
    │   │   └── BaseClass.java                  # Driver setup and teardown
    │   ├── pages/
    │   │   ├── SauceDemoLoginPage.java          # Page Object for web login page
    │   │   └── SwagLabsLoginPage.java           # Page Object for native app login
    │   ├── tests/
    │   │   └── SauceDemoLoginTest.java          # TestNG test cases
    │   ├── stepdefinitions/
    │   │   ├── Hooks.java                       # Cucumber driver lifecycle
    │   │   ├── LoginSteps.java                  # Web Cucumber step definitions
    │   │   └── AppLoginSteps.java               # App Cucumber step definitions
    │   ├── runners/
    │   │   ├── WebTestRunner.java               # Runs web tests only (@web tag)
    │   │   └── AppTestRunner.java               # Runs app tests only (@app tag)
    │   └── utils/
    │       └── ConfigReader.java                # Reads config.properties
    └── resources/
        ├── apps/                                # Place APK file here (see setup below)
        ├── config.properties                    # Test data and URL config
        └── features/
            ├── login.feature                    # Web BDD feature file (@web)
            └── app_login.feature                # Native app BDD feature file (@app)
```

---

## ⚙️ Prerequisites

- Java JDK 17+
- Maven
- [Appium Server 2.x](https://appium.io)
- [UiAutomator2 Driver](https://github.com/appium/appium-uiautomator2-driver)
- ChromeDriver matching your device's Chrome version (for web tests)
- Android device with USB debugging enabled
- ADB installed and on PATH

### Install Appium and UiAutomator2 driver:
```bash
npm install -g appium
appium driver install uiautomator2
```

---

## 📱 Native App Setup

The Swag Labs APK is not included in this repository. To run the native app tests:

1. Download the APK from the [Sauce Labs sample app releases page](https://github.com/saucelabs/sample-app-mobile/releases)
2. Download `Android.SauceLabs.Mobile.Sample.app.2.7.1.apk`
3. Place it in `src/test/resources/apps/`
4. Install it on your device via ADB:
```bash
adb -s YOUR_DEVICE_UDID install src/test/resources/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk
```

---

## 🔧 Configuration

Update `src/test/resources/config.properties`:

```properties
url=https://www.saucedemo.com
username=standard_user
password=secret_sauce
env=local
app.path=src/test/resources/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk
```

### Environment Options

| `env` value | Description |
|---|---|
| `local` | Physical device — Chrome browser (web tests) |
| `local-app` | Physical device — Swag Labs native app (app tests) |
| `ci` | GitHub Actions emulator — Chrome browser (set automatically by CI) |

Update `Hooks.java` with your device capabilities:

```java
options.setDeviceName("YOUR_DEVICE_NAME");
options.setUdid("YOUR_DEVICE_UDID");
options.setPlatformVersion("YOUR_ANDROID_VERSION");
options.setChromedriverExecutable("C:\\path\\to\\chromedriver.exe");
```

Find your device UDID by running:
```bash
adb devices
```

---

## ▶️ Running the Tests

**Step 1 — Start Appium server:**
```bash
appium --allow-insecure chromedriver_autodownload
```

**Step 2 — Connect your Android device via USB and authorise debugging**

### Running Web Tests

Set `env=local` in `config.properties`, then right-click `WebTestRunner.java` → **Run As → TestNG Test**

### Running Native App Tests

Set `env=local-app` in `config.properties`, then right-click `AppTestRunner.java` → **Run As → TestNG Test**

### Running via Maven
```bash
# Web tests only
mvn clean test -Dtest=WebTestRunner

# App tests only
mvn clean test -Dtest=AppTestRunner
```

---

## 🧪 Test Cases

### Web BDD Cucumber Scenarios (@web)
| Scenario | Description |
|---|---|
| `Successful login with valid credentials` | Logs in with valid credentials and verifies inventory page loads |
| `Login with invalid credentials` | Logs in with invalid credentials and verifies error message |

### Native App BDD Cucumber Scenarios (@app)
| Scenario | Description |
|---|---|
| `Successful login with valid credentials` | Logs in to Swag Labs app and verifies products page loads |
| `Login with invalid credentials` | Logs in with invalid credentials and verifies error message |

---

## 🥒 BDD Feature Files

### Web (`login.feature`)
```gherkin
@web
Feature: Sauce Demo Login

  Scenario: Successful login with valid credentials
    Given I am on the Sauce Demo login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see the inventory page

  Scenario: Login with invalid credentials
    Given I am on the Sauce Demo login page
    When I enter username "wrong_user"
    And I enter password "wrong_pass"
    And I click the login button
    Then I should see an error message "Epic sadface: Username and password do not match any user in this service"
```

### Native App (`app_login.feature`)
```gherkin
@app
Feature: Swag Labs App Login

  Scenario: Successful login with valid credentials
    Given I am on the Swag Labs app login screen
    When I enter app username "standard_user"
    And I enter app password "secret_sauce"
    And I tap the login button
    Then I should see the products page

  Scenario: Login with invalid credentials
    Given I am on the Swag Labs app login screen
    When I enter app username "wrong_user"
    And I enter app password "wrong_pass"
    And I tap the login button
    Then I should see the app error message "Username and password do not match any user in this service."
```

---

## 🏗 Design Patterns

- **Page Object Model (POM)** — locators and actions are encapsulated in page classes, keeping tests clean and readable
- **Base Class inheritance** — driver lifecycle managed in `BaseClass`, inherited by all test classes
- **BDD with Cucumber** — test scenarios written in Gherkin for readability by both technical and non-technical stakeholders
- **Hooks** — Cucumber `@Before` and `@After` hooks manage driver initialisation and teardown for BDD tests
- **Config Reader** — test data and URLs stored in `config.properties`, not hardcoded in tests
- **Explicit Waits** — all element interactions wait for visibility before acting, ensuring stability on mobile
- **Tagged runners** — separate `WebTestRunner` and `AppTestRunner` allow independent execution of web and app test suites

---

## 🔄 CI/CD

This project uses **GitHub Actions** to run web tests automatically on every push to `main`. The workflow:

- Spins up an Android emulator (API 29)
- Installs Appium and UiAutomator2
- Overrides `env` to `ci` automatically (regardless of local setting)
- Runs all `@web` tagged tests via Maven
- Tears down the emulator

---

## 📝 Notes

- Web tests target **Chrome browser on Android** — ChromeDriver version must match the Chrome version on your device
- Native app tests use **Accessibility IDs** as locators (`test-Username`, `test-Password`, `test-LOGIN`)
- After navigating to the URL in web tests, Appium switches from `NATIVE_APP` to `WEBVIEW` context automatically
- Cucumber HTML reports are generated at `target/cucumber-reports-web.html` and `target/cucumber-reports-app.html` after each test run

---

## 👤 Author

**kingster80** — [GitHub](https://github.com/kingster80)
