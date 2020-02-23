package clientTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ui.Connect4TextConsole;

class TextConsoleTest {

	private ByteArrayInputStream inputStream;
	private ByteArrayOutputStream outputStream;
	private InputStream sysInBackup;
	private PrintStream sysOutBackup;
	private Scanner in;
	private Connect4TextConsole ui;

	@BeforeEach
	void setUp() throws Exception {
		sysOutBackup = System.out;
		sysInBackup = System.in;
		in = new Scanner(System.in);
		outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		ui = new Connect4TextConsole();
	}

	@AfterEach
	void tearDown() throws Exception {
		in.close();
		System.setIn(sysInBackup);
		System.setOut(sysOutBackup);

		inputStream = null;
		outputStream = null;
		sysInBackup = null;
		sysOutBackup = null;
		in = null;
		ui = null;
	}

	Scanner setupInput(String input) {
		inputStream = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputStream);
		return new Scanner(System.in);
	}

	@Test
	void testGetComputer1() {
		in = setupInput("c\n");
		ui = new Connect4TextConsole();
		assert (ui.getComputer(in));
	}

	@Test
	void testGetComputer2() {
		in = setupInput("C\n");
		ui = new Connect4TextConsole();
		assert (ui.getComputer(in));
		in.close();
		System.setIn(sysInBackup);
	}

	@Test
	void testGetComputer3() {
		in = setupInput("p\n");
		ui = new Connect4TextConsole();
		assert (!ui.getComputer(in));
	}

	@Test
	void testGetComputer4() {
		in = setupInput("P\n");
		ui = new Connect4TextConsole();
		assert (!ui.getComputer(in));
	}

	@Test
	void testGetComputer5() {
		in = setupInput("a\nf\nC\n");
		ui = new Connect4TextConsole();
		assert (ui.getComputer(in));
	}

	@Test
	void testDisplayStatus1() {
		String input = "";
		ui.displayStatus(input);
		String result = outputStream.toString();
		assert (result.equals(input + "\r\n"));
	}

	@Test
	void testDisplayStatus2() {
		String input = "Turn: Player X";
		ui.displayStatus(input);
		String result = outputStream.toString();
		assert (result.equals(input + "\r\n"));
	}

	@Test
	void testDisplayBoard1() {
		ui.displayBoard(Constants.boardEmpty);
		String result = outputStream.toString();
		assert (result.equals(Constants.boardEmptyConsoleRep));
	}

	@Test
	void testDisplayBoard2() {
		ui.displayBoard(Constants.board2);
		String result = outputStream.toString();
		assert (result.equals(Constants.board2ConsoleRep));
	}

	@Test
	void testDisplayBoard3() {
		ui.displayBoard(Constants.board3);
		String result = outputStream.toString();
		assert (result.equals(Constants.board3ConsoleRep));
	}

	@Test
	void testDisplayBoard4() {
		ui.displayBoard(Constants.board4);
		String result = outputStream.toString();
		assert (result.equals(Constants.board4ConsoleRep));
	}

	@Test
	void testGetCol1() {
		in = setupInput("3\n");
		int col = ui.getCol(in);
		assert (col == 2);
	}

	@Test
	void testGetCol2() {
		in = setupInput("0\n1\n");
		int col = ui.getCol(in);
		assert (col == 0);
	}
}
