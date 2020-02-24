package network;

import java.io.DataInputStream;
import java.io.IOException;

import core.Connect4;
import core.Player;

/**
 * A separate thread for managing the individual games taking place on the
 * server
 * 
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/16/20
 */
public class GameThread implements Runnable {

	/**
	 * The first player for this individual game (Will always be human)
	 */
	private Player player1;

	/**
	 * The second player for this individual game (Can be human or computer)
	 */
	private Player player2;

	/**
	 * The model of the game
	 */
	private Connect4 game;

	/**
	 * The constructor for this game
	 * 
	 * @param player1 The first player for this individual game (Will always be
	 *                human)
	 * @param player2 The second player for this individual game (Can be human or
	 *                computer)
	 */
	public GameThread(Player player1, Player player2) {
		Connect4 game = new Connect4(player1, player2, this);
		this.player1 = player1;
		this.player2 = player2;
		this.game = game;
	}

	/**
	 * Create a inputListener object for player1 and player2 (if human)
	 */
	@Override
	public void run() {
		if (!player1.isComputer()) {
			InputListener inListener1 = new InputListener(player1);
			new Thread(inListener1).start();
		}

		if (!player2.isComputer()) {
			InputListener inListener2 = new InputListener(player2);
			new Thread(inListener2).start();
		}
	}

	/**
	 * Update the client with the supplied information
	 * 
	 * @param status   Text to be placed at the bottom of the game (indicated turn,
	 *                 win / tie)
	 * @param gameOver Indicates to the player if the game is over or not
	 * @param board    A 2d array to be rendered on the clients application
	 * @param turn     true if it is the players turn, false if not
	 * @param player   the player which to update
	 */
	public void updateClient(String status, boolean gameOver, int[][] board, boolean turn, Player player) {
		if (!player.isComputer()) {
			try {
				player.getOut().writeObject(new GameState(status, gameOver, board, turn));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes the connections for both players
	 */
	public void close() {
		player1.close();
		player2.close();
	}

	/**
	 * A class implementing Runnable that continually listens for user input
	 */
	public class InputListener implements Runnable {

		/**
		 * The player the class is listening for
		 */
		private Player player;

		/**
		 * The Data Input Stream to be listening on
		 */
		private DataInputStream in;

		/**
		 * True if game is over, false if not. Used to close the connections
		 */
		private boolean gameOver = false;

		/**
		 * Default constructor
		 * 
		 * @param player The player the class is listening for. Data input stream is
		 *               obtained from this object
		 */
		public InputListener(Player player) {
			this.in = player.getIn();
			this.player = player;
		}

		/**
		 * Continually listens for user input of integers. When input is received, the
		 * method attempts to play the game in the selected column
		 */
		@Override
		public void run() {
			try {
				while (!gameOver) {
					int col = in.readInt();
					gameOver = game.playTurn(col, player);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
			close();
		}
	}
}