package org.biscience.pages.brands;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

public class ReportCard {

    private final Locator root;

    public ReportCard(Locator root) {
        this.root = root;
    }

    public Locator brandName() {
        return root.locator(".h-group.title-and-date .title");
    }

    public Locator date() {
        return root.locator(".title-info");
    }

    public Locator category() {
        return root.locator("associated-link .title").first();
    }

    public Locator duration() {
        return root.locator(".duration span");
    }

    public Locator channel() {
        return root.locator(".channel .title");
    }

    public Locator countryFlag() {
        return root.locator("[data-unit='country-flag-image']");
    }

    public Locator cardLink() {
        return root.locator("a.card-link");
    }

    @Step("Verify all values are present on report card")
    public ReportCard verifyAllValuesPresent(String brandName) {
        brandName().filter(new Locator.FilterOptions().setHasText(brandName))
                   .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        date().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        category().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        duration().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        channel().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        countryFlag().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Click report card link")
    public void click() {
        cardLink().click();
    }
}
