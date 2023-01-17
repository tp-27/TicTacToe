package tictactoe;

/**
 *  This class serves to register the position of where the user
 *  has clicked on the button grid used in TicTacView for the GUI
 *  of TicTacToe. This code has been re-used from Judi McQuaig from the 
 *  Kakuro Game example. 
 *  @author Judi McQuaig
 */
public class PositionAwareButton extends javax.swing.JButton{
    private int yPos;
    private int xPos;

    public PositionAwareButton(){
        super();
    }

    public PositionAwareButton(String val){
        super(val);
    }

    /**
     *  This method returns position clicked across
     *  @return xPos
     */
    public int getAcross(){
        return xPos;
    }

    /**
     *  This method returns position clicked down
     *  @return yPos
     */
    public int getDown(){
        return yPos;
    }

    /**
     *  This method sets the across position
     *  @param val position across
     */
    public void setAcross(int val){
        xPos = val;
    }
    
    /**
     *  This method sets the down position
     *  @param val position down
     */
    public void setDown(int val){
        yPos = val;
    }

}