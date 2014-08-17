package net.buddat.ludumdare.kickergame.client.gfx;

import net.buddat.ludumdare.kickergame.Constants;
import net.buddat.ludumdare.kickergame.client.KickerGame;
import net.buddat.ludumdare.kickergame.client.Scenario;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PlayScreen implements Screen {

	private KickerGame parent;

	private Scenario currentScenario;
	private Image testbg, testball;
	private Image testGauge, testArrow;

	private DirectionGauge directionDial;
	private PowerGauge powerDial;

	private enum State {
		SETTING_UP, KICK_DIR, KICK_POW, ANIMATING, SCORING
	};

	private State currentState = State.SETTING_UP;

	public PlayScreen(KickerGame kickerGame) {
		this.parent = kickerGame;

		currentScenario = new Scenario(0.1f, 0.5f);
		changeState(State.KICK_DIR);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float ratio = testbg.getWidth() / testbg.getHeight();
		int yBg = (int) (-Constants.HEIGHT + Constants.HEIGHT * currentScenario.getKickingDistance());
		int h = Constants.HEIGHT
				+ (Constants.HEIGHT * 2 - (int) (Constants.HEIGHT * 2 * currentScenario.getKickingDistance()));
		int w = (int) (h * ratio);
		int xBg = -((w - Constants.WIDTH) / 2);
		xBg += (int) (-Constants.WIDTH * 0.4 + Constants.WIDTH * 0.8 * currentScenario.getKickingPosition());
		testbg.draw(xBg, yBg, w, h);

		currentScenario.getBallImage().render(gc, g);

		if (currentState == State.KICK_DIR || currentState == State.KICK_POW) {
			directionDial.render(g);
		}

		if (currentState == State.KICK_POW) {
			powerDial.render(g);
		}
	}

	@Override
	public void init(GameContainer gc) {
		try {
			testbg = new Image("/data/bgtest.jpg");
			testball = new Image("/data/ball.png");
			testGauge = new Image("/data/basicDirGauge.png");
			testArrow = new Image("/data/basicArrow.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		currentScenario.getBallImage().setImage(testball);
		directionDial = new DirectionGauge("Direction", 2, testGauge, testArrow);
		powerDial = new PowerGauge("Power", 2);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if (currentState == State.KICK_DIR) {
			if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
				directionDial.pause();
				currentScenario.setAppliedDirection(directionDial.arrow.getRotation());
				changeState(State.KICK_POW);
			}

			//currentScenario.setKickingPosition(currentScenario.getKickingPosition() - 0.01f);

			if (!directionDial.isPaused())
				directionDial.update(delta);
		} else if (currentState == State.KICK_POW) {
			if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
				powerDial.pause();
				currentScenario.setAppliedPower(powerDial.percentageFull);
				changeState(State.ANIMATING);
			}

			if (!powerDial.isPaused())
				powerDial.update(delta);
		} else if (currentState == State.ANIMATING) {
			currentScenario.animateKick();

			if (currentScenario.isCompleted()) {
				changeState(State.SCORING);
			}
		}

		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			directionDial.arrow.setRotation(0);
			directionDial.paused = false;
			powerDial.percentageFull = 0.0f;
			powerDial.paused = false;
			changeState(State.KICK_DIR);
		}
	}

	public void dispose() {

	}

	public void changeState(State newState) {
		currentState = newState;
	}

	private class DirectionGauge {

		int speed;
		String title;
		boolean paused = false;

		Image gauge, arrow;
		boolean reverse = false;

		private DirectionGauge(String title, int speed, Image dial, Image arrow) {
			this.title = title;
			this.speed = speed;
			this.gauge = dial;
			this.arrow = arrow;
		}

		private void pause() {
			paused = true;
		}

		private boolean isPaused() {
			return paused;
		}

		private void render(Graphics g) {
			g.drawImage(gauge, Constants.WIDTH / 2 - gauge.getWidth() / 2,
					Constants.HEIGHT - gauge.getHeight());
			g.drawImage(arrow, Constants.WIDTH / 2 - arrow.getWidth() / 2,
					Constants.HEIGHT - arrow.getHeight());
		}

		private void update(int delta) {
			arrow.setCenterOfRotation(arrow.getWidth() / 2,
					arrow.getHeight() - 5);

			if (reverse)
				arrow.rotate(-speed);
			else
				arrow.rotate(speed);

			if (arrow.getRotation() >= 90) {
				arrow.setRotation(90);
				reverse = true;
			} else if (arrow.getRotation() <= -90) {
				arrow.setRotation(-90);
				reverse = false;
			}
		}
	}

	private class PowerGauge {
		
		int speed;
		String title;
		
		boolean paused = false, reverse = false;
		float percentageFull = 0.0f;
		
		private PowerGauge(String title, int speed) {
			this.title = title;
			this.speed = speed;
		}

		private void pause() {
			paused = true;
		}

		private boolean isPaused() {
			return paused;
		}

		private void render(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(Constants.WIDTH / 2 + Constants.WIDTH / 6, Constants.HEIGHT / 2, 52, 152);

			g.setColor(Color.gray);
			g.fillRect(Constants.WIDTH / 2 + Constants.WIDTH / 6 + 1, Constants.HEIGHT / 2 + 1
					+ (int) (150 - 150 * percentageFull), 50, (int) (150 * percentageFull));
		}

		private void update(int delta) {
			if (reverse)
				percentageFull -= 0.01f * speed;
			else
				percentageFull += 0.01f * speed;

			if (percentageFull <= 0.0f) {
				percentageFull = 0.0f;
				reverse = false;
			} else if (percentageFull >= 1.0f) {
				percentageFull = 1.0f;
				reverse = true;
			}
		}
	}

}
