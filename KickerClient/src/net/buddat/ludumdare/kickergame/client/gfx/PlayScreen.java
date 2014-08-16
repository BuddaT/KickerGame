package net.buddat.ludumdare.kickergame.client.gfx;

import net.buddat.ludumdare.kickergame.Constants;
import net.buddat.ludumdare.kickergame.client.KickerGame;
import net.buddat.ludumdare.kickergame.client.Scenario;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PlayScreen implements Screen {

	private KickerGame parent;

	private Scenario currentScenario;
	private Image testbg;

	public PlayScreen(KickerGame kickerGame) {
		this.parent = kickerGame;

		currentScenario = new Scenario(0.1f, 0.9f);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		int xPos = (int) (-Constants.WIDTH + Constants.WIDTH
				* currentScenario.getKickingDistance());
		xPos += (int) (-Constants.WIDTH * 0.4 + Constants.WIDTH * 0.8
				* currentScenario.getKickingPosition());
		int yPos = (int) (-Constants.HEIGHT + Constants.HEIGHT
				* currentScenario.getKickingDistance());

		testbg.draw(xPos, yPos,
				Constants.WIDTH + (Constants.WIDTH * 2 - (int) (Constants.WIDTH * 2 
						* currentScenario.getKickingDistance())),
				Constants.HEIGHT + (Constants.HEIGHT * 2 - (int) (Constants.HEIGHT * 2 
						* currentScenario.getKickingDistance())));
	}

	@Override
	public void init(GameContainer gc) {
		try {
			testbg = new Image("/data/bgtest.jpg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, int delta) {

	}

	public void dispose() {

	}

}
