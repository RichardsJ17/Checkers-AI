import java.util.ArrayList;

public class Board {
	
	private Piece[][] board;
	private String[][] textBoard;

	private ArrayList<Piece> redPieces;
	private ArrayList<Piece> blackPieces;

	public Board()
	{
		this.board = new Piece[8][8];
		this.textBoard = new String[18][18];

		this.redPieces = new ArrayList<Piece>();
		this.blackPieces = new ArrayList<Piece>();

		setUpTextBoard();
	}

	// Finish
	public Board duplicateBoard(){
		Board replicaGameState = new Board();

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(this.board[i][j] != null)
				{
					replicaGameState.board[i][j] = this.board[i][j].duplicatePiece();
					
					if(this.board[i][j].getColor().equals("Red")) {
						replicaGameState.redPieces.add(replicaGameState.board[i][j]);
					} else {
						replicaGameState.blackPieces.add(replicaGameState.board[i][j]);
					}
				}
			}
		}

		for(int i = 0; i < 18; i++)
		{
			for(int j = 0; j < 18; j++)
			{
				replicaGameState.textBoard[i][j] = this.textBoard[i][j];
			}
		}

		return replicaGameState;
	}
	
	public void movePiece(Position pos, Position targetPos)
	{
		Position textPosition = getTextCoords(pos);
		Piece movingPiece = getPiece(pos.getRow(), pos.getCol());
		
		this.board[targetPos.getRow()][targetPos.getCol()] = movingPiece;
		movingPiece.setPosition(targetPos);

		this.board[pos.getRow()][pos.getCol()] = null;
		this.textBoard[textPosition.getRow()][textPosition.getCol()] = " ";

		// King me
		if (movingPiece.getColor().equals("Red") && targetPos.getRow() == 7 || movingPiece.getColor().equals("Black") && targetPos.getRow() == 0) {
			movingPiece.setKing();
		}

		// Removing jumped piece
		if (Math.abs(targetPos.getRow() - pos.getRow()) == 2) {
			Position jumpedPosition = getJumpedPieceCoords(pos, targetPos);
			textPosition = getTextCoords(jumpedPosition);
			Piece removedPiece = getPiece(jumpedPosition.getRow(), jumpedPosition.getCol());
			this.board[jumpedPosition.getRow()][jumpedPosition.getCol()] = null;
			this.textBoard[textPosition.getRow()][textPosition.getCol()] = " ";
			ArrayList<Piece> pieces = getPieces(removedPiece.getColor());

			for(int i = 0; i < pieces.size(); i++) {
				if(pieces.get(i).getId() == removedPiece.getId()) {
					pieces.remove(i);
					break;
				}
			}
		}

		drawPiece (movingPiece, targetPos.getRow(), targetPos.getCol());
	}
	
	public Piece getPiece(int currentRow, int currentCol)
	{
		return this.board[currentRow][currentCol];
	}
	
	public void addPiece(Piece p, int targetRow, int targetCol)
	{
		if(p.getColor().equals("Red")) {
			this.redPieces.add(p);
		} else {
			this.blackPieces.add(p);
		}

		this.board[targetRow][targetCol] = p;
		drawPiece(p, targetRow, targetCol);
	}
	
	public String[][] displayBoard()
	{
		return this.textBoard;
	}

	private void drawPiece(Piece p, int targetRow, int targetCol) 
	{
		Position textPosition = getTextCoords(new Position(targetRow, targetCol));

		if(p.getColor() == "Red")
		{
			if(p.isKing())
			{
				this.textBoard[textPosition.getRow()][textPosition.getCol()] = "X";
			}
			else
			{
				this.textBoard[textPosition.getRow()][textPosition.getCol()] = "x";
			}
		}
		else if(p.getColor() == "Black")
		{
			if(p.isKing())
			{
				this.textBoard[textPosition.getRow()][textPosition.getCol()] = "O";
			}
			else
			{
				this.textBoard[textPosition.getRow()][textPosition.getCol()] = "o";
			}
		}
	}

	private Position getTextCoords(Position position) {
		return new Position((position.getRow() + 1) * 2, (position.getCol() + 1) * 2); 
	}

	private Position getJumpedPieceCoords(Position startingPosition, Position targetPosition) {
		return new Position((startingPosition.getRow() + targetPosition.getRow()) / 2, (startingPosition.getCol() + targetPosition.getCol()) / 2);
	}

	public ArrayList<Piece> getRedPieces() {
		return this.redPieces;
	}

	public ArrayList<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	public ArrayList<Piece> getPieces(String color) {
		if(color.toLowerCase().equals("red")) {
			return this.redPieces;
		} else {
			return this.blackPieces;
		}
	}

	public boolean isGameOver() {
		if(this.redPieces.isEmpty() || this.blackPieces.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setUpTextBoard()
	{
		int colCount = 0;
		int rowCount = 0;
		
		// row
		for(int i = 0; i < 18; i++)
		{
			// col
			for(int j = 0; j < 18; j++)
			{
				// Labeling rows
				if((i > 0) && (i %2 == 0) && (j == 0))
				{
					this.textBoard[i][j] = Integer.toString(rowCount + 1);
					rowCount++;
				}
				// Placing "|"
				else if(((i > 0) && (j % 2 == 1) && (i % 2 == 0)) || ((i > 1 && i < 17) && (j == 1 || j == 17)))
				{
					this.textBoard[i][j] = "|";
				}
				// Labeling cols
				else if((i == 0) && (j > 0) && (j % 2 == 0))
				{
					this.textBoard[i][j] = Integer.toString(colCount + 1);
					colCount++;
				}
				// Placing "-"
				else if(((i > 0) && (j > 0) && (j % 2 == 0) && (i % 2 == 1)) ||  (j > 1 && j < 17) &&(i == 1 || i == 17))
				{
					this.textBoard[i][j] = "-";
				}
				// Placing "+"
				else if(((i > 0) && (j > 0) && (j % 2 == 1) && (i % 2 == 1)))
				{
					this.textBoard[i][j] = "+";
				}
				// Placing initial empty spaces
				else
				{
					this.textBoard[i][j] = " ";
				}
			}
		}
	}
}
