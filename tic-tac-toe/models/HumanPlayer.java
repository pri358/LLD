package models;

public class HumanPlayer extends Player{

    public HumanPlayer(String name, char symbol)
    {
        super(name, symbol);
        moveStrategy = new DefaultMoveStrategy();
    }

    public Move makeMove()
    {
        return moveStrategy.getMove();
    }
}
