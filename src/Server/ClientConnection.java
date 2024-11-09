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

            Request request = (Request) in.readObject();

            switch (request.getRequestType()) {
                case LISTENING -> handleListeningRequest(request);
                case MESSAGE -> handleMessageRequest(request);
                case TERMINATION -> handleTerminationRequest(request);
            }


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

    private void handleTerminationRequest(Request request) throws IOException {

        ClientConnection clientConnectionToTerminate = server.clients.stream().filter(client -> client.getClientID() == request.getClientID()).findFirst().get();
        clientConnectionToTerminate.out.println("Terminating connection");
        clientConnectionToTerminate.out.close();
        clientConnectionToTerminate.in.close();
        server.broadcast(request.getUsername() + " has left the chat :(");
        server.clients.remove(clientConnectionToTerminate);
        out.close();
        in.close();
        System.out.println("Client " + request.getClientID() + "disconnected");
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public int getClientID() {
        return clientID;
    }
}
