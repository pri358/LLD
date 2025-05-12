package services;

import models.*;

public class BoardService {
    Board board; 

    public BoardService()
    {
    }

    public void createBoard(int size)
    {
        board = new Board(size);
    }

    public boolean makeMove(Move move, char symbol)
    {
        if(!isValid(move))
        {
            System.out.println("Invalid move! Try again");
            return false;
        }
        board.getBoard()[move.getX()][move.getY()] = symbol;
        return true;
    }

    public boolean checkDraw()
    {
        char[][] curBoard = board.getBoard();
        int size = board.getSize();
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(curBoard[i][j] == '.') return false;
            }
        }
        return true;
    }

    public boolean checkWin(char symbol)
    {
        int size = board.getSize(); 

        for (int i = 0; i < size; i++) {
            if (checkRow(symbol, i) || checkColumn(symbol, i)) {
                return true;
            }
        }
        // Check diagonals
        return checkDiagonal(symbol) || checkAntiDiagonal(symbol);

    }


    private boolean checkRow(char symbol, int row) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getBoard()[row][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(char symbol, int col) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getBoard()[i][col] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonal(char symbol) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getBoard()[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAntiDiagonal(char symbol) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            if (board.getBoard()[i][size-i-1] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(Move move)
    {
        return move.getX() >=0 && move.getY() >= 0 && move.getX() < board.getSize() && move.getY() < board.getSize() && board.getBoard()[move.getX()][move.getY()] == '.';
    }
}
