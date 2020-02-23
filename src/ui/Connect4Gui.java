package ui;

import java.util.Optional;

import core.Connect4;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import network.Connect4Client;

/**
 * The GUI for the Connect 4 game for the Connect4 game
 *
 * @author Ryan Cluff, rpcluff, rpcluff@asu.edu
 *
 * @version 2/9/20
 */
public class Connect4Gui extends Application implements UI {

	/**
	 * The board represented by a 2D array of custom panes. The idea to implement
	 * the board this way was from the tictactoe.java example
	 */
	private Cell[][] boardUI = new Cell[Connect4.NUM_COLS][Connect4.NUM_ROWS];

	/**
	 * An array of buttons to indicate the players column selection
	 */
	private Button[] buttons = new Button[Connect4.NUM_COLS];

	/**
	 * The status label at the bottom of the ui. Used to indicate turns and if the
	 * game has been won or tied.
	 */
	private Label status = new Label(" ");

	/**
	 * The object representing the model of the game
	 */
	private Connect4Client client;

	/**
	 * Starts the Javafx application
	 *
	 * @param primaryStage the main stage which the ui will be placed
	 */
	@Override
	public void start(Stage primaryStage) {

		GridPane grid = new GridPane();
		for (int col = 0; col < Connect4.NUM_COLS; col++) {
			buttons[col] = new Button(Integer.toString(col + 1));
			buttons[col].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			GridPane.setFillWidth(buttons[col], true);
			GridPane.setFillHeight(buttons[col], true);
			grid.add(buttons[col], col, 0);
			buttons[col].setOnAction(new SelectColumnHandler());
		}

		for (int col = 0; col < Connect4.NUM_COLS; col++) {
			for (int row = 0; row < Connect4.NUM_ROWS; row++) {
				grid.add(boardUI[col][row] = new Cell(), col, row + 1);
			}
		}

		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(grid);
		mainPane.setBottom(status);

		Scene scene = new Scene(mainPane, 700, 700);
		primaryStage.setTitle("Connect 4");
		primaryStage.setScene(scene);
		primaryStage.show();

		boolean computer = getComputer();

		client = new Connect4Client(this, computer);
	}

	public boolean getComputer() {
		boolean computer = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Number of players");
		alert.setHeaderText("Select number of players");
		alert.setContentText("");

		ButtonType buttonTypeOne = new ButtonType("One");
		ButtonType buttonTypeTwo = new ButtonType("Two");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			computer = true;
		} else if (result.get() == buttonTypeTwo) {
			computer = false;
		} else {
			System.exit(0);
		}

		return computer;
	}

	@Override
	public void displayStatus(String statusText) {
		status.setText(statusText);
	}

	@Override
	public void displayBoard(int[][] board) {
		for (int col = 0; col < Connect4.NUM_COLS; col++) {
			int rowUI = 5;
			for (int row = 0; row < Connect4.NUM_ROWS; row++) {
				if (board[col][row] == 1) {
					boardUI[col][rowUI].set(1);
				} else if (board[col][row] == 2) {
					boardUI[col][rowUI].set(2);
				}
				rowUI--;
			}
		}
	}

	/**
	 * Main method for launching the gui version of the game directly
	 *
	 * @param args none
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * A class used to represent the individual spaces on the board
	 */
	public class Cell extends Pane {
		/**
		 * The constructor for the object. Sets the borders to black and the prefered
		 * size to 2000x2000
		 */
		public Cell() {
			this.setStyle("-fx-border-color: black");
			this.setPrefSize(2000, 2000);
		}

		/**
		 * Sets the space to the desired icon, either 'X' or 'O'. Credit for this
		 * implementation to the tictactoe.java example
		 *
		 * @param playerNum the number of the player (1 for X, 2 for O)
		 */
		public void set(int playerNum) {
			final int OFFSET = 10;
			if (playerNum == 1) {
				Line x1 = new Line(OFFSET, OFFSET, this.getWidth() - OFFSET, this.getHeight() - OFFSET);
				x1.endXProperty().bind(this.widthProperty().subtract(OFFSET));
				x1.endYProperty().bind(this.heightProperty().subtract(OFFSET));
				Line x2 = new Line(OFFSET, this.getHeight() - OFFSET, this.getWidth() - OFFSET, OFFSET);
				x2.startYProperty().bind(this.heightProperty().subtract(OFFSET));
				x2.endXProperty().bind(this.widthProperty().subtract(OFFSET));
				this.getChildren().addAll(x1, x2);
			} else if (playerNum == 2) {
				Ellipse o = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - OFFSET,
						this.getHeight() / 2 - OFFSET);
				o.centerXProperty().bind(this.widthProperty().divide(2));
				o.centerYProperty().bind(this.heightProperty().divide(2));
				o.radiusXProperty().bind(this.widthProperty().divide(2).subtract(OFFSET));
				o.radiusYProperty().bind(this.heightProperty().divide(2).subtract(OFFSET));
				o.setStroke(Color.BLACK);
				o.setFill(null);
				getChildren().add(o);
			}
		}
	}

	/**
	 * Handler class for the column buttons
	 */
	class SelectColumnHandler implements EventHandler<ActionEvent> {

		/**
		 * How to handle a button being pressed. send the column - 1 to the game via the
		 * playTurn method.
		 *
		 * @param e the event triggered by the button
		 */
		@Override
		public void handle(ActionEvent e) {
			if (e.getSource().getClass().equals(Button.class)) {
				client.playTurn(Integer.parseInt(((Button) e.getSource()).getText()) - 1);
			}
		}
	}
}
