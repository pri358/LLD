package models;

import java.util.Scanner;

public class DefaultMoveStrategy implements MoveStrategy{
    public Move getMove()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter row (0-2)");
        int x = scanner.nextInt();
        System.out.println("Enter col (0-2)");
        int y = scanner.nextInt();
        return new Move(x,y);
    }
}
