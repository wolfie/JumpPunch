package com.github.wolfie.jumppunch;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.github.wolfie.jumppunch.shared.Player;

public class Server {

	public static Random RANDOM = new Random();

	private static ConcurrentHashMap<Player, Client> PLAYERS_TO_CLIENTS = new ConcurrentHashMap<Player, Client>();
	private static ConcurrentHashMap<Client, Player> CLIENTS_TO_PLAYERS = new ConcurrentHashMap<Client, Player>();

	private static class Ticker extends Thread {
		boolean running = false;

		@Override
		public void run() {
			running = true;
			System.out.println("Running ticker");
			try {
				long prevTime = System.currentTimeMillis();
				while (running) {
					Thread.sleep((int) (60.0d / 1000.0d));
					final long currentTime = System.currentTimeMillis();
					final long tickDelta = currentTime - prevTime;

					// do the actual things for the current tick.
					for (final Player player : PLAYERS_TO_CLIENTS.keySet()) {
						player.tick(tickDelta);
					}

					prevTime = currentTime;
				}
				System.out.println("Exiting ticker");
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		};
	};

	private static final Ticker TICKER = new Ticker();

	public synchronized static void start() {
		TICKER.start();
	}

	public synchronized static void stop() {
		CLIENTS_TO_PLAYERS.clear();
		PLAYERS_TO_CLIENTS.clear();
		TICKER.running = false;
	}

	public synchronized static Player register(final Client client) {
		System.out.println("Server.register()");

		final Player player = new Player(RANDOM.nextInt());
		player.x = RANDOM.nextInt(500);

		for (final Client c : CLIENTS_TO_PLAYERS.keySet()) {
			c.spawn(player.id, player.x, player.y);
		}

		for (final Player otherPlayer : PLAYERS_TO_CLIENTS.keySet()) {
			client.spawn(otherPlayer.id, otherPlayer.x, otherPlayer.y);
		}

		PLAYERS_TO_CLIENTS.put(player, client);
		CLIENTS_TO_PLAYERS.put(client, player);
		return player;
	}

	public synchronized static void sendJump(final Player player) {
		player.jump();

		final Client ignoreClient = PLAYERS_TO_CLIENTS.get(player);

		final HashSet<Client> clients = new HashSet<Client>(
				CLIENTS_TO_PLAYERS.keySet());

		new Thread() {
			@Override
			public void run() {
				for (final Client client : clients) {
					if (client != ignoreClient) {
						client.makeJump(player.id);
					}
				}
			};
		}.start();

		System.out.println("Server.sendJump()");
	}

	public synchronized static void sendPunch(final Player player) {
		player.punch();
		System.out.println("Server.sendPunch()");
	}

	public synchronized static void remove(final Client client) {
		System.out.println("Server.remove()");
		final Player player = CLIENTS_TO_PLAYERS.remove(client);
		PLAYERS_TO_CLIENTS.remove(player);

		for (final Client remainingClient : CLIENTS_TO_PLAYERS.keySet()) {
			remainingClient.remove(player.id);
		}
	}
}
