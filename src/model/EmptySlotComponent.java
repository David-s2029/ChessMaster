package model;

import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size,ChessComponent[][] chessComponents) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size,chessComponents);
        super.point=0;
        super.name='_';
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    public ArrayList<ChessComponent> canMovePoints(){
        ArrayList<ChessComponent> move=new ArrayList<>();
        return move;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }


}
