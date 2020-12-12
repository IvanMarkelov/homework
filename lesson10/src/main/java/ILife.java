public interface ILife {

    boolean[][] simulateLife(int numberOfCycles);

    default boolean getNextGenCell(int rowPos, int colPos, boolean[][] field) {
        boolean cellStatus = field[rowPos][colPos];
        int numberOfLiveNeighbours = 0;
        for (int x = rowPos - 1; x <= rowPos + 1; x++) {
            for (int y = colPos - 1; y <= colPos + 1; y++) {
                if (!(x == rowPos && y == colPos)) {
                    int adjustedX = adjustPosition(x, field.length);
                    int adjustedY = adjustPosition(y, field[1].length);
                    if (field[adjustedX][adjustedY]) {
                        numberOfLiveNeighbours++;
                    }
                }
            }
        }
        return calculateNextGenCellCondition(cellStatus, numberOfLiveNeighbours);
    }

    default int adjustPosition(int pos, int sideSize) {
        if (pos < 0) {
            pos = sideSize - 1;
        } else if (pos > sideSize - 1) {
            pos = 0;
        }
        return pos;
    }

    default boolean calculateNextGenCellCondition(boolean cellStatus, int numberOfLiveNeighbours) {
        boolean nextGenerationCellStatus = false;
        if (!cellStatus && numberOfLiveNeighbours == 3) {
            nextGenerationCellStatus = true;
        } else if (cellStatus &&
                (numberOfLiveNeighbours >= 2 && numberOfLiveNeighbours <= 3)) {
            nextGenerationCellStatus = true;
        }
        return nextGenerationCellStatus;
    }
}
