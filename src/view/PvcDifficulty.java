package view;

import model.Difficulty;

import javax.swing.*;
import java.awt.*;

public class PvcDifficulty extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    ChessGameFrame frame;

    public void setFrame(ChessGameFrame frame) {
        this.frame = frame;
    }

    public PvcDifficulty(int width, int height) {
        setTitle("PvC Difficulty"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addLabel();
        addDiff1();
        addDiff2();
        addDiff3();
        addOff();
    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("Choose a difficulty:");
        statusLabel.setLocation(WIDTH / 2 - 100, HEIGHT / 10 - 20);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addDiff1() {
        JButton button = new JButton("Rookie");
        button.addActionListener((e) -> {
            frame.setDifficulty(Difficulty.DIFFICULTY_Normal);
            frame.setPvc(true);
            this.setVisible(false);
        });
        button.setLocation(WIDTH / 2 - 120, HEIGHT / 10 + 50);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }

    private void addDiff2() {
        JButton button = new JButton("Challenger");
        button.addActionListener((e) -> {
            frame.setDifficulty(Difficulty.DIFFICULTY_Hard);
            frame.setPvc(true);
            this.setVisible(false);
        });
        button.setLocation(WIDTH / 2, HEIGHT / 10 + 50);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 11));
        add(button);
    }

    private void addDiff3() {
        JButton button = new JButton("MASTER");
        button.addActionListener((e) -> {
            frame.setDifficulty(Difficulty.DIFFICULTY_Hell);
            frame.setPvc(true);
            this.setVisible(false);
        });
        button.setLocation(WIDTH / 2-120, HEIGHT / 10 + 90);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }

    private void addOff() {
        JButton button = new JButton("Off");
        button.addActionListener((e) -> {
            frame.setPvc(false);
            this.setVisible(false);
        });
        button.setLocation(WIDTH / 2, HEIGHT / 10 + 90);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }
}
