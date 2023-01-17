package tictactoe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.io.BufferedReader;

/**
 *  This class serves to handle the saving/loading of Saveable objects to/from files.
 *  Only contains two static methods that serve to handle the latter responsiblities. 
 *  @author Thomas Phan
 */
public class FileSaving {
    
    /**
     *  This method gets the string of the Saveable object and writes it to
     *  a file given the filename
     *  @param object Saveable object
     *  @param String name of the file
     */
    public static void fileWriter(boardgame.Saveable object, String fileName) {
        Path path;
        String location = "assets/";
        String toSave;

        path = FileSystems.getDefault().getPath(location, fileName);
        toSave = object.getStringToSave();
        try {
            Files.writeString(path, toSave);
        } catch(Exception e) {
            System.out.println("Invalid Write to File");
        }
    }

    /**
     *  This method reads content from a file and imports into the game
     *  @param object Saveable object
     *  @param String name of the file
     */
    public static void fileReader(boardgame.Saveable object, String fileName) throws Exception {
        Path path;
        String location = "assets/";
        String line = "";
        String fileData = "";

        path = FileSystems.getDefault().getPath(location, fileName);
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            while ((line = reader.readLine()) != null) {
                fileData = fileData + line + "\n"; 
            }
            reader.close();
        } catch (Exception e) {
            throw new Exception("Invalid filename.");
        }
        
        object.loadSavedString(fileData);
    }
}