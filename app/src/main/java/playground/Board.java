package playground;

import java.util.ArrayList;
import java.util.List;

public class Board {  // save as Board.java
    // Named-constants for the dimensions
    //public static final int ROWS = 3;
    //public static final int COLS = 3;
    public int ROWS;
    public int COLS;
    public int numberToWin;
    public ArrayList winningFields ;

    // package access
    public Cell[][] cells;  // a board composes of ROWS-by-COLS Cell instances
    public int currentRow, currentCol;  // the current seed's row and column

    /**
     * Constructor to initialize the game board
     */
    public Board(int rows, int cols, int numberToWin) {
        this.ROWS = rows;
        this.COLS = cols;
        this.numberToWin = numberToWin;
        this.winningFields = new ArrayList<int[][]>();
        //this.winningFields = new int[rows][2];
        cells = new Cell[ROWS][COLS];  // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col); // allocate element of the array
            }
        }
    }


    /**
     * Return true if it is a draw (i.e., no more EMPTY cell)
     */
    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.EMPTY) {
                    return false; // an empty seed found, not a draw, exit
                }
            }
        }
        return true; // no empty cell, it's a draw
    }

    /**
     * Return true if the player with "theSeed" has won after placing at
     * (currentRow, currentCol)
     */
    public boolean hasWon(Seed theSeed) {
//
        int count1 = 0;
        int winnigFieldsCount = 0;
        winningFields.clear();
        winningFields.add(new int[][]{{currentRow,currentCol}});
        //horizontal
        while (((currentCol + count1 + 1) < COLS) &&
                cells[currentRow][currentCol + count1 + 1].content == theSeed) {

            count1++;
            winningFields.add(new int[][]{{currentRow,currentCol + count1}});

        }
        int count2 = 0;
        while ((currentCol - count2 - 1 >= 0) && cells[currentRow][currentCol - count2 - 1].content == theSeed) {
            count2++;
            winningFields.add(new int[][]{{currentRow,currentCol - count2}});
        }
        if ((count1 + count2) >= numberToWin - 1 &&
                cells[currentRow][currentCol].content == theSeed) {
            return true;
        }

        //vertikal
        count1 = 0;
        winnigFieldsCount = 0;
        winningFields.clear();
        while ((currentRow + count1 + 1) < ROWS &&
                cells[currentRow + count1 + 1][currentCol].content == theSeed) {
            count1++;
            winningFields.add(new int[][]{{currentRow + count1,currentCol}});
        }
        count2 = 0;
        while ((currentRow - count2 - 1) >= 0 &&
                cells[currentRow - count2 - 1][currentCol].content == theSeed) {
            count2++;
            winningFields.add(new int[][]{{currentRow - count2,currentCol}});

        }
        if ((count1 + count2) >= numberToWin - 1 &&
                cells[currentRow][currentCol].content == theSeed) {
            return true;
        }


        //diagonal 1
        count1 = 0;
        winnigFieldsCount=0;
        winningFields.clear();
        while ((currentRow + count1 + 1) < ROWS && (currentCol + count1 + 1) < COLS &&
                cells[currentRow + count1 + 1][currentCol + count1 + 1].content == theSeed) {
            count1++;
            winningFields.add(new int[][]{{currentRow + count1,currentCol + count1}});
        }
        count2 = 0;
        while ((currentRow - count2 - 1) >= 0 && (currentCol - count2 - 1) >= 0 &&
                cells[currentRow - count2 - 1][currentCol - count2 - 1].content == theSeed) {
            count2++;
            winningFields.add(new int[][]{{currentRow - count2,currentCol - count2}});
        }
        if ((count1 + count2) >= numberToWin - 1 &&
                cells[currentRow][currentCol].content == theSeed) {
            return true;
        }

        //diagonal 2
        count1 = 0;
        winnigFieldsCount=0;
        winningFields.clear();
        while ((currentRow - count1 - 1) >= 0 && (currentCol + count1 + 1) < COLS &&
                cells[currentRow - count1 - 1][currentCol + count1 + 1].content == theSeed) {
            count1++;
            winningFields.add(new int[][]{{currentRow - count1,currentCol + count1 }});

        }
        count2 = 0;
        while ((currentRow + count2 + 1) < ROWS && (currentCol - count2 - 1) >= 0 &&
                cells[currentRow + count2 + 1][currentCol - count2 - 1].content == theSeed) {
            count2++;
            winningFields.add(new int[][]{{currentRow + count2,currentCol - count2 }});

        }
        if ((count1 + count2) >= numberToWin - 1 &&
                cells[currentRow][currentCol].content == theSeed) {
            return true;
        }

        return false;
    }


    /**
     * Paint itself
     */
    public void paint() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint();   // each cell paints itself
                if (col < COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----------");
            }
        }
    }
}