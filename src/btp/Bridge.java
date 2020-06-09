/**
 * @author Kyle McPherson
 */

package btp;

public class Bridge 
{
	// The number of people who can be on the bridge at a given time.
	public static int BRIDGE_CAPACITY;
	
	/**
	 * Set the capacity of the bridge.
	 * @param capacity The number of people allowed on the bridge at a given time.
	 */
	public static void setCapacity(int capacity) { BRIDGE_CAPACITY = capacity; }
	
	/**
	 * Get the capacity of the bridge.
	 * @return The number of people allowed on the bridge at a given time.
	 */
	public static int getCapacity() { return BRIDGE_CAPACITY; }
	
	/**
	 * Create a string to model the bridge which has the capacity of it in the middle.
	 * @return The bridge.
	 */
	public static String printBridge()
	{
		return String.format("|======(%s)======|", getCapacity());
	}
}
