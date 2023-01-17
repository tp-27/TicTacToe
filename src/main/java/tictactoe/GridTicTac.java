package tictactoe;


import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  This class extends the GridFrame class which extends the Grid class in order to add
 *  additional features onto the methods provided by the ancestor classes. This class allows
 *  the programmer to utilizes the features necessary to implement a Regular Tic Tac Toe game
 *  @author Thomas Phan
 */

public class GridTicTac extends GridFrame { 
    private Iterator<String> iterate;

    public GridTicTac(int width, int height) {
        super(width,height);
    }

    /**
     *  This method checks to see if there is a horizontal win for Regular 
     *  Tic Tac Toe win conditions
     *  @return true if there is horizontal win, false otherwise
     */
    @Override
    public boolean horizontalWin() {
        boolean winner;

        winner = false;
        for (int i = 1; i <= 3; i++) { // Check horizontal
            if (getValue(1,i) != " ") {
                if (getValue(1, i) != " " && getValue(1,i) == getValue(2,i) && getValue(2,i) == getValue(3,i)) {
                    winner = true;
                    return winner;
                }
            }
        }  
        return winner;
    }

    /**
     *  This method checks to see if there is a vertical win for Regular 
     *  Tic Tac Toe win conditions
     *  @return boolean true if there is a vertical win, false otherwise
     */
    @Override
    public boolean verticalWin() {
        boolean winner;

        winner = false;
        for (int i = 1; i <= 3; i++) { // Check vertical
            if (getValue(i, 1) != " ") {
                if (getValue(i, 1) == getValue(i,2) && getValue(i,2) == getValue(i,3)) {
                    winner = true;
                    return winner;
                }
            }
        }

        return winner;
    }

    /**
     *  This method checks to see if there is a diagonal win for Regular 
     *  Tic Tac Toe win conditions
     *  @return true if there is diagonal win, false otherwise
     */
    @Override
    public boolean diagonalWin() {
        boolean winner;

        winner = false;
        if (getValue(1,1) != " ") {
            if (getValue(1, 1) != " " && getValue(1,1) 
            == getValue(2,2) && getValue(2,2) == getValue(3,3)) { 
                winner = true;
                return winner;
            }
        }
       
        if (getValue(3,1) != " ") {
            if (getValue(3, 1) != " " && getValue(3,1) == getValue(2,2) && getValue(2,2) == getValue(1,3)) {
                winner = true;
                return winner;
            }
        }
        return winner;
    }

    /**
     *  This method validates the input by checking if selected grid position
     *  is within bounds of Regular Tic Tac Toe board. Throws exception if invalid.
     *  @param across column selected
     *  @param down row selected
     *  @param input String representing input
     */
    @Override
    public void validateInput(int across, int down, String input) throws Exception {
        if ((across > 3) || (across < 0)) {
            throw new Exception("Invalid. Input out of bounds.");
        }

        if ((down > 3) || (down < 0)) {
            throw new Exception("Invalid. Input out of bounds.");
        }
    }

    /**
     *  This method validates the input by checking if selected grid position
     *  has available space to place desired move. Throws exception if invalid.
     *  @param across column selected
     *  @param down row selected
     */
    @Override
    public void validateLocation(int across, int down) throws Exception {
        String value;

        value = getValue(across, down);
        if (value != " ") {
            throw new Exception("Invalid. Space taken");
        }
    }

    /**
     * Takes in a string from saved file with correlated row in order to 
     * insert into game board. First line representing who's turn vary's for 
     * game type. For Regular Tic Tac Toe O - Player 1, X - Player 2.
     * @param gridData single string from file
     * @param row integer representing row number of read string
     */
    @Override
    public int parseStringIntoGrid(String gridData) {
        String[] lines = gridData.split("\\r?\\n|\\r");
        String[] tokens = new String[5];
        String theToken = "";
        int whoseTurn;
        
        if (lines[0] == "X") {
            whoseTurn = 1;
        } else {
            whoseTurn = 2;
        }

        for (int i = 1; i <= 3; i++) {
            tokens = lines[i].split(",", -1);
            for (int j = 0; j < 3; j++) {
                theToken = tokens[j];
                if (theToken.isEmpty()) {
                    setValue(j + 1, i, " ");
                } else {
                    setValue(j + 1,i, tokens[j]);
                }
            }
        }

        return whoseTurn;
    }

    /**
     *  This method overrides the toString method and adds text format to 
     *  create string representation of Tic Tac Toe board
     *  @return String representing board
     */
    @Override 
    public String toString() {
        String toPrint ="";
        toPrint = "  [TicTacToe]\n";

        toPrint = toPrint + getStringGrid();
        return toPrint;
    }
}