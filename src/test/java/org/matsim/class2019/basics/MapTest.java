package org.matsim.class2019.basics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class MapTest {
	
	@Test
	public void mapExample() {
		
		/*
		 * Maps store objects as key value pairs. As Sets the contained objects are unordered. 
		 * However, objects can be fetched by their corresponding key. Note that each key can only
		 * appear once. If you try to insert an object with a key which is already present the new 
		 * object is associated with the key and the old object is no longer part of the map
		 */
		Map<String, Rectangle> myMap = new HashMap<>();
		myMap.put("first", new Rectangle(10, 10));
		myMap.put("second", new Rectangle(20, 20));
		myMap.put("third", new Rectangle(30, 30));
		myMap.put("small", new Rectangle(1, 1));
		myMap.put("big", new Rectangle(1000, 1000));
		
		System.out.println("Iterate over map values");
		/*
		 * We can iterate over the values of a map
		 */
		for(Rectangle rect: myMap.values()) {
			System.out.println("The area of the rectangle is: " + rect.calculateArea());
		}
		
		System.out.println("\nIterate over map keys");
		/*
		 * We can iterate over the keys of a map
		 */
		for(String key: myMap.keySet()) {
			System.out.println("The key is: " + key);
		}
		
		System.out.println("\nIterate over the key-value pairs of the map");
		/*
		 * We can iterate over the keys of a map
		 */
		for(Entry<String, Rectangle> entry: myMap.entrySet()) {
			System.out.println("The key is: " + entry.getKey() + " and the area of the rectangle is: " + entry.getValue().calculateArea());
		}
		
		/*
		 * Maps are very fast when you want to know whether a certain key is present
		 */
		if (myMap.containsKey("small")) {

			// if a key is present you can fetch the associated value by key.
			Rectangle smallRectangle = myMap.get("small");
			System.out.println("\nThe small rectangle was contained and its area is: " + smallRectangle.calculateArea());
		} else {
			System.out.println("\nThere was no rectangle with the key 'small'");
		}
	}

}
