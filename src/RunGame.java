import java.util.Scanner;

public class RunGame {

	private static Checkers gameCheckers;

	public static void main (String args[])
	{
		Scanner input = new Scanner(System.in);
		String playerColor;
		int playerCount;
		int aiDifficulty = 0;
		boolean hasDifficulty = false;
		boolean hasColor = false;
		
		System.out.println("1 or 2 Players?");

		while (input.hasNextInt()) {

			try {
				playerCount = input.nextInt();

				if(playerCount == 1) {

					System.out.println("What Difficulty? 1 - 5");

					while(!hasDifficulty) {

						try {
							aiDifficulty = input.nextInt();

							if(aiDifficulty >= 1 && aiDifficulty <= 5) {
								hasDifficulty = true;
							}

						} catch (Exception e) {
							System.out.println("Invalid input. Try again.");
						}
					}

					System.out.println("Would you like to be Red or Black?");

					while(!hasColor) {

						try {
							playerColor = input.next();

							if(playerColor.toLowerCase().equals("red")) {
								gameCheckers = new Checkers("Black", aiDifficulty);
								hasColor = true;

							} else if (playerColor.toLowerCase().equals("black")) {
								gameCheckers = new Checkers("Red", aiDifficulty);
								hasColor = true;

							} else {
								System.out.println("Invalid color. Please try again.");
							}
						} catch (Exception e) {
							System.out.println("That wasn't even an option. Try again.");
						}
					}
					break;

				} else if (playerCount != 2) {
					System.out.println("Invalid number. Please try again.");

				} else {
					gameCheckers = new Checkers();
					break;
				}
			} catch (Exception e) {
				System.out.println("Try using a number this time!");
			}
		}
		
		if(gameCheckers != null) {		
			gameCheckers.displayBoard(gameCheckers.gameBoard.displayBoard());

			if(gameCheckers.aiDifficulty < 0) {

				while (!gameCheckers.isGameWon()) {
					System.out.println("Black's Turn!\n");
					gameCheckers.playerTurn("Black");
					gameCheckers.displayBoard(gameCheckers.gameBoard.displayBoard());
					System.out.println("Red's Turn!\n");
					gameCheckers.playerTurn("Red");
					gameCheckers.displayBoard(gameCheckers.gameBoard.displayBoard());
				}
				gameCheckers.playerResponse.close();

			} else {

				while (!gameCheckers.isGameWon()) {
						System.out.println("Black's Turn!\n");

						if(gameCheckers.aiColor.equals(gameCheckers.BLACK)) {
							gameCheckers.AITurn(gameCheckers.aiColor);
							gameCheckers.displayBoard(gameCheckers.gameBoard.displayBoard());
							System.out.println("Red's Turn!\n");
							gameCheckers.playerTurn(gameCheckers.RED);

						} else {
							gameCheckers.playerTurn(gameCheckers.BLACK);
							gameCheckers.displayBoard(gameCheckers.gameBoard.displayBoard());
							System.out.println("Red's Turn!\n");
							gameCheckers.AITurn(gameCheckers.RED);

						}
					gameCheckers.displayBoard(gameCheckers.gameBoard.displayBoard());
				}
				gameCheckers.playerResponse.close();
			}
		}
	}
}
