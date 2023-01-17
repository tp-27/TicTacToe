package tictactoe;

public class TicTacToe extends boardgame.BoardGame implements boardgame.Saveable { // implements Saveable
    private TextUI tictactoeUI;
    private Player player1;
    private Player player2;
    private boolean gameStatus = false;
    private String gameStateMessage;
    private int winner = -1;
    private int turn = 1;

    /** 
     * @param wide The desired width of the board
     * @param high The desired height of the board
     */ 
    public TicTacToe(int wide, int tall) {
        super(wide, tall);
        player1 = new Player();
        player2 = new Player();
    }

    /** 
     * Sets the turn for next player to move
     * @param whoseTurn which player just went
     */ 
    public void setTurn() {
        if (turn == 1) {
            turn = 2;  // O's turn
        } else {
            turn = 1;   // X's turn
        }
    }

    /** 
     * Sets the current state of the game defined by gameStatus parameter
     * @param status boolean variable stating if true (game is over), false otherwise
     */ 
    public void setGameStatus(boolean status) {
        gameStatus = status;
    }

    /** 
     * Returns current player's turn
     * @return integer representing player 
     */ 
    public int getTurn() {
        return turn;
    }
    
    /** 
     * Overloaded method that facilitates the placement of an input on the board 
     * with String input. Method should be overriden 
     * to validate input prior to placing the input value.  Overridden method 
     * should throw exceptions when validating input.
     * @param across across index, 1 based
     * @param down  down index, 1 based
     * @param input  String input from game
     * @return boolean  returns true if input was placed false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, String input) {
         try {
            theGrid().validateLocation(across, down);
            theGrid().validateInput(across, down, input);
            setValue(across,down, input);
            setTurn();
            setGameStateMessage(nextPlayerMessage());
        } catch (Exception e) {
                setGameStateMessage(e.getMessage());
                throw new RuntimeException(e.getMessage());
        }
        return true;
    }

   
    /** 
     * Overloaded method that facilitates the placement of an input on the board
     *  with integer input. Method should be overriden 
     * to validate input prior to placing the input value. 
     *  Overridden method should throw exceptions when validating input.
     * @param across across index, zero based
     * @param down  down index, zero based
     * @param input  int input from game
     * @return boolean  returns true if input was placed false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, int input) {

        try {
            theGrid().validateLocation(across, down);
            theGrid().validateInput(across, down, Integer.toString(input));
            setValue(across,down, convertIntToString(input));
            setTurn();
            setGameStateMessage(nextPlayerMessage());
        } catch (Exception e) {
                setGameStateMessage(e.getMessage());
                throw new RuntimeException(e.getMessage());
        }
    
        return true;
    }

    /**
     *  Helper method to convert integer to string
     */
    private String convertIntToString(int input) {
        String converted = "";
        converted = Integer.toString(input);

        return converted;
    }
        
    /** 
     * Returns true when game is over, false otherwise.  Method must be overridden.
     * @return boolean
     */
    @Override
    public boolean isDone() {
        if (!gameStatus) {
            gameStatus = theGrid().horizontalWin();
        }
       
        if (!gameStatus) {
            gameStatus = theGrid().verticalWin();
        }

        if (!gameStatus) {
            gameStatus = theGrid().diagonalWin();
        }

        if (!gameStatus) {
            gameStatus = theGrid().checkTie();
            if (gameStatus) {
                winner = 0;
                setGameStateMessage(gameCompleteMessage());
                return gameStatus;
            }
        }

        if (gameStatus) {
            setWinner();
            setGameStateMessage(gameCompleteMessage());
        }

        return gameStatus;
    }

    /**
     *  This method updates player statistics (games played, wins, losses) 
     *  after each game is played
     */
    public void updateStats() {
        player1.addGames();
        player2.addGames();

        if (winner == 1) {
            player1.addWin();
            player2.addLoss();
        } else if (winner == 2) {
            player1.addWin();
            player2.addLoss();
        } 
    }

    private void setWinner() {
        setTurn();
        winner = getTurn();
    }

    /**
     * Overriden method that returns the winner of the TicTacToe game.
     * @return 0 for tie, 1 for player 1, 2 for player 2, -1 if no winner
     */
    @Override
    public int getWinner() {
        winner = turn;
        return winner;
    }

    /** 
     * Must be overridden if used.  Returns a message that can be output to use that provides
     * information about the game state.    
     * @return String mesage to user
     */
     @Override
    public String getGameStateMessage() {
        return gameStateMessage;
    }
    
