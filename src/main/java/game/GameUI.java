package game;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 *  This class serves to act as the Graphical User Interface for the Tic Tac Toe game.
 *  This class sets up the frame required to implement various features of the game. For example,
 *  it includes a menu bar that allows users to load files and to view player statistics. The main
 *  driver features would include the Play buttons which then proceeds to set up the necessary
 *  features to play a game, with the help of functions from TicTacView
 *  @author Thomas Phan
 */
public class GameUI extends JFrame {
    private JPanel gamePanel;
    private JMenuBar menuBar;
    private TicTacView theView;
    
    public GameUI() {
       super();
       this.setSize(WIDTH, HEIGHT);
       this.setTitle("TicTacToe");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(new BorderLayout()); // layout for frame

       menuBar = new JMenuBar();
       makeMenuBar();
       setJMenuBar(menuBar);

       gamePanel = new JPanel();

       add(gamePanel, BorderLayout.CENTER);
       add(makeButtonPanel(), BorderLayout.NORTH);

    }

    private void makeGamePanel() {
        gamePanel.add(theView); //new TicTacView()
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(makeNumTTTButton());
        buttonPanel.add(makeTTTButton());
        return buttonPanel;
    }

    private JButton makeNumTTTButton() {
        JButton button = new JButton("Play Numerical TicTacToe");
        button.addActionListener(e->playNumerical());
        return button;
    }

    private JButton makeTTTButton() {
        JButton button = new JButton("Play Normal TicTacToe");
        button.addActionListener(e->playNormal());
        return button;
    }


    private void makeMenuBar() {
        JMenu options = new JMenu("Files");
        JMenu profile = new JMenu("View stats");
        JMenuItem item1 = new JMenuItem("Load saved game");
        JMenuItem item2 = new JMenuItem("Load Player 1");
        JMenuItem item3 = new JMenuItem("Load Player 2");
        JMenuItem item4 = new JMenuItem("Player 1");
        JMenuItem item5 = new JMenuItem("Player 2");

        options.add(item1);
        options.add(item2);
        options.add(item3);
        profile.add(item4);
        profile.add(item5);
        item1.addActionListener(e->loadGame());
        item2.addActionListener(e->loadProfile(1));
        item3.addActionListener(e->loadProfile(2));
        item4.addActionListener(e->viewProfile(1));
        item5.addActionListener(e->viewProfile(2));
        menuBar.add(options);
        menuBar.add(profile);
    }

    private void playNumerical() {
        gamePanel.removeAll();
        theView = new TicTacView(3,3,2);
        gamePanel.add(theView);
        theView.newGame();
        pack();
    }

    private void playNormal() {
        gamePanel.removeAll();
        theView = new TicTacView(3,3,1);
        gamePanel.add(theView);
        theView.newGame();
        pack();
    }

    private void loadGame() {
        String fileName;
        boolean valid = false;

        while (!valid) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);

            File file = fileChooser.getSelectedFile();
            fileName = file.getName();

            try {
                theView.readGridFromFile(fileName);
                valid = true;
            } catch (Exception e) {
                System.out.println("Try selecting a file again.");
            }
        }

    }

    private void loadProfile(int player) {
        String fileName;
        boolean valid = false;

        while (!valid) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);
        
            File file = fileChooser.getSelectedFile();
            fileName = file.getName();

            try {
                theView.readProfileFromFile(fileName, player);
                valid = true;
            } catch (Exception e) {
                System.out.println("Try selecting a file again.");
            }
        }
    }

    private void viewProfile(int player) {
        theView.getPlayerProfile(player);
    }

    /**
     *  This is the main function that initializes the GUI
     */
    public static void main(String[] args) {
        GameUI gameGUI = new GameUI();
        gameGUI.setVisible(true);
    }
}