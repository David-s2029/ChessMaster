package controller;

import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            Path path1=Paths.get(path);
            List<String> chessData = Files.readAllLines(path1);
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGame(){
        int cnt=0;
        while (true){
            String path=String.format("resource/save%d.txt",cnt);
            if (Files.exists(Paths.get(path))){
                cnt++;
            }else {
//                Files.createFile(Paths.get(path),
                break;
            }
        }
    }
}
