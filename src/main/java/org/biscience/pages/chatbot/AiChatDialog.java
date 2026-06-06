package org.biscience.pages.chatbot;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.biscience.pages.BasePage;
import org.testng.Assert;

public class AiChatDialog extends BasePage {
    private final Locator container = page.locator("[data-unit='chatbot-container']");
    private final Locator title = page.locator("[data-unit='header-title']");
    private final Locator description = page.locator("[data-unit='header-description']");
    private final Locator infoPanelText = page.locator("[data-unit='info-panel-text']");
    private final Locator promptButtons = page.locator("[data-unit='prompt-button']");
    private final Locator inputTextarea = page.locator("[data-unit='textarea']");
    private final Locator submitButton = page.locator("[data-unit='submit-btn']");
    private final Locator closeButton = page.locator("[data-unit='close-button']");
    private final Locator historyTitle = page.locator("[data-unit='chatbot-history-title']");
    private final Locator historyItems = page.locator("[data-unit='history-group-item']");
    private final Locator newChatButton = page.locator("[data-unit='chatbot-history-new-button']");
    private final Locator submitFeedback = page.locator("text=Submit feedback");

    public AiChatDialog(Page page) {
        super(page);
    }

    @Step("Verify chat header content is visible")
    public AiChatDialog verifyHeaderVisible() {
        title.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        description.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        infoPanelText.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify suggestion prompts are visible")
    public AiChatDialog verifySuggestionsVisible() {
        promptButtons.first()
                     .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(promptButtons.count() > 0, "Expected at least one prompt suggestion");
        return this;
    }

    @Step("Verify input and submit button are visible")
    public AiChatDialog verifyInputVisible() {
        inputTextarea.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        submitButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify chat history panel is visible")
    public AiChatDialog verifyHistoryVisible() {
        historyTitle.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Close AI chat dialog")
    public AiChatDialog close() {
        closeButton.click();
        return this;
    }

    @Step("Type message: {message}")
    public AiChatDialog typeMessage(String message) {
        inputTextarea.fill(message);
        return this;
    }

    @Step("Submit message")
    public AiChatDialog submitMessage() {
        submitButton.click();
        return this;
    }

    @Step("Verify AI chat dialog is open")
    public AiChatDialog verifyDialogOpen() {
        container.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(container.getAttribute("class").contains("active"),
                "Chatbot container should have 'active' class");
        return this;
    }

    @Step("Verify AI chat dialog is closed")
    public AiChatDialog verifyDialogClosed() {
        page.waitForCondition(() -> !container.getAttribute("class").contains("active"));
        return this;
    }


    @Step("Click history item: {name}")
    public AiChatDialog clickHistoryItem(String name) {
        historyItems.filter(new Locator.FilterOptions().setHasText(name))
                    .first()
                    .click();
        return this;
    }

    @Step("Verify New chat button is visible")
    public AiChatDialog verifyNewChatButtonVisible() {
        newChatButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Verify Submit feedback button is visible")
    public AiChatDialog verifySubmitFeedbackVisible() {
        submitFeedback.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }
}