    /**
     *  This method sets the game state message with input specifying the string
     *  @param theMessage string to set message to 
     */
    public void setGameStateMessage(String theMessage) {
        gameStateMessage = theMessage;
    }

    /**
     *  This method returns a quit message
     *  @return string representing a quit message
     */
    public String quitMessage() {
        String message;

        message = "Quitting game";
        return message;
    }

    /**
     *  This method returns welcome message corresponding to gameType
     *  @param gameType which game
     *  @return message 
     */
    public String welcomeMessage(int gameType) {
        String message = "";

        if (gameType == 1) {
            message = "Welcome to Regular TicTacToe";
        } else {
             message = "Welcome to Numerical TicTacToe";
        } 

        return message;
    }
    
    private String gameCompleteMessage() {
        String message;
        
        message = "The game is complete. ";
        if (winner ==  0) {
            message += "Tie game!";
        } else {
            message += "Player " + getTurn() + " takes the win!\n";
        }

        return message;
    }

    /**
     *  This method returns next player message
     *  @return message 
     */
    public String nextPlayerMessage() {
        String message;
    
        if (turn == 1) {
            message = "Player 1's turn!";
        } else {
            message = "Player 2's turn!";
        }
        return message;
    }   


    /**
     * Overriden Saveable method
     * Loads a string from textfile in order to load back into game grid
     * @param save String to be loaded into game grid
     */
    @Override 
    public void loadSavedString(String save) {
        theGrid().emptyGrid();
        turn = theGrid().parseStringIntoGrid(save);   
    }

    /**
     * Overriden Saveable method
     * Returns the grid into a String that is suitable to be saved into a text file
     * @return String representing game grid in comma delimited format
     */
    @Override 
    public String getStringToSave() {
        String toSave;

        toSave = theGrid().getCSVStringGrid(getTurn());
        return toSave;
    }

    /**
     *  This saves the current state of the grid into a file
     *  @param fileName name of the file to save to
     */
    public void saveGridToFile(String fileName) {
        FileSaving.fileWriter(this, fileName);
    }

    /**
     *  This reads data from file that represents grid state from previous game
     *  @param fileName name of the file to save to
     */
    public void readGridFromFile(String fileName) throws Exception {
        try {
            FileSaving.fileReader(this, fileName);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    
    }

    /**
     *  This saves the current player statistics to a file
     *  @param fileName name of the file to save to
     *  @param player integer representing which player's information
     */
    public void saveProfileToFile(String fileName, int player) {
        if (player == 1) {
            FileSaving.fileWriter(player1, fileName);
        } else {
            FileSaving.fileWriter(player2, fileName);
        }
       
    }

    /**
     *  This method reads input representing player statistics into game
     *  @param fileName name of the file to save to
     @  @param player integer representing which player's information
     */
    public void readProfileFromFile(String fileName, int player) throws Exception {
        if (player == 1) {
            try {
                 FileSaving.fileReader(player1, fileName);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
           
        } else {
            try {
                 FileSaving.fileReader(player2, fileName);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     *  This method returns a player profile represented as a string
     *  @param player integer representing player information to return
     *  @return string representing player statistics
     */
    public String getPlayerProfile(int player) {
        if (player == 1) {
            return player1.toString();
        } else {
            return player2.toString();
        }
    }

    /**
     *  This method overrides the parent setGrid method to allow for the
     *  grid to be set according to the type of game being played
     *  @param grid type of grid being played
     */
    @Override
    public void setGrid(boardgame.Grid grid) {
        super.setGrid(grid);
    }

    /**
     *  This method overrides the parent setGrid method to allow for the
     *  grid to be set according to the type of game being played
     *  @param whichGame type of grid being played
     *  @param width dimension 
     *  @param length dimension
     *  @return corresponding grid 
     */
    public static GridFrame newGrid(int whichGame, int width, int length) {
        if (whichGame == 1) {
            return new GridTicTac(width, length);
        } else {
            return new GridNumTicTac(width, length);
        }
    }

    private GridFrame theGrid() {
        return (GridFrame) getGrid();
    }

    /**
     *  This method returns the string grid as a string representation
     *  @return toPrint representing grid as a string
     */
    @Override 
    public String toString() {
        String toPrint = "";

        toPrint += theGrid().toString();
        return toPrint;
    }
}
