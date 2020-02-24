package test;

import core.Player;
import network.GameThread;

public class GameThreadStub extends GameThread {

	public GameThreadStub(Player player1, Player player2) {
		super(player1, player2);
	}

	@Override
	public void updateClient(String status, boolean gameOver, int[][] board, boolean turn, Player player) {

	}
}
