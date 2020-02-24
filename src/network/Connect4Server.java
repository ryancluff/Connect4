package network;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.Player;

/**
 * Server for hosting multiple connect 4 games. Spins up a thread for each of
 * the games (1 player or 2 player)
 * 
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/16/20
 */
public class Connect4Server extends JFrame {

	private static final long serialVersionUID = 2587745038047440942L;

	/**
	 * Port that the server is listening on
	 */
	public static final int PORT = 8000;

	private JTextArea textArea = new JTextArea();

	/**
	 * Starts the server
	 * 
	 * @param args none
	 */
	public static void main(String[] args) {
		new Connect4Server().run();
	}

	/**
	 * Creates the server, listens on the specified port. The assigns the player to
	 * a one or two player game depending on an initial boolean value sent by the
	 * client.
	 */
	public void run() {
		boolean playerIsWaiting = false;
		Player waitingPlayer = null;
		int clientWaitingNo = -1;
		ServerSocket serverSocket = null;

		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		setTitle("Connect4 Game Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try {
			serverSocket = new ServerSocket(PORT);
			textArea.append("Server started on port " + PORT + "\n");

			int clientNo = 1;

			while (true) {

				// Listen for a new connection request
				Socket socket = serverSocket.accept();

				InetAddress inetAddress = socket.getInetAddress();
				textArea.append("Client " + clientNo + " has joined (" + inetAddress.getHostName() + ", "
						+ inetAddress.getHostAddress() + ")\n");
				Player player = new Player(socket);

				boolean onePlayer = player.getIn().readBoolean();

				if (onePlayer) {
					textArea.append("Single player game starting for client " + clientNo + "\n");
					Player computer = new Player();
					player.getOut()
							.writeObject(new GameState("Starting game with computer", false, new int[7][6], false));
					GameThread gameThread = new GameThread(player, computer);
					new Thread(gameThread).start();
				} else {
					if (playerIsWaiting) {
						textArea.append(
								"Two player game starting for clients " + clientWaitingNo + " and " + clientNo + "\n");
						player.setPlayer2();
						player.getOut().writeObject(
								new GameState("Starting game with client " + clientNo, false, new int[7][6], false));
						GameThread gameThread = new GameThread(waitingPlayer, player);
						new Thread(gameThread).start();
						playerIsWaiting = false;
						waitingPlayer = null;

					} else {
						textArea.append("Client " + clientNo + " is waiting for another player to join\n");
						playerIsWaiting = true;
						waitingPlayer = player;
						clientWaitingNo = clientNo;
						player.getOut().writeObject(
								new GameState("Waiting for another player to join . . .", false, new int[7][6], false));
					}
				}

				clientNo++;
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
