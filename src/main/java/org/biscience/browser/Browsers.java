package org.biscience.browser;

import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum Browsers {

    CHROMIUM("chromium"),
    FIREFOX("firefox"),
    WEBKIT("webkit");

    private final String value;

    Browsers(String value) {
        this.value = value;
    }

    public static Browsers fromEnv(String value) {
        return Arrays.stream(values())
                     .filter(b -> b.value.equalsIgnoreCase(value))
                     .findFirst()
                     .orElseGet(() -> {
                         LoggerFactory.getLogger(Browsers.class)
                                      .warn("Unknown browser '{}' — falling back to CHROMIUM",
                                              value);
                         return CHROMIUM;
                     });
    }

    public String getValue() {
        return value;
    }
}
