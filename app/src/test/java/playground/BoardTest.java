package playground;

import com.google.gson.Gson;

import org.junit.Test;

import playground.Bot5.Bot5;

import static org.junit.Assert.*;

/**
 * Created by user on 17.06.2017.
 */
public class BoardTest {

    @Test
    public void testHasWonHorizontal() throws Exception {
        Board  board = new Board(10,10,5);
        board.cells[1][5].content=Seed.CROSS;
        board.cells[1][4].content=Seed.CROSS;
        board.cells[1][2].content=Seed.CROSS;
        board.cells[1][3].content=Seed.CROSS;
        board.cells[1][6].content=Seed.CROSS;
        board.currentRow = 1;
        board.currentCol = 6;
        System.out.println(board.hasWon(Seed.CROSS));
        for (int i = 0; i < board.winningFields.size(); i++) {
            int[][] tempArr = (int[][]) board.winningFields.get(i);
            System.out.println(tempArr[0][0]+","+tempArr[0][1]);

        }

        SimplePlayGround simplePlayGround = new SimplePlayGround(10,10,5);
        simplePlayGround.start();
        simplePlayGround.getBoard().cells[5][5].content=Seed.CROSS;
        simplePlayGround.setCurrentPlayer(Seed.NOUGHT);
        String s = new Gson().toJson(simplePlayGround);
        int[] temparr = new Bot5().makeBotMove(Seed.NOUGHT,s,3);
        //System.out.println(Bot5.totalRecCount);

    }

//    @Test
//    public void testHasWonVertikal() throws Exception {
//        Board  board = new Board(10,10,5);
//        board.cells[0][2].content=Seed.CROSS;
//        board.cells[1][2].content=Seed.CROSS;
//        board.cells[2][2].content=Seed.CROSS;
//        board.cells[3][2].content=Seed.CROSS;
//        board.cells[4][2].content=Seed.CROSS;
//        board.currentRow = 4;
//        board.currentCol = 2;
//        boolean hasWon =  board.hasWon(Seed.CROSS);
//        for (int i = 0; i < board.winningFields.length; i++) {
//            System.out.println(board.winningFields[i][0]+","+board.winningFields[i][1]);
//
//        }
//
//    }
//
//    @Test
//    public void testHasWonDiagonal1() throws Exception {
//        Board  board = new Board(3,3,3);
//        board.cells[0][0].content=Seed.CROSS;
//        board.cells[1][1].content=Seed.CROSS;
//        board.cells[2][2].content=Seed.CROSS;
//        board.currentRow = 2;
//        board.currentCol = 2;
//        boolean hasWon =  board.hasWon(Seed.CROSS);
//        for (int i = 0; i < board.winningFields.length; i++) {
//            System.out.println(board.winningFields[i][0]+","+board.winningFields[i][1]);
//
//        }
//
//    }
//
//    @Test
//    public void testHasWonDiagonal2() throws Exception {
//        Board  board = new Board(3,3,3);
//        board.cells[0][2].content=Seed.CROSS;
//        board.cells[1][1].content=Seed.CROSS;
//        board.cells[2][0].content=Seed.CROSS;
//        board.currentRow = 1;
//        board.currentCol = 1;
//        boolean hasWon =  board.hasWon(Seed.CROSS);
//        for (int i = 0; i <board.winningFields.length; i++) {
//            System.out.println(board.winningFields[i][0]+","+ board.winningFields[i][1]);
//        }
//
//    }
}