import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.StringJoiner;

public class LifeIO {

    public boolean[][] lifeReader(String filepath) {
        int numberOfRows = 0;
        int numberOfCols = 0;
        ArrayList<boolean[]> arrField = new ArrayList<>();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            while ((line = bufferedReader.readLine()) != null) {
                if (numberOfRows == 0) {
                    numberOfCols = line.split(", ").length;
                }
                boolean[] row = new boolean[numberOfCols];
                for (int i = 0; i < numberOfCols; i++) {
                    row[i] = Boolean.parseBoolean(line.split(", ")[i]);
                }
                arrField.add(row);
                numberOfRows++;
            }
            bufferedReader.close();

            boolean[][] field = new boolean[numberOfRows][numberOfCols];
            for (int i = 0; i < numberOfRows; i++) {
                field[i] = arrField.get(i);
            }
            return field;

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new InputMismatchException("Format of the file is invalid.");
    }

    public void lifeWriter(boolean[][] field, String filename) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            for (boolean[] booleans : field) {
                StringJoiner joiner = new StringJoiner(", ");
                for (int j = 0; j < field[1].length; j++) {
                    joiner.add(Boolean.toString(booleans[j]));
                }
                bufferedWriter.write(joiner.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lifeConsolePrinter(boolean[][] field) {
        for (boolean[] booleans : field) {
            for (int j = 0; j < field[1].length; j++) {
                char toPrint;
                toPrint = booleans[j] ? 'X' : 'O';
                System.out.print(toPrint + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean[][] lifeFieldGenerator(int rows, int columns) {
        boolean[][] field = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                field[i][j] = Math.random() > 0.7;
            }
        }
        return field;
    }

    public boolean compareFields(boolean[][] fieldOneThread, boolean[][] fieldMultiThread) {
        for (int i = 0; i < fieldOneThread.length; i++) {
            for (int j = 0; j < fieldOneThread[1].length; j++) {
                if (fieldOneThread[i][j] != fieldMultiThread[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
