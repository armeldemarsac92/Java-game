package Test;

import java.io.IOException;
import java.util.List;

import Data.ScoreSystem;

public class Test {
    public static void main(String[] args){
        try {
            // Read the file and store lines in a List
            List<String> lines = ScoreSystem.getScores();

            lines.add("Antoine: 20");

            // Write the modified content back to the file
            ScoreSystem.addScore(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}