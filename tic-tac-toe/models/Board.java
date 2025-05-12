package models;

import java.util.*;

public class Board {
    private char[][] board;

    public char[][] getBoard() {
        return this.board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    private int size;

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public Board(int size)
    {
        this.size = size;
        board = new char[size][size];
        for(char[] row: board)
        {
            Arrays.fill(row, '.');
        }
    }

    @Override
    public String toString()
    {
        StringBuilder total = new StringBuilder();
        for(int i=0;i<size;i++)
        {
            StringBuilder rep = new StringBuilder();
            for(int j=0;j<size;j++)
            {
                rep.append(board[i][j]);
                rep.append('|');
            }
            total.append(rep);
            total.append('\n');
        }
        return total.toString();
    }


}
