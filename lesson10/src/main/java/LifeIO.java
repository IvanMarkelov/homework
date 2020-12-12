import java.io.*;
import java.util.InputMismatchException;
import java.util.StringJoiner;

public class LifeIO {
    public boolean[][] dataParsed;

    public LifeIO(String filepath) {
        dataParsed = parseInput(filepath);
    }

    private boolean[][] parseInput(String filepath) {
        int numberOfRows = 0;
        int numberOfCols = 0;
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            while ((line = bufferedReader.readLine()) != null) {
                if (numberOfRows == 0) {
                    bufferedReader.mark(1000);
                }
                numberOfRows++;
                numberOfCols = line.split(", ").length;
            }
            bufferedReader.reset();

            boolean[][] field = new boolean[numberOfRows][numberOfCols];

            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                for (int i = 0; i < numberOfCols; i++) {
                    field[index][i] = line.split(", ")[i].equals("true");
                }
                index++;
            }
            bufferedReader.close();

            return field;

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new InputMismatchException("Format of the file is invalid.");
    }

    public void writeLifeField(boolean[][] field, String filename) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < field.length; i++) {
                StringJoiner joiner = new StringJoiner(", ");
                for (int j = 0; j < field[1].length; j++) {
                    joiner.add(Boolean.toString(field[i][j]));
                }
                bufferedWriter.write(joiner.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLifeField() {
        for (int i = 0; i < dataParsed.length; i++) {
            for (int j = 0; j < dataParsed[1].length; j++) {
                char toPrint;
                toPrint = dataParsed[i][j] ? 'X' : 'O';
                System.out.print(toPrint + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
