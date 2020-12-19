import java.io.IOException;

public class TestClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client("Second");
        client.startConnection("127.0.0.1", 5555);
    }
}