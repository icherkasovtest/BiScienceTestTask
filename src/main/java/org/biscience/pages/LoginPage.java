package org.biscience.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.biscience.config.EnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {
    public static final String PAGE_PATH = "/login";
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private final Locator emailInput = page.locator("#email");
    private final Locator passwordInput = page.locator("#password");
    private final Locator submitButton = page.locator("button.submit-button");

    public LoginPage(Page page) {
        super(page);
    }

    @Step("Open login page")
    public LoginPage open() {
        page.navigate(EnvConfig.getBaseUrl() + PAGE_PATH);
        return this;
    }

    @Step("Login with email {email}")
    public void login(String email, String password) {
        log.info("Logging in as {}", email);
        emailInput.fill(email);
        passwordInput.fill(password);
        submitButton.click();
    }
}