package controllers;

import services.*;

public class GameController {
    private static GameController instance;
    private Game game;

    private GameController() {
        game = new Game();
    }

    public static GameController getInstance()
    {
        if(instance == null) instance = new GameController();
        return instance;
    }

    public void startGame()
    {
        game.setupGame();
        game.playGame();
    }
}
