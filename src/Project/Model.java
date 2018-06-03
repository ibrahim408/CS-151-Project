package Project;

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates a class that holds an array of buckets and change Listeners.
 * 
 * @author Team Captain Josh Gill, Ibrahim Ibrahim, Alexander Nguyen
 */
public class Model {
	bucket bucket[];
	player a;
	player b;
	ArrayList<ChangeListener> listeners;
	boolean c = true;
	boolean playerOne = false;
	boolean playerTwo = false;

	/**
	 * Creates a Model object that creates an array of buckets for
	 * representation of the mancala pits. Creates an arrayList of
	 * changeListeners and initializes it. Sets Player turn order and fills
	 * array of buckets for empty board.
	 */
	public Model() {
		bucket = new bucket[14];
		listeners = new ArrayList<ChangeListener>();

		a = new player(true, 1);
		b = new player(false, 2);

		for (int x = 0; x < 14; x++) {
			if (x == 0 || x == 7) {
				bucket[x] = new bucket(x, 0);
			} else {
				bucket[x] = new bucket(x, 0);
			}
		}
	}
	
	/**
	 * Return if player one won
	 * @return player one
	 */
	public boolean getPlayerOne()
	{
		return playerOne;
	}
	
	/**
	 * Return if player two won
	 * @return player two
	 */
	public boolean getPlayerTwo()
	{
		return playerTwo;
	}
	
	/**
	 * Sets the number of stones in the user selected
	 * @param number the number of stones that will be in the pits.
	 */
	public void setUserNumberOfRocks(int number) {
		for (int x = 0; x < 14; x++) {
			if (x == 0 || x == 7) {
				bucket[x].setnumOfRocks(0, true);
			} else {
				bucket[x].setnumOfRocks(number, true);
			}
		}
	}

	/**
	 * Creates a clone of the bucket for use.
	 * 
	 * @return A clone of bucket to be of use.
	 */
	public bucket[] getData() {
		return (bucket.clone());
	}

	/**
	 * Gets the bucket of the specified index.
	 * 
	 * @param x the index of a particular bucket in the array.
	 * @return the bucket of the particular index.
	 */
	public bucket getbucket(int x) {
		return bucket[x];
	}

