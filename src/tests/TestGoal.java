/**
 * The TestGoal class will check if the goal state has been met
 * @author Kyle McPherson
 */

package tests;
import java.util.LinkedHashSet;

import cm3038.search.SearchProblem;
import cm3038.search.State;
import btp.*;

public class TestGoal 
{
	public static void main( String[] args )
    {
		LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
		LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
		
		westBank.add(new Person(1, "Adam"));   westBank.add(new Person(2, "Ben"));
		westBank.add(new Person(5, "Claire")); westBank.add(new Person(8, "Doris"));
		
		State initialState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		State goalState = new BridgeState(westBank, eastBank, TorchDirection.EAST);
		 
		SearchProblem problem = new Astar(initialState, goalState);
		
		// Print out if the current state is the goal state. True if goal, false if not.
		System.out.println("Are the following states the same as the goal?\n");
		System.out.println("Is goalState The Same As Goal?: " + problem.isGoal(goalState)); // True
		System.out.println("Is initialState The Same As Goal?: " + problem.isGoal(initialState)); // False
		
		
    }
}
