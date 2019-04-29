package org.matsim.class2019.basics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class CollectionTest {
	
	@Test
	public void listExample() {
		
		System.out.println("List Example");
		
		/*
		 * A list is an ordered collection of objects. The objects contained in the list are held
		 * in the order they were added to the list. New items are added at the end of the list 
		 */
		List<Rectangle> myList = new ArrayList<>();
		myList.add(new Rectangle(10, 20));
		myList.add(new Rectangle(10, 200));
		myList.add(new Rectangle(10, 2));
		myList.add(new Rectangle(10, 10));
		myList.add(new Rectangle(10, 200000));

		/*
		 * Iterate over each rectangle contained in the list and print its area
		 */
		for(Rectangle rect : myList) {
			System.out.println("The area is: " + rect.calculateArea());
		}
		
		/*
		 * We could also retrieve an object by index. Note: Lists have 0-based indices.
		 * 
		 */
		System.out.println("\nFetching rectangles by index");
		Rectangle firstRectangleAtPositionZero = myList.get(0);
		Rectangle secondRectangleAtPositionOne = myList.get(1);
		System.out.println("The area of the first rectangle is: " + firstRectangleAtPositionZero.calculateArea());
		System.out.println("The area of the second rectangle is: " + secondRectangleAtPositionOne.calculateArea());
	}
	
	@Test
	public void setExample() {
		
		System.out.println("\nSet Example");
		
		/*
		 * A set is an unordered collection of objects. There is no guarantee in which order objects are held. 
		 * Actually, you have to expect the order to change on every call you make to the set.
		 * Additionally each object can only be contained once. 
		 */
		Set<Rectangle> mySet = new HashSet<>();
		Rectangle rectangleWeWantToTest = new Rectangle(100, 100);
		mySet.add(rectangleWeWantToTest);
		mySet.add(new Rectangle(10, 20));
		mySet.add(new Rectangle(10, 200));
		mySet.add(new Rectangle(10, 2));
		mySet.add(new Rectangle(10, 10));
		mySet.add(new Rectangle(10, 20000000));
		
		/*
		 * We can iterate over a set the same way we do over a list. Note that the order is probably different
		 * Than the order in which we inserted the rectangles
		 */
		for(Rectangle rect: mySet) {
			System.out.println("The area is: " + rect.calculateArea());
		}
		
		/*
		 * Sets cannot be accessed by index. Instead they are very efficient when it comes to testing whether 
		 * an object is part of a set or not.
		 */
		if (mySet.contains(rectangleWeWantToTest)) {
			System.out.println("The rectangle was contained in the set");
		} else {
			System.out.println("The rectangle was not contained in the set");
		}
	}
	
	@Test
	public void sortingAList() {
		
		System.out.println("\nSorting a list");
		
		List<Rectangle> myList = new ArrayList<>();
		myList.add(new Rectangle(10, 20));
		myList.add(new Rectangle(10, 200));
		myList.add(new Rectangle(10, 2));
		myList.add(new Rectangle(10, 10));
		myList.add(new Rectangle(10, 200000));
		
		/*
		 * As in the list example the order of the rectangles is the same as we've inserted them
		 */
		System.out.println("Printing the initial order of the list");
		for(Rectangle rect: myList) {
			System.out.println("The area is: " + rect.calculateArea());
		}
		
		/*
		 * now, we are going to sort the rectangles by area. The sort function of the list takes a comparison function as a 
		 * parameter. The notation used to pass the compare function is called lambda expression you can read up on it here:
		 * https://javabeginnerstutorial.com/core-java-tutorial/java-lambda-beginners-guide/
		 */
		myList.sort((rect1, rect2) -> compareRectangles(rect1, rect2));
		
		/*
		 * Now, the rectangles should be sorted in ascending order
		 */
		System.out.println("\nPrinting the sorted order of the list");
		for(Rectangle rect: myList) {
			System.out.println("The area is: " + rect.calculateArea());
		}
	}
	
	/*
	 * This function compares areas of two rectangles
	 */
	private int compareRectangles(Rectangle rect1, Rectangle rect2) {
		
		// a comparison function returns -1 if rect1 should be 'on the left' of rect2 within a list
		if (rect1.calculateArea() < rect2.calculateArea()) return -1;
		// a comparison function returns 1 if rect1 should be 'on the right' of rect2 within a list
		else if (rect1.calculateArea() > rect2.calculateArea()) return 1;
		// a comparison function returns 0 if rect1 and rect2 have the same value. The sorting algorithm will most probably
		// keep the previous order
		return 0;
	}
}
