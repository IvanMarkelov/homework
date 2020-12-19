import java.net.Socket;
import java.util.*;

public interface IChat {
    String DISCONNECT_USER_COMMAND = "quit";
    String SEND_MESSAGE_COMMAND = "";

    void notifyUsers(String authorName, String message);

    boolean addUser(Socket userSocket, IUser user);

    boolean removeUser(Socket userSocket, IUser user);

    Set<Socket> getChatUsers();

    Collection<IUser> getChatUserNames();
}
