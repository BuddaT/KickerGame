package net.buddat.ludumdare.kickergame.client;

public class Scenario {
	
	private float kickingDistance, kickingPosition;

	public Scenario(float distance, float position) {
		this.kickingDistance = distance;
		this.kickingPosition = position;
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
		if (newPosition <= 1.0 && newPosition > 0.0f)
			kickingPosition = newPosition;
	}
	
}
