import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String name;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String name) {
        this.name = name;
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sendName();

        Thread listener = new Thread(() -> {
            try {
                listenServerForResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread messageSender = new Thread(() -> {
            try {
                sendMessagesToServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        listener.start();
        messageSender.start();

        try {
            messageSender.join();
            listener.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    /**
     * Sends the Client's name to the Server
     */
    public void sendName() {
        out.println(this.name);
    }

    /**
     * Listens Server for response and prints it in the console.
     *
     * @throws IOException
     */
    private void listenServerForResponse() throws IOException {
        String serverResponse;
        while ((serverResponse = in.readLine()) != null) {
            System.out.println(serverResponse);
        }
    }

    /**
     * Sends messages to the Server. Can be stopped by the message "quit".
     *
     * @throws IOException
     */
    private void sendMessagesToServer() throws IOException {
        String messageToSend = "|";
        while (!IChat.DISCONNECT_USER_COMMAND.equals(messageToSend)) {
            messageToSend = readConsoleInput();
            out.println(messageToSend);
        }
        out.println(IChat.DISCONNECT_USER_COMMAND);
        stopConnection();
    }

    /**
     * Reads console's input and forms a String, based on this input.
     *
     * @return String, constructed out of console's input.
     */
    public static String readConsoleInput() {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String line = "|";
        while (!line.equals(IChat.SEND_MESSAGE_COMMAND)) {
            line = sc.nextLine();
            sb.append("\n").append(line);
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("First");
        client.startConnection("127.0.0.1", 5555);
    }
}