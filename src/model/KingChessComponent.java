package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的王
 */
public class KingChessComponent extends ChessComponent {

    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE= ImageIO.read(new File("./images/king-white.gif"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.gif"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage=KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage=KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,ChessComponent[][] chessComponents) {
        super(chessboardPoint, location, color, listener, size,chessComponents);
        super.point=10;
        if (color == ChessColor.WHITE) {
            super.name='k';
        } else if (color == ChessColor.BLACK) {
            super.name='K';
        }
        initiateKingImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(destination.getX()-source.getX())<=1&&Math.abs(destination.getY()-source.getY())<=1){
            return true;
        } else return false;
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
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
    }
}
