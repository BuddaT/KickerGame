package net.buddat.ludumdare.kickergame.net;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MessageType
{
    /**
     * Heartbeat to allow server to disconnect if non-responsive.
     */
    KEEP_ALIVE(0x0),
    /**
     * Chat text
     */
    CHAT(0x10);

    private static final Map<Integer, MessageType> ID_TO_TYPE;
    static {
        Map<Integer, MessageType> idToType = new HashMap<Integer, MessageType>();
        for (MessageType type : MessageType.values()) {
            idToType.put(type.id, type);
        }
        ID_TO_TYPE = Collections.unmodifiableMap(idToType);
    }

    public static MessageType forId(int id) {
        return ID_TO_TYPE.get(id);
    }

    private int id;

    private MessageType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
