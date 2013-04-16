package com.github.wolfie.jumppunch;

import com.github.wolfie.jumppunch.shared.GameScreenClientRpc;
import com.github.wolfie.jumppunch.shared.GameScreenServerRpc;
import com.github.wolfie.jumppunch.shared.GameScreenState;
import com.github.wolfie.jumppunch.shared.Player;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;

public class GameScreen extends AbstractComponent {
	private static final long serialVersionUID = -7239012367182355597L;

	private final GameScreenClientRpc rpc = getRpcProxy(GameScreenClientRpc.class);

	private final Client client = new Client() {
		private static final long serialVersionUID = 4569442502906949281L;

		@Override
		public void spawn(final int id, final int x, final int y) {
			UI.getCurrent().runSafely(new Runnable() {
				@Override
				public void run() {
					rpc.spawn(id, x, y);
				}
			});
		}

		@Override
		public void remove(final int id) {
			UI.getCurrent().runSafely(new Runnable() {
				@Override
				public void run() {
					rpc.remove(id);
				}
			});
		}

		@Override
		public void makeJump(final int id) {
			UI.getCurrent().runSafely(new Runnable() {
				@Override
				public void run() {
					rpc.makeJump(id);
				}
			});
		}
	};

	private final Player player;

	public GameScreen() {
		setStyleName("gamescreen");
		player = Server.register(client);
		rpc.spawn(player.id, player.x, player.y);
		rpc.setPlayer(player.id);
		registerRpc(new GameScreenServerRpc() {
			private static final long serialVersionUID = 5152865521517411032L;

			@Override
			public void punch() {
				Server.sendPunch(player);
			}

			@Override
			public void jump() {
				Server.sendJump(player);
			}
		});
		setSizeFull();
	}

	@Override
	public void detach() {
		Server.remove(client);
		super.detach();
	}

	@Override
	protected GameScreenState getState() {
		return (GameScreenState) super.getState();
	}
}
