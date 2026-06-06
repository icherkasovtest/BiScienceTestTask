package org.biscience.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.biscience.config.EnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BrowserContextProvider {

    private static final Logger log = LoggerFactory.getLogger(BrowserContextProvider.class);

    public BrowserContext createContext(Playwright playwright) {
        var browserType = EnvConfig.BROWSER.getValue();
        var launchOptions = new BrowserType.LaunchOptions().setHeadless(EnvConfig.HEADLESS)
                                                           .setArgs(List.of("--start-maximized"));

        var browser = BrowserPool.getInstance().acquire(browserType, playwright, launchOptions);

        var context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null).setIgnoreHTTPSErrors(true));
        context.setDefaultTimeout(30000);
        context.setDefaultNavigationTimeout(60000);

        log.debug("Created new BrowserContext for browser: {}", browserType);
        return context;
    }
}
