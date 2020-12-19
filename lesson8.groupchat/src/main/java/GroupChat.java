import java.io.*;
import java.net.Socket;
import java.util.*;

public class GroupChat implements IChat {
    private String chatName;
    private Map<Socket, IUser> chatUsers;
    private Collection<Message> chatMessages;

    public GroupChat(String chatName) {
        this.chatName = chatName;
        this.chatUsers = new HashMap<>();
        this.chatMessages = new ArrayList<>();
    }

    public Set<Socket> getChatUsers() {
        return this.chatUsers.keySet();
    }

    public Collection<IUser> getChatUserNames() {
        return this.chatUsers.values();
    }

    /**
     * Sends a message to all members of the chatUser HashMap.
     *
     * @param authorName     Name of the message's author, in the chat message will be signed by that name.
     * @param messageContent Content of the message, that will be shown in the chat.
     */
    @Override
    public void notifyUsers(String authorName, String messageContent) {
        if (!messageContent.equals(IChat.SEND_MESSAGE_COMMAND)) {
            Message message = new Message(authorName, messageContent);
            this.chatMessages.add(message);
            for (Socket socket : getChatUsers()) {
                try {
                    PrintWriter messageOut = new PrintWriter(socket.getOutputStream(), true);
                    messageOut.println(message.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Adds the specified user to the chatUsers HashMap and notifies all the user in the chatUsers about it.
     *
     * @param userSocket User's socket serves as a key for adding user in the HashMap.
     * @param user       User serves as a value for adding user in the HashMap.
     * @return True if the user was added successfully, false if the user already is a member of chat.
     */
    @Override
    public boolean addUser(Socket userSocket, IUser user) {
        if (this.chatUsers.containsKey(userSocket)) {
            return false;
        } else {
            this.chatUsers.put(userSocket, user);
            notifyUsers(this.chatName,
                    "User " + user.getName() + " has joined the chat.");
        }
        return true;
    }

    /**
     * Removes the specified user from the chatUsers HashMap and notifies all the user in the chatUsers about it.
     *
     * @param userSocket User's socket serves as a key for removing user in the HashMap.
     * @param user       User serves as a value for removing user in the HashMap.
     * @return True if the user was removed successfully, false if the user is not a member of chat
     * and cannot be removed.
     */
    @Override
    public boolean removeUser(Socket userSocket, IUser user) {
        if (this.chatUsers.containsKey(userSocket)) {
            this.chatUsers.remove(userSocket);
            notifyUsers(this.chatName,
                    "User " + user.getName() + " has left the chat.");
            return true;
        }
        return false;
    }
}
