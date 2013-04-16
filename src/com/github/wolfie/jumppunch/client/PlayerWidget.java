package com.github.wolfie.jumppunch.client;

import com.github.wolfie.jumppunch.client.GameScreenWidget.PlayerControlsListener;
import com.github.wolfie.jumppunch.shared.Player;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class PlayerWidget extends Composite implements PlayerControlsListener {

	private final Player player;
	private final Image rootImage;

	public PlayerWidget(final Player player) {
		this.player = player;

		rootImage = new Image(
				"http://www.moonsoft.fi/images/icons/icon_winxp.png");
		initWidget(rootImage);
		setStyleName("player");
	}

	@Override
	public void jump() {
		this.player.jump();
	}

	@Override
	public void punch() {
		this.player.punch();
	}

	public void tick(final long deltaMillis) {
		player.tick(deltaMillis);
	}

	public Player getPlayer() {
		return player;
	}
}
