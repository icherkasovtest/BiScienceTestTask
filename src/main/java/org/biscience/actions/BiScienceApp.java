package org.biscience.actions;

import com.microsoft.playwright.Page;
import org.biscience.browser.BrowserContextProvider;
import org.biscience.browser.PlaywrightHolder;
import org.biscience.config.EnvConfig;
import org.biscience.testcontext.ContextData;
import org.biscience.testcontext.TestContextHolder;

public class BiScienceApp {

    private final Page page;
    private final AppActions actions;

    private BiScienceApp(Page page) {
        this.page = page;
        this.actions = new AppActions(page);
    }

    public static BiScienceApp init() {
        if (TestContextHolder.get() == null) {
            TestContextHolder.init();
        }
        if (PlaywrightHolder.getOrCreate() == null) {
            PlaywrightHolder.getOrCreate();
        }

        var contextProvider = new BrowserContextProvider();
        var context = contextProvider.createContext(PlaywrightHolder.getOrCreate());
        var page = context.newPage();

        TestContextHolder.get().put(ContextData.BROWSER, context.browser());
        TestContextHolder.get().put(ContextData.BROWSER_NAME, EnvConfig.BROWSER.getValue());
        TestContextHolder.get().put(ContextData.BROWSER_CONTEXT, context);
        TestContextHolder.get().put(ContextData.PAGE, page);

        return new BiScienceApp(page);
    }

    public AppActions getActions() {
        return actions;
    }

    public Page getPage() {
        return page;
    }
}