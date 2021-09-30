import java.util.ArrayList;
import java.util.Scanner;

public class Checkers {

	Board gameBoard;
	Scanner playerResponse;
	String aiColor;
	int aiDifficulty;
	boolean hasNoMoves;

	final String RED = "Red";
	final String BLACK = "Black";


	public Checkers() {
		this.gameBoard = new Board();
		this.hasNoMoves = false;
		this.aiDifficulty = -1;
		this.playerResponse = new Scanner(System.in);

		setUp();
	}

	public Checkers(String aiColor, int aiDifficulty) {
		this.aiColor = aiColor;
		this.aiDifficulty = aiDifficulty;
		this.hasNoMoves = false;
		this.gameBoard = new Board();
		this.playerResponse = new Scanner(System.in);

		setUp();
	}

	private void setUp() {
		int row = 0;
		int col = 1;

		// Set red pieces
		for (int i = 0; i < 12; i++) {
			Piece p = new Piece(RED, i);
			this.gameBoard.addPiece(p, row, col);
			p.setPosition(row, col);
			if (col == 7) {
				col = 0;
				row++;
			} else if (col == 6) {
				col = 1;
				row++;
			} else {
				col += 2;
			}
		}

		row = 5;
		col = 0;

		// Set black pieces
		for (int i = 0; i < 12; i++) {
			Piece p = new Piece(BLACK, i);
			this.gameBoard.addPiece(p, row, col);
			p.setPosition(row, col);
			if (col == 7) {
				col = 0;
				row++;
			} else if (col == 6) {
				col = 1;
				row++;
			} else {
				col += 2;
			}
		}
	}

	public void playerTurn(String playerTurn) {
		boolean isValidMove = false;
		Position piecePos = new Position();
		Position targetPos = new Position();

		while (!isValidMove && !hasNoMoves) {
			try {
				System.out.println("Enter the Row and Column Number of the Piece you would like to move");
				piecePos.setRow(playerResponse.nextInt() - 1);
				piecePos.setCol(playerResponse.nextInt() - 1);

				System.out.println("Which Row and Column would you like to move it to?");
				targetPos.setRow(playerResponse.nextInt() - 1);
				targetPos.setCol(playerResponse.nextInt() - 1);

				if (isMoveValid(piecePos, targetPos, playerTurn)) {
					this.gameBoard.movePiece(piecePos, targetPos);
					isValidMove = true;
				
				} else {
					System.out.println("Invalid move. Try again!");
				}
			} catch (Exception e) {
				System.out.println("Rows and Columns are depicted with numbers, try using those");
			}
		}
	}

