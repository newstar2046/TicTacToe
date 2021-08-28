import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameStarter {

    private String[][] board = new String[3][3];
    private int step = 0;
    private final int CAPACITY = 9;
    private boolean isXNext = true;
    private String defaultVal = " ";
    private final String X = "X";
    private final String O = "O";
    private final String SEPARATOR = ",";
    private final List<String> winLines = new ArrayList();

    GameStarter(){
        for(String[] line : board){
            Arrays.fill(line, defaultVal);
        }
        //horizontal
        winLines.add("00,01,02");
        winLines.add("10,11,12");
        winLines.add("20,21,22");
        //cross
        winLines.add("00,11,22");
        winLines.add("02,11,20");
        //vertical
        winLines.add("00,10,20");
        winLines.add("01,11,21");
        winLines.add("02,12,22");
    }
    public void initialGame() {
        System.out.println("┌───┬───┬───┐");
        System.out.println("│   │   │   │");
        System.out.println("├───┼───┼───┤");
        System.out.println("│   │   │   │");
        System.out.println("├───┼───┼───┤");
        System.out.println("│   │   │   │");
        System.out.println("└───┴───┴───┘");
        while(step < CAPACITY) {
            Scanner in = new Scanner(System.in);
            System.out.println("Type (x,y) to place the symbol in row x, column y.");
            System.out.print("Next player: ".concat(isXNext?X:O) .concat(", Input: "));
            String cellIndex = in.nextLine();
            int[] cellNumbers = getCellNumbers(cellIndex);
            System.out.println("\n\n\n");
            if (!board[cellNumbers[0] - 1][cellNumbers[1] - 1].equals(defaultVal)) {
                System.out.println(cellIndex + " has been placed, please choose another cell.");
                printBroad();
                step ++;
            } else {
                if (isXNext) {
                    board[cellNumbers[0] - 1][cellNumbers[1] - 1] = X;
                } else {
                    board[cellNumbers[0] - 1][cellNumbers[1] - 1] = O;
                }
                printBroad();
                step ++;
                int currentState = caculateWinner();
                if (currentState == 1) { //1: win
                    System.out.print("Congratulations!!! Player: ".concat(isXNext?X:O) .concat(" won the game!!!"));
                    break;
                } else if (currentState == 9) { //9: on draw
                    System.out.print("Game finished! Nobody win.");
                    break;
                }
                isXNext = !isXNext;
            }
        }
    }

    private int[] getCellNumbers(String cellIndex) {
        if(cellIndex == null || cellIndex.indexOf(SEPARATOR) < 0) {
            throw new RuntimeException("Invalid input!");
        }
        String[] cellNumbers = cellIndex.split(SEPARATOR);
        int rowNum = Integer.valueOf(cellNumbers[0]);
        int columnNum = Integer.valueOf(cellNumbers[1]);
        return new int[] {rowNum, columnNum};
    }

    private void printBroad(){
        System.out.println("┌───┬───┬───┐");
        System.out.println("│ " + board[0][0] + " │ " + board[0][1] + " │ " + board[0][2] + " │");
        System.out.println("├───┼───┼───┤");
        System.out.println("│ " + board[1][0] + " │ " + board[1][1] + " │ " + board[1][2] + " │");
        System.out.println("├───┼───┼───┤");
        System.out.println("│ " + board[2][0] + " │ " + board[2][1] + " │ " + board[2][2] + " │");
        System.out.println("└───┴───┴───┘");
    }
    private int caculateWinner() {
        StringBuilder result = new StringBuilder();
        String winner = isXNext?X:O;
        int countOfValueCells = 0;
        for (int j = 0; j < board.length; j++) {
            String[] columnArr = board[j];
            for (int k = 0; k < columnArr.length; k++) {
                if(board[j][k].equals(defaultVal)) {
                    continue;
                }
                countOfValueCells++;
                if (board[j][k].equals(winner)) {
                    result.append(j).append(k).append(SEPARATOR);
                }
            }
        }

        for (String winLine : winLines) {
            boolean isWin = true;
            for (String cellIndex : winLine.split(SEPARATOR)) {
                if (result.indexOf(cellIndex) < 0) {
                    isWin = false;
                }
            }
            if (isWin) {
                System.out.println(winLine);
                return 1;
            }
        }
        if (countOfValueCells == CAPACITY) {
            return 9;
        }
        return -1;
    }
    public static void main(String[] args) {
        GameStarter game = new GameStarter();
        game.initialGame();
    }
}