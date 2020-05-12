/**
 * @author Kyle McPherson
 */

package btp;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import cm3038.search.*;

public class RunProblem 
{
	// Create a Comparator object that will compare the crossing times of a Person object
	public static Comparator<Person> comparator = Comparator.comparing( Person::getTime );
	
	// Set up the eastern and western banks as LinkedHashSets
	private static LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
	private static LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
	
	// Default value of the advanced status, this will be modified when either of the problems are configured
	private static boolean advancedStatus = false;
	
	public static void main(String[] args)
    {
		/*
		 *  Switch the menu choice
		 *  
		 *  Configure the basic problem if the choice is 1 or configure
		 *  the advanced problem if the choice is 2
		 */
		switch(createMenu())
		{
			case 1: configureBasicProblem(); break;
			case 2: configureAdvancedProblem(); break;
		}
		
		// Set up the initial and goal state
		BridgeState initialState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		BridgeState goalState = new BridgeState(eastBank, westBank, TorchDirection.EAST);
		
		// Print out the initial and goal states
		System.out.println(String.format("\nInit: %s", initialState.toString()));
		System.out.println(String.format("Goal: %s\n", goalState.toString()));
		
		// Create an A* problem to solve the solution
		Astar problem = new Astar(initialState, goalState);
		
		// Carry out the search and get the returned result of the Path object
		Path path = problem.search();
		
		// If the goal state cannot be reached then there is no solution
		if (path == null)							
			System.out.println("No solution.");
		
		// If there's a path to the goal state then print the path with the number of nodes visited / overall cost.
		else	
		{
			System.out.println(String.format("Nodes Explored: %s\nSolution Cost: %s\n\nSolution:", problem.nodeVisited, path.cost));
			path.print();
		}
    }
	
	/**
	 * Configure and set up the basic problem.
	 * 
	 * The basic problem deals with 4 people on the western bank and a bridge
	 * capacity of 2
	 */
	private static void configureBasicProblem()
	{
		// Add people to the western bank
		westBank.add(new Person(1, "Adam"));
		westBank.add(new Person(2, "Ben")); 
		westBank.add(new Person(5, "Claire"));
		westBank.add(new Person(8, "Doris"));
		
		// Set the capacity of the bridge to 2
		Bridge.setCapacity(2);
	}
	
	/**
	 * Configure and set up the advanced problem.
	 * 
	 * The advanced problem deals with 4 people on the western bank and 2 people on the eastern.
	 * The bridge has a capacity of 3.
	 */
	private static void configureAdvancedProblem()
	{
		// Configure the basic problem to set up the western bank
		configureBasicProblem();
		
		// Set the advanced status to true
		setAdvancedStatus(true);
		
		// Add people to the eastern bank
		eastBank.add(new Person(9, "Edward"));
		eastBank.add(new Person(10, "Fiona"));
		
		// Set the capacity of the bridge to 3
		Bridge.setCapacity(3);
	}
	
	/**
	 * An easy to use menu that shows a list of actions the program can do.
	 * A choice to either complete the basic or advanced problem can be carried out.
	 * @return The menu choice
	 */
	@SuppressWarnings("resource")
	private static int createMenu()
	{
        Scanner input = new Scanner(System.in);
        int choice;
        System.out.print("Choose a Problem To Solve\n-------------------------\n1 - Basic Problem\n2 - Advanced Problem\n");
        do
        {
        	System.out.print("> ");
        	choice = input.nextInt();
        }
        while(choice < 1 || choice > 2);
        
        return choice;
	}
	
	/**
	 * Set the parameter for if the advanced problem is running.
	 * 
	 * @param runningAdv boolean value to indicate if the advanced problem is running or not.
	 *                   True to run, default: false.
	 */
	public static void setAdvancedStatus(boolean runningAdv)
	{
		advancedStatus = runningAdv;
	}
	
	/**
	 * Return the advanced status to check if the advanced problem is running.
	 * @return true if the advanced problem is running otherwise false.
	 */
	public static boolean getAdvancedStatus()
	{
		return advancedStatus;
	}
	
	/**
	 * Manipulate a set of people to sort them from the shortest to longest time
	 * using a sorting comparator.
	 * 
	 * @param people The set of people to be manipulated.
	 * @return The LinkedHashSet<Person> with the people sorted.
	 */
	public static LinkedHashSet<Person> sortListByTime(Set<Person> people)
	{
		// Create a TreeSet to sort the people by their time
		TreeSet<Person> sorted = new TreeSet<>(comparator);
		
		// Add the people to the TreeSet
		sorted.addAll(people);
		
		// Return the sorted LinkedHashSet
		return new LinkedHashSet<>(sorted);
		
	}
}