package network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javafx.application.Platform;
import ui.Connect4Gui;
import ui.Connect4TextConsole;
import ui.UI;

/**
 * Networking portion of the client application
 * 
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/16/20
 */
public class Connect4Client {

	/**
	 * A data output stream for indicating which columns to select
	 */
	private DataOutputStream out;

	/**
	 * An object input stream for recieve game state object with which to update the
	 * ui
	 */
	private ObjectInputStream in;

	/**
	 * Socket used for network communication
	 */
	private Socket socket;

	/**
	 * Reference to the UI object, used for updating visuals
	 */
	private UI ui;

	/**
	 * Starting the networking client, creates input listeners for ui updates
	 * 
	 * @param ui        Reference to the UI object, used for updating visuals
	 * @param onePlayer true is one player game, false if two player game
	 */
	public Connect4Client(UI ui, boolean onePlayer) {
		this.ui = ui;
		try {
			socket = new Socket("localhost", Connect4Server.PORT);
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			out.writeBoolean(onePlayer);
			if (ui.getClass().equals(Connect4Gui.class)) {
				InputListener inListener = new InputListener(in);
				new Thread(inListener).start();
			} else {
				runConsole();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void runConsole() {
		boolean gameOver = false;
		Connect4TextConsole console = (Connect4TextConsole) ui;
		while (!gameOver) {
			try {
				GameState state = (GameState) in.readObject();
				gameOver = state.isGameOver();
				boolean turn = state.getTurn();
				updateUI(state);
				if (turn) {
					int selected = console.getCol();
					playTurn(selected);
					turn = false;
				}
			} catch (IOException e) {
				System.err.println("Error reading the input from the data stream");
			} catch (ClassNotFoundException e) {
				System.err.println("The recieved object was not of the proper type (Must be GameState)");
			}
		}
	}

	/**
	 * Updates the UI
	 * 
	 * @param state information for updating the UI (status, board, is game over)
	 */
	private void updateUI(GameState state) {
		ui.displayBoard(state.getBoard());
		ui.displayStatus(state.getStatus());
		if (state.isGameOver() && ui.getClass().equals(Connect4Gui.class)) {

		}
	}

	/**
	 * Send the selected column to the game server for input
	 * 
	 * @param col the selected column
	 */
	public void playTurn(int col) {
		try {
			out.writeInt(col);
			out.flush();
		} catch (IOException e) {
			System.err.println("Game thread has been closed. Pressing buttons will not do anything.");
		}
	}

	/**
	 * Closes the input and output data streams as well as the socket
	 */
	private void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("An error occurred when closing the data streams or socket.");
		}
	}

	/**
	 * A class implementing runnable that continually listens for input and updates
	 * the ui accordingly
	 * 
	 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
	 *
	 * @version 2/16/20
	 */
	public class InputListener implements Runnable {

		/**
		 * Object input stream to listen for GameState objects on
		 */
		private ObjectInputStream in;

		/**
		 * Input listener constructor, assigned the object input stream
		 * 
		 * @param in the object input stream
		 */
		public InputListener(ObjectInputStream in) {
			this.in = in;
		}

		/**
		 * continually listens for input and updates the UI accordingly
		 */
		@Override
		public void run() {
			boolean gameOver = false;
			try {
				while (!gameOver) {
					GameState state = (GameState) in.readObject();
					gameOver = state.isGameOver();
					if (ui.getClass().equals(Connect4Gui.class)) {
						Platform.runLater(() -> {
							updateUI(state);
						});
					} else {
						updateUI(state);
					}
				}
				close();
			} catch (IOException e) {
				System.err.println("Error reading the input from the data stream");
			} catch (ClassNotFoundException e) {
				System.err.println("The recieved object was not of the proper type (Must be GameState)");
			}
		}
	}
}
