package cz.muni.fi.pb162.hw01.impl;


/**
 *
 * Represents the board for the game of Tic Tac Toe.
 *
 * @author Martin Oliver Pitonak
 */
public class Board {
    private final int size;
    private final int winCondition;
    private final Character[][] values;
    private Board pastBoard;

    /**
     *
     * Sets the height and width of the board to the given size
     * and remembers the necessary win condition for the game with
     * this particular board.
     *
     * @param size of the board (both height and width)
     * @param winCondition win condition for the game
     */
    public Board(int size, int winCondition) {

        Character[][] values = new Character[size][size];

        initializeArray(values, size);

        this.values = values;
        this.size = size;
        this.winCondition = winCondition;
    }

    public int getSize() {
        return size;
    }

    public int getWinCondition() {
        return winCondition;
    }

    /**
     *
     * Return the value of the cell at the given position indicated by
     * its row and column.
     *
     * @param row row index of the needed cell
     * @param col column index of the needed cell
     * @return the corresponding value of the cell
     */
    public Character getValue(int row, int col) {
        return values[row][col];
    }

    /**
     *
     * Sets the value to the cell on the particular row and column.
     *
     * @param row row of the cell
     * @param col column of the cell
     * @param value value to set
     */
    public void setValue(int row, int col, Character value) {
        values[row][col] = value;
    }

    public Board getPastBoard() {
        return pastBoard;
    }

    public void setPastBoard(Board board) {
        pastBoard = board;
    }

    /**
     *
     * Checks if the board is fully filled, by check for the presence
     * of ' ' space character which indicates empty cell.
     *
     * @return true if empty, false otherwise
     */
    public boolean isFull() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (values[i][j] == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *
     * Checks if the board is in a particular state which would indicate
     * that one of the players have won.
     *
     * @param row row of the last placed character
     * @param col column of the last placed character
     * @param player character which to look for
     * @return true if one of the players have won
     */
    public boolean checkWin(int row, int col, char player) {

        if (Checker.row(this, row, player)) {
            return true;
        }

        if (Checker.column(this, col, player)) {
            return true;
        }

        return Checker.diagonal(this, row, col);
    }

    /**
     *
     *  Prints the board to the standard output.
     *
     */
    public void print() {
        for (int i = 0; i < size; i++) {
            printLine();
            printRow(i);
        }
        printLine();
    }

    /**
     *
     * Helper function for print that just prints the
     * dividing line between the rows.
     *
     */
    private void printLine() {
        String string = "";

        for (int i = 0; i < size; i++) {
            string += "--";
        }

        System.out.println(string + '-');
    }

    /**
     *
     * Prints a particular row of the board.
     *
     * @param row which row to print
     */
    private void printRow(int row) {

        for (int i = 0; i < size; i++) {
            System.out.printf("|%c", getValue(row, i));
        }

        System.out.println("|");
    }

    /**
     *
     * Copies the current state of the board into the new
     * Board object.
     *
     * @return the new board
     */
    public Board copyBoard() {

        Board newBoard = new Board(size, winCondition);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newBoard.setValue(i, j, getValue(i, j));
            }
        }
        return newBoard;
    }

    /**
     *
     * Helper function for the constructor that fills a nested array
     * filled with ' ' space character
     *
     * @param values array to fill
     * @param size size of the array and arrays inside
     */
    private void initializeArray(Character[][] values, int size) {

        for (int i = 0; i < size; i++) {

            Character[] row = new Character[size];

            for (int j = 0; j < size; j++) {

                row[j] = ' ';
            }

            values[i] = row;
        }
    }
}
