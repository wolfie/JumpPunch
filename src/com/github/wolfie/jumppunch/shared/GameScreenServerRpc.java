package com.github.wolfie.jumppunch.shared;

import com.vaadin.shared.communication.ServerRpc;

public interface GameScreenServerRpc extends ServerRpc {
	void jump();

	void punch();
}
