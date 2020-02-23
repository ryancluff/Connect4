package clientTest;

public class Constants {
	public static final int[][] boardEmpty = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };

	public static final int[][] board2 = { { 1, 0, 0, 0, 0, 0 }, { 1, 2, 0, 0, 0, 0 }, { 1, 1, 2, 2, 2, 1 },
			{ 2, 2, 1, 1, 0, 0 }, { 1, 1, 2, 0, 0, 0 }, { 1, 2, 0, 0, 0, 0 }, { 2, 2, 0, 0, 0, 0 } };

	public static final int[][] board3 = { { 1, 0, 0, 0, 0, 0 }, { 1, 2, 0, 0, 0, 0 }, { 1, 1, 2, 2, 2, 0 },
			{ 2, 2, 1, 1, 0, 0 }, { 1, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0 }, { 2, 2, 0, 0, 0, 0 } };

	public static final int[][] board4 = { { 1, 1, 0, 0, 0, 0 }, { 1, 2, 1, 1, 0, 0 }, { 1, 1, 2, 2, 2, 1 },
			{ 2, 2, 1, 1, 0, 0 }, { 1, 1, 2, 2, 0, 0 }, { 1, 2, 2, 0, 0, 0 }, { 2, 2, 0, 0, 0, 0 }, };

	public static final String boardEmptyConsoleRep = "| | | | | | | |\n| | | | | | | |\n| | | | | | | |\n| | | | | | | |\n| | | | | | | |\n| | | | | | | |\n 1 2 3 4 5 6 7 \n\r\n";

	public static final String board2ConsoleRep = "| | |X| | | | |\n| | |O| | | | |\n| | |O|X| | | |\n| | |O|X|O| | |\n| |O|X|O|X|O|O|\n|X|X|X|O|X|X|O|\n 1 2 3 4 5 6 7 \n\r\n";

	public static final String board3ConsoleRep = "| | | | | | | |\n| | |O| | | | |\n| | |O|X| | | |\n| | |O|X| | | |\n| |O|X|O| | |O|\n|X|X|X|O|X|X|O|\n 1 2 3 4 5 6 7 \n\r\n";

	public static final String board4ConsoleRep = "| | |X| | | | |\n| | |O| | | | |\n| |X|O|X|O| | |\n| |X|O|X|O|O| |\n|X|O|X|O|X|O|O|\n|X|X|X|O|X|X|O|\n 1 2 3 4 5 6 7 \n\r\n";
}
