package Controller;

import Model.*;
import Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
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

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    // context.json("sample text");
    // }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.addAccount(account);
        if (registeredAccount != null) {
            ctx.json(om.writeValueAsString(registeredAccount));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void postAccountLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.getAccountByUserPass(account);
        if (loginAccount != null) {
            ctx.json(om.writeValueAsString(loginAccount));
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            ctx.json(om.writeValueAsString(addedMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    // ObjectMapper objectMapper = new ObjectMapper();
    // try {
    // String jsonArray
    // = objectMapper.writeValueAsString(courses);
    // System.out.println(jsonArray);
    // }
    // catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }

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

    private void getMessagesByAccountHandler(Context ctx) {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(accountID);
        ctx.json(messages);
    }
}