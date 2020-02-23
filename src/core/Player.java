package core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * An object that contains the players name, their number, an icon, and input
 * and output data streams
 *
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/16/20
 */
public class Player {

	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;

	/**
	 * Name of the player
	 */
	private String name = "";

	/**
	 * Number of the player
	 */
	private int num = 0;

	/**
	 * Icon used to display the player's pieces
	 */
	private String icon = "";

	/**
	 * Indicates if the player is a computer
	 */
	private boolean computer = false;

	/**
	 * Socket the player started
	 */
	private Socket socket;

	/**
	 * Input stream, used for read which column the player wishes to play a piece
	 */
	private DataInputStream in;

	/**
	 * Output stream, used to update the client application
	 */
	private ObjectOutputStream out;

	/**
	 * The Player constructor.
	 * 
	 * @param socket the socket used for communication with the players client
	 *               application
	 */
	public Player(Socket socket) {
		this.icon = "X";
		this.name = "Player " + this.icon;
		this.num = PLAYER_1;
		this.computer = false;

		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			// TODO: add error message
		}
	}

	/**
	 * Constructor for creating computer players
	 */
	public Player() {
		this.icon = "O";
		this.name = "Computer " + this.icon;
		this.num = PLAYER_2;
		this.computer = true;
	}

	/**
	 * name accessor method
	 *
	 * @return name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Number accessor method
	 *
	 * @return the number of the player
	 */
	public int getNum() {
		return this.num;
	}

	/**
	 * Icon accessor method
	 *
	 * @return the String to represent the players pieces on the board
	 */
	public String getIcon() {
		return this.icon;
	}

	/**
	 * Computer accessor method.
	 *
	 * @return Returns true if the player is controlled by the computer, false if
	 *         controlled by player
	 */
	public boolean isComputer() {
		return this.computer;
	}

	/**
	 * Sets an already created player to player 2. The player name will be changed
	 * to Player O and the player number will be set to 2
	 */
	public void setPlayer2() {
		this.icon = "O";
		this.name = "Player " + this.icon;
		this.num = PLAYER_2;
	}

	/**
	 * Gets the data input stream
	 * 
	 * @return in
	 */
	public DataInputStream getIn() {
		return in;
	}

	/**
	 * Gets the data output stream
	 * 
	 * @return out
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * closes the socket, input, and output data streams
	 */
	public void close() {
		if (!computer) {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
