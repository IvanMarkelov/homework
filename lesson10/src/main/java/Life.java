

public class Life implements ILife {
    public boolean[][] field;

    public Life(boolean[][] field) {
        this.field = field;
    }

    public boolean[][] simulateLife(int numberOfCycles) {
        boolean[][] nextGenField = this.field;
        if (numberOfCycles < 0) {
            throw new IllegalArgumentException("Number of cycles cannot be less than 0!");
        }
        for (int i = 0; i < numberOfCycles; i++) {
            nextGenField = calculateNextGeneration(nextGenField);
        }
        return nextGenField;
    }

    public boolean[][] calculateNextGeneration(boolean[][] field) {
        boolean[][] nextGen = new boolean[field.length][field[1].length];
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[1].length; col++) {
                nextGen[row][col] = getNextGenCell(row, col, field);
            }
        }
        return nextGen;
    }
}
