package org.biscience.pages.brands;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.biscience.pages.BasePage;

public class BrandsPage extends BasePage {

    private final Locator pageTitle = page.locator(".title")
                                          .filter(new Locator.FilterOptions().setHasText("Brands"))
                                          .first();
    private final Locator searchInput = page.locator("[data-unit='search-input']");
    private final Locator recentReportsTitle = page.locator(".title")
                                                   .filter(new Locator.FilterOptions().setHasText(
                                                           "Recent Brand reports"));
    private final Locator askAnythingButton = page.locator("[data-unit='input-control']");
    private final Locator searchDropdown = page.locator(
            "div[role='listbox'].mat-mdc-autocomplete-panel");
    private final Locator reportCards = page.locator("report-card");

    public BrandsPage(Page page) {
        super(page);
    }

    @Step("Verify page title is visible")
    public BrandsPage verifyPageTitleVisible() {
        pageTitle.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify search input is visible")
    public BrandsPage verifySearchVisible() {
        searchInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify Recent Brand reports section is visible")
    public BrandsPage verifyRecentReportsTitleVisible() {
        recentReportsTitle.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify Ask anything block is visible and clickable")
    public BrandsPage verifyAskAnythingVisible() {
        askAnythingButton.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator("[data-unit='textarea']").click();
        return this;
    }

    @Step("Search for brand: {brandName}")
    public BrandsPage searchBrand(String brandName) {
        searchInput.click();
        searchInput.fill(brandName);
        return this;
    }

    @Step("Verify search results are visible")
    public BrandsPage verifySearchResultsVisible() {
        searchDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify search results contain: {brandName}")
    public BrandsPage verifySearchResultContains(String brandName) {
        page.locator("mat-option, .mat-mdc-option")
            .filter(new Locator.FilterOptions().setHasText(brandName))
            .first()
            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    public ReportCard getRecentReportCard(String brandName) {
        return new ReportCard(
                reportCards.filter(new Locator.FilterOptions().setHasText(brandName)).first()
        );
    }

    @Step("Get recent report cards count")
    public int getRecentReportCardsCount() {
        return reportCards.count();
    }

    public BrandsPage verifyRecentReportCardExists(String brandName) {
        getRecentReportCard(brandName).brandName()
                                      .waitFor(new Locator.WaitForOptions().setState(
                                              WaitForSelectorState.VISIBLE));
        return this;
    }

    public BrandReportPage clickRecentReportCard(String brandName) {
        getRecentReportCard(brandName).click();
        return new BrandReportPage(page);
    }

    @Step("Select brand from search dropdown: {brandName}")
    public BrandReportPage selectBrandFromSearch(String brandName) {
        page.locator("mat-option")
            .filter(new Locator.FilterOptions().setHasText(brandName))
            .filter(new Locator.FilterOptions().setHasNotText(
                    ">")) // exclude sub-brand results like "SHIFT UP > Nikke"
            .first()
            .click();
        return new BrandReportPage(page);
    }

}
