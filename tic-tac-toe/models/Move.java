package models;

public class Move {
    protected int x;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    int y;

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Move(int x, int y)
    {
        this.x =x; this.y = y;
    }

}
