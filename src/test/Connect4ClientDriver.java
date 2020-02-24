package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import network.Connect4Server;
import network.GameState;

public class Connect4ClientDriver {
	/**
	 * A data output stream for indicating which columns to select
	 */
	private DataOutputStream out;

	/**
	 * An object input stream for recieve game state object with which to update the
	 * ui
	 */
	private ObjectInputStream in;

	GameState state;

	/**
	 * Socket used for network communication
	 */
	private Socket socket;

	public void start(String hostname, int port, Boolean vsComputer) {
		try {
			socket = new Socket("localhost", Connect4Server.PORT);
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			out.writeBoolean(vsComputer);
			state = (GameState) in.readObject();
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
	}

	public String getStatus() {
		return state.getStatus();
	}

	public Boolean getTurn() {
		return state.getTurn();
	}

	public Boolean getGameOver() {
		return state.isGameOver();
	}

	public GameState recieve() {
		GameState state = null;
		try {
			state = (GameState) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return state;
	}

	public void sendCol(int col) {
		try {
			out.writeInt(col);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
