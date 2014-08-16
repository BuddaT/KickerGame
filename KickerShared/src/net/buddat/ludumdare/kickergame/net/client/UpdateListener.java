package net.buddat.ludumdare.kickergame.net.client;

import net.buddat.ludumdare.kickergame.net.Message;

public interface UpdateListener {
    @SuppressWarnings(value = "unused") // used in clj
    public void onMessage(Message message);
}
