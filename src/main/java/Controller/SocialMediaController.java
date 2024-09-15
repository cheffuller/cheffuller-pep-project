package Controller;

import Model.*;
import Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

// SocialMediaController class to handle various routes of the API
public class SocialMediaController {
    // Services to manage accounts and messages
    AccountService accountService;
    MessageService messageService;

    // Constructor initializes the services
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    // Method to start the Javalin API and define the routes/endpoints
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);
        return app;
    }

    // Handler for POST /register: Registers a new account
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.addAccount(account);
        if (registeredAccount != null) {
            ctx.json(registeredAccount);
        } else {
            ctx.status(400);
        }
    }

    // Handler for POST /login: Logs in an existing account
    private void postAccountLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.getAccountByUserPass(account);
        if (loginAccount != null) {
            ctx.json(loginAccount);
        } else {
            ctx.status(401);
        }
    }

    // Handler for POST /messages: Creates a new message
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            ctx.json(addedMessage);
        } else {
            ctx.status(400);
        }
    }

    // Handler for GET /messages: Retrieves all messages
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    // Handler for GET /messages/{message_id}: Retrieves a message by ID
    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnedMessage = messageService.getMessageByID(messageID);
        if (returnedMessage != null) {
            ObjectMapper om = new ObjectMapper();
            ctx.json(om.writeValueAsString(returnedMessage));
            ctx.status(200);
        } else {
            ctx.status(200);
        }
    }

    // Handler for DELETE /messages/{message_id}: Deletes a message by ID
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnedMessage = messageService.deleteMessage(messageID);
        if (returnedMessage != null) {
            ObjectMapper om = new ObjectMapper();
            ctx.json(om.writeValueAsString(returnedMessage));
        } else {
            ctx.status(200);
        }
    }

    // Handler for PATCH /messages/{message_id}: Updates a message by ID
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper om = new ObjectMapper();
        Message newMessageText = om.readValue(ctx.body(), Message.class);
        newMessageText.message_id = messageID;
        Message updatedMessage = messageService.updateMessage(newMessageText);
        if (updatedMessage != null) {
            ctx.json(om.writeValueAsString(updatedMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    // Handler for GET /accounts/{account_id}/messages: Retrieves all messages for a specific account
    private void getMessagesByAccountHandler(Context ctx) {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(accountID);
        ctx.json(messages);
    }
}