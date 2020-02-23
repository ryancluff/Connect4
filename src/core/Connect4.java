package core;

import network.GameThread;

/**
 * The core components of the connect4 game.
 *
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/16/20
 */
public class Connect4 {
	/**
	 * Number of column for the Connect 4 board
	 */
	public static final int NUM_COLS = 7;

	/**
	 * Number of rows for the Connect 4 board
	 */
	public static final int NUM_ROWS = 6;

	/**
	 * A 2D array representing the Connect4 board
	 */
	private int[][] board;

	/**
	 * The user interface for the game
	 */
	private GameThread gameThread;

	/**
	 * Player object representing the first player (Will always be human player)
	 */
	private Player player1;

	/**
	 * Player object representing the second player (Can be human player or computer
	 * player)
	 */
	private Player player2;

	/**
	 * Indicates whether the game has been won
	 */
	private boolean win;

	/**
	 * Indicates whether the game has resulted in a tie (The board is full, no
	 * possible moves)
	 */
	private boolean tie;

	/**
	 * Used to determine which player's turn it currently is (true for player 1,
	 * false for player 2)
	 */
	boolean player1Turn;

	/**
	 * Represents the current player (A reference variable used to point to the
	 * current player)
	 */
	Player current;

	/**
	 * Represents the player which is not the current player (A reference variable
	 * used to point to the not current player)
	 */
	Player notCurrent;

	/**
	 * Constructor for the game. Initializes a 7x6 board and two players. Also sets
	 * up the current player and prints the initial board and player 1's turn prompt
	 * 
	 * @param player1    Player object representing the first player (Will always be
	 *                   human player)
	 * @param player2    Player object representing the second player (Can be human
	 *                   player or computer player)
	 * @param gameThread a thread managing the game for both clients. Used to send
	 *                   ui updates
	 */
	public Connect4(Player player1, Player player2, GameThread gameThread) {
		board = new int[NUM_COLS][NUM_ROWS];

		this.player1 = player1;
		this.player2 = player2;
		this.gameThread = gameThread;

		player1Turn = true;

		current = player1Turn ? player1 : player2;
		notCurrent = !player1Turn ? player1 : player2;

		gameThread.updateClient(turn(current.getName()), false, board, true, current);
		gameThread.updateClient(turn(current.getName()), false, board, false, notCurrent);
	}

	/**
	 * Return if the selected column is valid for play. Checks if it is in bounds
	 * and if the column if full
	 *
	 * @param col the column to check
	 * @return if the play is valid or not
	 */
	private boolean validPlay(int col) {
		boolean result = true;
		if (col < 0 || col >= NUM_COLS) {
			result = false;
		} else if (board[col][board[col].length - 1] != 0) {
			result = false;
		}
		return result;
	}

	/**
	 * Returns the row the piece will fall to in the selected column
	 *
	 * @param col the selected column
	 * @return the row in which the piece would end in (the lowest empty row in the
	 *         selected column
	 */
	private int getRow(int col) {
		int row = 0;
		int rowContents = board[col][row];
		while (rowContents != 0) {
			row++;
			rowContents = board[col][row];
		}
		return row;
	}

	/**
	 * Checks if the most recently added piece will result in a win
	 *
	 * @param playerNum the number of the player that placed the piece
	 * @param col       the selected column
	 * @param row       the row the piece would fall to
	 * @return true if the game has been won, false if not
	 */
	private boolean checkForWin(int playerNum, int col, int row) {
		boolean result = false;
		// Horizontal check
		int count = 0;
		for (int i = 0; i < board.length; i++) {

			if (board[i][row] == playerNum) {
				count++;
				if (count >= 4) {
					result = true;
				}
			} else {
				count = 0;
			}

		}

		// Vertical check
		count = 0;
		for (int i = 0; i < board[col].length; i++) {
			if (board[col][i] == playerNum) {
				count++;
				if (count >= 4) {
					result = true;
				}
			} else {
				count = 0;
			}
		}

		// Rising diagonal check
		int colRoot = col;
		int rowRoot = row;
		while (colRoot > 0 && rowRoot > 0) {
			rowRoot--;
			colRoot--;
		}
		count = 0;
		while (colRoot < board.length && rowRoot < board[colRoot].length) {
			if (board[colRoot][rowRoot] == playerNum) {
				count++;
				if (count >= 4) {
					result = true;
				}
			} else {
				count = 0;
			}
			rowRoot++;
			colRoot++;
		}

		// Falling diagonal check
		colRoot = col;
		rowRoot = row;
		while (colRoot > 0 && rowRoot < board[colRoot].length - 1) {
			rowRoot++;
			colRoot--;
		}
		count = 0;
		while (colRoot < board.length && rowRoot >= 0) {
			if (board[colRoot][rowRoot] == playerNum) {
				count++;
				if (count >= 4) {
					result = true;
				}
			} else {
				count = 0;
			}
			rowRoot--;
			colRoot++;
		}

		return result;
	}

