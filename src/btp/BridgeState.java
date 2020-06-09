/**
 * @author Kyle McPherson
 */

package btp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cm3038.search.*;

public class BridgeState implements State
{
	private LinkedHashSet<Person> westBank, eastBank;
	private TorchDirection torchLocation;
	private List<ActionStatePair> successors = new ArrayList<ActionStatePair>();
	
	/**
	 * Create a new BridgeState object with a western bank, eastern bank and a torch location
	 * 
	 * @param westBank  The set of people on the western bank
	 * @param eastBank  The set of people on the eastern bank
	 * @param direction The current torch location
	 */
	public BridgeState(Set<Person> westBank, Set<Person> eastBank, TorchDirection direction)
	{
		this.westBank = RunProblem.sortListByTime(westBank);
		this.eastBank = RunProblem.sortListByTime(eastBank);
		this.torchLocation = direction;
	}

	/**
	 * Creates a string to model the BridgeState object.
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder("");
		this.getWest().forEach(westernPerson -> result.append(westernPerson.toString()));
		result.append(Bridge.printBridge());
		
		switch(this.getTorchLocation())
		{
			case EAST: result.append(" <Torch> "); break;
			case WEST: result.insert(0, "<Torch> "); break;
		}
		
		this.getEast().forEach(easternPerson -> result.append(easternPerson.toString()));
		return result.toString();
	}
	
	/**
	 * Create a list of action-state pairs from the current BridgeState
	 * @return A List<ActionStatePair> that contains all valid action and next-state pairs.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActionStatePair> successor() 
	{
		// Get the set of people on the same side as the torch
		Set<Person> people = getTorchLocation() == TorchDirection.WEST ? this.getWest() : this.getEast();	

		people.forEach(person -> 
		{
			LinkedHashSet<Person> peopleCrossing = new LinkedHashSet<>();
			getSuccessor(peopleCrossing, person);
			
			people.forEach(person2 -> 
			{
				LinkedHashSet<Person> peopleCrossing2 = (LinkedHashSet<Person>) peopleCrossing.clone();
				getSuccessor(peopleCrossing2, person2);
				
				if(RunProblem.getAdvancedStatus())
				{
					people.forEach(person3 -> 
					{
						LinkedHashSet<Person> peopleCrossing3 = (LinkedHashSet<Person>) peopleCrossing2.clone();
						getSuccessor(peopleCrossing3, person3);
					});
				}
			});
		});
	
		return successors;
	}
	
	/**
	 * Generate valid successor states
	 * 
	 * @param peopleCrossing The people crossing the bridge
	 * @param person         The current person
	 */
	private void getSuccessor(LinkedHashSet<Person> peopleCrossing, Person person)
	{
		if(!peopleCrossing.contains(person))
		{
			peopleCrossing.add(person);
			BridgeAction action = new BridgeAction(peopleCrossing, switchTorchLocation(this.getTorchLocation()));
			BridgeState state = this.nextState(action);
			
			// Remove duplicate successor states
			for(int i=0; i<successors.size(); i++)
				if(successors.get(i).state.equals(state))
					successors.remove(i);
			
			successors.add(new ActionStatePair(action, state));
		}
	}

	/**
	 * Switch what side of the bank the torch is on to the opposite bank.
	 * 
	 * @param torchLocation The current torch location.
	 * @return 				The new torch location.
	 */
	public static TorchDirection switchTorchLocation(TorchDirection torchLocation) 
	{
		if(torchLocation == TorchDirection.WEST)
			return TorchDirection.EAST;
		return TorchDirection.WEST;
	}

	/**
	 * Apply an action to the current state to transition it into the next state
	 * @return The new state with the torch location switched to the opposite bank
	 */
	@SuppressWarnings("unchecked")
	public BridgeState nextState(BridgeAction action)
	{
		LinkedHashSet<Person> people = action.getPeople();
		
		LinkedHashSet<Person> westSide = (LinkedHashSet<Person>) this.westBank.clone();
		LinkedHashSet<Person> eastSide = (LinkedHashSet<Person>) this.eastBank.clone();
		
		LinkedHashSet<Person> src = new LinkedHashSet<>();
		LinkedHashSet<Person> dest = new LinkedHashSet<>();
		
		switch(getTorchLocation())
		{
			case EAST: src = eastSide; dest = westSide; break;
			case WEST: src = westSide; dest = eastSide; break;
		}
		
		src.removeAll(people);
		dest.addAll(people);
		
		return new BridgeState(westSide, eastSide, switchTorchLocation(this.getTorchLocation()));
	}
	
	/**
	 * Create a hash code for the BridgeState that will be stored into a hash map.
	 * 
	 * A hash code will be generated for a sequence of input values. In this case the input
	 * values will be the list of people on the western/eastern banks and what side the torch is on
	 * 
	 * @return An Integer hash value that will be used to identify a BridgeState
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(getWest(), getEast(), getTorchLocation());
	}
	
	/**
	 * Compare if an Object is equal to a BridgeState.
	 * 
	 * If the Object is an instance of BridgeState then it will check if the
	 * current states western/eastern banks and torch location are all equal
	 * to the people on the western bank, eastern bank and torch location.
	 * 
	 * True will be returned if equal otherwise false.
	 */
	@Override
	public boolean equals(Object state)
	{
		if(!(state instanceof BridgeState))
			return false;
		
		BridgeState bs = (BridgeState) state;
		
		return getWest().equals(bs.westBank) && 
			   getEast().equals(bs.eastBank) &&
			   getTorchLocation() == bs.getTorchLocation();
		
	}
	
	// Getter Methods
	
	/**
	 * Get the list of people on the western bank.
	 * @return The list of people on the western bank.
	 */
	public LinkedHashSet<Person> getWest() { return this.westBank; }
	
	/**
	 * Get the list of people on the eastern bank.
	 * @return The list of people on the eastern bank.
	 */
	public LinkedHashSet<Person> getEast() { return this.eastBank; }
	
	/**
	 * Return what bank the torch is currently on.
	 * @return The torch location.
	 */
	public TorchDirection getTorchLocation() { return this.torchLocation; }
	
	// Setter Methods
	
	/**
	 * Set which bank the torch will be on.
	 * @param direction The new location of the torch i.e western or eastern bank.
	 */
	public void setTorchLocation(TorchDirection direction) { this.torchLocation = direction; }
}
