package adclarity;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.biscience.browser.BrowserPool;
import org.biscience.testcontext.ContextData;
import org.biscience.testcontext.TestContext;
import org.biscience.testcontext.TestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.ByteArrayInputStream;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        takeScreenshotOnFailure(result);
        closeContext();
        releaseBrowser();
        TestContextHolder.clear();
    }

    private void takeScreenshotOnFailure(ITestResult result) {
        if (result.isSuccess()) return;
        var ctx = TestContextHolder.get();
        if (ctx == null) return;
        Page page = ctx.get(ContextData.PAGE);
        if (page == null) return;
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment(
                    "Screenshot on failure — " + result.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );
        } catch (Exception e) {
            log.warn("Failed to take screenshot: {}", e.getMessage());
        }
    }

    private void closeContext() {
        TestContext ctx = TestContextHolder.get();
        if (ctx == null) return;
        BrowserContext context = ctx.get(ContextData.BROWSER_CONTEXT);
        if (context != null) {
            try {
                context.close();
            } catch (Exception e) {
                log.warn("Failed to close context: {}", e.getMessage());
            }
        }
    }

    private void releaseBrowser() {
        TestContext ctx = TestContextHolder.get();
        if (ctx == null) return;
        Browser browser = ctx.get(ContextData.BROWSER);
        String browserName = ctx.get(ContextData.BROWSER_NAME);
        if (browser != null && browserName != null) {
            BrowserPool.getInstance().release(browserName, browser);
        }
    }
}