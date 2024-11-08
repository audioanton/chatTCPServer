package Server;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<String> messages = new ArrayList<>();


    public synchronized void addMessage(String message) {
        messages.add(message);
    }

    public synchronized String getMessages() {
        StringBuilder sb = new StringBuilder();

        for (String message : messages) {
            sb.append(message).append("\n");
        }
        return sb.toString();
    }
}
