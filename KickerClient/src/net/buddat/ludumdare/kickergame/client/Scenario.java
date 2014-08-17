package net.buddat.ludumdare.kickergame.client;

import net.buddat.ludumdare.kickergame.Constants;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Scenario {
	
	private float kickingDistance, kickingPosition;
	private float appliedDirection, appliedPower, powerRemaining;

	private BallImage ballImage;

	private boolean scenarioCompleted = false;

	public Scenario(float distance, float position) {
		this.kickingDistance = distance;
		this.kickingPosition = position;

		ballImage = new BallImage(null, kickingDistance, kickingPosition);
	}
	
	public float getKickingDistance() {
		return kickingDistance;
	}
	
	public void setKickingDistance(float newDistance) {
		if (newDistance <= 1.0f && newDistance > 0.0f)
			kickingDistance = newDistance;
	}
	
	public float getKickingPosition() {
		return kickingPosition;
	}
	
	public void setKickingPosition(float newPosition) {
		if (newPosition <= 1.0 && newPosition > 0.0f) {
			kickingPosition = newPosition;
			ballImage.setPercentfromCenter(newPosition);
		}
	}
	
	public void setAppliedDirection(float newDirection) {
		appliedDirection = newDirection;
	}

	public void setAppliedPower(float newPower) {
		appliedPower = newPower;
		powerRemaining = newPower;
	}

	public BallImage getBallImage() {
		return ballImage;
	}

	public void animateKick() {
		if (scenarioCompleted)
			return;

		float moveDistance = (appliedPower * 10);
		float moveLeftRight = 0.5f / 90.0f * (100.0f / 90.0f * appliedDirection);
		float moveForward = 1.0f - moveLeftRight;

		powerRemaining -= 0.01f;

		ballImage.percentageFromEnd -= moveForward / 20;
		ballImage.currentScale -= (100 - ballImage.getPercentageMoved()) / 40000.0f;
		if (powerRemaining > 0)
			ballImage.height -= powerRemaining * (8 - (ballImage.startingFromEnd / 100 * 4));
		else {
			ballImage.height += 1;
			if (ballImage.height >= 400 - (200 / ballImage.percentageFromEnd)) {
				scenarioCompleted = true;
			}
		}
		setKickingPosition(getKickingPosition() - moveLeftRight / (50.0f + ballImage.getPercentageMoved() * 2));
		System.out.println(ballImage.getPercentageMoved());

	}

	public void setCompleted(boolean isCompleted) {
		scenarioCompleted = isCompleted;
	}

	public boolean isCompleted() {
		return scenarioCompleted;
	}

	public class BallImage {

		private Image theImage;
		private float currentScale;
		private int height;
		private int startingFromEnd, startingFromCenter;
		private float percentageFromEnd, percentageFromCenter;
		private boolean leftOfCenter = true;

		private BallImage(Image ballImage, float startingDistance, float startingPosition) {
			this.theImage = ballImage;
			currentScale = 1.0f;
			percentageFromEnd = (int) (startingDistance * 100);
			percentageFromCenter = (int) ((startingPosition - 0.5f) * 200);
			startingFromEnd = (int) percentageFromEnd;
			startingFromCenter = (int) percentageFromCenter;
			if (percentageFromCenter < 0) {
				percentageFromCenter *= -1;
				leftOfCenter = false;
			}

			height = 400;
		}

		public void setHeight(int newHeight) {
			height = newHeight;
		}

		public void setImage(Image newImage) {
			theImage = newImage;
		}

		public Image getImage() {
			return theImage;
		}

		public float getScale() {
			return currentScale;
		}

		public float getPercentFromEnd() {
			return percentageFromEnd;
		}

		public float getPercentageMoved() {
			if ((float) (100 / startingFromEnd * (startingFromEnd - percentageFromEnd)) > 100)
				return 100;
			return (float) (100 / startingFromEnd * (startingFromEnd - percentageFromEnd));
		}

		public float getPercentFromCenter() {
			return percentageFromCenter;
		}

		public void setPercentfromCenter(float newStartingPosition) {
			percentageFromCenter = (int) ((newStartingPosition - 0.5f) * 200);
			if (percentageFromCenter < 0) {
				percentageFromCenter *= -1;
				leftOfCenter = false;
			}
		}

		public void render(GameContainer gc, Graphics g) {
			int x = (int) (Constants.WIDTH / 2 + (leftOfCenter ? -percentageFromCenter : percentageFromCenter));
			theImage.draw(x, height, currentScale);
		}
	}
}
