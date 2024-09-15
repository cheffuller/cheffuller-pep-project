package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

// Service class to handle the business logic related to messages
public class MessageService {
    // Reference to the MessageDAO for database operations
    private MessageDAO messageDAO;

    // Default constructor initializes MessageDAO
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    // Constructor that accepts a MessageDAO
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // Method to add a new message
    // If the message text is empty, return null
    public Message addMessage(Message message){
        if (message.message_text == "") return null;
        return messageDAO.insertMessage(message);
    }

    // Method to get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // Method to get a message by its ID
    public Message getMessageByID(int messageID){
        return messageDAO.getMessageByID(messageID);
    }

    // Method to delete a message by its ID
    public Message deleteMessage(int messageID){
        return messageDAO.deleteMessage(messageID);
    }

    // Method to update an existing message
    // If the new message text is empty, return null
    public Message updateMessage(Message newMessageText){
        if (newMessageText.message_text == "") return null;
        return messageDAO.updateMessage(newMessageText);
    }

    // Method to get all messages posted by a specific account
    public List<Message> getMessagesByAccount(int accountID){
        return messageDAO.getMessagesByAccount(accountID);
    }
}