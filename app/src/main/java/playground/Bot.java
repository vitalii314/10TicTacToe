package playground;

/**
 * Created by user on 15.08.2016.
 */
public class Bot {

    private boolean firstMove;

    public Bot(){
        firstMove = true;
    }
    public void start() {
        firstMove=true;
    }

    public int[] getRandomMove(SimplePlayGround playGround) {
        while (true) {
            int a = ((int) (Math.random() * 3));
            int b = ((int) (Math.random() * 3));
            int[] computerMove = new int[2];

            if (playGround.getBoard().cells[a][b].content == Seed.EMPTY) {
                computerMove[0] = a;
                computerMove[1] = b;
                return computerMove;
            }

        }

    }


    public int[] makeMove(SimplePlayGround playGround) {
        int[] computerMove = new int[2];
        if ((firstMove) && playGround.getBoard().cells[1][1].content == Seed.CROSS) {
            computerMove[0] = 0;
            computerMove[1] = 0;
            firstMove = false;
            return computerMove;
        }

        if ((firstMove)&&playGround.getBoard().cells[1][1].content == Seed.EMPTY) {
            computerMove[0] = 1;
            computerMove[1] = 1;
            firstMove = false;
            return computerMove;

        }

        // checking if can win - to be optimized
        //horizontal oportunities
        for (int i = 0; i < playGround.getBoard().cells.length; i++) {
            int repeatNumber = 0;
            int numberOfColumn = 0;
            for (int j = 0; j < playGround.getBoard().cells[i].length; j++) {
                if (playGround.getBoard().cells[i][j].content == Seed.NOUGHT) {
                    repeatNumber++;
                }
                if (playGround.getBoard().cells[i][j].content == Seed.EMPTY) {
                    numberOfColumn = j;
                }

            }
            if ((repeatNumber == 2) &&
                    (playGround.getBoard().cells[i][numberOfColumn].content == Seed.EMPTY)) {
                computerMove[0] = i;
                computerMove[1] = numberOfColumn;
                firstMove = false;
                return computerMove;
            }
        }

        ////// vertical oportunities
        for (int i = 0; i < playGround.getBoard().cells.length; i++) {
            int repeatNumber = 0;
            int numberOfLine = 0;
            for (int j = 0; j < playGround.getBoard().cells[i].length; j++) {
                if (playGround.getBoard().cells[j][i].content == Seed.NOUGHT) {
                    repeatNumber++;
                }
                if (playGround.getBoard().cells[j][i].content == Seed.EMPTY) {
                    numberOfLine = j;
                }

            }
            if ((repeatNumber == 2) &&
                    (playGround.getBoard().cells[numberOfLine][i].content == Seed.EMPTY)) {
                computerMove[0] = numberOfLine;
                computerMove[1] = i;
                firstMove = false;
                return computerMove;
            }
        }


        // crossing oportunities
        int repeatNumber = 0;
        int number = 0;
        for (int i = 0; i < 3; i++) {
            if (playGround.getBoard().cells[i][i].content == Seed.NOUGHT) {
                repeatNumber++;
            }
            if (playGround.getBoard().cells[i][i].content == Seed.EMPTY) {
                number = i;
            }
        }
        if ((repeatNumber == 2) &&
                (playGround.getBoard().cells[number][number].content == Seed.EMPTY)) {
            computerMove[0] = number;
            computerMove[1] = number;
            firstMove = false;
            return computerMove;
        }

        int repeatNumber2 = 0;
        int number2 = 0;
        int number3 = 0;

        if (playGround.getBoard().cells[2][0].content == Seed.NOUGHT) {
            repeatNumber2++;
        }
        if (playGround.getBoard().cells[2][0].content == Seed.EMPTY) {
            number2 = 2;
            number3 = 0;
        }

        if (playGround.getBoard().cells[1][1].content == Seed.NOUGHT) {
            repeatNumber2++;
        }
        if (playGround.getBoard().cells[1][1].content == Seed.EMPTY) {
            number2 = 1;
            number3 = 1;
        }

        if (playGround.getBoard().cells[0][2].content == Seed.NOUGHT) {
            repeatNumber2++;
        }
        if (playGround.getBoard().cells[0][2].content == Seed.EMPTY) {
            number2 = 0;
            number3 = 2;
        }

        // add this condition to avoid bot's mistake &&(!(number2==0&&number3==0))
        if ((repeatNumber2 == 2) &&
                (playGround.getBoard().cells[number2][number3].content == Seed.EMPTY)) {
            computerMove[0] = number2;
            computerMove[1] = number3;
            firstMove = false;
            return computerMove;
        }

        // checking if oponent can win
        for (int i = 0; i < playGround.getBoard().cells.length; i++) {
            int repeatNumberNew=0;
            int numberOfColumnNew=0;
            for (int j = 0; j < playGround.getBoard().cells[i].length; j++) {
                if (playGround.getBoard().cells[i][j].content == Seed.CROSS) {
                    repeatNumberNew++;
                }
                if (playGround.getBoard().cells[i][j].content== Seed.EMPTY) {
                    numberOfColumnNew=j;
                }

            }
            if ((repeatNumberNew==2)&&
                    (playGround.getBoard().cells[i][numberOfColumnNew].content== Seed.EMPTY)) {
                computerMove[0]=i;
                computerMove[1]=numberOfColumnNew;
                firstMove=false;
                return computerMove;
            }
        }
        ////// vertical oportunities
        for (int i = 0; i < playGround.getBoard().cells.length; i++) {
            int repeatNumberNew=0;
            int numberOfLineNew=0;
            for (int j = 0; j < playGround.getBoard().cells[i].length; j++) {
                if (playGround.getBoard().cells[j][i].content == Seed.CROSS) {
                    repeatNumberNew++;
                }
                if (playGround.getBoard().cells[j][i].content== Seed.EMPTY) {
                    numberOfLineNew=j;
                }

            }
            if ((repeatNumberNew==2)&&
                    (playGround.getBoard().cells[numberOfLineNew][i].content== Seed.EMPTY)) {
                computerMove[0]=numberOfLineNew;
                computerMove[1]=i;
                firstMove=false;
                return computerMove;
            }
        }

        // crossing oportunities
        int repeatNumberCross=0;
        int numberCross=0;
        for (int i = 0; i < 3; i++) {
            if (playGround.getBoard().cells[i][i].content == Seed.CROSS) {
                repeatNumberCross++;
            }
            if (playGround.getBoard().cells[i][i].content == Seed.EMPTY) {
                numberCross = i;
            }
        }
        if ((repeatNumberCross==2)&&
                (playGround.getBoard().cells[numberCross][numberCross].content== Seed.EMPTY)) {
            computerMove[0] = numberCross;
            computerMove[1] = numberCross;
            firstMove=false;
            return computerMove;
        }

        int repeatNumberCross2=0;
        int numberCross2=0;
        int numberCross3=0;

        if (playGround.getBoard().cells[2][0].content == Seed.CROSS) {
            repeatNumberCross2++;
        }
        if (playGround.getBoard().cells[2][0].content == Seed.EMPTY) {
            numberCross2 = 2;
            numberCross3 = 0;
        }

        if (playGround.getBoard().cells[1][1].content == Seed.CROSS) {
            repeatNumberCross2++;
        }
        if (playGround.getBoard().cells[1][1].content == Seed.EMPTY) {
            numberCross2 = 1;
            numberCross3=1;
        }

        if (playGround.getBoard().cells[0][2].content == Seed.CROSS) {
            repeatNumberCross2++;
        }
        if (playGround.getBoard().cells[0][2].content == Seed.EMPTY) {
            numberCross2 = 0;
            numberCross3 = 2;
        }

        if ((repeatNumberCross2==2)&&
                (playGround.getBoard().cells[numberCross2][numberCross3].content== Seed.EMPTY)) {
            computerMove[0] = numberCross2;
            computerMove[1] = numberCross3;
            firstMove=false;
            return computerMove;
        }

        return getRandomMove(playGround);

    }

}


