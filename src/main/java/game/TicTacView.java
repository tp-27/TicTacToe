package game;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import tictactoe.PositionAwareButton;
import tictactoe.TicTacToe;

/**
 *   This class serves to interact with the TicTacToe class in order to utilize 
 *   the specific game implementations that interact with the GUI for the player
 *   to properly play the Tic Tac Toe game.
 *   @author Thomas Phan
 */
public class TicTacView extends JPanel {
    private TicTacToe gameRemote;
    private JPanel panelTop;
    private JPanel panelBot;
    private JLabel playerTurnLabel;
    private JLabel gameStateMessage;
    private PositionAwareButton[][] buttons;
    private int gameType;


    public TicTacView(int wide, int tall, int whichGame) {
        super();
        setLayout(new BorderLayout());
        gameType = whichGame;
        setGameController(new TicTacToe(wide, tall)); 

        add(makePlayerTurnLabel(), BorderLayout.SOUTH);
        add(makeWelcomeLabel(), BorderLayout.NORTH);
        add(makeOptionsPanel(), BorderLayout.EAST);
        add(makeButtonGrid(wide,tall), BorderLayout.WEST);

    }

    private JPanel makeOptionsPanel() {
        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.add(makeQuitButton());
        options.add(makeSaveButton());
        return options;
    }


    private void setGameController(TicTacToe gameControl) {
        gameRemote = gameControl;
    }

    private JLabel makeWelcomeLabel() {
        gameRemote.setGameStateMessage(gameRemote.welcomeMessage(gameType));
        gameStateMessage = new JLabel(gameRemote.getGameStateMessage());
        return gameStateMessage;
    }

    private JLabel makePlayerTurnLabel() {
        gameRemote.setGameStateMessage(gameRemote.nextPlayerMessage());
        playerTurnLabel = new JLabel(gameRemote.getGameStateMessage());
        return playerTurnLabel;
    }

    private void setPlayerTurnLabel() {
        gameRemote.setGameStateMessage(gameRemote.nextPlayerMessage());
        playerTurnLabel.setText(gameRemote.getGameStateMessage());
    }

    private JButton makeSaveButton() {
        JButton button = new JButton("Save game");
        button.addActionListener(e->saveGameToFile());
        return button;
    }

    private JButton makeQuitButton() {
        JButton button = new JButton("Quit game");
        button.addActionListener(e->quitGame());
        return button;
    }

    /**
     *  This method was re-used from the Kakuro Game example
     *  written by Judi McQuaig. Permission was granted for usage.
     */
    private JPanel makeButtonGrid(int tall, int wide){
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
            for (int y=0; y<wide; y++){
                for (int x=0; x<tall; x++){ 
                    buttons[y][x] = new PositionAwareButton();
                    buttons[y][x].setAcross(x+1); 
                    buttons[y][x].setDown(y+1);
                    buttons[y][x].addActionListener(e->{
                                            inputNumber(e);
                                            checkGameStatus();
                                            });
                    panel.add(buttons[y][x]);
                }
        }
        return panel;
    }

    private void emptyGrid() {
        gameRemote.newGame();
        gameRemote.setGameStatus(false);
        updateView();
        emptyButton();

    }

    /**
     *  This method sets a new grid type based on the user's selection
     *  of a button
     *  @param gridType game chosen
     */
    public void newGrid(int gridType) {
        gameRemote.setGrid(TicTacToe.newGrid(gridType, 3, 3));
    }

    /**
     *  This method sets up a new instance of specified game board 
     */
    public void newGame() {
        if (gameType == 1) {
            gameRemote.setGrid(TicTacToe.newGrid(1,3,3));   // Regular TTT
        } else {
            gameRemote.setGrid(TicTacToe.newGrid(2,3,3));   // Numerical TTT
        }
    }

