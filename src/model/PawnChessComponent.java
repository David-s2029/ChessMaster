package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的兵
 */
public class PawnChessComponent extends ChessComponent {

    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.gif"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.gif"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,ChessComponent[][] chessComponents) {
        super(chessboardPoint, location, color, listener, size,chessComponents);
        super.point=1;
        if (color == ChessColor.WHITE) {
            super.name='p';
        } else if (color == ChessColor.BLACK) {
            super.name='P';
        }
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (pawnImage == PAWN_BLACK) {
            if (destination.getY() == source.getY()) {
                if (destination.getX() == source.getX() + 1 && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)
                    return true;
                if (source.getX() == 1) {
                    if (chessComponents[2][source.getY()] instanceof EmptySlotComponent && chessComponents[3][source.getY()] instanceof EmptySlotComponent && destination.getX() == 3)
                        return true;
                }
            }
            if (Math.abs(source.getY() - destination.getY()) == 1 && destination.getX() == 1 + source.getX()) {
                return !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
            }
        }
        if (pawnImage == PAWN_WHITE) {
            if (destination.getY() == source.getY()) {
                if (destination.getX() == source.getX() - 1 && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)
                    return true;
                if (source.getX() == 6) {
                    if (chessComponents[4][source.getY()] instanceof EmptySlotComponent && chessComponents[5][source.getY()] instanceof EmptySlotComponent && destination.getX() == 4)
                        return true;
                }
            }
            if (Math.abs(source.getY() - destination.getY()) == 1 && destination.getX() + 1 == source.getX()) {
                return !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
            }
        }
        return false;
    }

    public ArrayList<ChessComponent> canMovePoints(){
        ArrayList<ChessComponent> move=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (canMoveTo(chessComponents,new ChessboardPoint(i,j))&&chessComponents[i][j].chessColor!=this.chessColor)
                    move.add(chessComponents[i][j]);
            }
        }
        return move;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
