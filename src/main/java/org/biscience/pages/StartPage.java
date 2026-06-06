package org.biscience.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.biscience.pages.brands.BrandsPage;

public class StartPage extends BasePage {
    public static final String PAGE_PATH = "/ad-intelligence/start";
    private final Locator brandsMenuItem = page.locator("a").filter(new Locator.FilterOptions().setHasText("Brands"));
    public StartPage(Page page) {
        super(page);
    }

}
