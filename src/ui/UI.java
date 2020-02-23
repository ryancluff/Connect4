package ui;

/**
 * The ui interface for the Connect4 game
 *
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/1/20
 */
public interface UI {

	/**
	 * Sets the status text for the UI
	 * 
	 * @param status text to be displayed
	 */
	public void displayStatus(String status);

	/**
	 * Prints a representation of the board using pipe and space characters as well
	 * as the two icons specified by the game itself
	 *
	 * @param board a 2D array representing the board
	 */
	public void displayBoard(int[][] board);
}
