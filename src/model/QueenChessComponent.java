package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的后
 */
public class QueenChessComponent extends ChessComponent {

    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.gif"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.gif"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage=QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage=QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,ChessComponent[][] chessComponents) {
        super(chessboardPoint, location, color, listener, size,chessComponents);
        super.point=8;
        if (color == ChessColor.WHITE) {
            super.name='q';
        } else if (color == ChessColor.BLACK) {
            super.name='Q';
        }
        initiateQueenImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())) {
            if (destination.getX() > source.getX() && destination.getY() > source.getY()) {
                for (int i = 1; i < destination.getY()-source.getY(); i++) {
                    if (!(chessComponents[source.getX()+i][source.getY()+i] instanceof EmptySlotComponent))
                        return false;
                }
            }
            if (destination.getX() < source.getX() && destination.getY() < source.getY()) {
                for (int i = 1; i < source.getY()-destination.getY(); i++) {
                    if (!(chessComponents[source.getX()-i][source.getY()-i] instanceof EmptySlotComponent))
                        return false;
                }
            }
            if (destination.getX() > source.getX() && destination.getY() < source.getY()) {
                for (int i = 1; i < destination.getX()-source.getX(); i++) {
                    if (!(chessComponents[source.getX()+i][source.getY()-i] instanceof EmptySlotComponent))
                        return false;
                }
            }
            if (destination.getX() < source.getX() && destination.getY() > source.getY()) {
                for (int i = 1; i < destination.getY()-source.getY(); i++) {
                    if (!(chessComponents[source.getX()-i][source.getY()+i] instanceof EmptySlotComponent))
                        return false;
                }
            }
        } else return false;
        return true;
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
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
