package net.buddat.ludumdare.kickergame.client.gfx;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface Screen {

	public void render(GameContainer gc, Graphics g);
	
	public void init(GameContainer gc);
	
	public void update(GameContainer gc, int delta);
	
}
