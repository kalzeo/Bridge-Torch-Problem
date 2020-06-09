/**
 * @author Kyle McPherson
 */

package btp;
import java.util.Set;

import cm3038.search.*;
import cm3038.search.informed.BestFirstSearchProblem;

public class Astar extends BestFirstSearchProblem
{
	/**
	 * Create an astar object using a start and goal state.
	 * @param start The initial state.
	 * @param goal  The goal state.
	 */
	public Astar(State start, State goal) 
	{
		super(start, goal);
	}

	/**
	 * Return the f-cost using f(n) = g(n) + h(n) where f(n) is the evaluation function,
	 * g(n) is the cost of the path from the start node to the current node and h(n) 
	 * is the estimated cost from the current node to goal
	 */
	@Override
	public double evaluation(Node node)
	{
		return node.getCost() + heuristic(node.state);
	}
	
	/**
	 * The heuristic function estimates how far a state/node is from the goal state
	 * For A* to work, the heuristic function cannot over-estimate the cost
	 * 
	 * @param currentState The current state of the problem
	 * @return A double value which holds the estimated cost of the current state to the goal.
	 */
	public double heuristic(State currentState)
	{
		BridgeState state = (BridgeState) currentState;
		
		Set<Person> people = state.getTorchLocation() == TorchDirection.WEST ? state.getWest() : state.getEast();
		
		int min = people.stream().min(RunProblem.comparator).get().getTime();
		int max = people.stream().max(RunProblem.comparator).get().getTime();
		
		if(state.getTorchLocation() == TorchDirection.EAST && !RunProblem.getAdvancedStatus())
			return max;
		return min;
		
	}

	/**
	 * Check if the state is equal to the goal state.
	 */
	@Override
	public boolean isGoal(State state) 
	{
		return state.equals(this.goalState);
	}
}