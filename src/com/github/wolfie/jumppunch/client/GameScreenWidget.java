package com.github.wolfie.jumppunch.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.wolfie.jumppunch.shared.Constants;
import com.github.wolfie.jumppunch.shared.Player;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;

public class GameScreenWidget extends Composite {
	public interface PlayerControlsListener {
		void jump();

		void punch();
	}

	private PlayerWidget playerWidget;

	private final AbsolutePanel absolutePanel;
	private final FocusPanel focusPanel;

	private final KeyDownHandler keyDownHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(final KeyDownEvent event) {
			boolean wasHandled = false;
			if (event.getNativeKeyCode() == KeyCodes.KEY_UP) {
				wasHandled = true;
				fireJump();
			}

			else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN) {
				wasHandled = true;
				firePunch();
			}

			if (wasHandled) {
				event.preventDefault();
			}
		}
	};
	private final Set<PlayerControlsListener> listeners = new HashSet<GameScreenWidget.PlayerControlsListener>();
	private final Map<Integer, PlayerWidget> idToWidgetMap = new HashMap<Integer, PlayerWidget>();

	private final Timer ticker = new Timer() {
		@Override
		public void run() {
			tick();
		}
	};
	private long previousTick;

	protected void fireJump() {
		for (final PlayerControlsListener listener : listeners) {
			listener.jump();
		}
	}

	protected void firePunch() {
		for (final PlayerControlsListener listener : listeners) {
			listener.punch();
		}
	}

	private void tick() {
		final long currentMillis = System.currentTimeMillis();
		final long deltaMillis = currentMillis - previousTick;
		previousTick = currentMillis;
		for (final Entry<Integer, PlayerWidget> entry : idToWidgetMap
				.entrySet()) {
			final PlayerWidget widget = entry.getValue();
			widget.tick(deltaMillis);
			absolutePanel.setWidgetPosition(widget, widget.getPlayer().x,
					widget.getPlayer().y);
		}
	}

	public GameScreenWidget() {
		absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("100%", "100%");
		focusPanel = new FocusPanel(absolutePanel);
		focusPanel.addKeyDownHandler(keyDownHandler);
		focusPanel.setSize("100%", "100%");
		initWidget(focusPanel);
		setStyleName("gamescreen");
		setSize("100%", "100%");
		previousTick = System.currentTimeMillis();
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		ticker.scheduleRepeating(Constants.TICK_LENGTH_MILLIS);
	}

	@Override
	protected void onDetach() {
		ticker.cancel();
		super.onDetach();
	}

	public void addPlayerControlsListener(final PlayerControlsListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	public void removePlayercontrolsListener(
			final PlayerControlsListener listener) {
		listeners.remove(listener);
	}

	public PlayerWidget spawn(final int id, final int x, final int y) {
		final Player player = new Player(id);
		player.x = x;
		player.y = y;

		final PlayerWidget playerWidget = new PlayerWidget(player);
		idToWidgetMap.put(id, playerWidget);
		absolutePanel.add(playerWidget, playerWidget.getPlayer().x,
				playerWidget.getPlayer().y);
		return playerWidget;
	}

	public void setPlayer(final int id) {
		// in case we're miraculously swapping the player we control...
		removePlayercontrolsListener(playerWidget);
		final PlayerWidget playerWidget = idToWidgetMap.get(id);
		if (playerWidget == null) {
			throw new IllegalStateException(
					"you need to spawn the player before setting it");
		}
		this.playerWidget = playerWidget;
		addPlayerControlsListener(playerWidget);
	}

	public void focus() {
		focusPanel.setFocus(true);
	}

	public void remove(final int id) {
		final PlayerWidget widget = idToWidgetMap.remove(id);
		widget.removeFromParent();
	}

	public void makeJump(final int id) {
		idToWidgetMap.get(id).jump();
	}
}
