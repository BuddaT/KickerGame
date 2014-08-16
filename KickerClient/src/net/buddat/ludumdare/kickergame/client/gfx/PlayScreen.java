package net.buddat.ludumdare.kickergame.client.gfx;

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
		int xPos = (int) (-800 + 800 * currentScenario.getKickingDistance());
		xPos += (int) (-300 + 600 * currentScenario.getKickingPosition());
		int yPos = (int) (-600 + 600 * currentScenario.getKickingDistance());
		testbg.draw(xPos, yPos, 800 + (1600 - (int) (1600 * currentScenario.getKickingDistance())), 
				600 + (1200 - (int) (1200 * currentScenario.getKickingDistance())));
		System.out.println(xPos + "," + yPos);
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
		//currentScenario.setKickingPosition(currentScenario.getKickingPosition() + 0.001f);
	}
	
	public void dispose() {
		
	}

}