	/**
	 * Adds a ChangeListener to the model.
	 * @param c the ChangeListener to be added.
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * Updates the model with the index of the chosen bucket, or undo
	 * calls updateUndo or UpdateMoveRock
	 * @param index the number of the selected bucket, or undo clicked
	 */
	public void update(int index) {

		if (index == 99) {
			updateUndo();
		} else {
			if (index > 7) {
				if (b.getTurn() == true) {
					b.setTurn(false);
					a.setTurn(true);
					updateMoveRocks(index);
					a.resetUndoIncrement();
				}
			} else {
				if (a.getTurn() == true) {
					a.setTurn(false);
					b.setTurn(true);
					updateMoveRocks(index);
					b.resetUndoIncrement();
				}
			}
		}

		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Updates the model that an undo has been called and resets player turn.
	 */
	public void updateUndo() {

		// player A clicked undo, special case (freeTurn)
		if (a.getFreeTurn() == true) {
			if (a.getUndoLimit() == 3)
				return;

			a.setTurn(true);
			b.setTurn(false);

			a.setFreeTurn(false);
			a.incrementUndo();
		}

		// player B clicked undo, special case (freeTurn)
		else if (b.getFreeTurn() == true) {
			if (b.getUndoLimit() == 3)//
				return;

			b.setTurn(true);
			a.setTurn(false);

			b.setFreeTurn(false);
			b.incrementUndo();
		}

		// player A clicked undo
		else if (a.getTurn() == false) {
			if (a.getUndoLimit() == 3)
				return;

			a.setTurn(true);
			b.setTurn(false);

			a.incrementUndo();
		}

		// player B clicked undo
		else {
			if (b.getUndoLimit() == 3)//
				return;

			b.setTurn(true);
			a.setTurn(false);

			b.incrementUndo();
		}

		for (int x = 0; x < 14; x++) {
			int prevNumber = bucket[x].gePrevNumOfRocks();
			bucket[x].setnumOfRocks(prevNumber, false);
		}
	}

	/**
	 * updates the model with Mancala rules in counter clockwise direction through array.
	 * @param index, Index of starting pit.
	 */
	public void updateMoveRocks(int index) {

		for (int x = 0; x < 14; x++) {
			bucket[x].setPrevNumOfRocks();
		}

		// Keep turn if click an empty pit.
		if (bucket[index].getnumOfRocks() == 0) {
			int player;
			if (index > 7) {
				player = 2;
			} else {
				player = 1;
			}
			if (player == 1) {
				b.setTurn(false);
				a.setTurn(true);
				a.resetUndoIncrement();
			} else {
				a.setTurn(false);
				b.setTurn(true);
				b.resetUndoIncrement();
			}

			return;
		}

		int sIndex = index;
		int count = bucket[sIndex].getnumOfRocks();
		bucket[sIndex].setnumOfRocks(0, c);
		int eIndex = 0;

		if (sIndex == 0 || sIndex == 7)
			return;

		int i = sIndex - 1;
		while (!(count == 0)) {
			if (i == 7 && (sIndex == 1 || sIndex == 2 || sIndex == 3 || sIndex == 4 || sIndex == 5 || sIndex == 6)) {
				i--;
			}
			if (i == 0
					&& (sIndex == 13 || sIndex == 12 || sIndex == 11 || sIndex == 10 || sIndex == 9 || sIndex == 8)) {
				i = 13;
			}

			int t = bucket[i].getnumOfRocks() + 1;
			bucket[i].setnumOfRocks(t, c);
			count--;
			if (count == 0) {
				eIndex = i; // Gets the ending index so it can be checked for
							// capture and not reset if ends on 0.
			}
			if (i == 0) {
				i = 14;
			}
			i--;

		}

		// extra turn logic
		checkIfFreeTurn(index, eIndex);

		// Capture Stone function
		checkCaptureRock(eIndex, sIndex);

		// end game scenario, when one side is out of stones.
		checkEndGame();

	}

	/**
	 * checks if the last rock landed on the players Mancala if so, let the
	 * player go again
	 * @param eIndex, index that last rock is placed
	 * @param index, the bucket that the player pressed
	 */
	public void checkIfFreeTurn(int index, int eIndex) {
		int player;
		if (index > 7) {
			player = 2;
		} else {
			player = 1;
		}
		if (eIndex == 0 || eIndex == 7) {
			if (player == 1) {
				b.setTurn(false);
				a.setTurn(true);

				a.setFreeTurn(true);
				// a.resetUndoIncrement();
			} else {
				a.setTurn(false);
				b.setTurn(true);

				b.setFreeTurn(true);
				// b.resetUndoIncrement();
			}

		} else {
			a.setFreeTurn(false);
			b.setFreeTurn(false);
		}
	}

	/**
	 * checks if the last rock placed, is placed in a empty bucket if so, if
	 * there are rocks on the parallel side of the board, capture those rocks
	 * and the rock that was just placed
	 * @param eIndex,index that last rock is placed
	 * @param sIndex,the bucket that the player pressed.
	 */
	public void checkCaptureRock(int eIndex, int sIndex) {
		if (bucket[eIndex].getnumOfRocks() == 1 && (eIndex != 0 && eIndex != 7)) {

			//System.out.println(eIndex);
			int cap = bucket[eIndex].getnumOfRocks() + bucket[14 + eIndex - (2 * eIndex)].getnumOfRocks();

			if ((eIndex == 1 || eIndex == 2 || eIndex == 3 || eIndex == 4 || eIndex == 5 || eIndex == 6)
					&& (sIndex == 1 || sIndex == 2 || sIndex == 3 || sIndex == 4 || sIndex == 5 || sIndex == 6)) {
				if (bucket[14 + eIndex - (2 * eIndex)].getnumOfRocks() != 0) {

					int total = bucket[0].getnumOfRocks() + cap;
					bucket[0].setnumOfRocks(total, c);
					bucket[eIndex].setnumOfRocks(0, c);
					bucket[14 + eIndex - (2 * eIndex)].setnumOfRocks(0, c);
				}
			}
			if ((eIndex == 8 || eIndex == 9 || eIndex == 10 || eIndex == 11 || eIndex == 12 || eIndex == 13)
					&& (sIndex == 13 || sIndex == 12 || sIndex == 11 || sIndex == 10 || sIndex == 9 || sIndex == 8)) {
				if (bucket[14 + eIndex - (2 * eIndex)].getnumOfRocks() != 0) {

					int total = bucket[7].getnumOfRocks() + cap;
					bucket[7].setnumOfRocks(total, c);
					bucket[eIndex].setnumOfRocks(0, c);
					bucket[14 + eIndex - (2 * eIndex)].setnumOfRocks(0, c);
				}
			}
		}
	}

	/**
	 * checks if the game ended set the number of rocks in the pit to zero add
	 * the remaining rocks to each corresponding Mancala
	 */
	public void checkEndGame() {
		
		boolean gameFinished = false;
		//boolean playerOne = false;
		//boolean playerTwo = false;
		
		
		if (bucket[13].getnumOfRocks() == 0 && bucket[12].getnumOfRocks() == 0 && bucket[11].getnumOfRocks() == 0
				&& bucket[10].getnumOfRocks() == 0 && bucket[9].getnumOfRocks() == 0
				&& bucket[8].getnumOfRocks() == 0) {

			int left = bucket[6].getnumOfRocks() + bucket[5].getnumOfRocks() + bucket[4].getnumOfRocks()
					+ bucket[3].getnumOfRocks() + bucket[2].getnumOfRocks() + bucket[1].getnumOfRocks()
					+ bucket[0].getnumOfRocks();

			bucket[0].setnumOfRocks(left, c);
			bucket[6].setnumOfRocks(0, c);
			bucket[5].setnumOfRocks(0, c);
			bucket[4].setnumOfRocks(0, c);
			bucket[3].setnumOfRocks(0, c);
			bucket[2].setnumOfRocks(0, c);
			bucket[1].setnumOfRocks(0, c);
			
			gameFinished = true;
		}

		if (bucket[1].getnumOfRocks() == 0 && bucket[2].getnumOfRocks() == 0 && bucket[3].getnumOfRocks() == 0
				&& bucket[4].getnumOfRocks() == 0 && bucket[5].getnumOfRocks() == 0 && bucket[6].getnumOfRocks() == 0) {

			int right = bucket[13].getnumOfRocks() + bucket[12].getnumOfRocks() + bucket[11].getnumOfRocks();
			right = right + bucket[10].getnumOfRocks() + bucket[9].getnumOfRocks() + bucket[8].getnumOfRocks()
					+ bucket[7].getnumOfRocks();

			bucket[7].setnumOfRocks(right, c);
			bucket[13].setnumOfRocks(0, c);
			bucket[12].setnumOfRocks(0, c);
			bucket[11].setnumOfRocks(0, c);
			bucket[10].setnumOfRocks(0, c);
			bucket[9].setnumOfRocks(0, c);
			bucket[8].setnumOfRocks(0, c);

			gameFinished = true;
		}
		
		
		if(gameFinished==true){
			if(bucket[7].getnumOfRocks()<bucket[0].getnumOfRocks()){
				playerOne=true;
			}else{
				playerTwo=true;
			}
		}
		
		/*if(playerOne==true){
			System.out.println("Player A won");
		}
		if(playerTwo == true){
			System.out.println("Player B won");
		}
		 */
		
	}

}