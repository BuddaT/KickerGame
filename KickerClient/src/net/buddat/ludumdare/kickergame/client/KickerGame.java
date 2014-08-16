package net.buddat.ludumdare.kickergame.client;

import net.buddat.ludumdare.kickergame.Constants;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class KickerGame extends BasicGame {

	public KickerGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		try {
			AppGameContainer gameContainer = new AppGameContainer(new KickerGame(Constants.NAME));
			gameContainer.setDisplayMode(Constants.WIDTH, Constants.HEIGHT, Constants.FULLSCREEN);
			gameContainer.setShowFPS(Constants.SHOW_FPS);
			gameContainer.setTargetFrameRate(Constants.FPS);
			gameContainer.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
