package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import network.Connect4Client;

/**
 * The text console ui class for the Connect4 game
 *
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/9/20
 */
public class Connect4TextConsole implements UI {

	/**
	 * The object representing the model of the game
	 */
	@SuppressWarnings("unused")
	private Connect4Client client;

	public void run() {
		Scanner in = new Scanner(System.in);
		boolean computer = getComputer(in);
		this.client = new Connect4Client(this, computer);
	}

	public boolean getComputer(Scanner in) {
		System.out.print("Enter ‘P’ if you want to play against another player or ‘C’ to play against a computer: ");
		String answer = in.next().toLowerCase();
		while (!answer.equals("p") && !answer.equals("c")) {
			System.out.print(
					"Invalid input. Enter ‘P’ if you want to play against another player or ‘C’ to play against a computer: ");
			answer = in.next().toLowerCase();
		}
		System.out.println();
		return answer.equals("c");
	}

	@Override
	public void displayStatus(String status) {
		System.out.println(status);
	}

	@Override
	public void displayBoard(int[][] board) {
		String result = "";

		String border = "|";
		String empty = " ";
		for (int row = board[0].length - 1; row >= 0; row--) {
			result += border;
			for (int col = 0; col < board.length; col++) {
				if (board[col][row] == 1) {
					result += "X";
				} else if (board[col][row] == 2) {
					result += "O";
				} else {
					result += empty;
				}
				result += border;
			}
			result += "\n";
		}
		result += " 1 2 3 4 5 6 7 \n";
		System.out.println(result);
	}

	public int getCol() {
		Scanner in = new Scanner(System.in);
		return getCol(in);
	}

	public int getCol(Scanner in) {
		boolean valid = false;
		int col = -1;
		while (!valid) {
			try {
				System.out.print("Please select a column (1-7): ");
				col = in.nextInt();
				if (col < 1 || col > 6) {
					throw new InputMismatchException();
				}
				col--;
				valid = true;
			} catch (InputMismatchException e) {
				System.out.println();
				System.out.println("That is not a valid input. Try again.");
				in.nextLine();
			}
		}
		System.out.println();
		return col;
	}

	/**
	 * Main method for launching the text ui version of the game directly
	 *
	 * @param args none
	 */
	public static void main(String[] args) {
		new Connect4TextConsole();
	}
}
