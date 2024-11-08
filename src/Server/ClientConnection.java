package Server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Socket socket;
    Database database;

    public ClientConnection(Socket socket, Database database) {
        this.socket = socket;
        this.database = database;
    }

    @Override
    public void run() {
        initializeClientConnection();
    }

    public void initializeClientConnection() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("Connection Established.");

            while (true) {
                String incomingMessage = in.readLine();
                database.addMessage(incomingMessage);
                out.println(database.getMessages());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
