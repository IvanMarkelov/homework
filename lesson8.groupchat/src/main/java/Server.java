import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private static IChat chat;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            new ClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientName = in.readLine();
                IUser clientUser = new User(clientName);
                chat.addUser(clientSocket, clientUser);

                String clientLine;
                while (!(clientLine = in.readLine()).equals(IChat.DISCONNECT_USER_COMMAND)) {
                    chat.notifyUsers(clientName, clientLine.trim());
                }
                chat.removeUser(clientSocket, clientUser);

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public static void main(String[] args) throws IOException {
            Server server = new Server();
            chat = new GroupChat("MyChat");
            server.start(5555);
        }
    }
}
