package Gui;

import Configs.Config;
import Game.Logic;
import GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MyPanel extends JPanel {
    private static MyPanel instance;
    private final Logic logic;
    private final Board board;
    private final Config config=new Config();
    public static MyPanel getInstance() {
        return instance;
    }
    public MyPanel(Logic logic) throws IOException {
        this.logic=logic;
        board = Board.getInstance();
        instance = this;
        setLayout(null);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                MyPanel panel = MyPanel.getInstance();
                int windowWidth = panel.getWidth() - 5;
                int windowHeight = panel.getHeight() - 20;
                int x = me.getX() / (windowWidth / board.getRowCount());
                int y = me.getY() / (windowHeight / board.getColCount());
                logic.changePlayerTurn(x,y);
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(0, 106, 65));
        g.setColor(Color.WHITE);
        int windowHeight = this.getHeight() - 20;
        if (!(logic.playerHasMove(false) || logic.playerHasMove(true))) {
            int p1Score = 0;
            int p2Score = 0;
            for (Piece piece : board.getPieces()) {
                p1Score += piece.color ? 0 : 1;
                p2Score += piece.color ? 1 : 0;
            }
            String gameEndStr;
            if (p1Score > p2Score)
                gameEndStr = config.getStrConfig("player1Win");
            else if (p2Score > p1Score)
                gameEndStr = config.getStrConfig("player2Win");
            else
                gameEndStr = config.getStrConfig("Tie");
            g.drawString(gameEndStr, 0, windowHeight + 15);
        } else {
            g.drawString(config.getStrConfig("turn") + (logic.isPlayerTurn() ? config.getStrConfig("White")  : config.getStrConfig("Black") ), 0, windowHeight + 15);
        }
        int windowWidth = this.getWidth() - 5;
        for (int i = 0; i <= board.getRowCount(); i++) {
            g.drawLine(0, i * windowHeight / board.getRowCount(), windowWidth, i * windowHeight / board.getRowCount());
            g.drawLine(i * windowWidth / board.getColCount(), 0, i * windowWidth / board.getColCount(), windowHeight);
        }
        int pieceSize = Math.min(windowWidth / board.getColCount(), windowHeight / board.getColCount()) / 2;
        for (Piece piece : board.getPieces()) {
            int x = piece.x * (windowWidth / board.getColCount()) + (windowWidth / board.getColCount()) / 2 - pieceSize / 2;
            int y = piece.y * (windowHeight / board.getRowCount()) + (windowHeight / board.getRowCount()) / 2 - pieceSize / 2;
            g.setColor(piece.color ? Color.WHITE : Color.BLACK);
            g.drawOval(x, y, pieceSize, pieceSize);
            g.fillOval(x, y, pieceSize, pieceSize);
        }
    }
}
