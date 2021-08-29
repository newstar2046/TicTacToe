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
    private final List<String> winLines = new ArrayList<>();

    GameStarter(){
        for(String[] line : board){
            Arrays.fill(line, defaultVal);
        }
        //horizontal
        winLines.add("11,12,13");
        winLines.add("21,22,23");
        winLines.add("31,32,33");
        //cross
        winLines.add("11,22,33");
        winLines.add("13,22,31");
        //vertical
        winLines.add("11,21,31");
        winLines.add("12,22,32");
        winLines.add("13,23,33");
    }
    public void initialGame() {
        System.out.println("┌───┬───┬───┐");
        System.out.println("│1 1│1 2│1 3│");
        System.out.println("├───┼───┼───┤");
        System.out.println("│2 1│2 2│2 3│");
        System.out.println("├───┼───┼───┤");
        System.out.println("│3 1│3 2│3 3│");
        System.out.println("└───┴───┴───┘");
        while(step < CAPACITY) {
            Scanner in = new Scanner(System.in);
            System.out.println("Type (xy) to place the symbol in row x, column y.");
            System.out.print("Next player: ".concat(isXNext?X:O) .concat(", Input: "));
            String cellIndex = in.nextLine();
            int[] cellNumbers = getCellNumbers(cellIndex);
            System.out.println("\n\n\n");
            if (!board[cellNumbers[0] - 1][cellNumbers[1] - 1].equals(defaultVal)) {
                System.out.println(cellIndex + " has been placed, please choose another cell.");
                printBroad();
            } else {
                if (isXNext) {
                    board[cellNumbers[0] - 1][cellNumbers[1] - 1] = X;
                } else {
                    board[cellNumbers[0] - 1][cellNumbers[1] - 1] = O;
                }
                printBroad();
                step ++;
                if (step > 4) { //possibly there is a winner
                    if (isWin()) { //win
                        System.out.print("Congratulations!!! Player: ".concat(isXNext ? X : O).concat(" won the game!!!"));
                        break;
                    } else if (step == 9) { //in draw
                        System.out.print("Game finished! Nobody win.");
                        break;
                    }
                }
                isXNext = !isXNext;
            }
        }
    }

    private int[] getCellNumbers(String cellIndex) {
        if(cellIndex == null || cellIndex.length() != 2) {
            throw new RuntimeException("Invalid input!");
        }
        try {
            return new int[] {cellIndex.charAt(0) - '0',(int)cellIndex.charAt(1) - '0'};
        }catch (Exception e) {
            throw new RuntimeException("Invalid input!");
        }
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
    private boolean isWin() {
        StringBuilder result = new StringBuilder();
        String winner = isXNext?X:O;
        for (int j = 0; j < board.length; j++) {
            String[] columnArr = board[j];
            for (int k = 0; k < columnArr.length; k++) {
                if(board[j][k].equals(defaultVal)) {
                    continue;
                }
                if (board[j][k].equals(winner)) {
                    result.append(j + 1).append(k + 1).append(SEPARATOR);
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
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        GameStarter game = new GameStarter();
        game.initialGame();
    }
}