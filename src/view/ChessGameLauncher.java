package view;

import javax.swing.*;
import java.awt.*;


public class ChessGameLauncher extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;

    public ChessGameLauncher(int width, int height) {
        setTitle("ChessMaster"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addLabel();
        addStartButton();
        addBackground();
    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("ChessMaster");
        statusLabel.setLocation(WIDTH / 2 - 105, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel);
    }

    private void addStartButton() {
        JButton button = new JButton("Start Game");
        button.addActionListener((e) -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
            this.setVisible(false);
            dispose();
        });
        button.setLocation(WIDTH / 2 - 110, HEIGHT / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addBackground(){
        ImageIcon image=new ImageIcon("images/ChessMaster.jpeg");
        JLabel bk=new JLabel(image);
        bk.setBounds(0,0,image.getIconWidth(),image.getIconHeight());
        add(bk);
    }
}
