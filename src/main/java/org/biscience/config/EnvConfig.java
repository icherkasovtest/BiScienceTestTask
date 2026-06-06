package org.biscience.config;

import org.biscience.browser.Browsers;

public class EnvConfig {

    public static final String USER_EMAIL = resolve("USER_EMAIL", "fahosev830@nriza.com");
    public static final String USER_PASSWORD = resolve("USER_PASSWORD", ":3I{*SK0Le.6");
    public static final String ENV = resolve("ENV", "dev");
    public static final int THREAD_COUNT = Integer.parseInt(resolve("THREAD_COUNT", "4"));
    public static final String GROUP = resolve("GROUP", "ALL");
    public static final Browsers BROWSER = Browsers.fromEnv(resolve("BROWSER", "chromium"));
    public static final boolean HEADLESS = Boolean.parseBoolean(resolve("HEADLESS", "false"));

    private static String resolve(String key, String defaultValue) {
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isBlank()) return envValue;

        String propValue = System.getProperty(key);
        if (propValue != null && !propValue.isBlank()) return propValue;

        return defaultValue;
    }

    public static String getBaseUrl() {
        return switch (ENV.toLowerCase()) {
            case "dev" -> "https://stg-ui.adcint.com";
            case "staging" -> "https://stg-ui.adcint.com";
            case "prod" -> "https://stg-ui.adcint.com";
            default -> throw new IllegalArgumentException("Unknown ENV: " + ENV);
        };
    }
}