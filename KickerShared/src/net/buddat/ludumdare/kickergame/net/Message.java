package net.buddat.ludumdare.kickergame.net;

public class Message {
    private final MessageType type;
    private final String body;

    public Message(MessageType type, String body) {
        this.type = type;
        this.body = body;
    }

    public MessageType getType() {
        return type;
    }

    public String getBody() {
        return body;
    }
}
