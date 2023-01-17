package tictactoe;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class GridFrame extends boardgame.Grid {
    private Iterator<String> iterate;

    public GridFrame(int across, int down) {
        super(across,down);
    }

    /**
     *  This method serves as a framework for horizontal win conditions
     *  @return true if there is horizontal win, false otherwise
     */
    public abstract boolean horizontalWin();


    /**
     *  This method serves as a framework for vertical win conditions
     *  @return true if there is horizontal win, false otherwise
     */
    public abstract boolean verticalWin();

    /**
     *  This method serves as a framework for digonal win conditions
     *  @return true if there is horizontal win, false otherwise
     */
    public abstract boolean diagonalWin();

    /**
     *  This method serves as a framework for validating input
     *  @param across column selected
     *  @param down row selected
     *  @param input String representing input
     */
    public abstract void validateInput(int across, int down, String input) throws Exception;

    /**
     *  This method serves as a framework for validating input availability
     *  @param across column selected
     *  @param down row selected
     */
    public abstract void validateLocation(int across, int down) throws Exception;

    /**
     *  This method checks to see if there is a tie game. Can be used for various
     *  types of board games.
     *  @return boolean, true if tie, false otherwise
     */
    public boolean checkTie() {
        boolean maxDepth;

        iterate = iterator();
        maxDepth = true;
        while (iterate.hasNext()) {
            if (iterate.next() == " ") {
                maxDepth = false;
                return maxDepth;
            }
        }
        return maxDepth;
    }

    /**
     *  This method prints a string representation of the grid with additional
     *  features that improve the UI of the Text-Based version of Tic Tac Toe
     *  @return toPrint , string representing formatted grid 
     */
    @Override 
    public String getStringGrid() {
        String toPrint = "";
        int i;

        iterate = iterator();
        i = 0;
        toPrint += "  1  2  3\n";
        toPrint += "1";
        while (iterate.hasNext()) {
            if (i == 3) {
                toPrint += "2";
            } 
            if (i == 6) {
                toPrint += "3";
            }
            toPrint = toPrint +  "[" + iterate.next() + "]";
            i++;
            if (i % getWidth() == 0) {
                toPrint = toPrint + "\n";
            }
        }
        return toPrint;
    }

    /**
     * Takes in a string from saved file with correlated row in order to 
     * insert into game board
     * @param gridData single string from file
     * @param row integer representing row number of read string
     */
    public abstract int parseStringIntoGrid(String gridData);

    /**
     * Uses an iterator in order to get the values from grid and turn into
     * string with comma delimited formatting
     * @return String representing game grid in CSV format
     */
    public String getCSVStringGrid(int lastTurn) {
        String gridCSV = "";
        String next = "";
        int i;

        if (lastTurn == 1) {
            gridCSV += "X\n";
        } else {
            gridCSV += "O\n";
        }

        i = 1;
        iterate = iterator();
        while (iterate.hasNext()) {
            next = iterate.next();
            if (next == " ") {
                gridCSV += ",";
            } else {
                gridCSV += next + ",";
            }
            if (i % 3 == 0) {
                gridCSV += "\n";
            }
            i++;
        }
        return gridCSV;
    }

}