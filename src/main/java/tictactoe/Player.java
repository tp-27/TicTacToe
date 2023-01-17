package tictactoe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.io.BufferedReader;

/**
 *  This class serves to represent a player object with certain attributes 
 *  that define a player's state. Methods in this class allow the programmer 
 *  to update the player's state as well as getting a string representation of the 
 *  player object.
 *  @author Thomas Phan
 */

public class Player implements boardgame.Saveable { 
    private int wins = 0;
    private int losses = 0;
    private int gamesPlayed = 0;
    private String playerName = "NA";

    /**
     *  This method allows user to set player name
     *  @param namePlayer player's name
     */
    public void setName(String namePlayer) {
        playerName = namePlayer;
    }

    /**
     *  This method allows user to get player name
     *  @return playerName
     */
    public String getName() {
        return playerName;
    }

    private void setWin(int numWins) {
        wins = numWins;
    }

    private void setLoss(int numLoss) {
        losses = numLoss;
    }

    private void setGamesPlayed(int numGames) {
        gamesPlayed = numGames;
    }

    /**
     *  This method adds a win
     */
    public void addWin() {
        wins++;
    }

    /**
     *  This method adds a loss
     */
    public void addLoss() {
        losses++;
    }

    /**
     *  This method adds a game
     */
    public void addGames() {
        gamesPlayed++;
    }

    private String getStats() {
        String profile; 

        profile = "Player name: " + playerName + "\n";
        profile += "Wins: " + wins + "\n";
        profile += "Losses: " + losses + "\n";
        profile += "Games played: " + gamesPlayed + "\n";

        return profile;
    }

    private String getStatsToCSV() {
        String statsCSV = "";

        statsCSV += playerName + "\n";
        statsCSV += Integer.toString(wins) + ",";
        statsCSV += Integer.toString(losses) + ",";
        statsCSV += Integer.toString(gamesPlayed);
        return statsCSV;
    }

    /**
     * Overriden Saveable method
     * Loads a string from textfile in order to set player state
     * @param save String to be loaded into game grid
     */
    @Override
    public void loadSavedString(String save) {
        String[] lines = save.split("\\r?\\n|\\r");
        String[] tokens = new String[5];

        setName(lines[0]);
        tokens = lines[1].split(",");
        setWin(Integer.parseInt(tokens[0]));
        setLoss(Integer.parseInt(tokens[1]));
        setGamesPlayed(Integer.parseInt(tokens[2]));
    }

    /**
     * Overriden Saveable method
     * Returns the player's statistics represented as a string - wrapper class for getStats
     * @return String representing game grid in comma delimited format
     */
    @Override
    public String getStringToSave() {
       return getStatsToCSV();
    }



    @Override public String toString() {
        return getStats();
    }
}
