import java.util.ArrayList;

public class Node {

    ArrayList<Node> children;
    Node parent;
    Board gameState;
    Piece piece;
    Position move;

    Node() {

    }

    Node(Board gameBoard, Piece piece, Position move) {
        children = new ArrayList<Node>();
        this.gameState = gameBoard;
        this.piece = piece;
        this.move = move;
    }

    public void setChild(Node child) {
        children.add(child);
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setGameState(Board gameBoard) {
        this.gameState = gameBoard;
    }

    public Board getGameState() {
        return this.gameState;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setMove(Position move) {
        this.move = move;
    }

    public Position getMove() {
        return this.move;
    }
}
