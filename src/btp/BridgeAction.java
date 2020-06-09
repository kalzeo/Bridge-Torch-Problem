/**
 * @author Kyle McPherson
 */

package btp;
import java.util.LinkedHashSet;
import java.util.Set;

import cm3038.search.*;

public class BridgeAction extends Action
{
	public LinkedHashSet<Person> people;
	private TorchDirection direction;
	
	/**
	 * Create a bridgeAction object with the people that are being across the
	 * bridge and which direction the torch is moving to
	 * 
	 * @param people    The set of people being moved across the bridge
	 * @param direction The direction the torch/people are moving to
	 */
	public BridgeAction(Set<Person> people, TorchDirection direction)
	{
		this.people = RunProblem.sortListByTime(people);
		this.direction = direction;
		this.findCost();
	}
	
	/**
	 * Calculate the overall cost of the movement across the bridge from the set of people.
	 * The slowest time out of all the people moving will be overall cost of the action.
	 */
	private void findCost() 
	{
		this.cost = getPeople().stream().max(RunProblem.comparator).get().getTime();
	}

	/**
	 * Creates a string to model the BridgeAction object.
	 */
	@Override
	public String toString() 
	{
		StringBuilder result = new StringBuilder("Move <torch>, ");
		people.forEach(p -> result.append(String.format("%s ", p.getName())));
		result.append(String.format("%s to %s (cost:%s)", BridgeState.switchTorchLocation(getTorchDirection()), getTorchDirection(), getCost()));
		return result.toString();
	}
	
	// Getter Methods
	
	/**
	 * Get which direction the torch is moving to.
	 * 
	 * If the torch is coming from the WEST then its going to the EAST
	 * and vice-versa
	 * 
	 * @return The direction the torch is moving to.
	 */
	public TorchDirection getTorchDirection() 
	{ 
		return this.direction; 
	}
	
	/**
	 * Get the cost of the action for the people crossing the bridge.
	 * @return The cost of the action.
	 */
	public double getCost()
	{ 
		return this.cost;
	}
	
	/**
	 * Get the Set of people that are crossing the bridge in the action.
	 * @return The Set of people crossing the bridge.
	 */
	public LinkedHashSet<Person> getPeople()
	{
		return this.people;
	}
}