	/**
	 * Checks if the board is full
	 *
	 * @return true if the board is full, false if not
	 */
	private boolean checkForTie() {
		boolean result = true;
		for (int col = 0; col < board.length; col++) {
			if (board[col][board[col].length - 1] == 0) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * Returns the column the computer wants to place a piece. Computer will take
	 * into account which columns will result in a win for either itself or the
	 * player.
	 *
	 * @return selected column
	 */
	public int computerPlay() {
		int selected = -1;
		boolean win = false;
		for (int col = 0; col < NUM_COLS && !win; col++) {
			if (validPlay(col)) {
				int row = getRow(col);
				board[col][row] = 2;
				win = checkForWin(2, col, row);
				board[col][row] = 0;
				if (win) {
					selected = col;
				} else {
					board[col][row] = 1;
					if (checkForWin(1, col, row)) {
						selected = col;
					}
					board[col][row] = 0;
				}
			}
		}
		while (!validPlay(selected)) {
			selected = (int) Math.ceil(Math.random() * NUM_COLS);
		}
		return selected;
	}

	/**
	 * Generates win status message
	 * 
	 * @param name the winner
	 * @return name + " has won the game!"
	 */
	private String win(String name) {
		return name + " has won the game!";
	}

	/**
	 * Generates the tie status message
	 * 
	 * @return "The board is now full, the result of the game is a tie!"
	 */
	private String tie() {
		return "The board is now full, the result of the game is a tie!";
	}

	/**
	 * Generates the status message indicating which players turn it is
	 * 
	 * @param name whose turn it is
	 * @return "Turn: " + name
	 */
	private String turn(String name) {
		return "Turn: " + name;
	}

	/**
	 * Generates a message indicating that the selected column in invalid for play
	 * 
	 * @param col  invalid column (zero indexed)
	 * @param name name of player
	 * @return "Column " + (col + 1) + " is not valid for play. Turn: " + name
	 */
	private String invalidCol(int col, String name) {
		return "Column " + (col + 1) + " is not valid for play. " + turn(name);
	}

	/**
	 * Gets the board model
	 * 
	 * @return the 2d array containing the board
	 */
	public int[][] getBoard() {
		return this.board;
	}

	/**
	 * Gets the current player whose turn it is
	 * 
	 * @return current player whose turn it is
	 */
	public Player getCurrent() {
		return this.current;
	}

	/**
	 * Process the turn for the current player. Will also process the following turn
	 * if player 2 is a computer
	 *
	 * @param col    the column that the player has selected.
	 * @param player the player that selected the column
	 * @return true if the game is over (Someone has either won or the board is
	 *         full)
	 */
	public boolean playTurn(int col, Player player) {
		boolean gameOver = false;
		String status = "";
		if (current.equals(player)) {
			boolean valid = validPlay(col);
			if (valid) {
				int row = getRow(col);
				board[col][row] = current.getNum();
				win = checkForWin(current.getNum(), col, row);
				tie = checkForTie();
				if (win) {
					gameOver = true;
					status = win(current.getName());
				} else if (tie) {
					gameOver = true;
					status = tie();
				} else {
					player1Turn = !player1Turn;
					current = player1Turn ? player1 : player2;
					notCurrent = !player1Turn ? player1 : player2;
				}
				if (!gameOver) {
					status = turn(current.getName());
				}

			} else {
				status = invalidCol(col, current.getName());
			}
			gameThread.updateClient(status, gameOver, board, true, current);
			gameThread.updateClient(status, gameOver, board, false, notCurrent);

			if (!gameOver && current.isComputer()) {
				gameOver = playTurn(computerPlay(), current);
			}
		}
		return gameOver;
	}
}
