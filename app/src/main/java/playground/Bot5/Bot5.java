package playground.Bot5;

/**
 * Created by user on 10.02.2017.
 */

import com.google.gson.Gson;

import playground.Playground;
import playground.Seed;
import playground.SimplePlayGround;

import java.util.ArrayList;
import java.util.List;


/**
 * New Bot class , which can be used with different playground sizes and different depth
 * Uses "maximin" procedure with alpha beta pruning for determining best move
 * Max recommended depth for playground 3x3 with 3 in row to win - 8
 * Max recommended depth for playground 10x10 with 5 in row to win - 3
 */
public class Bot5 implements Playable {
    List<int[][]> randomMoveList = new ArrayList<int[][]>();
    private int depth;
    private SimplePlayGround playGround;
    private int[] move = new int[2];
    int[][] tempMoves;
    int count = 0;
    private boolean mWasMoveMade;
    private boolean mFlag;  // this variables are used to adopt random move selection
    private boolean mWasFinished; //with alpha beta
    // they determine if returning score is "fair"

    // standart maximin procedure, without alpha beta
    public int maximin(Seed seed) {
        if (count >= depth || playGround.isFinished()) {
            return evaluate(playGround, seed);
        }
        int score = (count + 1) % 2 == 0 ? 1000 : -1000;
        for (int i = 0; i < playGround.getBoard().cells.length; i++) {
            for (int j = 0; j < playGround.getBoard().cells[i].length; j++) {
                if (playGround.getBoard().cells[i][j].content == Seed.EMPTY &&
                        !playGround.isFinished()) {
                    count++;
                    playGround.setCurrentPlayer(count % 2 != 0 ? seed : (seed == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS));
                    playGround.doStep(i, j);
                    tempMoves[count - 1][0] = i;
                    tempMoves[count - 1][1] = j;

                    int s = maximin(seed);

                    if (count % 2 == 0) {
                        if (s < score) {
                            score = s;
                        }
                    } else if (s > score) {
                        score = s;
                        if (count == 1) {
                            move[0] = tempMoves[0][0];
                            move[1] = tempMoves[0][1];
                        }

                    }

                    count--;
                    playGround.setCurrentPlayer(seed);
                    playGround.getBoard().cells[tempMoves[count][0]][tempMoves[count][1]].content = Seed.EMPTY;
                }

            }
        }
        return score;
    }

    public int maximinWithAlphaBeta(int alpha, int beta, Seed seed) {
        if (count >= depth || playGround.isFinished()) {
            if (playGround.isFinished()) mWasFinished = true;
            return evaluateAdvanced(playGround,
                    seed == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS, tempMoves[count - 1][0], tempMoves[count - 1][1]);
        }

        int score = (count + 1) % 2 == 0 ? 1000 : -1000;  //without alpha beta
        //int score = (count + 1) % 2 == 0 ? beta : alpha;
        boolean flag = (count + 1) % 2 == 0 ? true : false;


        for (int i = 0; i < playGround.getBoard().cells.length; i++) {
            for (int j = 0; j < playGround.getBoard().cells[i].length; j++) {
                if (Thread.currentThread().isInterrupted()) return score;
                if (playGround.getBoard().cells[i][j].content == Seed.EMPTY &&
                        !playGround.isFinished() && isThereEmptyFieldNear(i, j)) {
                    count++;
                    mFlag = false;
                    mWasMoveMade = true;

                    playGround.setCurrentPlayer(seed);

                    playGround.doStep(i, j);
                    tempMoves[count - 1][0] = i;
                    tempMoves[count - 1][1] = j;

                    int s = maximinWithAlphaBeta((count % 2 != 0 ? score : alpha), (count % 2 != 0 ? beta : score),
                            seed == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS);
                    if (Thread.currentThread().isInterrupted()) return score;
                    count--;


                    playGround.setCurrentPlayer(seed);
                    playGround.getBoard().cells[tempMoves[count][0]][tempMoves[count][1]].content = Seed.EMPTY;

                    if ((count + 1) % 2 == 0) {

                        // determining if score value is fair
                        if (s < score) {
                            flag = mFlag;
                        } else if (s == score && !mFlag) {
                            flag = false;
                        }

                        if ((count + 1) == depth || mWasFinished) {
                            if (s <= score) flag = true;
                        }

                        if (s < score) {
                            score = s;
                        }
                        if (score < alpha) {
                            mFlag = flag;
                            mWasFinished = false;
                            return score;
                        }
                    } else {

                        if (s > score) {
                            flag = mFlag;
                        } else if (s == score && mFlag) {
                            flag = true;
                        }
                        if ((count + 1) == depth || mWasFinished) {
                            if (s >= score) flag = true;
                        }
                        if (s == score && mFlag) {
                            if (count == 0) {
                                move[0] = tempMoves[0][0];
                                move[1] = tempMoves[0][1];
                                int[][] tempArr = {{move[0], move[1]}};
                                randomMoveList.add(tempArr);
                            }
                        }


                        if (s > score) {

                            score = s;

                            if (count == 0) {
                                move[0] = tempMoves[0][0];
                                move[1] = tempMoves[0][1];
                                randomMoveList.clear();
                                int[][] tempArr = {{move[0], move[1]}};
                                randomMoveList.add(tempArr);

                            }

                        }

                        if (score > beta) {

                            mFlag = flag;
                            mWasFinished = false;
                            return score;

                        }

                    }

                    mWasFinished = false;
                }

            }


        }

        mFlag = flag;
        mWasFinished = false;
        return score;
    }

