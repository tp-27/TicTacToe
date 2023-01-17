package tictactoe;

/**
 *  This class extends the GridFrame class which extends the Grid class in order to add
 *  additional features onto the methods provided by the ancestor classes. This class allows
 *  the programmer to utilizes the features necessary to implement a Numerical Tic Tac Toe game
 *  @author Thomas Phan
 */

public class GridNumTicTac extends GridFrame{ 

    public GridNumTicTac(int width, int length) {
        super(width, length);
    }

    /**
     *  This method checks to see if there is a horizontal win for Numerical 
     *  Tic Tac Toe win conditions (ie. if sum equates to 15)
     *  @return true if there is horizontal win, false otherwise
     */
    @Override
    public boolean horizontalWin() {
        boolean winner;
        int sum;
        String value = "";

        winner = false; 
        sum = 0;
        for (int i = 1; i <= 3; i++) { 
            for (int j = 1; j <= 3; j++) {
                value = getValue(j,i);
                if (value != " ") {
                    sum += Integer.parseInt(value);
                }
            }

            if (sum == 15) {
                winner = true;
                return winner;
            } 
            sum = 0;
        }  
        return winner;
    }

    /**
     *  This method checks to see if there is a vertcial win for Numerical
     *  Tic Tac Toe win conditions
     *  @return boolean true if there is a vertical win, false otherwise
     */
    @Override
    public boolean verticalWin() {
        boolean winner;
        int sum;
        String value = "";

        winner = false; 
        sum = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                value = getValue(i,j);
                if (value != " ") {
                    sum += Integer.parseInt(value);
                }
            }

            if (sum == 15) {
                winner = true;
                return winner;
            } 
            sum = 0;
        }  
        return winner;
    }

    /**
     *  This method checks to see if there is a diagonal win for Numerical
     *  Tic Tac Toe win conditions
     *  @return true if there is diagonal win, false otherwise
     */
    @Override
    public boolean diagonalWin() {
        String[] value = new String[3];
        boolean winner;
        int sum;

        winner = false;
        sum = 0;
        value[0] = getValue(1,1);
        value[1] = getValue(2,2);
        value[2] = getValue(3,3);
        for (int i = 0; i < 3; i++) {
            if (value[i] != " ") {
                sum += Integer.parseInt(value[i]);
            }
        }
        if (sum == 15) {
            winner = true;
            return winner;
        } 
        sum = 0;
        value[0] = getValue(3,1);
        value[1] = getValue(2,2);
        value[2] = getValue(1,3);
        for (int i = 0; i < 3; i++) {
            if (value[i] != " ") {
                sum += Integer.parseInt(value[i]);
            }
        }
        return winner;
    }

    /**
     *  This method validates the input by checking if selected grid position
     *  is within bounds of Numerical TicTacToe board. Throws exception if invalid.
     *  @param across column selected
     *  @param down row selected
     *  @param input String representing input
     */
    @Override
    public void validateInput(int across, int down, String input) throws Exception{
        if ((across > 3) || (across < 1)) {
            throw new Exception("Invalid. Input out of bounds.");
        }

        if ((down > 3) || (down < 1)) {
            throw new Exception("Invalid. Input out of bounds.");
        }

        int temp;
        temp = Integer.valueOf(input);
        if ((temp < 0) || (temp > 9)) {
            throw new Exception("Invalid. Input");
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
     * game type. For Numerical Tic Tac Toe O - Player 1, E - Player 2.
     * @param gridData single string from file
     * @param row integer representing row number of read string
     */
     @Override
    public int parseStringIntoGrid(String gridData) {
        String[] lines = gridData.split("\\r?\\n|\\r");
        String[] tokens = new String[5];
        String theToken = "";
        int whoseTurn;
        
        if (lines[0] == "O") {
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
}