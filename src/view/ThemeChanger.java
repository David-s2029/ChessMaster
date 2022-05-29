package view;

import javax.swing.*;
import java.awt.*;

public class ThemeChanger extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    ChessGameFrame frame;

    public void setFrame(ChessGameFrame frame) {
        this.frame = frame;
    }

    public ThemeChanger(int width, int height) {
        setTitle("Theme"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addLabel();
        addTheme1();
        addTheme2();
        addTheme3();
        addTheme4();

    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("Choose a theme:");
        statusLabel.setLocation(WIDTH / 2 - 90, HEIGHT / 10 - 20);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addTheme1() {
        JButton button = new JButton("Classic1");
        button.addActionListener((e) -> {
            frame.setTheme("images/classic1.jpeg");
            this.setVisible(false);
        });
        button.setLocation(WIDTH/2-120, HEIGHT / 10+50);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }

    private void addTheme2() {
        JButton button = new JButton("Classic2");
        button.addActionListener((e) -> {
            frame.setTheme("images/classic2.jpeg");
            this.setVisible(false);
        });
        button.setLocation(WIDTH/2-120, HEIGHT / 10+90);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }

    private void addTheme3() {
        JButton button = new JButton("Classic3");
        button.addActionListener((e) -> {
            frame.setTheme("images/classic3.jpeg");
            this.setVisible(false);
        });
        button.setLocation(WIDTH/2, HEIGHT / 10+50);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }

    private void addTheme4() {
        JButton button = new JButton("Modern1");
        button.addActionListener((e) -> {
            frame.setTheme("images/modern1.jpeg");
            this.setVisible(false);
        });
        button.setLocation(WIDTH/2, HEIGHT / 10+90);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 13));
        add(button);
    }
}

