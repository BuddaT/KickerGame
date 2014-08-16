package net.buddat.ludumdare.kickergame.net;

public enum MessageType
{
    /**
     * Heartbeat to allow server to disconnect if non-responsive.
     */
    KEEP_ALIVE(0x0);

    private byte id;

    private MessageType(int id) {
        this.id = (byte) id;
    }
}
