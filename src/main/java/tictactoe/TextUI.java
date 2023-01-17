package tictactoe;

import java.util.Scanner;

/**
 *  This class serves as the Text-based user interface for the Regular Version of Tic 
 *  Tac Toe.
 */
public class TextUI {
    private TicTacToe theGame;
    private Scanner keyboardInput;
    private String fileName;
    private String namePlayer;
    private int across;
    private int down;

    public TextUI() {
        theGame = new TicTacToe(3,3);
        keyboardInput = new Scanner(System.in);
        printWelcome();
        theGame.setGrid(TicTacToe.newGrid(1, 3, 3));
    }

    /**
     *  This method prints welcome message
     */
    public void printWelcome() {
        System.out.println("Welcome to TicTacToe"); 
    }

    private void newGame() {
        theGame.setGrid(TicTacToe.newGrid(1, 3, 3));
        theGame.setGameStatus(false);
        playGame();
    }

    /**
     *  This method enters the game and plays until the game is done or until user quits
     */
    public void playGame() {
        int selection;

        selection = 1;
        while (selection == 1) {
            printBoard();
            while(!theGame.isDone()) {
                setMove();
                if (checkStatus()) {
                    break;
                }
                try {
                    theGame.takeTurn(across, down, getToken());
                } catch (RuntimeException e) {
                    System.out.println("Invalid move. Please re-enter.");
                }
                printBoard();
            } 
            theGame.updateStats();
            System.out.println(theGame.getGameStateMessage());
            selection = promptPlayAgain();
            if (selection == 1) {
                newGame();
            } else {
                break;
            }
        }
    }

    private int promptPlayAgain() {
        boolean valid = false;
        int selection = -1;

        while (!valid) {
            System.out.println("Enter 1 if you would like to play again, 0 to quit");
            keyboardInput = new Scanner(System.in);
            try {
                selection = keyboardInput.nextInt();
                valid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
       return selection;
    }

    private String getToken() {
        String token = "";

        if (theGame.getTurn() == 1) {
            token = "X";
        } else {
            token = "O";
        }
        return token;
    }

    /**
     *  This method checks to see if user has quit the game, if so then end the game and 
     *  set the game state message
     *  @return true if user wants to quit, false otherwise
     */
    private boolean checkStatus() {
        if ((across == 0) || (down == 0)) {
            theGame.setGameStatus(true);
            theGame.setGameStateMessage(theGame.quitMessage());
            return true;
        }

        return false;
    }

    /**
     *  Prints the current state of the gameboard
     */
    public void printBoard() {
        System.out.println(theGame.toString());
    }

    /**
     *  Prints the game state message
     *  @return string representing game state message
     */
    public String printGameStateMessage() {
        String message;

        message = theGame.nextPlayerMessage();
        return message;
    }

    /**
     *  This method sets the users move on the grid, if the quit option 
     *  is received then stop collecting input and return
     */
    public void setMove() {
        keyboardInput = new Scanner(System.in);
        System.out.println("If you would like to quit. Press '0'");
        System.out.println(theGame.nextPlayerMessage());

        System.out.println("Enter across: ");
        across = keyboardInput.nextInt();
        if (across == 0) {
            return;
        }
     
        System.out.println("Enter down: ");
        down = keyboardInput.nextInt();
        if (down == 0) {
            return;
        }
    }

    /**
     *  This method is used to set the file name
     */
    public void setFileName() {
        System.out.println("Please enter the name of the file:");
        keyboardInput = new Scanner(System.in);
        fileName = keyboardInput.nextLine();
    }

    /**
     *  This method is used to get the file name
     *  @return String representing file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *  This method is used to set the player's name
     *  @param name string representing desired name
     */
    public void setPlayerName(String name) {
        namePlayer = name;
    }

    /**
     *  This method is used to get the player's name
     *  @return String representing player's name
     */
    public String getPlayerName() {
        return namePlayer;
    }

    /**
     *  Tester method to test if loading grid works 
     */
    public void loadFile() {
        setFileName();

        try {
            theGame.readGridFromFile(fileName);
        } catch (Exception e) {
            System.out.println("Try again");
        }
        printBoard();
    }

    /**
     *  Tester method to test if loading player profile works
     */
    public void loadPlayerProfile(int player) {
        setFileName();

        try {
            theGame.readProfileFromFile(fileName, player);
        } catch (Exception e) {
            System.out.println("Try again");
        }

        System.out.println(theGame.getPlayerProfile(1));
    }

    /**
     *  Tester method to check if player stats are updated each game
     */
    public void printPlayerProfile(int player) {
        System.out.println(theGame.getPlayerProfile(player));
    }

    /**
     *  Main method to run the TextUI based game
     */
    public static void main(String[] args) {
        TextUI ui = new TextUI();

        // ui.loadFile();
        // ui.loadPlayerProfile(1);
        ui.playGame();
        ui.printPlayerProfile(1);
    }
}