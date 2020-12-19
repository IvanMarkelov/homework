public class Message {
    private String author;
    private String content;

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }

    @Override
    public String toString() {
        return author + ": " + content;
    }
}