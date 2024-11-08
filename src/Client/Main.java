package Client;

public class Main {
    public static void main(String[] args) {
        Chat chat = new Chat(44444, "Andreas");
        new Thread(chat).start();
    }

}
