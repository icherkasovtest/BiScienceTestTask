package adclarity.chatbot;

import adclarity.BaseTest;
import io.qameta.allure.Description;
import org.biscience.actions.BiScienceApp;
import org.testng.annotations.Test;

public class AiChatbotTests extends BaseTest {

    @Test(groups = {"ALL","CHAT"})
    @Description("Ask anything button opens AI chat dialog with all elements visible")
    public void askAnythingOpensChat() {
        var app = BiScienceApp.init();
        app.getActions().login()
           .sidebar().openAiChat()
           .verifyDialogOpen()
           .verifyHeaderVisible()
           .verifySuggestionsVisible()
           .verifyInputVisible()
           .verifyHistoryVisible();
    }

    @Test(groups = {"ALL","CHAT"})
    @Description("AI chat dialog can be closed")
    public void aiChatCanBeClosed() {
        var app = BiScienceApp.init();
        app.getActions().login()
           .sidebar().openAiChat()
           .verifyDialogOpen()
           .close()
           .verifyDialogClosed();
    }

    @Test(groups = {"ALL","CHAT"})
    @Description("Clicking a history item opens the session and shows New button and Submit feedback")
    public void sessionInfoShowsConversationView() {
        var app = BiScienceApp.init();
        app.getActions().login()
           .sidebar().openAiChat()
           .verifyDialogOpen()
           .clickHistoryItem("Session Info")
           .verifyNewChatButtonVisible()
           .verifySubmitFeedbackVisible();
    }
}