    /**
     * A simple evaluation function,
     * evaluates: win, loose, draw, none
     */
    public int evaluate(SimplePlayGround playGround, Seed seed) {
        if (!playGround.isFinished()) return 0;
        if (playGround.getBoard().hasWon(seed)) {
            return 5;
        } else {
            if (playGround.getBoard().hasWon(seed == Seed.NOUGHT ? Seed.CROSS : Seed.NOUGHT)) {
                return -5;
            } else {
                return 2;
            }

        }
    }

    /**
     * Advanced evaluation function
     * evaluates
     * how many player's fields the move completes
     * plus how many opponent's fields the move destroys
     * Also detects next move win both for player and opponent
     */

    public int evaluateAdvanced(SimplePlayGround playGround, Seed seed, int i, int j) {
        int countTotalPlayer;
        boolean isWinningNextMovePlayer = false;
        boolean isWinningNextMoveOpp = false;
        //horizontal player's
        int count1 = 0;
        while (((j + count1 + 1) < playGround.getBoard().COLS) &&
                playGround.getBoard().cells[i][j + count1 + 1].content == seed) {
            count1++;
        }
        int count2 = 0;
        while ((j - count2 - 1 >= 0) &&
                playGround.getBoard().cells[i][j - count2 - 1].content == seed) {
            count2++;
        }
        countTotalPlayer = count1 + count2;

        // checking if we have numberToWin-1 in row and
        // 2 empty on both sides (winning next move)
        if (countTotalPlayer == playGround.getBoard().numberToWin - 2 &&
                j + count1 + 1 < playGround.getBoard().COLS &&
                playGround.getBoard().cells[i][j + count1 + 1].content == Seed.EMPTY &&
                j - count2 - 1 >= 0 &&
                playGround.getBoard().cells[i][j - count2 - 1].content == Seed.EMPTY) {
            isWinningNextMovePlayer = true;
        }

        // vertikal player's
        count1 = 0;
        while ((i + count1 + 1) < playGround.getBoard().ROWS &&
                playGround.getBoard().cells[i + count1 + 1][j].content == seed) {
            count1++;
        }
        count2 = 0;
        while ((i - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i - count2 - 1][j].content == seed) {
            count2++;
        }
        if ((count1 + count2) > countTotalPlayer) countTotalPlayer = count1 + count2;

        // checking if we have numberToWin-1 in col and
        // 2 empty on both sides (winning next move)
        if (countTotalPlayer == playGround.getBoard().numberToWin - 2 &&
                i + count1 + 1 < playGround.getBoard().ROWS &&
                playGround.getBoard().cells[i + count1 + 1][j].content == Seed.EMPTY &&
                i - count2 - 1 >= 0 &&
                playGround.getBoard().cells[i - count2 - 1][j].content == Seed.EMPTY) {
            isWinningNextMovePlayer = true;
        }

        // player's diagonal 1
        count1 = 0;
        while ((i + count1 + 1) < playGround.getBoard().ROWS &&
                (j + count1 + 1) < playGround.getBoard().COLS &&
                playGround.getBoard().cells[i + count1 + 1][j + count1 + 1].content == seed) {
            count1++;
        }
        count2 = 0;
        while ((i - count2 - 1) >= 0 && (j - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i - count2 - 1][j - count2 - 1].content == seed) {
            count2++;
        }
        if ((count1 + count2) > countTotalPlayer) countTotalPlayer = count1 + count2;

        // checking if we have numberToWin-1 in diagonal 1 and
        // 2 empty on both sides (winning next move)
        if (countTotalPlayer == playGround.getBoard().numberToWin - 2 &&
                j + count1 + 1 < playGround.getBoard().COLS &&
                i + count1 + 1 < playGround.getBoard().ROWS &&
                playGround.getBoard().cells[i + count1 + 1][j + count1 + 1].content == Seed.EMPTY &&
                (i - count2 - 1) >= 0 && (j - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i - count2 - 1][j - count2 - 1].content == Seed.EMPTY) {
            isWinningNextMovePlayer = true;
        }


        // player's diagonal 2
        count1 = 0;
        while ((i - count1 - 1) >= 0 && (j + count1 + 1) < playGround.getBoard().COLS &&
                playGround.getBoard().cells[i - count1 - 1][j + count1 + 1].content == seed) {
            count1++;
        }
        count2 = 0;
        while ((i + count2 + 1) < playGround.getBoard().ROWS && (j - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i + count2 + 1][j - count2 - 1].content == seed) {
            count2++;
        }
        if ((count1 + count2) > countTotalPlayer) countTotalPlayer = count1 + count2;

        // checking if player has numberToWin-1 in diagonal 2 and
        // 2 empty on both sides (winning next move)
        if (countTotalPlayer == playGround.getBoard().numberToWin - 2 &&
                j + count1 + 1 < playGround.getBoard().COLS &&
                i - count1 - 1 >= 0 &&
                playGround.getBoard().cells[i - count1 - 1][j + count1 + 1].content == Seed.EMPTY &&
                (i + count2 + 1) < playGround.getBoard().ROWS &&
                (j - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i + count2 + 1][j - count2 - 1].content == Seed.EMPTY) {
            isWinningNextMovePlayer = true;
        }

        Seed opponentSeed = seed == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS;

        //horizontal opponent's
        int countTotalOpp;
        count1 = 0;
        while (((j + count1 + 1) < playGround.getBoard().COLS) &&
                playGround.getBoard().cells[i][j + count1 + 1].content == opponentSeed) {
            count1++;
        }
        count2 = 0;
        while ((j - count2 - 1 >= 0) &&
                playGround.getBoard().cells[i][j - count2 - 1].content == opponentSeed) {
            count2++;
        }
        countTotalOpp = count1 + count2;

        // vertikal opponent's
        count1 = 0;
        while ((i + count1 + 1) < playGround.getBoard().ROWS &&
                playGround.getBoard().cells[i + count1 + 1][j].content == opponentSeed) {
            count1++;
        }
        count2 = 0;
        while ((i - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i - count2 - 1][j].content == opponentSeed) {
            count2++;
        }
        if ((count1 + count2) > countTotalOpp) countTotalOpp = count1 + count2;

        // opponent's diagonal 1
        count1 = 0;
        while ((i + count1 + 1) < playGround.getBoard().ROWS &&
                (j + count1 + 1) < playGround.getBoard().COLS &&
                playGround.getBoard().cells[i + count1 + 1][j + count1 + 1].content == opponentSeed) {
            count1++;
        }
        count2 = 0;
        while ((i - count2 - 1) >= 0 && (j - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i - count2 - 1][j - count2 - 1].content == opponentSeed) {
            count2++;
        }
        if ((count1 + count2) > countTotalOpp) countTotalOpp = count1 + count2;

        // opponents's diagonal 2
        count1 = 0;
        while ((i - count1 - 1) >= 0 && (j + count1 + 1) < playGround.getBoard().COLS &&
                playGround.getBoard().cells[i - count1 - 1][j + count1 + 1].content == opponentSeed) {
            count1++;
        }
        count2 = 0;
        while ((i + count2 + 1) < playGround.getBoard().ROWS && (j - count2 - 1) >= 0 &&
                playGround.getBoard().cells[i + count2 + 1][j - count2 - 1].content == opponentSeed) {
            count2++;
        }
        if ((count1 + count2) > countTotalOpp) countTotalOpp = count1 + count2;

        //checking if opponent is winning next move
        // (opp has numberToWin and 2 empty cells on both sides
        if (count - 2 >= 0) {
            int i1 = tempMoves[count - 2][0]; // prev opponent's
            int j1 = tempMoves[count - 2][1];   //move

            //removing last player's move as we are evaluating previos move board state
            playGround.getBoard().cells[tempMoves[count - 1][0]][tempMoves[count - 1][1]].content = Seed.EMPTY;

            //horizontal opponent's
            int countTotalOppPrevMove;
            count1 = 0;
            while (((j1 + count1 + 1) < playGround.getBoard().COLS) &&
                    playGround.getBoard().cells[i1][j1 + count1 + 1].content == opponentSeed) {
                count1++;
            }
            count2 = 0;
            while ((j1 - count2 - 1 >= 0) &&
                    playGround.getBoard().cells[i1][j1 - count2 - 1].content == opponentSeed) {
                count2++;
            }
            countTotalOppPrevMove = count1 + count2;
            // checking if opp has numberToWin-2 in row and
            // 2 empty on both sides (winning next move)
            if (countTotalOppPrevMove == playGround.getBoard().numberToWin - 2 &&
                    j1 + count1 + 1 < playGround.getBoard().COLS &&
                    playGround.getBoard().cells[i1][j1 + count1 + 1].content == Seed.EMPTY &&
                    j1 - count2 - 1 >= 0 &&
                    playGround.getBoard().cells[i1][j1 - count2 - 1].content == Seed.EMPTY) {

                isWinningNextMoveOpp = true;
            }

            //vertikal opponent
            count1 = 0;
            while (((i1 + count1 + 1) < playGround.getBoard().ROWS) &&
                    playGround.getBoard().cells[i1 + count1 + 1][j1].content == opponentSeed) {
                count1++;
            }
            count2 = 0;
            while ((i1 - count2 - 1 >= 0) &&
                    playGround.getBoard().cells[i1 - count2 - 1][j1].content == opponentSeed) {
                count2++;
            }
            countTotalOppPrevMove = count1 + count2;

            // checking if opp has numberToWin-2 in col and
            // 2 empty on both sides (winning next move)
            if (countTotalOppPrevMove == playGround.getBoard().numberToWin - 2 &&
                    i1 + count1 + 1 < playGround.getBoard().ROWS &&
                    playGround.getBoard().cells[i1 + count1 + 1][j1].content == Seed.EMPTY &&
                    i1 - count2 - 1 >= 0 &&
                    playGround.getBoard().cells[i1 - count2 - 1][j1].content == Seed.EMPTY) {

                isWinningNextMoveOpp = true;
            }

            //diagonal 1 opp
            count1 = 0;
            while ((i1 + count1 + 1) < playGround.getBoard().ROWS &&
                    (j1 + count1 + 1) < playGround.getBoard().COLS &&
                    playGround.getBoard().cells[i1 + count1 + 1][j1 + count1 + 1].content == opponentSeed) {
                count1++;
            }
            count2 = 0;
            while ((i1 - count2 - 1) >= 0 && (j1 - count2 - 1) >= 0 &&
                    playGround.getBoard().cells[i1 - count2 - 1][j1 - count2 - 1].content == opponentSeed) {
                count2++;
            }
            countTotalOppPrevMove = count1 + count2;

            // checking if opp has numberToWin-2 in diagonal 1 and
            // 2 empty on both sides (winning next move)
            if (countTotalOppPrevMove == playGround.getBoard().numberToWin - 2 &&
                    i1 + count1 + 1 < playGround.getBoard().ROWS &&
                    j1 + count1 + 1 < playGround.getBoard().COLS &&
                    playGround.getBoard().cells[i1 + count1 + 1][j1 + count1 + 1].content == Seed.EMPTY &&
                    j1 - count2 - 1 >= 0 &&
                    i1 - count2 - 1 >= 0 &&
                    playGround.getBoard().cells[i1 - count2 - 1][j1 - count2 - 1].content == Seed.EMPTY) {
                isWinningNextMoveOpp = true;
            }

            //diagonal 2 opp
            count1 = 0;
            while ((i1 + count1 + 1) < playGround.getBoard().ROWS &&
                    (j1 - count1 - 1) >= 0 &&
                    playGround.getBoard().cells[i1 + count1 + 1][j1 - count1 - 1].content == opponentSeed) {
                count1++;
            }
            count2 = 0;
            while ((i1 - count2 - 1) >= 0 && (j1 + count2 + 1) < playGround.getBoard().COLS &&
                    playGround.getBoard().cells[i1 - count2 - 1][j1 + count2 + 1].content == opponentSeed) {
                count2++;
            }
            countTotalOppPrevMove = count1 + count2;

            // checking if opp has numberToWin-2 in diagonal 2 and
            // 2 empty on both sides (winning next move)
            if (countTotalOppPrevMove == playGround.getBoard().numberToWin - 2 &&
                    i1 + count1 + 1 < playGround.getBoard().ROWS &&
                    j1 - count1 - 1 >= 0 &&
                    playGround.getBoard().cells[i1 + count1 + 1][j1 - count1 - 1].content == Seed.EMPTY &&
                    j1 + count2 + 1 < playGround.getBoard().COLS &&
                    i1 - count2 - 1 >= 0 &&
                    playGround.getBoard().cells[i1 - count2 - 1][j1 + count2 + 1].content == Seed.EMPTY) {
                isWinningNextMoveOpp = true;
            }


        }

        Seed tempSeed = (count % 2 != 0 ? seed :
                (seed == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS));
        if (seed == tempSeed) {

            if (countTotalPlayer >= playGround.getBoard().numberToWin - 1) return 900;
            if (isWinningNextMoveOpp) {
                return -800;
            }
            if (isWinningNextMovePlayer) return 800;
            return countTotalPlayer + countTotalOpp;
        } else {
            if (countTotalPlayer >= playGround.getBoard().numberToWin - 1) return -900;
            if (isWinningNextMoveOpp) return 800;
            if (isWinningNextMovePlayer) return -800;
            return -(countTotalPlayer + countTotalOpp);
        }

    }

