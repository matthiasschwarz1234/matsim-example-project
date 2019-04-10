package org.matsim.class2019.basics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CollectionTest {
	
	@Test
	public void listExample() {
		
		List<Rectangle> myList = new ArrayList<>();
		myList.add(new Rectangle(10, 20));
		myList.add(new Rectangle(10, 200));
		myList.add(new Rectangle(10, 2));
		myList.add(new Rectangle(10, 10));
		myList.add(new Rectangle(10, 200000));

		for(Rectangle rect : myList) {
			System.out.println("The area is: " + rect.calculateArea());
		}
	}

}
