/**
 * The TestSuccessor class is used to get all the possible situations from
 * the current state to generate a list of action-state pairs.
 * 
 * Duplicate action-state pairs will be removed.
 * 
 * Moving [1,2] west to east is the same as moving [2,1]
 * west to east so it will be removed due to it having the
 * same outcome.
 * 
 * @author Kyle McPherson
 */

package btp.tests;
import java.util.LinkedHashSet;
import java.util.Set;

import cm3038.search.State;
import btp.*;

public class TestSuccessor 
{
	public static void main( String[] args )
    {
		Bridge.setCapacity(2);
		
		Set<Person> westBank = new LinkedHashSet<Person>();
		Set<Person> eastBank = new LinkedHashSet<Person>();
		
		westBank.add(new Person(1, "Adam"));   westBank.add(new Person(2, "Ben"));
		westBank.add(new Person(5, "Claire")); westBank.add(new Person(8, "Doris"));

		State initialState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		System.out.println("Initial State: " + initialState.toString());
		
		System.out.println("\nSuccessor: \n" + initialState.successor().toString());
		System.out.println("\nSuccessor Size: " + initialState.successor().size());
    }
}
