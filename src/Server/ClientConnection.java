package Server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Socket socket;
    Database database;
    Server server;
    PrintWriter out;
    BufferedReader in;

    public ClientConnection(Socket socket, Database database, Server server) {
        this.socket = socket;
        this.database = database;
        this.server = server;
    }

    @Override
    public void run() {
        initializeClientConnection();
    }

    public void initializeClientConnection() {


        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String incomingMessage = in.readLine();

            if (incomingMessage.contains("--JOIN_REQUEST--")) {
                String response = incomingMessage.split(" ")[1];
                server.clients.add(this);
                server.broadcast(response + " has joined the chat");

            }

            else {
                database.addMessage(incomingMessage);
                server.broadcast(incomingMessage);
                out.close();
                in.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String message) {
        out.println(message);
    }
}
