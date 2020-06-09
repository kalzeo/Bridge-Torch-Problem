/**
 * The TestAction class is used to show if an action contains enough information
 * for a movement across the bridge and if action has been applied correctly.
 * @author Kyle McPherson
 */

package tests;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

import btp.*;

public class TestAction 
{
	public static void main( String[] args )
    {
		Bridge.setCapacity(2);
		LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
		LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
		
		westBank.add(new Person(1, "Adam"));   westBank.add(new Person(2, "Ben"));
		westBank.add(new Person(5, "Claire")); westBank.add(new Person(8, "Doris"));

		BridgeState testState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		
		LinkedHashSet<Person> crossing = new LinkedHashSet<Person>();
		LinkedHashSet<Person> crossing2 = new LinkedHashSet<Person>();
		LinkedHashSet<Person> crossing3 = new LinkedHashSet<Person>();
		
		// Move people with a cost <= 2 from the west to the east
		System.out.println("-- MOVING PEOPLE WITH CROSSING TIMES <= 2 MINUTES --");
		testState.getWest().forEach(person -> 
		{
			if(person.getTime() <= 2 && !crossing.contains(person))
				crossing.add(person);
		});
		
		doAction(testState, crossing);
		
		System.out.println("\n--------------------\n");
		
		// Move a random person from the west bank onto the east bank
		Person person = getRandom(new ArrayList<Person>(westBank), TorchDirection.EAST);
		crossing2.add(person);
		doAction(testState, crossing2);
		
		System.out.println("\n--------------------\n");
		
		// Move a random person from the east bank onto the west bank
		eastBank.add(new Person(9, "Edward"));
		eastBank.add(new Person(10, "Fiona"));
		BridgeState testState2 = new BridgeState(westBank, eastBank, TorchDirection.EAST);
		Person person1 = getRandom(new ArrayList<Person>(eastBank), TorchDirection.WEST);
		crossing3.add(person1);
		doAction(testState2, crossing3);
	}
	
	/**
	 * Get a random Person object from an ArrayList<Person>.
	 * 
	 * @param bank    The bank of people to get a random Person from.
	 * @param goingTo Where the Person is being moved to.
	 * @return
	 */
	public static Person getRandom(ArrayList<Person> bank, TorchDirection goingTo)
	{
		System.out.println(String.format("-- MOVING RANDOM PERSON FROM %s TO THE %s --", bank, goingTo));
		return bank.get(new Random().nextInt(bank.size()));
	}
	
	/**
	 * Carry out the action to bring the current state into a new state.
	 * 
	 * @param currentState   The current state.
	 * @param peopleCrossing The set of people crossing.
	 */
	public static void doAction(BridgeState currentState, LinkedHashSet<Person> peopleCrossing)
	{
		BridgeAction action = new BridgeAction(peopleCrossing, TorchDirection.EAST);
		BridgeState newState = (BridgeState) currentState.nextState(action);
		
		System.out.println("Initial State: " + currentState.toString());
		System.out.println("Action Applied: " + action.toString());
		System.out.println("New State: " + newState.toString());
	}
}
