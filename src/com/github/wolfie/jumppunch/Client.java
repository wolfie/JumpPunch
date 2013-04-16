package com.github.wolfie.jumppunch;

import java.io.Serializable;

public interface Client extends Serializable {
	/**
	 * Register a new player
	 * 
	 * @param y
	 * @param x
	 */
	void spawn(int id, int x, int y);

	void remove(int id);

	void makeJump(int id);
}
