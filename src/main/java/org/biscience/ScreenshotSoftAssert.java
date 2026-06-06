package org.biscience;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.biscience.testcontext.ContextData;
import org.biscience.testcontext.TestContext;
import org.biscience.testcontext.TestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScreenshotSoftAssert extends SoftAssert {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotSoftAssert.class);
    private final List<Map.Entry<String, byte[]>> pendingScreenshots = new ArrayList<>();

    @Override
    protected void doAssert(IAssert<?> assertCommand) {
        try {
            super.doAssert(assertCommand);
        } catch (AssertionError e) {
            captureScreenshot(assertCommand.getMessage());
            throw e;
        }
    }

    @Override
    public void fail(String message) {
        captureScreenshot(message);
        super.fail(message);
    }

    @Override
    public void assertAll() {
        try {
            super.assertAll();
            pendingScreenshots.clear();
        } catch (AssertionError e) {
            attachPendingScreenshots();
            throw e;
        }
    }

    private void captureScreenshot(String message) {
        TestContext ctx = TestContextHolder.get();
        if (ctx == null) return;
        Page page = ctx.get(ContextData.PAGE);
        if (page == null) return;
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String label = message != null ? message : "Soft assert #" + (pendingScreenshots.size() + 1);
            pendingScreenshots.add(Map.entry(label, screenshot));
        } catch (Exception e) {
            log.warn("Failed to capture soft assert screenshot: {}", e.getMessage());
        }
    }

    private void attachPendingScreenshots() {
        for (int i = 0; i < pendingScreenshots.size(); i++) {
            Map.Entry<String, byte[]> entry = pendingScreenshots.get(i);
            try {
                Allure.addAttachment(
                        "Soft assert [" + (i + 1) + "] — " + entry.getKey(),
                        "image/png",
                        new ByteArrayInputStream(entry.getValue()),
                        "png"
                );
            } catch (Exception e) {
                log.warn("Failed to attach soft assert screenshot: {}", e.getMessage());
            }
        }
        pendingScreenshots.clear();
    }
}
