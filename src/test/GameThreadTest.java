package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Player;
import network.GameThread;

class GameThreadTest {

	static Connect4ServerRunner serverRunner;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		serverRunner = new Connect4ServerRunner();
		new Thread(serverRunner).start();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGameThread() {
		Player computer1 = new Player();
		Player computer2 = new Player();
		GameThread game = new GameThread(computer1, computer2);
		game.run();
		game.close();
	}

	@Test
	void testRun() {
		Connect4ClientDriver client1P = new Connect4ClientDriver();
		client1P.start("localHost", 8000, false);

		Connect4ClientDriver client2P = new Connect4ClientDriver();
		client2P.start("localHost", 8000, false);

		client1P.recieve();
		client2P.recieve();

		client1P.sendCol(0);
		client2P.sendCol(1);
	}

}
