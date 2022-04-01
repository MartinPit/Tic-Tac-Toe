package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.Messages;

/**
 *
 * Represents a game of Tic Tac Toe.
 *
 * @author Martin Oliver Pitonak
 */
public class Game {
    private final String players;
    private final int history;
    private Board board;
    private int rewindCount = 0;
    private int turn = 1;

    /**
     *
     * Creates a game of Tic Tac Toe on a given board, that
     * is played by given players and sets up the maximum amount
     * of turns the players can rewind.
     *
     * @param board board of the game
     * @param players string representing all the playing players,
     *                one player is represented by one character
     * @param history the amount of turns players are able to rewind
     */
    public Game(Board board, String players, int history) {

        this.board = board;
        this.players = players;
        this.history = history;
    }

    /**
     *
     * Increments the turn counter.
     *
     */
    public void incrementTurn() {
        turn++;
    }

    /**
     *
     * Plays a single turn of the game.
     *
     * @return the state of the game after the turn
     */
    public GameStatus playTurn() {

        char player = players.charAt((turn - 1) % players.length()); //computes the current player

        System.out.printf(Messages.TURN_COUNTER, turn);
        board.print();
        System.out.printf(Messages.TURN_PROMPT, player);

        String input = Utils.readLineFromStdIn();
        GameStatus status = executeInput(input, player);
        System.out.println(Messages.TURN_DELIMITER);

        return status;
    }

    /**
     *
     * Prints the messages if game ends, if its necessary also prints
     * a message with the winning player
     *
     * @param status last status of the game
     *               GAME_OVER - just ends
     *               WIN - also prints the winning player
     */
    public void end(GameStatus status) {

        System.out.printf(Messages.GAME_OVER, turn);
        board.print();

        if (status == GameStatus.WIN) {
            System.out.printf(Messages.GAME_WINNER, players.charAt((turn - 1) % players.length()));
        }
    }

    /**
     *
     * Checks and executes the given input by the player
     *
     * @param input input of the player
     * @param player which player it belongs to
     * @return status of the game after trying to execute the input
     */
    private GameStatus executeInput(String input, char player) {

        input = input.trim();

        if (input.equals(":q")) {
            turn--;
            return GameStatus.GAME_OVER;
        }

        if (input.charAt(0) == '<' && input.charAt(1) == '<') {
            return executeRewind(input.substring(2));
        }

        return executePlay(input, player);
    }

    /**
     *
     * Helper method for the executeInput. If the input is determined
     * to be the action of game rewind, this function executes it.
     *
     * @param input the player's of the number of turns to rewind back
     * @return the status of the game after executing the rewind
     */
    private GameStatus executeRewind(String input) {

        if (input.length() == 0) {
            System.out.println(Messages.ERROR_INVALID_COMMAND);
            return  GameStatus.ERROR;
        }
        int amount = createNum(input);

        if (amount == -1) {
            System.out.println(Messages.ERROR_INVALID_COMMAND);
            return GameStatus.ERROR;
        }

        if (amount >= history || amount >= turn - (rewindCount + 1)) { //check if the amount to rewind is not bigger
                                                                       // or equal to characters on the playing board
            System.out.println(Messages.ERROR_REWIND);
            return GameStatus.ERROR;
        }

        rewindCount += amount + 1;  // adding number of turns rewound plus this turn
        for (int i = 0; i < amount; i++) {
            board = board.getPastBoard();
        }

        return GameStatus.CONTINUE;
    }

    /**
     *
     * Helper method for the executeInput. If the input is determined
     * to be the action of playing the game, this function executes it.
     *
     * @param input input of the player
     * @param player which player made the input
     * @return the status of the game after executing the play input
     */
    private GameStatus executePlay(String input, char player) {

        String[] numbers = input.split(" ");

        if (numbers.length != 2) {
            System.out.println(Messages.ERROR_INVALID_COMMAND);
            return GameStatus.ERROR;
        }

        int row = createNum(numbers[0]);
        int col = createNum(numbers[1]);

        if (row == -1 || col == -1) {
            System.out.println(Messages.ERROR_INVALID_COMMAND);
            return GameStatus.ERROR;
        }

        if (row < 0 || row >= board.getSize()) {
            System.out.println(Messages.ERROR_ILLEGAL_PLAY);
            return GameStatus.ERROR;
        }

        if (col < 0 || col >= board.getSize()) {
            System.out.println(Messages.ERROR_ILLEGAL_PLAY);
            return GameStatus.ERROR;
        }

        if (board.getValue(row, col) != ' ') {
            System.out.println(Messages.ERROR_ILLEGAL_PLAY);
            return GameStatus.ERROR;
        }

        Board oldBoard = board;
        board = board.copyBoard();
        board.setPastBoard(oldBoard);
        board.setValue(row, col, player);

        return checkEnd(row, col, player);
    }

    /**
     *
     * Helper method for executeInput. Checks if the string is able
     * to be converted into int, if yes then it returns the converted number.
     *
     * @param input input to be converted
     * @return -1 if the input cannot be converted, the converted number otherwise
     */
    private int createNum(String input) {

        int num = 0;

        for (int i =0; i < input.length(); i++) {
            Character ch = input.charAt(i);

            if (! Character.isDigit(ch)) {
                return -1;
            }
            int tempNum = Character.getNumericValue(ch);
            num = num * 10 + tempNum;
        }

        return num;
    }

    /**
     *
     * Checks if the game has ended or if it is able to continue.
     *
     * @param row index of the row of the last placed character
     * @param col index of the column of the last placed character
     * @param player player who placed the character
     * @return status if the game is able to continue or not, or if
     *          one of the players have won
     */
    private GameStatus checkEnd(int row, int col, char player) {

        if (board.checkWin(row, col, player)) {
            return GameStatus.WIN;
        }
        if (board.isFull()) {
            return  GameStatus.GAME_OVER;
        }

        return GameStatus.CONTINUE;
    }
}
