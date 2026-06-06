package org.biscience.actions;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.biscience.config.EnvConfig;
import org.biscience.pages.LoginPage;
import org.biscience.pages.StartPage;

public class AppActions {

    private final Page page;

    public AppActions(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    @Step("Login and open start page")
    public StartPage login() {
        new LoginPage(page).open().login(EnvConfig.USER_EMAIL, EnvConfig.USER_PASSWORD);
        return new StartPage(page);
    }

    @Step("Login and open start page with custom credentials")
    public StartPage login(String email, String password) {
        new LoginPage(page).open().login(email, password);
        return new StartPage(page);
    }
}
