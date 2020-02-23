package network;

import java.io.Serializable;

import core.Connect4;

/**
 * An object to be sent to the client to update the ui.
 * 
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/16/20
 */
public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Text to be placed at the bottom of the game (indicated turn, win / tie)
	 */
	private String status;

	/**
	 * Indicates to the player if the game is over or not
	 */
	private boolean gameOver;

	/**
	 * board A 2d array to be rendered on the clients application
	 */
	private int[][] board;

	private boolean turn;

	/**
	 * Creates a gameState object to be sent to the client
	 * 
	 * @param status   Text to be placed at the bottom of the game (indicated turn,
	 *                 win / tie)
	 * @param gameOver Indicates to the player if the game is over or not
	 * @param board    board A 2d array to be rendered on the clients application
	 * @param turn     true if is the players turn, false if not
	 */
	public GameState(String status, boolean gameOver, int[][] board, boolean turn) {
		this.status = status;
		this.gameOver = gameOver;
		this.turn = turn;

		this.board = new int[Connect4.NUM_COLS][Connect4.NUM_ROWS];
		for (int col = 0; col < Connect4.NUM_COLS; col++) {
			for (int row = 0; row < Connect4.NUM_ROWS; row++) {
				this.board[col][row] = board[col][row];
			}
		}
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return is gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @return the board
	 */
	public int[][] getBoard() {
		return board;
	}

	public boolean getTurn() {
		return turn;
	}
}
