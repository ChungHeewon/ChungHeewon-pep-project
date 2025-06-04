package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login", this::loginHandler);
        app.post("/register", this::registerHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::retrieveAllMessageHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveUserMessagesHandler);
        return app;
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedInAccount = accountService.verifyAccount(account);
        if(loggedInAccount!=null){
            context.json(mapper.writeValueAsString(loggedInAccount));
        }else{
            context.status(401);
        }
    }

    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            context.json(mapper.writeValueAsString(addedMessage));
        }else{
            context.status(400);
        }
    }

    private void retrieveAllMessageHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void retrieveMessageByIdHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if(message == null) {
            context.json("");
        } else {
            context.json(message);
        }
    }

    private void deleteMessageHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if(message == null) {
            context.json("");
        } else {
            context.json(message);
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        int id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        String newText = message.message_text;
        Message updatedMessage = messageService.updateMessage(id, newText);
        if(updatedMessage != null) {
            context.json(mapper.writeValueAsString(updatedMessage));
        } else {
            context.status(400);
        }
    }

    private void retrieveUserMessagesHandler(Context context) {
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByUser(id);
        context.json(messages);
    }

}