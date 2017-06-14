package playground;

public class SimplePlayGround implements Playground {
    private Board board;
    private Seed currentPlayer = Seed.CROSS;
    private int rows;
    private int cols;
    private int numberToWin;

    public SimplePlayGround(int rows, int cols, int numberToWin) {
        this.rows = rows;
        this.cols = cols;
        this.numberToWin=numberToWin;
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {this.board=board;}

    public Seed getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Seed currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void start() {
        board = new Board(rows,cols,numberToWin);
        currentPlayer = Seed.CROSS;
    }



    public String print(){
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < board.ROWS; ++row) {
            for (int col = 0; col < board.COLS; ++col) {
                sb.append(" ");
                switch (board.cells[row][col].content) {
                    case CROSS:  sb.append(" X "); break;
                    case NOUGHT: sb.append(" O "); break;
                    case EMPTY:  sb.append("   "); break;
                }

                if (col < board.COLS - 1) sb.append("|");
            }
            sb.append("\n");
            if (row < board.ROWS - 1) {
                sb.append("-----------");
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public State doStep(int i, int j) {
        if (isFinished()) {
            return getWinner();
        }
        if (isMoveAllowed(i, j)) {
            updateState(i, j);
            if (isFinished()) {
                return getWinner();
            }

        } else {
            return State.INVALID_INPUT_DATA;
        }

        currentPlayer = (currentPlayer == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS);
        return State.PLAY;
    }

    public boolean isFinished() {
        return board.hasWon(Seed.CROSS) || (board.hasWon(Seed.NOUGHT) || board.isDraw());
    }

    private State getWinner() {
        if (board.isDraw()) {
            return State.DRAW;
        } else if (currentPlayer == Seed.CROSS) {
            return State.CROSS_WON;

        } else return State.NOUGHT_WON;
    }

    private boolean isMoveAllowed(int i, int j) {
        if (!(i < this.rows && i >= 0 && j < this.cols && j >= 0)) {
            return false;
        }
        if (board.cells[i][j].content != Seed.EMPTY) {
            return false;
        }
        return true;
    }

    private void updateState(int i, int j) {
        board.cells[i][j].content = currentPlayer;
        board.currentRow = i;
        board.currentCol = j;
    }

}
