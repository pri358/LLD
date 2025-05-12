package services;

import java.util.Scanner;

import factories.PlayerFactory;
import models.*;

public class Game {
    BoardService boardService;
    Player player1;
    Player player2;
    Player currentPlayer; 

    public void setupGame()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Player 1 name: ");
        String name1 = scanner.nextLine();
        player1 = PlayerFactory.createPlayer(name1, 'X');

        System.out.print("Enter Player 2 name: ");
        String name2 = scanner.nextLine();
        player2 = PlayerFactory.createPlayer(name2, 'O');

        boardService = new BoardService();
        boardService.createBoard(3);
        currentPlayer = player1;

    }

    public void playGame()
    {
        while(true)
        {
            System.out.println(currentPlayer.toString() + " turn");
            Move move = currentPlayer.makeMove(); 
            if(boardService.makeMove(move, currentPlayer.getSymbol()))
            {
                if(boardService.checkWin(currentPlayer.getSymbol()))
                {
                    System.out.println(currentPlayer.toString()  + " wins!!");
                    break;
                }

                if(boardService.checkDraw())
                {
                    System.out.println("Its a draw!!");
                    break;
                }
                System.out.println(boardService.board.toString());
                switchPlayer();
            }

        }
    }

    private void switchPlayer()
    {
        currentPlayer = (currentPlayer.getName().equals(player1.getName())) ? player2 : player1;
    }
}
