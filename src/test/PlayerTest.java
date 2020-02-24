package test;

import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Player;

class PlayerTest {

	static Connect4ServerRunner serverRunner;
	Player player1;
	Player player2;
	Player computer;
	Socket socket1;
	Socket socket2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		serverRunner = new Connect4ServerRunner();
		new Thread(serverRunner).start();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		serverRunner = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		Socket socket1 = new Socket("localhost", 8000);
		Socket socket2 = new Socket("localhost", 8000);
		player1 = new Player(socket1);
		player2 = new Player(socket2);
		player2.setPlayer2();
		computer = new Player();
	}

	@AfterEach
	void tearDown() throws Exception {
		socket1 = null;
		socket2 = null;
		player1 = null;
		player2 = null;
		computer = null;
	}

	@Test
	void testGetName() {
		assert (player1.getName().equals("Player X"));
		assert (player2.getName().equals("Player O"));
		assert (computer.getName().equals("Computer O"));
	}

	@Test
	void testGetNum() {
		assert (player1.getNum() == 1);
		assert (player2.getNum() == 2);
		assert (computer.getNum() == 2);
	}

	@Test
	void testGetIcon() {
		assert (player1.getIcon().equals("X"));
		assert (player2.getIcon().equals("O"));
		assert (computer.getIcon().equals("O"));
	}

	@Test
	void testIsComputer() {
		assert (!player1.isComputer());
		assert (!player2.isComputer());
		assert (computer.isComputer());
	}

	@Test
	void testSetPlayer2() {
		assert (player1.getName().equals("Player X"));
		assert (player1.getNum() == 1);
		assert (player1.getIcon().equals("X"));
		assert (!player1.isComputer());
		player1.setPlayer2();
		assert (player1.getName().equals("Player O"));
		assert (player1.getNum() == 2);
		assert (player1.getIcon().equals("O"));
		assert (!player1.isComputer());
	}

	@Test
	void getIconX() {
		assert (player1.getIcon().equals("X"));
		assert (player2.getIcon().equals("O"));
	}

	@Test
	void testClose() {
		player1.close();
		player2.close();
	}

}
