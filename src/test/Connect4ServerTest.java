package test;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class Connect4ServerTest {

	static Connect4ServerRunner serverRunner;

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

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	void testRunComputer() {
		Connect4ClientDriver clientComputer1 = new Connect4ClientDriver();
		clientComputer1.start("localHost", 8000, true);

		assert (clientComputer1.getStatus().equals("Starting game with computer"));
		assert (!clientComputer1.getGameOver());
		assert (!clientComputer1.getTurn());
	}

	@Test
	void testRun2P() {
		Connect4ClientDriver client1P = new Connect4ClientDriver();
		client1P.start("localHost", 8000, false);

		Connect4ClientDriver client2P = new Connect4ClientDriver();
		client2P.start("localHost", 8000, false);

		assert (client1P.getStatus().equals("Waiting for another player to join . . ."));
		assert (!client1P.getGameOver());
		assert (!client1P.getTurn());

		assert (client2P.getStatus().startsWith("Starting game with client "));
		assert (!client2P.getGameOver());
		assert (!client2P.getTurn());
	}

	@Test
	void testRunMixed() {
		Connect4ClientDriver client1P = new Connect4ClientDriver();
		client1P.start("localHost", 8000, false);

		Connect4ClientDriver client2P = new Connect4ClientDriver();
		client2P.start("localHost", 8000, false);

		Connect4ClientDriver clientComputer1 = new Connect4ClientDriver();
		clientComputer1.start("localHost", 8000, true);

		assert (client1P.getStatus().equals("Waiting for another player to join . . ."));
		assert (!client1P.getGameOver());
		assert (!client1P.getTurn());

		assert (clientComputer1.getStatus().equals("Starting game with computer"));
		assert (!clientComputer1.getGameOver());
		assert (!clientComputer1.getTurn());

		assert (client2P.getStatus().startsWith("Starting game with client "));
		assert (!client2P.getGameOver());
		assert (!client2P.getTurn());
	}
}
