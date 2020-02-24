package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import network.GameState;

class GameStateTest {

	GameState state1;
	GameState state2;
	GameState state3;
	GameState state4;

	@BeforeEach
	public void setUp() throws Exception {
		state1 = new GameState("Status", false, Constants.boardEmpty, false);
		state2 = new GameState("Turn: Player O", true, Constants.board2, true);
		state3 = new GameState("Turn: Player X", true, Constants.board3, false);
		state4 = new GameState("", false, new int[7][6], true);
	}

	@AfterEach
	void tearDown() throws Exception {
		state1 = null;
		state2 = null;
		state3 = null;
		state4 = null;
	}

	@Test
	void testGetStatus() {
		assert (state1.getStatus().equals("Status"));
		assert (state2.getStatus().equals("Turn: Player O"));
		assert (state3.getStatus().equals("Turn: Player X"));
		assert (state4.getStatus().equals(""));
	}

	@Test
	void testIsGameOver() {
		assert (!state1.isGameOver());
		assert (state2.isGameOver());
		assert (state3.isGameOver());
		assert (!state4.isGameOver());
	}

	@Test
	void testGetBoard() {

		assert (compareBoards(state1.getBoard(), Constants.boardEmpty));
		assert (compareBoards(state2.getBoard(), Constants.board2));
		assert (compareBoards(state3.getBoard(), Constants.board3));
		assert (compareBoards(state4.getBoard(), Constants.boardEmpty));
	}

	Boolean compareBoards(int[][] board1, int[][] board2) {
		Boolean result = true;
		if (board1.length != board2.length || board1[0].length != board2[0].length) {
			result = false;
		} else {
			for (int col = 0; col < board1.length; col++) {
				for (int row = 0; row < board1[0].length; row++) {
					if (board1[col][row] != board2[col][row]) {
						result = false;
					}
				}
			}
		}
		return result;
	}

	@Test
	void testGetTurn() {
		assert (!state1.getTurn());
		assert (state2.getTurn());
		assert (!state3.getTurn());
		assert (state4.getTurn());
	}

}
