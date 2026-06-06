package org.biscience.pages.sidebar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.biscience.pages.brands.BrandsPage;
import org.biscience.pages.chatbot.AiChatDialog;

public class SidebarNav {
    private final Page page;
    private final Locator askAnythingButton;
    private final Locator brands;

    public SidebarNav(Page page) {
        this.page = page;
        this.askAnythingButton = page.locator("a.activate-chatbot-button");
        this.brands = page.locator("[data-userflow='sidebar-menu-item-title']")
                          .filter(new Locator.FilterOptions().setHasText("Brands"));
    }

    @Step("Open AI chatbot from sidebar")
    public AiChatDialog openAiChat() {
        askAnythingButton.click();
        return new AiChatDialog(page);
    }

    @Step("Navigate to Brands from sidebar")
    public BrandsPage goToBrands() {
        brands.click();
        return new BrandsPage(page);
    }

}
