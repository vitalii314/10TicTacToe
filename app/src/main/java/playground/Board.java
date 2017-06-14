package playground;

public class Board {  // save as Board.java
    // Named-constants for the dimensions
    //public static final int ROWS = 3;
    //public static final int COLS = 3;
    public int ROWS;
    public int COLS;
    public int numberToWin;
    public int[][] winningFields = new int[3][2];

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
//        if (cells[currentRow][0].content == theSeed         // 3-in-the-row
//                && cells[currentRow][1].content == theSeed
//                && cells[currentRow][2].content == theSeed) {
//            winningFields[0][0] = currentRow;
//            winningFields[0][1] = 0;
//            winningFields[1][0] = currentRow;
//            winningFields[2][0] = currentRow;
//            winningFields[1][1] = 1;
//            winningFields[2][1] = 2;
//            return true;
//
//        }

        int count1 = 0;
        while (((currentCol+count1+1)<COLS)&&
                cells[currentRow][currentCol+ count1+1].content==theSeed) {
            count1++;
        }
        int count2 = 0;
        while ((currentCol-count2-1>=0)&&cells[currentRow][currentCol-count2-1].content==theSeed) {
            count2++;
        }
        if ((count1+count2)>=numberToWin-1&&
                cells[currentRow][currentCol].content==theSeed) {
            return true;
        }


//         if (cells[0][currentCol].content == theSeed      // 3-in-the-column
//                && cells[1][currentCol].content == theSeed
//                && cells[2][currentCol].content == theSeed) {
//             winningFields[0][0] = 0;
//             winningFields[0][1] = currentCol;
//             winningFields[1][0] = 1;
//             winningFields[1][1] = currentCol;
//             winningFields[2][0] = 2;
//             winningFields[2][1] = currentCol;
//             return true;
//         }

        count1 = 0;
        while ((currentRow+count1+1)<ROWS&&
                cells[currentRow+count1+1][currentCol].content==theSeed) {
            count1++;
        }
        count2 = 0;
        while ((currentRow-count2-1)>=0&&
                cells[currentRow-count2-1][currentCol].content==theSeed) {
            count2++;
        }
        if ((count1+count2)>=numberToWin-1&&
                cells[currentRow][currentCol].content==theSeed) {
            return true;
        }


//                if ( currentRow == currentCol            // 3-in-the-diagonal
//                && cells[0][0].content == theSeed
//                && cells[1][1].content == theSeed
//                && cells[2][2].content == theSeed) {
//                    winningFields[0][0]=0;
//                    winningFields[0][1]=0;
//                    winningFields[1][0]=1;
//                    winningFields[1][1]= 1;
//                    winningFields[2][0] = 2;
//                    winningFields[2][1] = 2;
//                    return  true;
//                }
//                if ( currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
//                && cells[0][2].content == theSeed
//                && cells[1][1].content == theSeed
//                && cells[2][0].content == theSeed) {
//                    winningFields[0][0]=0;
//                    winningFields[0][1]=2;
//                    winningFields[1][0]=1;
//                    winningFields[1][1]=1;
//                    winningFields[2][0]=2;
//                    winningFields[2][1]=0;
//                    return true;
//                }

        //diagonal 1
        count1 = 0;
        while ((currentRow+count1+1)<ROWS&&(currentCol+count1+1)<COLS&&
                cells[currentRow+count1+1][currentCol+count1+1].content==theSeed) {
            count1++;
        }
        count2 = 0;
        while ((currentRow-count2-1)>=0&&(currentCol-count2-1)>=0&&
                cells[currentRow-count2-1][currentCol-count2-1].content==theSeed) {
            count2++;
        }
        if ((count1+count2)>=numberToWin-1&&
                cells[currentRow][currentCol].content==theSeed) {
            return true;
        }

        //diagonal 2
        count1 = 0;
        while ((currentRow-count1-1)>=0&&(currentCol+count1+1)<COLS&&
                cells[currentRow-count1-1][currentCol+count1+1].content==theSeed) {
            count1++;
        }
        count2 = 0;
        while ((currentRow+count2+1)<ROWS&&(currentCol-count2-1)>=0&&
                cells[currentRow+count2+1][currentCol-count2-1].content==theSeed) {
            count2++;
        }
        if ((count1+count2)>=numberToWin-1&&
                cells[currentRow][currentCol].content==theSeed) {
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