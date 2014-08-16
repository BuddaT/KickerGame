package net.buddat.ludumdare.kickergame.net.client;

public interface TcpClient {
    @SuppressWarnings(value = "unused")
    public void connect();
    @SuppressWarnings(value = "unused")
    public void addUpdateListener(UpdateListener listener);
}
