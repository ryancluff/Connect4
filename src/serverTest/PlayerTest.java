package serverTest;

import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Player;
import network.Connect4Server;

class PlayerTest {

	static Connect4Server server;
	Player player1;
	Player player2;
	Player computer;
	Socket socket1;
	Socket socket2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		server = new Connect4Server();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		server = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		Socket socket1 = new Socket("localhost", Connect4Server.PORT);
		player1 = new Player(socket1);
		player2 = new Player(socket1);
		player2.setPlayer2();
		computer = new Player();
	}

	@AfterEach
	void tearDown() throws Exception {
		socket1.close();
		socket2.close();
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
	void testGetIn() {
	}

	@Test
	void testGetOut() {
	}

	@Test
	void testClose() {
	}

}
