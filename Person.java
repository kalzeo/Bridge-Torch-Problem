/**
 * Class to hold information about a person
 * @author Kyle
 */

package btp;

public class Person 
{
	// Instance variables to store the name and time of a Person
	private String name;
	private int time;
	
	/**
	 * Construct a Person object with a name and time
	 * 
	 * @param time The crossing time (cost) of the person to cross the bridge.
	 * @param name The name of the person.
	 */
	public Person(int time, String name)
	{
		this.name = name;
		this.time = time;
	}
	
	/**
	 * Creates a string to model the Person object.
	 * The string will return the name and crossing time of a Person object.
	 */
	public String toString()
	{
		return String.format("%s(%d) ", getName(), getTime());
	}
	
	// Getter Methods
	
	/**
	 * Get the name of a person.
	 * @return The String value of the persons name.
	 */
	public String getName() { return this.name; }
	
	/**
	 * Get the crossing time (cost) of a person.
	 * @return An int value of the persons crossing time (cost).
	 */
	public int getTime() { return this.time; }
}
