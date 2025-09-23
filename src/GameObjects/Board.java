package GameObjects;
import Configs.Config;
import java.io.IOException;
import java.util.ArrayList;

public class Board {
    public int rowCount,colCount;
    public ArrayList<Piece> pieces =new ArrayList<>();
    public Piece[][] board;
    private Config config=new Config();
    private static Board instance;
    public static Board getInstance() throws IOException {
        if(instance==null){
            instance=new Board();
        }
        return instance;
    }
    private Board() throws IOException {
        rowCount = config.getIntConfig("rowCount");
        colCount = config.getIntConfig("colCount");
    }
    public Piece[][] getCellState() {
        Piece[][] board = new Piece[rowCount][colCount];
        for (Piece piece : this.pieces) {
            board[piece.x][piece.y] = piece;
        }
        return board;
    }
    public int getRowCount() {
        return rowCount;
    }
    public int getColCount() {
        return colCount;
    }
    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    public Piece[][] getBoard() {
        return board;
    }
    public void setBoard(Piece[][] board) {
        this.board = board;
    }
}
