package core;

import java.util.Scanner;

import javafx.application.Application;
import ui.Connect4Gui;
import ui.Connect4TextConsole;

/**
 * The main class which starts the programs. Prompts the user for ui selection
 * on the console
 *
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/1/20
 */
public class Main {

	/**
	 * Starts the program.
	 *
	 * @param args none
	 */
	public static void main(String[] args) {
		new Main().run();
	}

	public void run() {
		Scanner in = new Scanner(System.in);
		boolean gui = getGui(in);
		if (gui) {
			Application.launch(Connect4Gui.class);
		} else {
			new Connect4TextConsole().run();
		}
	}

	/**
	 * Prompts the user for either a gui or text based ui
	 *
	 * @return true if the user wants a gui, false if text ui
	 */
	public boolean getGui(Scanner in) {
		System.out.print("Welcome! Enter ‘G’ for GUI or ’T’ for text UI: ");
		String answer = in.next().toLowerCase();
		while (!answer.equals("t") && !answer.equals("g")) {
			System.out.print("Invalid input. Enter ‘G’ for GUI or ’T’ for text UI: ");
			answer = in.next().toLowerCase();
		}
		System.out.println();
		return answer.equals("g");
	}
}
