package Game;


import Configs.Config;
import GameObjects.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
public class Logic {
    public Board board=Board.getInstance();
    private boolean playerTurn = false;
    public Logic() throws IOException {}
    public void startGame(JFrame frame) throws InterruptedException {
        for(int i=3;i<5;i++){
            for(int j=3;j<5;j++){
                if((i+j)%2==0) board.getPieces().add(new Piece(i,j,false));
                else board.getPieces().add(new Piece(i,j,true));
            }
        }
        while (true) {
            Thread.sleep(1000 / 60);
            frame.repaint();
            frame.revalidate();
            if (!(playerHasMove(false) || playerHasMove(true))) {
                JOptionPane.showMessageDialog(frame,"somebody win the game!");
                board.getPieces().clear();
                startGame(frame);
            }
        }
    }
    public boolean playerHasMove(boolean player) {
        for (int i = 0; i < board.getRowCount(); i++)
            for (int j = 0; j < board.getColCount(); j++) {
                Piece[][] board = this.board.getCellState();
                if (board[i][j] != null) {
                    continue;
                }
                if(mustChangePiece(i,j,player,false))
                    return true;
            }
        return false;
    }
    public void changePlayerTurn(int x, int y) {
        if (x >= board.getColCount() || y >= board.getRowCount()) {
            return;
        }
        board.setBoard(board.getCellState());
        if (board.getBoard()[x][y] != null) {
            return;
        }
        boolean doesFlip = mustChangePiece(x, y, playerTurn, true);
        if (doesFlip) {
            board.getPieces().add(new Piece(x, y, playerTurn));
        }
        if (!doesFlip) return;
        playerTurn = !playerTurn;
        if (!playerHasMove(playerTurn))
            playerTurn = !playerTurn;
    }
    public boolean mustChangePiece(int i, int j, boolean player, boolean mouse) {
        int[] dx = {1, 1, -1, -1, 0, 0, 1, -1};
        int[] dy = {-1, 1, 1, -1, 1, -1, 0, 0};
        boolean doesFlip = false;
        board.setBoard(board.getCellState());
        mainLoop:
        for (int k = 0; k < dx.length; k++) {
            ArrayList<Piece> flipRow = new ArrayList<>();
            for (int l = 1; l <= Math.max(board.getRowCount(), board.getColCount()); l++) {
                int nx = i + l * dx[k], ny = j + l * dy[k];
                if (nx >= board.getColCount() || nx < 0 || ny >= board.getRowCount() || ny < 0 || board.getBoard()[nx][ny] == null) {
                    continue mainLoop;
                }
                if (board.getBoard()[nx][ny].color == player)
                    break;
                flipRow.add(board.getBoard()[nx][ny]);
            }
            if (mouse) {
                for (Piece piece : flipRow) {
                    doesFlip = true;
                    piece.color = playerTurn;
                }
            } else {
                doesFlip = !flipRow.isEmpty();
            }
        }
        return doesFlip;
    }
    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
