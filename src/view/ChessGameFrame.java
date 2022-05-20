package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Chessboard chessboard;

    public ChessGameFrame(int width, int height) {
        setTitle("ChessMaster"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addRestartButton();
        addLabel();
        addSaveButton();
        addLoadButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("ChessMaster 1.0");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            chessboard.initChessGame();
            chessboard.repaint();
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener((e) -> {
            gameController.saveGame();
            JOptionPane.showMessageDialog(this, "Current state successfully saved.");
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input path here");
            if (!path.endsWith(".txt") || Files.notExists(Paths.get(path)))
                JOptionPane.showMessageDialog(this, "Invalid path, please try again.");
            //若棋盘属性不对则怎么怎么样
            gameController.loadGameFromFile(path);
        });
    }

}