	public boolean isGameWon() {
		if(this.gameBoard.getRedPieces().isEmpty()) {
			System.out.println("Black Team Wins!!");
			return true;
		} else if (this.gameBoard.getBlackPieces().isEmpty()) {
			System.out.println("Red Team Wins!!");
			return true;
		} else if(hasNoMoves) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMoveValid(Position pos, Position targetPos, String color) {
		
		if (this.gameBoard.getPiece(pos.getRow(), pos.getCol()) != null) {
			Piece p = this.gameBoard.getPiece(pos.getRow(), pos.getCol());

			if(!p.getColor().equals(color)) {
				System.out.println("That's not your piece. Try again");
				return false;				
			}
			ArrayList<Position> possibleMoves = getPossibleMoves(this.gameBoard, p);

			if(possibleMoves.isEmpty()) {
				hasNoMoves = true;
				System.out.println(aiColor + " has no more moves!");
				System.out.println(getOtherPlayerColor(aiColor) + " wins!!");
				return false;
			}
			else if(possibleMoves.stream().anyMatch(move -> move.getRow() == targetPos.getRow() && move.getCol() == targetPos.getCol())) {
				return true;

			} else {
				return false;
			}

		} else {
			System.out.println("No checker in that location");
			return false;
		}
	}

	public ArrayList<Position> getPossibleMoves(Board gameBoard, Piece piece) {
		ArrayList<Position> possibleMoves = new ArrayList<>();
		Position pos = piece.getPosition();
		String oppositePlayer;

		if(piece.getColor().equals(RED)) {
			oppositePlayer = BLACK;
		} else {
			oppositePlayer = RED;
		}
		if(piece.getColor().equals(RED) || piece.getColor().equals(BLACK) && piece.isKing()) {
			// Downwards
			if(pos.getRow() != 7) {
				// Left
				if(pos.getCol() != 0) {
					if((gameBoard.getPiece(pos.getRow() + 1, pos.getCol() - 1) == null)) {
						possibleMoves.add(new Position(pos.getRow() + 1, pos.getCol() - 1));
					} 
					else if (pos.getRow() != 6 && pos.getCol() != 1 && gameBoard.getPiece(pos.getRow() + 1, pos.getCol() - 1).getColor().equals(oppositePlayer) && gameBoard.getPiece(pos.getRow() + 2, pos.getCol() - 2) == null) {
						possibleMoves.add(new Position(pos.getRow() + 2, pos.getCol() - 2));
					}
				}
				// Right
				if(pos.getCol() != 7) {
					if((gameBoard.getPiece(pos.getRow() + 1, pos.getCol() + 1) == null)) {
						possibleMoves.add(new Position(pos.getRow() + 1, pos.getCol() + 1));
					} 
					else if (pos.getRow() != 6 && pos.getCol() != 6 && gameBoard.getPiece(pos.getRow() + 1, pos.getCol() + 1).getColor().equals(oppositePlayer) && gameBoard.getPiece(pos.getRow() + 2, pos.getCol() + 2) == null) {
						possibleMoves.add(new Position(pos.getRow() + 2, pos.getCol() + 2));
					}
				}
			}
		}
		if(piece.getColor().equals(BLACK) || piece.getColor().equals(RED) && piece.isKing()) {
			// Up
			if(pos.getRow() != 0) {
				// Left
				if(pos.getCol() != 0) {
					if((gameBoard.getPiece(pos.getRow() - 1, pos.getCol() - 1) == null)) {
						possibleMoves.add(new Position(pos.getRow() - 1, pos.getCol() - 1));
					} 
					else if (pos.getRow() != 1 && pos.getCol() != 1 && gameBoard.getPiece(pos.getRow() - 1, pos.getCol() - 1).getColor().equals(oppositePlayer) && gameBoard.getPiece(pos.getRow() - 2, pos.getCol() - 2) == null) {
						possibleMoves.add(new Position(pos.getRow() - 2, pos.getCol() - 2));
					}
				}
				// Right
				if(pos.getCol() != 7) {
					if((gameBoard.getPiece(pos.getRow() - 1, pos.getCol() + 1) == null)) {
						possibleMoves.add(new Position(pos.getRow() - 1, pos.getCol() + 1));
					} 
					else if (pos.getRow() != 1 && pos.getCol() != 6 && gameBoard.getPiece(pos.getRow() - 1, pos.getCol() + 1).getColor().equals(oppositePlayer) && gameBoard.getPiece(pos.getRow() - 2, pos.getCol() + 2) == null) {
						possibleMoves.add(new Position(pos.getRow() - 2, pos.getCol() + 2));
					}
				}
			}
		}

		return possibleMoves;
	}

	public void AITurn(String aiColor) {
		int bestMove = Integer.MIN_VALUE;
		int depth = aiDifficulty;
		Node bestState = new Node();

		ArrayList<Node> possibleGameStates = getPossibleGameStates(this.gameBoard, aiColor);

		if(possibleGameStates.isEmpty()) {
			hasNoMoves = true;
			System.out.println(aiColor + " has no more moves!");
			System.out.println(getOtherPlayerColor(aiColor) + " wins!!");
		}
			
		for(int i = 0; i < possibleGameStates.size(); i++) {
			int moveValue = minimax(possibleGameStates.get(i), depth, getOtherPlayerColor(aiColor), Integer.MIN_VALUE, Integer.MAX_VALUE);
			if(moveValue > bestMove) {
				bestMove = moveValue;
				bestState = possibleGameStates.get(i);
			}
		}

		this.gameBoard.movePiece(bestState.getPiece().getPosition(), bestState.move);
	}

	private int minimax(Node childNode, int depth, String playerColor, int alpha, int beta) {
		ArrayList<Node> possibleGameStates;

		if(depth == 0 || childNode.gameState.isGameOver()){
			return evaluateBoardPosition(childNode.gameState);
		}
		if(this.aiColor.equals(playerColor)) {
			int bestMove = Integer.MIN_VALUE;

			// Get Possible Moves and Gamestates from that
			possibleGameStates = getPossibleGameStates(childNode.gameState, playerColor);
			
			for(int i = 0; i < possibleGameStates.size(); i++) {
				int moveValue = minimax(possibleGameStates.get(i), depth - 1, getOtherPlayerColor(playerColor), alpha, beta);
				bestMove = Math.max(bestMove, moveValue);
				alpha = Math.max(alpha, bestMove);

				if(beta <= alpha) {
					break;
				}
			}

			return bestMove;
		} else {
			int bestMove = Integer.MAX_VALUE;

			// Get Possible Moves and Gamestates from that
			possibleGameStates = getPossibleGameStates(childNode.gameState, playerColor);

			for(int i = 0; i < possibleGameStates.size(); i++) {
				int moveValue = minimax(possibleGameStates.get(i), depth - 1, getOtherPlayerColor(playerColor), alpha, beta);
				bestMove = Math.min(bestMove, moveValue);
				beta = Math.min(beta, bestMove);

				if(beta <= alpha) {
					break;
				}
			}

			return bestMove;
		}
	}

	private ArrayList<Node> getPossibleGameStates (Board gameBoard, String color) {
		ArrayList<Piece> pieces = gameBoard.getPieces(color);
		ArrayList<Node> possibleGameStates = new ArrayList<>();

		// Create all possible game states
		for(int i = 0; i < pieces.size(); i++) {
			Piece checkPiece = pieces.get(i);
			ArrayList<Position> moves = getPossibleMoves(gameBoard, checkPiece);

			if(moves.size() > 0) {
				for(int j = 0; j < moves.size(); j++) {
					Board board = gameBoard.duplicateBoard();
					board.movePiece(checkPiece.getPosition(), moves.get(j));
					Node gameState = new Node(board, pieces.get(i), moves.get(j));
					possibleGameStates.add(gameState);
				}
			}
		}

		return possibleGameStates;
	}

	private int evaluateBoardPosition(Board gameState) {
		ArrayList<Piece> aiPieces = gameState.getPieces(aiColor);
		ArrayList<Piece> playerPieces = gameState.getPieces(getOtherPlayerColor(aiColor));

		int value = aiPieces.size() * 10;
		value -= playerPieces.size() * 10;

		for(int i = 0; i < aiPieces.size(); i++) {
			if(aiPieces.get(i).isKing()) {
				value += 5;
			}
			if(aiPieces.get(i).getPosition().getCol() == 0 || aiPieces.get(i).getPosition().getCol() == 7) {
				value += 2;
			}
		}
		for(int i = 0; i < playerPieces.size(); i ++) {
			if(playerPieces.get(i).isKing()) {
				value -= 5;
			}
			if(playerPieces.get(i).getPosition().getCol() == 0 || playerPieces.get(i).getPosition().getCol() == 7) {
				value -= 2;
			}
		}

		System.out.println("--------------------------------");
		displayBoard(gameState.displayBoard());
		System.out.println("Evaluation Value: " +value);

		return value;
	}

	private String getOtherPlayerColor(String playerColor) {

		if(playerColor.equals(BLACK)) {
			return RED;
		} else {
			return BLACK;
		}
	}

	public void displayBoard(String[][] board) {
		String display = "";
		display += "\nx = Red - "+ this.gameBoard.getRedPieces().size() +"\no = Black - "+ this.gameBoard.getBlackPieces().size() + "\n";

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				display += board[i][j];
			}

			display += "\n";
		}

		System.out.println(display);
		return;
	}
}
