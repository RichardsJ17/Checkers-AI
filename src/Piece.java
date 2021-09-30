
public class Piece {

	private String color;
	private boolean bIsKing;
	private int id;
	private Position pos;
	
	public Piece(String c, int id)
	{
		this.color = c;
		this.bIsKing = false;
		this.id = id;	
		this.pos = new Position();
	}

	public Piece duplicatePiece() {
		Piece temp = new Piece(this.color, this.id);
		temp.setPosition(this.pos);

		return temp;
	}
	
	public String getColor()
	{
		return this.color;
	}
	
	public boolean isKing()
	{
		return this.bIsKing;
	}
	
	public void setKing()
	{
		this.bIsKing = true;
	}
	
	public int getId()
	{
		return this.id;
	}

	public void setPosition(int row, int col) {
		this.pos.setRow(row);
		this.pos.setCol(col);
	}

	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public Position getPosition() {
		return this.pos;
	}
}