    private void emptyButton() {
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){  
                buttons[y][x].setText(""); 
            }
        }
    }

    private void emptyPlayerTurn() {
        playerTurnLabel.setText("Game Complete");
    }

    private void endGameStateMessage() {
        gameStateMessage.setText("Click button above to start a new game");
    }

    private void refreshContents() {
        emptyGrid();
        emptyPlayerTurn();
        endGameStateMessage();
    }

    private void checkGameStatus() {
        JOptionPane gameStatus = new JOptionPane();
        int option;

        if(gameRemote.isDone()) {
            option = gameStatus.showConfirmDialog(null, getResultMessage(), "Results", JOptionPane.YES_NO_OPTION);   
            performOption(option);
            refreshContents();
            gameRemote.updateStats();
        } 
    }

    private String getResultMessage() {
        String endMessage;

        endMessage = gameRemote.getGameStateMessage() + "Would you like to play another game?";
        return endMessage;
    }

    private void performOption(int option) {
        if (option == JOptionPane.NO_OPTION) {
            makeProfileSavePane();
        } 
    }

    private void makeProfileSavePane() {
        JOptionPane askToSave = new JOptionPane();
        int option;
        String[] players = new String[3];
        players[0] = "Player 1";
        players[1] = "Player 2";
        players[2] = "Exit";

        option = askToSave.showConfirmDialog(null, 
        "Would you like to save Player Profiles?", "Save Profile", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            JOptionPane whichPlayer = new JOptionPane();
            option = -1;
            while (option != 2) {
                option = whichPlayer.showOptionDialog(null,
                "Select a player to save", "Save Profile", 2, 1, new ImageIcon(), players, players[1]);
                if (option == 1) {
                    saveProfileToFile(2); // save player 2
                } else if (option == 0) {
                    saveProfileToFile(1); // save player 1
                }
            }
        }
    }

    private void inputNumber(ActionEvent e) {
        String value;
        boolean valid = false;
        JOptionPane pane = new JOptionPane();
        PositionAwareButton clicked = ((PositionAwareButton)(e.getSource()));
       
        value = pane.showInputDialog("Please input a value");  
            try {
                gameRemote.takeTurn(clicked.getAcross(), clicked.getDown(), value);
                valid = true;
                clicked.setText(gameRemote.getCell(clicked.getAcross(),clicked.getDown()));
                setPlayerTurnLabel();
            } catch (RuntimeException ex) {
                pane.showMessageDialog(null, "Invalid input", "Please try again", 1);   
            }
        
    }

    private void updateView(){
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){  
                buttons[x][y].setText(gameRemote.getCell(buttons[x][y].getAcross(),buttons[x][y].getDown())); 
            }
        }

    }

    /**
     * This method serves as a wrapper class for readGridFromFile from the 
     * TicTacToe class 
     * @param fileName name of the file
     * @param player which player's statistics
     */
    public void readGridFromFile(String fileName) throws Exception {
        try {
            gameRemote.readGridFromFile(fileName);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        updateView();
    }

    /**
     * This method serves as a wrapper class for readProfileFromFile from the 
     * TicTacToe class 
     * @param fileName name of the file
     */
    public void readProfileFromFile(String fileName, int player) throws Exception {
        try {
            gameRemote.readProfileFromFile(fileName, player);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
     
    }

    /**
     * This method imports the string representation a player's statistics into 
     * a JOption message dialog
     * @param player which player's statistics
     */
    public void getPlayerProfile(int player) {
        String playerProfile;
        playerProfile = gameRemote.getPlayerProfile(player);
        JOptionPane.showMessageDialog(null, playerProfile, "Statistics", 1);
    }


    private void saveGameToFile() {
        String fileName;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(this);
        
        File file = fileChooser.getSelectedFile();
        fileName = file.getName();

        gameRemote.saveGridToFile(fileName);
    }

    private void saveProfileToFile(int player) {
        String fileName;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(this);
        
        File file = fileChooser.getSelectedFile();
        fileName = file.getName();

        gameRemote.saveProfileToFile(fileName, player);
    }

    private void quitGame() {
        refreshContents();
        makeProfileSavePane();
    }

}