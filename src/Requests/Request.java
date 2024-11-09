package Requests;

import java.io.Serializable;

public class Request implements Serializable {
    int clientID;
    RequestType requestType;
    String username;
    String payload;

    public Request(int clientID, RequestType requestType, String username, String payload) {
        this.clientID = clientID;
        this.requestType = requestType;
        this.username = username;
        this.payload = payload;
    }

    public int getClientID() {
        return clientID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getUsername() {
        return username;
    }

    public String getPayload() {
        return payload;
    }
}
