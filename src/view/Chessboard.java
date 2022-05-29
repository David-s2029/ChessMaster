package view;

import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private JLabel playerLabel;
    private Difficulty difficulty;

    public void setPvcMode(boolean pvcMode) {
        PvcMode = pvcMode;
    }

    private boolean PvcMode=false;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initChessGame();
    }

    public void initChessGame() {
        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
        }
        currentColor=ChessColor.WHITE;
    }

    public void playerLabel(JLabel label){
        playerLabel=label;
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        ArrayList<ChessComponent> canMoveTo=chess1.canMovePoints();
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE,chessComponents));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        if (winnerCheck()==ChessColor.WHITE){
            JOptionPane.showMessageDialog(this,"Player WHITE wins! Congrats!");
            currentColor=ChessColor.NONE;
            playerLabel.setText("ENDED");
        }
        else if (winnerCheck()==ChessColor.BLACK){
            JOptionPane.showMessageDialog(this,"Player BLACK wins! Congrats!");
            currentColor=ChessColor.NONE;
            playerLabel.setText("ENDED");
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].setSelected(false);
                chessComponents[i][j].setCanMove(false);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE,chessComponents));
            }
        }
    }

    public void AiMoveNormal(){
        List<ChessComponent> movable=new ArrayList<>();
        ChessComponent move1;
        ChessComponent move2;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    if (chessComponents[i][j].canMovePoints().size()!=0)
                        movable.add(chessComponents[i][j]);
                }
            }
        }
        Random random=new Random();
        move1=movable.get(random.nextInt(movable.size()));
        move2=move1.canMovePoints().get(random.nextInt(move1.canMovePoints().size()));
        swapChessComponents(move1,move2);
        swapColor();
    }

    public void AiMoveHard(){
        List<ChessComponent> movable=new ArrayList<>();
        List<ChessComponent> movablePoints=new ArrayList<>();
        ChessComponent move1;
        ChessComponent move2;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    if (chessComponents[i][j].canMovePoints().size()!=0) {
                        movablePoints.addAll(chessComponents[i][j].canMovePoints());
                    }
                }
            }
        }
        movablePoints.sort(ChessComponent::compareTo);
        int maxPoint=movablePoints.get(0).getPoint();
        movablePoints.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    if (chessComponents[i][j].canMovePoints().size()!=0) {
                        for (int k = 0; k < chessComponents[i][j].canMovePoints().size(); k++) {
                            if (chessComponents[i][j].canMovePoints().get(k).getPoint()==maxPoint){
                                movable.add(chessComponents[i][j]);
                                break;
                            }
                        }
                    }
                }
            }
        }
        Random random=new Random();
        move1=movable.get(random.nextInt(movable.size()));
        for (int i = 0; i < move1.canMovePoints().size(); i++) {
            if (move1.canMovePoints().get(i).getPoint()==maxPoint)
                movablePoints.add(move1.canMovePoints().get(i));
        }
        move2=movablePoints.get(random.nextInt(movablePoints.size()));
        swapChessComponents(move1,move2);
        swapColor();
    }

    public void swapColor() {
        if (currentColor==ChessColor.BLACK)
            currentColor=ChessColor.WHITE;
        else if (currentColor==ChessColor.WHITE) {
            currentColor = ChessColor.BLACK;
            if (PvcMode){
                if (getDifficulty()==Difficulty.DIFFICULTY_Normal) {
                    AiMoveNormal();
                }
                else if (getDifficulty()==Difficulty.DIFFICULTY_Hard){
                    AiMoveHard();
                }
                else if (getDifficulty()==Difficulty.DIFFICULTY_Hell){
                    AiMoveHard();
                }
            }
        }
        if (currentColor==ChessColor.BLACK) playerLabel.setText("Black");
        if (currentColor==ChessColor.WHITE) playerLabel.setText("White");
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,chessComponents);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,chessComponents);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,chessComponents);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,chessComponents);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,chessComponents);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,chessComponents);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public List<String> saveGame() {
        List<String> save = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= 7; j++) {
                sb.append(chessComponents[i][j].getName());
            }
            save.add(sb.toString());
        }
        if (currentColor == ChessColor.BLACK) save.add("B");
        else if (currentColor==ChessColor.WHITE) save.add("w");
        return save;
    }

    public void loadGame(List<String> chessData) {
        initiateEmptyChessboard();
        for (int i = 0; i < chessData.size() - 1; i++) {
            for (int j = 0; j < chessData.get(i).length(); j++) {
                if (chessData.get(i).charAt(j) == '_') continue;
                if (chessData.get(i).charAt(j) == 'K')
                    initKingOnBoard(i,j,ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'k')
                    initKingOnBoard(i,j,ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'R')
                    initRookOnBoard(i,j,ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'r')
                    initRookOnBoard(i,j,ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'B')
                    initBishopOnBoard(i,j,ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'b')
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'Q')
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'q')
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'N')
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'n')
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'P')
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'p')
                    initPawnOnBoard(i,j,ChessColor.WHITE);
            }
        }
        if (chessData.get(chessData.size()-1).equals("B")) {
            currentColor=ChessColor.BLACK;
            playerLabel.setText("Black");
        }
        else if (chessData.get(chessData.size()-1).equals("w")) {
            currentColor=ChessColor.WHITE;
            playerLabel.setText("White");
        }
    }

    public ChessColor winnerCheck(){
        boolean white=false;
        boolean black=false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.WHITE)
                        white=true;
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK)
                        black=true;
                }
            }
        }
        if (white&&black) return ChessColor.NONE;
        else if (white) return ChessColor.WHITE;
        else return ChessColor.BLACK;
    }
}
