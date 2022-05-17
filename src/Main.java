import view.ChessGameLauncher;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameLauncher launcher=new ChessGameLauncher( 400, 760);
            launcher.setVisible(true);
        });
    }
}
