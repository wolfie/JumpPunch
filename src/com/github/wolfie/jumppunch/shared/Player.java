package com.github.wolfie.jumppunch.shared;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 2173313922443637271L;

	public final int id;

	public int x = 100;
	public int y = Constants.FLOOR_Y;
	public double yVel = 0.0d;
	public double xVel = 0.0d;

	public Player(final int id) {
		this.id = id;
	}

	public void jump() {
		yVel = -Constants.JUMP_VELOCITY;
	}

	public void punch() {
		// TODO Auto-generated method stub

	}

	public void tick(final long deltaMillis) {
		y += yVel;

		if (y >= Constants.FLOOR_Y) {
			yVel = 0.0;
			y = Constants.FLOOR_Y;
		} else {
			yVel -= Constants.GRAVITY_PER_SEC * (deltaMillis / 1000.0d);
		}
	}
}
