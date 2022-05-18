package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的马
 */
public class KnightChessComponent extends ChessComponent {

    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    private Image knightImage;

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage=KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage=KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        if (color == ChessColor.WHITE) {
            super.name='n';
        } else if (color == ChessColor.BLACK) {
            super.name='N';
        }
        initiateKnightImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (destination.getX()-source.getX()==2&&destination.getY()-source.getY()==1)
            return true;
        if (destination.getX()-source.getX()==-2&&destination.getY()-source.getY()==1)
            return true;
        if (destination.getX()-source.getX()==2&&destination.getY()-source.getY()==-1)
            return true;
        if (destination.getX()-source.getX()==-2&&destination.getY()-source.getY()==-1)
            return true;
        if (destination.getX()-source.getX()==1&&destination.getY()-source.getY()==2)
            return true;
        if (destination.getX()-source.getX()==-1&&destination.getY()-source.getY()==2)
            return true;
        if (destination.getX()-source.getX()==1&&destination.getY()-source.getY()==-2)
            return true;
        if (destination.getX()-source.getX()==-1&&destination.getY()-source.getY()==-2)
            return true;
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
