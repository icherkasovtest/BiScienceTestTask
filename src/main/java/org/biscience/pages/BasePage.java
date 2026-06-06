package org.biscience.pages;

import com.microsoft.playwright.Page;
import org.biscience.pages.sidebar.SidebarNav;

public abstract class BasePage {

    protected final Page page;
    public BasePage(Page page) {
        this.page = page;
    }
    public SidebarNav sidebar() {
        return new SidebarNav(page);
    }
}
