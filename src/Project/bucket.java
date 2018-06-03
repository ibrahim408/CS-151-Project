package Project;

/**
 * 
 * @author Team Captain
 * Josh Gill, Ibrahim Ibrahim, Alexander Nguyen
 * Makes a class that holds its own index,
 * number of rocks, and the old number of rocks. 
 */
public class bucket {
	int index;
	int numOfRocks;
	int prevNumOfRocks;
/**
 * Creates a Bucket object that holds an index, a number of rocks,
 * and its previous number of rocks.
 * @param i Index of the bucket.
 * @param x the number of rocks the bucket starts with. 
 */
	public bucket(int i, int x) {
		index = i;
		numOfRocks = x;
		prevNumOfRocks = x;
	}
/**
 * Gives the index of the bucket
 * @return an int of the index.
 */
	int getIndex() {
		return index;
	}
/**
 * The number of rocks inside the bucket.
 * @return An int of the numOfRocks.
 */
	int getnumOfRocks() {
		return numOfRocks;
	}
	/**
	 * Gets the previous Number of rocks for undo use.
	 * @return The int of the last number of rocks.
	 */
	int gePrevNumOfRocks() {
		return prevNumOfRocks;
	}
	/**
	 * Sets the value for the prevNumOfRocks.
	 */
	void setPrevNumOfRocks(){
		prevNumOfRocks = numOfRocks;
	}
	/**
	 * Set the number of rocks in a pit.
	 * @param x The number to set numOfRocks.
	 * @param resetUndo Check for undo.
	 */
	void setnumOfRocks(int x, boolean resetUndo) {
		numOfRocks =x;
	}

}