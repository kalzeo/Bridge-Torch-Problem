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
	// Instance variables to store the western/eastern banks and where the torch currently is
	private LinkedHashSet<Person> westBank, eastBank;
	private TorchDirection torchLocation;
	
	/**
	 * Create a new BridgeState object with a western bank, eastern bank and a torch location
	 * 
	 * @param westBank  The set of people on the western bank
	 * @param eastBank  The set of people on the eastern bank
	 * @param direction The current torch location
	 */
	public BridgeState(Set<Person> westBank, Set<Person> eastBank, TorchDirection direction)
	{
		// Set up the states west and east banks
		this.westBank = RunProblem.sortListByTime(westBank);
		this.eastBank = RunProblem.sortListByTime(eastBank);
		
		// Set the location of the torch
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
	@Override
	public List<ActionStatePair> successor() 
	{
		List<ActionStatePair> successors = new ArrayList<ActionStatePair>();
		
		// Get the set of people on the same side as the torch
		Set<Person> people = getTorchLocation() == TorchDirection.WEST ? this.getWest() : this.getEast();	

		people.forEach(person -> 
		{
			// Create a LinkedHashSet that stores the Person objects of people crossing the bridge
			LinkedHashSet<Person> peopleCrossing = new LinkedHashSet<>();
			
			if(!peopleCrossing.contains(person))
			{
				// Add the Person object to the peopleCrossing Set if they're not in it
				peopleCrossing.add(person);
				
				// Generate the action
				BridgeAction action = new BridgeAction(peopleCrossing, switchTorchLocation(this.getTorchLocation()));
				
				// Get the next state
				BridgeState newState = this.nextState(action);
				
				// Remove duplicate successor states to reduce time-complexity, increase efficiency and use less space
				for(int i=0; i<successors.size(); i++)
					if(successors.get(i).state.equals(newState))
						successors.remove(i);
				
				successors.add(new ActionStatePair(action, newState));
			}
			
			people.forEach(person2 -> 
			{
				// Clone the peopleCrossing Set
				LinkedHashSet<Person> peopleCrossing2 = (LinkedHashSet<Person>) peopleCrossing.clone();
				
				if(!peopleCrossing2.contains(person2))
				{
					peopleCrossing2.add(person2);
					BridgeAction action2 = new BridgeAction(peopleCrossing2, switchTorchLocation(this.getTorchLocation()));
					BridgeState newState2 = this.nextState(action2);
					
					for(int i=0; i<successors.size(); i++)
						if(successors.get(i).state.equals(newState2))
							successors.remove(i);
					
					successors.add(new ActionStatePair(action2, newState2));
				}
				
				// If the advanced problem is running
				if(RunProblem.getAdvancedStatus())
				{
					people.forEach(person3 -> 
					{
						// Clone the peopleCrossing2 Set
						LinkedHashSet<Person> peopleCrossing3 = (LinkedHashSet<Person>) peopleCrossing2.clone();
						
						if(!peopleCrossing3.contains(person3))
						{
							peopleCrossing3.add(person3);
							BridgeAction action3 = new BridgeAction(peopleCrossing3, switchTorchLocation(this.getTorchLocation()));
							BridgeState newState3 = this.nextState(action3);
							
							for(int i=0; i<successors.size(); i++)
								if(successors.get(i).state.equals(newState3))
									successors.remove(i);
							
							successors.add(new ActionStatePair(action3, newState3));
						}
					});
				}
			});
		});
	
		return successors;
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
		// Get the Set of people crossing the bridge in the action
		LinkedHashSet<Person> people = action.getPeople();
		
		// Clone the eastern/western banks
		LinkedHashSet<Person> westSide = (LinkedHashSet<Person>) this.westBank.clone();
		LinkedHashSet<Person> eastSide = (LinkedHashSet<Person>) this.eastBank.clone();
		
		LinkedHashSet<Person> src = new LinkedHashSet<>();
		LinkedHashSet<Person> dest = new LinkedHashSet<>();
		
		/*
		 * Switch the torch location to populate the src and dest LinkedHashSets
		 * If the torch is on the EAST then the source is the eastern bank and the
		 * destination is the western bank and vice-versa
		 */
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
