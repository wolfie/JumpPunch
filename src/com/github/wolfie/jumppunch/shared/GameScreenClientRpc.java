package com.github.wolfie.jumppunch.shared;

import com.vaadin.shared.communication.ClientRpc;

public interface GameScreenClientRpc extends ClientRpc {
	void spawn(int id, int x, int y);

	/** Sets the current player id. */
	void setPlayer(int id);

	void remove(int id);

	void makeJump(int id);
}
