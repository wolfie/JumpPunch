package com.github.wolfie.jumppunch.shared;

import com.github.wolfie.jumppunch.client.GameScreenWidget;
import com.github.wolfie.jumppunch.client.GameScreenWidget.PlayerControlsListener;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(com.github.wolfie.jumppunch.GameScreen.class)
public class GameScreenConnector extends AbstractComponentConnector implements
		PlayerControlsListener {

	private GameScreenServerRpc rpc;

	@Override
	protected void init() {
		rpc = RpcProxy.create(GameScreenServerRpc.class, this);
		registerRpc(GameScreenClientRpc.class, new GameScreenClientRpc() {
			@Override
			public void spawn(final int id, final int x, final int y) {
				getWidget().spawn(id, x, y);
			}

			@Override
			public void setPlayer(final int id) {
				getWidget().setPlayer(id);
			}

			@Override
			public void remove(final int id) {
				getWidget().remove(id);
			}

			@Override
			public void makeJump(final int id) {
				getWidget().makeJump(id);
			}
		});

		getWidget().addPlayerControlsListener(this);
		getWidget().focus();
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(GameScreenWidget.class);
	}

	@Override
	public GameScreenWidget getWidget() {
		return (GameScreenWidget) super.getWidget();
	}

	@Override
	public void jump() {
		rpc.jump();
	}

	@Override
	public void punch() {
		rpc.punch();
	}
}
