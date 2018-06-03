package Project;

/**
 * Class that lets you remember player.
 * @author Team Captain
 * Josh Gill, Ibrahim Ibrahim, Alexander Nguyen
 */
public class player {	
	boolean turn;
	int number;
	int undoLimit;
	boolean freeTurn;
	/**
	 * A player of the game.
	 * @param t boolean for checking turn.
	 * @param n number of the player.
	 */
	public player(boolean t, int n){
		turn = t;
		number = n;
		undoLimit=0;
	}
	/**
	 * Gets the turn of the player, true if is turn.
	 * @return the boolean value.
	 */
	public boolean getTurn(){
		return turn;
	}
	
	/**
	 * Gets the free turn of the player
	 * @return the freeTurn 
	 */
	public boolean getFreeTurn()
	{
		return freeTurn;
	}
	
	/**
	 * Sets the free turn of the player
	 * @param c true or false
	 */
	public void setFreeTurn(boolean c)
	{
		freeTurn = c;
	}
	/**
	 * Gets the players number
	 * @return int value of player.
	 */
	public int getPlayerNumber(){
		return number;
	}
	/**
	 * Gets the number of undos that have been called.
	 * @return int value of undo limit.
	 */
	public int getUndoLimit(){
		return undoLimit;
	}
	/**
	 * Sets whose turn it is.
	 * @param t Boolean value to change turn.
	 */
	public void setTurn(boolean t){
		turn =t;
	}
	/**
	 * Adds one to undo limit.
	 */
	public void incrementUndo(){
		undoLimit = undoLimit + 1;
	}
	/**
	 * Resets undo limit to 0.
	 */
	public void resetUndoIncrement(){
		undoLimit=0;
	}
}