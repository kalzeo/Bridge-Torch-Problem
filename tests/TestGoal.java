/**
 * The TestGoal class will check if the goal state has been met
 * @author Kyle McPherson
 */

package btp.tests;
import java.util.LinkedHashSet;

import cm3038.search.SearchProblem;
import cm3038.search.State;
import btp.*;

public class TestGoal 
{
	public static void main( String[] args )
    {
		// Set up the initial and goal states as LinkedHashSets
		LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
		LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
		
		// Add people to the west bank
		westBank.add(new Person(1, "Adam"));   westBank.add(new Person(2, "Ben"));
		westBank.add(new Person(5, "Claire")); westBank.add(new Person(8, "Doris"));
		
		// Create initial and goal states as BridgeStates
		State initialState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		State goalState = new BridgeState(westBank, eastBank, TorchDirection.EAST);
		 
		// Create a problem instance that will solve the solution using A*
		SearchProblem problem = new Astar(initialState, goalState);
		
		// Print out if the current state is the goal state. True if goal, false if not.
		System.out.println("Are the following states the same as the goal?\n");
		System.out.println("Is goalState The Same As Goal?: " + problem.isGoal(goalState)); // True
		System.out.println("Is initialState The Same As Goal?: " + problem.isGoal(initialState)); // False
		
		
    }
}
