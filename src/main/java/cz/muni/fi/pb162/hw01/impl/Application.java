package cz.muni.fi.pb162.hw01.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.CommandLine;

/**
 * Application class represents the command line interface of this application.
 *
 * You are expected to implement the {@link Application#run()} method
 *
 * @author jcechace
 */
public class Application {
    @Parameter(names = { "--size", "-s" })
    private int size = 3;

    @Parameter(names = { "--win", "-w" })
    private int win = 3;

    @Parameter(names = { "--history", "-h" })
    private int history = 1;

    @Parameter(names = { "--players", "-p" })
    private String players = "xo";

    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

    /**
     * Application entry point
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        Application app = new Application();

        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);

        if (app.showUsage) {
            cli.showUsage();
        } else {
            app.run();
        }
    }

    /**
     * Application runtime logic
     */
    private void run() {

        if (size < 3) {
            Utils.error("Size option is out of accepted range.");
        }

        if (win < 3 || win > size) {
            Utils.error("Win option is out of accepted range.");
        }

        if (history <= 0 || history >= size * size) {
            Utils.error("History option is out of accepted range.");
        }

        if (players.length() <= 1) {
            Utils.error("Not enough players.");
        }

        Board board = new Board(size, win);
        Game game = new Game(board, players, history);
        boolean playing = true;

        while (playing) {

            GameStatus status = game.playTurn();

            switch (status) {
                case WIN:
                case GAME_OVER:
                    game.end(status);
                    playing = false;
                    break;

                case CONTINUE:
                    game.incrementTurn();
                    break;

                default:
                    break;
            }
        }

    }
}
