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
	
	private static LinkedHashSet<Person> westBank = new LinkedHashSet<Person>();
	private static LinkedHashSet<Person> eastBank = new LinkedHashSet<Person>();
	private static boolean advancedStatus = false;
	
	public static void main(String[] args)
    {
		switch(createMenu())
		{
			case 1: configureBasicProblem(); break;
			case 2: configureAdvancedProblem(); break;
		}
		
		BridgeState initialState = new BridgeState(westBank, eastBank, TorchDirection.WEST);
		BridgeState goalState = new BridgeState(eastBank, westBank, TorchDirection.EAST);
		
		System.out.println(String.format("\nInit: %s", initialState.toString()));
		System.out.println(String.format("Goal: %s\n", goalState.toString()));
		
		Astar problem = new Astar(initialState, goalState);
		
		Path path = problem.search();
		
		if (path == null)							
			System.out.println("No solution.");
		
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
		westBank.add(new Person(1, "Adam"));
		westBank.add(new Person(2, "Ben")); 
		westBank.add(new Person(5, "Claire"));
		westBank.add(new Person(8, "Doris"));
		
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
		
		setAdvancedStatus(true);
		
		eastBank.add(new Person(9, "Edward"));
		eastBank.add(new Person(10, "Fiona"));
		
		Bridge.setCapacity(3);
	}
	
	/**
	 * An easy to use menu that shows a list of actions the program can do.
	 * A choice to either complete the basic or advanced problem can be carried out.
	 * @return The menu choice
	 */
	private static int createMenu()
	{
        try (Scanner input = new Scanner(System.in)) {
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
		TreeSet<Person> sorted = new TreeSet<>(comparator);
		sorted.addAll(people);
		return new LinkedHashSet<>(sorted);
		
	}
}
