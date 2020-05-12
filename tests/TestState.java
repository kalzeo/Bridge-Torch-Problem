/**
 * The TestState class is used to check if a state has been correctly set up.
 * It will print out the state(s) to see if they contain the correct information.
 * @author Kyle McPherson
 */

package btp.tests;
import java.util.LinkedHashSet;

import btp.*;

public class TestState
{
	private static LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
	private static LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
	
	public static void main(String[] args)
	{
		Bridge.setCapacity(2);
		
		// Add people to the west bank
		westBank.add(new Person(1, "Adam"));   westBank.add(new Person(2, "Claire"));
		westBank.add(new Person(5, "Claire")); westBank.add(new Person(8, "Doris"));
		
		// Create initial and goal states
		BridgeState initialState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		BridgeState goalState = new BridgeState(eastBank, westBank, TorchDirection.EAST);

		System.out.println("Initial State: " + initialState.toString()); // prints out the initial state
		System.out.println("Goal State: " + goalState.toString());       // prints out the goal state
	}
}
