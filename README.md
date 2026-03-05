# AppiumSauceDemoProject

An Android mobile test automation framework built with **Java**, **Appium**, **TestNG**, and **Cucumber BDD**, demonstrating the Page Object Model (POM) design pattern against the [Sauce Demo](https://www.saucedemo.com) web application running in Chrome on a physical Android device.

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
    │   │   └── SauceDemoLoginPage.java          # Page Object for login page
    │   ├── tests/
    │   │   └── SauceDemoLoginTest.java          # TestNG test cases
    │   ├── stepdefinitions/
    │   │   ├── Hooks.java                       # Cucumber driver lifecycle
    │   │   └── LoginSteps.java                  # Cucumber step definitions
    │   ├── runners/
    │   │   └── TestRunner.java                  # Cucumber test runner
    │   └── utils/
    │       └── ConfigReader.java                # Reads config.properties
    └── resources/
        ├── config.properties                    # Test data and URL config
        └── features/
            └── login.feature                    # BDD feature file
```

---

## ⚙️ Prerequisites

- Java JDK 17+
- Maven
- [Appium Server 2.x](https://appium.io)
- [UiAutomator2 Driver](https://github.com/appium/appium-uiautomator2-driver)
- ChromeDriver matching your device's Chrome version
- Android device with USB debugging enabled
- ADB installed and on PATH

### Install Appium and UiAutomator2 driver:
```bash
npm install -g appium
appium driver install uiautomator2
```

---

## 🔧 Configuration

Update `src/test/resources/config.properties` with your device details:

```properties
url=https://www.saucedemo.com
username=standard_user
password=secret_sauce
```

Update `BaseClass.java` with your device capabilities:

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

**Step 3 — Run TestNG tests:**
```bash
mvn clean test
```
Or right-click `SauceDemoLoginTest.java` in Eclipse → **Run As → TestNG Test**

**Step 4 — Run BDD Cucumber tests:**

Right-click `TestRunner.java` in Eclipse → **Run As → TestNG Test**

Or via Maven:
```bash
mvn clean test -Dtest=TestRunner
```

---

## 🧪 Test Cases

### TestNG Tests
| Test | Description |
|---|---|
| `testSuccessfulLogin` | Logs in with valid credentials and verifies inventory page loads |
| `testLoginWithInvalidCredentials` | Logs in with invalid credentials and verifies error message |

### BDD Cucumber Scenarios
| Scenario | Description |
|---|---|
| `Successful login with valid credentials` | Logs in with valid credentials and verifies inventory page loads |
| `Login with invalid credentials` | Logs in with invalid credentials and verifies error message |

---

## 🥒 BDD Feature File

Tests are written in plain English using Gherkin syntax, making them readable by non-technical stakeholders:

```gherkin
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

---

## 🏗 Design Patterns

- **Page Object Model (POM)** — locators and actions are encapsulated in page classes, keeping tests clean and readable
- **Base Class inheritance** — driver lifecycle managed in `BaseClass`, inherited by all test classes
- **BDD with Cucumber** — test scenarios written in Gherkin for readability by both technical and non-technical stakeholders
- **Hooks** — Cucumber `@Before` and `@After` hooks manage driver initialisation and teardown for BDD tests
- **Config Reader** — test data and URLs stored in `config.properties`, not hardcoded in tests
- **Explicit Waits** — all element interactions wait for visibility before acting, ensuring stability on mobile

---

## 📝 Notes

- This framework targets **Chrome browser on Android** (not a native app)
- After navigating to the URL, Appium switches from `NATIVE_APP` to `WEBVIEW` context automatically to interact with web elements
- ChromeDriver version must match the Chrome version installed on your device
- Cucumber HTML reports are generated at `target/cucumber-reports.html` after each test run

---

## 👤 Author

**kingster80** — [GitHub](https://github.com/kingster80)
