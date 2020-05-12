/**
 * The TestEquals class will check if two states equal each other.
 * @author Kyle McPherson
 */

package btp.tests;
import java.util.LinkedHashSet;
import cm3038.search.State;
import btp.*;

public class TestEquals 
{
	public static void main( String[] args )
    {
		// Set up the initial and goal states as LinkedHashSets
		LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
		LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
		
		// Add people to the western bank
		westBank.add(new Person(1, "Adam"));   westBank.add(new Person(2, "Ben"));
		westBank.add(new Person(5, "Claire")); westBank.add(new Person(8, "Doris"));
		
		// Create identical initial and goal states
		State testSame = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		State testSame2 = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		
		// Create different initial and goal states
		State testDifferent = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		State testDifferent2 = new BridgeState(eastBank, westBank, TorchDirection.EAST);

		// Print out if states are equal. True if equal otherwise false.
		System.out.println("Are the following states equal?\n");
		System.out.println("Is testSame Equal TO testSame2: " + testSame.equals(testSame2)); // true
		System.out.println("Is testSame Equal TO testDifferent: " + testSame.equals(testDifferent)); // true
		System.out.println("Is testSame Equal TO testDifferent2: " + testSame.equals(testDifferent2)); // false
		System.out.println("Is testDifferent Equal TO testDifferent2: " + testDifferent.equals(testDifferent2)); // false
		System.out.println("Is testDifferent2 Equal TO testSame2: " + testDifferent2.equals(testSame2)); // false
    }
}
