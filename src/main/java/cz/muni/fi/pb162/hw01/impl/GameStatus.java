package cz.muni.fi.pb162.hw01.impl;

/**
 *
 * Helper enum class for the Game class
 *       ERROR - if the game encounters an error
 *       CONTINUE - if the game is able to continue after the turn
 *       WIN - if one of the players have won
 *       GAME_OVER - if the game is over due to the lack of free cells
 *
 * @author Martin Oliver Pitonak
 */
public enum GameStatus {
    ERROR,
    GAME_OVER,
    WIN,
    CONTINUE
}
