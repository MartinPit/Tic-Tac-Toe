package cz.muni.fi.pb162.hw01.impl;

/**
 *
 * Helper class for the board class. Checks row, column
 * or diagonals for the win condition.
 *
 * @author Martin Oliver Pitonak
 */
public final class Checker {

    private Checker() {}

    /**
     *
     * Checks if there is enough characters of the given
     * player in the given row one after another so that
     * he is able to win.
     *
     * @param board board to check it on
     * @param row row which to check
     * @param player player that would win
     * @return true if player has won, false otherwise
     */
    public static boolean row(Board board, int row, char player) {

        int consecutiveCells = 0;

        for (int i = 0; i < board.getSize(); i++) {

            if (board.getValue(row, i) != player) {
                consecutiveCells = 0;
            } else {
                consecutiveCells++;
            }

            if (consecutiveCells >= board.getWinCondition()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Checks if there is enough characters of the given
     * player in the given column one after another so that
     * he is able to win.
     *
     * @param board board to check it on
     * @param col column which to check
     * @param player player that would win
     * @return true if player has won, false otherwise
     */
    public static boolean column(Board board, int col, char player) {

        int consecutiveCells = 0;

        for (int i = 0; i < board.getSize(); i++) {

            if (board.getValue(i, col) != player) {
                consecutiveCells = 0;
            } else {
                consecutiveCells++;
            }

            if (consecutiveCells >= board.getWinCondition()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Checks if there is enough characters of the given
     * player, in the diagonals given by the index of row and column,
     * one after another so that he is able to win. Deduces the player
     * from the character in the cell on the given row and column.
     *
     * @param board board to check it on
     * @param row row of the last placed character
     * @param col column of the last placed character
     * @return true if player has won, false otherwise
     */
    public static boolean diagonal(Board board, int row, int col) {

        char player = board.getValue(row, col);
        int size = board.getSize();
        int consecutiveCells = 1;

        for (int i = 1; i < size; i++) {

            if (row + i < size && col + i < size && board.getValue(row + i, col + i) == player) {
                consecutiveCells++;
            }

            if (row - i >= 0 && col - i >= 0 && board.getValue(row - i, col - i)== player) {
                consecutiveCells++;
            }

            if (consecutiveCells >= board.getWinCondition()) {
                return true;
            }
        }

        consecutiveCells = 1;

        for (int i = 1; i < size; i++) {

            if (row + i < size && col - i >= 0 && board.getValue(row + i, col - i) == player) {
                consecutiveCells++;
            }

            if (row - i >= 0 && col + i < size && board.getValue(row - i, col + i)== player) {
                consecutiveCells++;
            }

            if (consecutiveCells >= board.getWinCondition()) {
                return true;
            }
        }

        return false;
    }
}
