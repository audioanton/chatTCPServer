package Client;

public class Main {
    public static void main(String[] args) {
        Chat chat = new Chat(44444, "Andreas");
        new Thread(chat).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }

        Chat chat2 = new Chat(44444, "Anton");
        new Thread(chat2).start();
    }

}
