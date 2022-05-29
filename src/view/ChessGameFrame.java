package view;

import controller.GameController;
import model.ChessColor;
import model.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
    JLabel playerLabel = new JLabel("White");
    JLabel bk;

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
        addTurns();
        addPlayerLabel();
        addSaveButton();
        addLoadButton();
        addThemeButton();
        addPvcButton();
        addBackground();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        chessboard.playerLabel(playerLabel);
        add(chessboard);
    }

    private void addBackground() {
        ImageIcon image = new ImageIcon("images/modern1.jpeg");
        bk = new JLabel(image);
        bk.setBounds(0, 0, WIDTH, HEIGHT);
        add(bk);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("ChessMaster 1.0");
        statusLabel.setLocation(WIDTH - 250, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel);
    }

    private void addTurns() {
        JLabel statusLabel = new JLabel("Current player:");
        statusLabel.setLocation(WIDTH - 250, HEIGHT / 10 + 50);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 17));
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel);
    }

    private void addPlayerLabel() {
        playerLabel.setLocation(WIDTH - 250, HEIGHT / 10 + 70);
        playerLabel.setSize(200, 60);
        playerLabel.setFont(new Font("Rockwell", Font.BOLD, 17));
        playerLabel.setForeground(Color.LIGHT_GRAY);
        add(playerLabel);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            chessboard.initChessGame();
            chessboard.repaint();
            playerLabel.setText("White");
        });
        button.setLocation(WIDTH - 265, HEIGHT / 10 + 160);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener((e) -> {
            if (chessboard.getCurrentColor() == ChessColor.NONE) {
                JOptionPane.showMessageDialog(this, "Current game already ended");
            } else {
                gameController.saveGame();
                JOptionPane.showMessageDialog(this, "Current state successfully saved.");
            }
        });
        button.setLocation(WIDTH - 265, HEIGHT / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH - 265, HEIGHT / 10 + 320);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            boolean doable = false;
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input path here (txt files only)");
            if (!path.endsWith(".txt") || Files.notExists(Paths.get(path)))
                JOptionPane.showMessageDialog(this, "Invalid path, please try again.(Error: 104)");
            else try {
                List<String> chessData = Files.readAllLines(Paths.get(path));
                if (chessData.size() == 9) {
                    if (chessData.get(8).equals("w") || chessData.get(8).equals("B")) {
                        int error = 0;
                        boolean chessboard = true;
                        for (int i = 0; i < 8; i++) {
                            if (chessData.get(i).length() != 8) {
                                chessboard = false;
                                JOptionPane.showMessageDialog(this, "Invalid file, please try again.(Error: 101)");
                                break;
                            }
                            for (int j = 0; j < chessData.get(i).length(); j++) {
                                switch (chessData.get(i).charAt(j)) {
                                    case 'K':
                                    case 'Q':
                                    case 'k':
                                    case 'q':
                                    case 'P':
                                    case 'p':
                                    case 'N':
                                    case 'n':
                                    case 'R':
                                    case 'r':
                                    case 'B':
                                    case 'b':
                                    case '_':
                                        continue;
                                    default:
                                        error++;
                                        break;
                                }
                            }
                        }
                        if (error > 0 && chessboard)
                            JOptionPane.showMessageDialog(this, "Invalid file, please try again.(Error: 102)");
                        else if (error == 0 && chessboard) doable = true;
                    } else JOptionPane.showMessageDialog(this, "Invalid file, please try again.(Error: 103)");
                } else {
                    if (chessData.get(chessData.size() - 1).equals("w") || chessData.get(chessData.size() - 1).equals("B"))
                        JOptionPane.showMessageDialog(this, "Invalid file, please try again.(Error: 101)");
                    else JOptionPane.showMessageDialog(this, "Invalid file, please try again.(Error: 103)");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (doable) {
                gameController.loadGameFromFile(path);
                chessboard.repaint();
            }
        });
    }

    private void addThemeButton() {
        JButton button = new JButton("Theme");
        button.addActionListener((e) -> {
            ThemeChanger themeChanger = new ThemeChanger(400, 200);
            themeChanger.setFrame(this);
            themeChanger.setVisible(true);
        });
        button.setLocation(WIDTH - 265, HEIGHT / 10 + 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void setTheme(String path) {
        this.bk.setVisible(false);
        ImageIcon image = new ImageIcon(path);
        JLabel bk = new JLabel(image);
        bk.setBounds(0, 0, WIDTH, HEIGHT);
        this.add(bk);
        this.bk = bk;
    }

    private void addPvcButton() {
        JButton button = new JButton("PvC Mode");
        button.addActionListener((e) -> {
            PvcDifficulty difficulty=new PvcDifficulty(400, 200);
            difficulty.setFrame(this);
            difficulty.setVisible(true);
        });
        button.setLocation(WIDTH - 265, HEIGHT / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void setDifficulty(Difficulty difficulty){
        chessboard.setDifficulty(difficulty);
    }

    public void setPvc(boolean pvc){
        chessboard.setPvcMode(pvc);
    }
}
