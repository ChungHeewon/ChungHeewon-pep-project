package Service;

import DAO.AccountDAO;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        accountDAO = new AccountDAO();
    }
    public Message addMessage(Message message) {
        if(!message.getMessage_text().equals("") && message.getMessage_text().length() <= 255 && accountDAO.getAccountById(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(int id, String newText) {
        if(messageDAO.getMessageById(id) != null && !newText.equals("") && newText.length() <= 255) {
            return messageDAO.updateMessage(id, newText);
        }
        return null;
    }

    public List<Message> getAllMessagesByUser(int id) {
        return messageDAO.getAllMessagesByUser(id);
    }
}
