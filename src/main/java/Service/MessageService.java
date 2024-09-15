package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        if (message.message_text == "") return null;
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int messageID){
        return messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessage(int messageID){
        return messageDAO.deleteMessage(messageID);
    }

    public Message updateMessage(Message newMessageText){
        if (newMessageText.message_text == "") return null;
        return messageDAO.updateMessage(newMessageText);
    }

    public List<Message> getMessagesByAccount(int accountID){
        return messageDAO.getMessagesByAccount(accountID);
    }
}
