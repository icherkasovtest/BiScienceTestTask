package org.biscience.browser;

import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaywrightHolder {

    private static final Logger log = LoggerFactory.getLogger(PlaywrightHolder.class);
    private static final List<Playwright> instances = Collections.synchronizedList(
            new ArrayList<>());
    private static final ThreadLocal<Playwright> threadLocal = new ThreadLocal<>();

    public static Playwright getOrCreate() {
        if (threadLocal.get() == null) {
            Playwright pw = Playwright.create();
            threadLocal.set(pw);
            instances.add(pw);
        }
        return threadLocal.get();
    }

    public static void closeAll() {
        log.info("[PlaywrightHolder] Closing {} Playwright instance(s)", instances.size());
        instances.forEach(pw -> {
            try {
                pw.close();
            } catch (Exception ignored) {
            }
        });
        instances.clear();
    }
}
