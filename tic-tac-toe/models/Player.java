package models;

public abstract class Player {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private char symbol;

    public char getSymbol() {
        return this.symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    protected MoveStrategy moveStrategy;

    public Player(String name, char symbol)
    {
        this.name = name; 
        this.symbol = symbol;
    }

    public String toString()
    {
        return "Player name: " + this.name + " Symbol: " + this.symbol;
    }


    public abstract Move makeMove();

}
