package controller;

import view.Chessboard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private Chessboard chessboard;
    public static int cnt=0;

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
        cnt++;
        File file= new File("resource/save"+cnt+".txt");

    }
}
