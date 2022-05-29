package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的象
 */
public class BishopChessComponent extends ChessComponent {

    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;


    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.gif"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.gif"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,ChessComponent[][] chessComponents) {
        super(chessboardPoint, location, color, listener, size,chessComponents);
        super.point=4;
        if (color == ChessColor.WHITE) {
            super.name='b';
        } else if (color == ChessColor.BLACK) {
            super.name='B';
        }
        initiateBishopImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())) {
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

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
    }
}

