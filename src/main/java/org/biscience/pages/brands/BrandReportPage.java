package org.biscience.pages.brands;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.biscience.ScreenshotSoftAssert;
import org.biscience.pages.BasePage;

public class BrandReportPage extends BasePage {

    // headers
    private final Locator pageTitle = page.locator("[data-unit='report-header-title']");
    private final Locator compareButton = page.locator("[data-unit='compare-button']");
    private final Locator entityChips = page.locator("group-list-entity .entity__title");

    // page name
    private final Locator brandHeaderBlock = page.locator("[data-unit='entity-header-content']");
    private final Locator brandNameTitle = brandHeaderBlock.locator(".title-container .title");

    // filters
    private final Locator channelSelector = page.locator(
            "channel-selector [data-unit='root-button']");
    private final Locator deviceSelector = page.locator("device-selector button");
    private final Locator periodSelector = page.locator("[data-unit='period-button']");
    private final Locator countrySelector = page.locator("[data-unit='country-button']");
    private final Locator hierarchySelector = page.locator(
            "brand-hierarchy-toggle [data-unit='menu-trigger-button']");
    private final Locator exportButton = page.locator("[data-userflow='common-export-button']")
                                             .first();

    // widgets
    private final Locator widgetBrandHeader = page.locator("report-header");
    private final Locator widgetInsights = page.locator("brand-insights");
    private final Locator widgetMediaCoverage = page.locator("media-coverage");
    private final Locator mediaCoverageWidget = page.locator("media-coverage");

    private final Locator widgetAdTypes = page.locator("widget-channels-distribution");
    private final Locator widgetAdBuyingMethods = page.locator("widget-transaction-methods")
                                                      .first();
    private final Locator widgetPerformance = page.locator("widget-performance-overview");
    private final Locator widgetExpenditureTrend = page.locator("widget-expenditure-trend");
    private final Locator widgetExpenditureBreak = page.locator("widget-expenditure-breakdown");
    private final Locator widgetTopAds = page.locator("widget-top-ads");
    private final Locator widgetTopCampaigns = page.locator("widget-top-campaigns");
    private final Locator widgetTopPublishers = page.locator("widget-top-advertisers-publishers");
    private final Locator widgetAdBuyingDetails = page.locator(
            "widget-transaction-methods-details");

    public BrandReportPage(Page page) {
        super(page);
    }

    @Step("Verify Brand report page title is visible")
    public BrandReportPage verifyPageTitleVisible() {
        pageTitle.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify brand chip '{brandName}' is visible near Compare button")
    public BrandReportPage verifyBrandChipVisible(String brandName) {
        compareButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        entityChips.filter(new Locator.FilterOptions().setHasText(brandName))
                   .first()
                   .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify brand block is visible for: {brandName}")
    public BrandReportPage verifyBrandBlockVisible(String brandName) {
        brandHeaderBlock.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        brandNameTitle.filter(new Locator.FilterOptions().setHasText(brandName))
                      .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify all top filter selectors are visible")
    public BrandReportPage verifyTopSelectorsVisible() {
        channelSelector.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        deviceSelector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        periodSelector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        countrySelector.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        hierarchySelector.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        exportButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify all widgets are present on the page")
    public BrandReportPage verifyAllWidgetsVisible() {
        widgetBrandHeader.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetInsights.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetMediaCoverage.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetAdTypes.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetAdBuyingMethods.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetPerformance.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetExpenditureTrend.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetExpenditureBreak.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetTopAds.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetTopCampaigns.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetTopPublishers.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        widgetAdBuyingDetails.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Get media coverage metric value for: {label}")
    private int getMediaCoverageValue(String label) {
        String raw = widgetMediaCoverage
                .locator(".metrics")
                .filter(new Locator.FilterOptions().setHasText(label))
                .locator(".metrics-value--count")
                .innerText()
                .trim();
        return parseMetricValue(raw);
    }

    private int parseMetricValue(String raw) {
        String cleaned = raw.trim().toUpperCase();
        if (cleaned.endsWith("K")) {
            return (int) (Double.parseDouble(cleaned.replace("K", "")) * 1_000);
        }
        if (cleaned.endsWith("M")) {
            return (int) (Double.parseDouble(cleaned.replace("M", "")) * 1_000_000);
        }
        return Integer.parseInt(cleaned.replaceAll("[^0-9]", ""));
    }

    @Step("Verify media coverage metrics are == {publishersValue,campaignsValue,adsValue}")
    public BrandReportPage verifyMediaCoverageMetrics(int publishersValue, int campaignsValue,
                                                      int adsValue) {
        int publishers = getMediaCoverageValue("publishers");
        int campaigns = getMediaCoverageValue("campaigns");
        int ads = getMediaCoverageValue("ads");

        ScreenshotSoftAssert soft = new ScreenshotSoftAssert();
        soft.assertTrue(publishers == publishersValue,
                "Publishers count expected == " + publishersValue + " but was: " + publishers);
        soft.assertTrue(campaigns == campaignsValue,
                "Campaigns count expected >= " + campaignsValue + " but was: " + campaigns);
        soft.assertTrue(ads == adsValue,
                "Ads count expected >= " + adsValue + " but was: " + ads);
        soft.assertAll();
        return this;
    }
}