    public boolean isThereEmptyFieldNear(int i, int j) {

        int[][] firstRing = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};

        for (int k = 0; k < firstRing.length; k++) {
            if (i + firstRing[k][0] >= 0 && i + firstRing[k][0] < playGround.getBoard().cells.length &&
                    j + firstRing[k][1] >= 0 && j + firstRing[k][1] < playGround.getBoard().cells[i].length &&
                    playGround.getBoard().cells[i + firstRing[k][0]][j + firstRing[k][1]].content != Seed.EMPTY) {
                return true;
            }
        }

        return false;
    }

    public int[] makeBotMove(Seed seed, String GsonPlayGround, int depth1) {
        playGround = new Gson().fromJson(GsonPlayGround, SimplePlayGround.class);
        depth = depth1;
        tempMoves = new int[depth][2];
        count = 0;
        mWasMoveMade = false;
        maximinWithAlphaBeta(-1000, 1000, seed);

        if (Thread.currentThread().isInterrupted()) {
            int[] arr = {0, 0};
            return arr;
        }
        if (mWasMoveMade) {
            int i = (int) (Math.random() * randomMoveList.size());
            move[0] = randomMoveList.get(i)[0][0];
            move[1] = randomMoveList.get(i)[0][1];
            return move;
        } else {      // no moves were made yet, comp playing cross;
            if (playGround.getBoard().cells.length == 3) {
                move[0] = 1;
                move[1] = 1;
            } else {
                move[0] = (int) (Math.random() * 4) + 3;
                move[1] = (int) (Math.random() * 4) + 3;
            }
            return move;
        }


    }


}

