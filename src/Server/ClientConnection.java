package Server;

import Requests.Request;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Socket socket;
    Database database;
    Server server;
    PrintWriter out;
    ObjectInputStream in;
    int clientID;

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
            in = new ObjectInputStream(socket.getInputStream());
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Request request = (Request) in.readObject();


            switch (request.getRequestType()) {
                case LISTENING -> handleListeningRequest(request);
                case MESSAGE -> handleMessageRequest(request);
                case TERMINATION -> handleTerminationRequest(request);
            }

//            String incomingMessage = in.readLine();

//            if (incomingMessage.contains("--JOIN_REQUEST--")) {
//                String response = incomingMessage.split(" ")[1];
//                server.clients.add(this);
//                server.broadcast(response + " has joined the chat");
//
//            }
//
//            else {
//                database.addMessage(incomingMessage);
//                server.broadcast(incomingMessage);
//                out.close();
//                in.close();
//            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleListeningRequest(Request request) {
            this.clientID = request.getClientID();
            server.clients.add(this);
            server.broadcast(request.getUsername() + " has joined the chat :)");
    }

    private void handleMessageRequest(Request request) throws IOException {
        String message = request.getUsername() + ": " +request.getPayload();
        database.addMessage(message);
        server.broadcast(message);
        out.close();
        in.close();
    }

    private void handleTerminationRequest(Request request) {
        System.out.println("Client " + request.getClientID() + "disconnected");
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
