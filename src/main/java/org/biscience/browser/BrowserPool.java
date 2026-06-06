package org.biscience.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BrowserPool {

    private static final Logger log = LoggerFactory.getLogger(BrowserPool.class);
    private static final BrowserPool INSTANCE = new BrowserPool();

    private final Map<String, ConcurrentLinkedQueue<Browser>> freePool = new ConcurrentHashMap<>();
    private final List<Browser> allBrowsers = Collections.synchronizedList(new ArrayList<>());

    private BrowserPool() {
        Runtime.getRuntime()
               .addShutdownHook(new Thread(this::shutdownAll, "browser-pool-shutdown"));
    }

    public static BrowserPool getInstance() {
        return INSTANCE;
    }

    public Browser acquire(String browserType, Playwright playwright,
                           BrowserType.LaunchOptions options) {
        var queue = freePool.computeIfAbsent(browserType, k -> new ConcurrentLinkedQueue<>());

        Browser browser;
        while ((browser = queue.poll()) != null) {
            if (isHealthy(browser)) {
                log.debug("[BrowserPool] Reusing {} browser. Free in pool: {}", browserType,
                        queue.size());
                return browser;
            }
            log.warn("[BrowserPool] Discarding unhealthy {} browser", browserType);
            closeSilently(browser);
            allBrowsers.remove(browser);
        }

        log.info("[BrowserPool] Launching new {} browser. Total: {}", browserType,
                allBrowsers.size() + 1);
        var newBrowser = launch(browserType, playwright, options);
        allBrowsers.add(newBrowser);
        return newBrowser;
    }

    public void release(String browserType, Browser browser) {
        if (isHealthy(browser)) {
            freePool.computeIfAbsent(browserType, k -> new ConcurrentLinkedQueue<>())
                    .offer(browser);
            log.debug("[BrowserPool] Returned {} browser to pool", browserType);
        } else {
            log.warn("[BrowserPool] Unhealthy browser returned — discarding");
            closeSilently(browser);
            allBrowsers.remove(browser);
        }
    }

    private Browser launch(String browserType, Playwright playwright,
                           BrowserType.LaunchOptions options) {
        return switch (browserType) {
            case "chromium" -> playwright.chromium().launch(options);
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> throw new IllegalArgumentException("Unknown browser type: " + browserType);
        };
    }

    private boolean isHealthy(Browser browser) {
        try {
            return browser != null && browser.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    private void closeSilently(Browser browser) {
        try {
            browser.close();
        } catch (Exception e) {
            log.warn("[BrowserPool] Failed to close browser: {}", e.getMessage());
        }
    }

    private void shutdownAll() {
        log.info("[BrowserPool] Shutting down {} browser(s)", allBrowsers.size());
        allBrowsers.forEach(this::closeSilently);
        allBrowsers.clear();
    }
}
