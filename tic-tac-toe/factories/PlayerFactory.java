package factories;

import models.*;

public class PlayerFactory {
    public static Player createPlayer(String playerName, char symbol)
    {
        return new HumanPlayer(playerName, symbol);
    }
}
