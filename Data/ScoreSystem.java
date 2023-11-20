package Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreSystem {

    /*---------- Methods ---------- */
    private static String filePath = "./scores.txt";

    public static List<String> getScores() throws IOException {
        FileReader fileReader = new FileReader(ScoreSystem.filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<>();

        while (bufferedReader.readLine() != null) {
            lines.add(bufferedReader.readLine());
        }

        bufferedReader.close();
        return lines;
    }

    public static List<String> getOrderedScores() throws IOException {
        FileReader fileReader = new FileReader(ScoreSystem.filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<String> lines = new ArrayList<>();

        while(bufferedReader.readLine() != null){
            lines.add(bufferedReader.readLine());
        }

        bufferedReader.close();

        return lines;
    }

    public static void addScore(List<String> lines) throws IOException {
        FileWriter fileWriter = new FileWriter(ScoreSystem.filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine(); // Add a new line after each line
        }

        bufferedWriter.close();
    }
}
