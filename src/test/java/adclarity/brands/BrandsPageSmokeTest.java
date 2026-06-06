package adclarity.brands;

import adclarity.BaseTest;
import io.qameta.allure.Description;
import org.biscience.actions.BiScienceApp;
import org.testng.annotations.Test;

public class BrandsPageSmokeTest extends BaseTest {

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Brands page has correct title")
    public void brandPageTitleIsVisible() {
        var app = BiScienceApp.init();
        app.getActions().login().sidebar().goToBrands().verifyPageTitleVisible();
    }

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Search input is visible and returns results for Nike")
    public void searchInputShowsResults() {
        var app = BiScienceApp.init();
        app.getActions()
           .login()
           .sidebar()
           .goToBrands()
           .verifySearchVisible()
           .searchBrand("Nike")
           .verifySearchResultsVisible()
           .verifySearchResultContains("Nike");
    }

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Recent Brand reports section is visible")
    public void recentBrandReportsSectionIsVisible() {
        var app = BiScienceApp.init();
        app.getActions().login().sidebar().goToBrands().verifyRecentReportsTitleVisible();
    }

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Ask anything block is visible")
    public void askAnythingIsVisible() {
        var app = BiScienceApp.init();
        app.getActions().login().sidebar().goToBrands().verifyAskAnythingVisible();
    }

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Search Nike and verify brand report page")
    public void searchNikeAndOpenBrandReport() {
        var app = BiScienceApp.init();
        app.getActions()
           .login()
           .sidebar()
           .goToBrands()
           .verifySearchVisible()
           .searchBrand("Nike")
           .verifySearchResultContains("Nike")
           .selectBrandFromSearch("Nike")
           .verifyPageTitleVisible()
           .verifyBrandChipVisible("Nike")
           .verifyBrandBlockVisible("Nike");
    }

    @Test(groups = {"ALL", "BRANDS"}, dependsOnMethods = "searchNikeAndOpenBrandReport")
    @Description("Click Nike from recent brands and verify brand report page")
    public void openNikeFromRecentBrands() {
        var app = BiScienceApp.init();
        app.getActions()
           .login()
           .sidebar()
           .goToBrands()
           .verifyRecentReportCardExists("Nike")
           .clickRecentReportCard("Nike")
           .verifyPageTitleVisible()
           .verifyBrandChipVisible("Nike")
           .verifyBrandBlockVisible("Nike");
    }

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Nike brand report page has all selectors and widgets visible")
    public void nikeReportPageHasAllSelectorsAndWidgets() {
        var app = BiScienceApp.init();
        app.getActions()
           .login()
           .sidebar()
           .goToBrands()
           .searchBrand("Nike")
           .verifySearchResultContains("Nike")
           .selectBrandFromSearch("Nike")
           .verifyPageTitleVisible()
           .verifyTopSelectorsVisible()
           .verifyAllWidgetsVisible();
    }

    @Test(groups = {"ALL", "BRANDS"})
    @Description("Nike media coverage block shows publishers, campaigns and ads == 1. Fails intentionally to have something guaranteed failed")
    public void shouldFailNikeMediaCoverageHasData() {
        var app = BiScienceApp.init();
        app.getActions()
           .login()
           .sidebar()
           .goToBrands()
           .searchBrand("Nike")
           .selectBrandFromSearch("Nike")
           .verifyMediaCoverageMetrics(1, 1, 1);
    }

    @Test(groups = {"ALL", "BRANDS"}, dependsOnMethods = "searchNikeAndOpenBrandReport")
    @Description("Nike recent report card shows all values: name, date, category, duration, channel, country")
    public void nikeRecentReportCardHasAllValues() {
        var app = BiScienceApp.init();
        app.getActions()
           .login()
           .sidebar()
           .goToBrands()
           .getRecentReportCard("Nike")
           .verifyAllValuesPresent("Nike");
    }
}
