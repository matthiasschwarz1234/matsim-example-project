package org.matsim.class2019.basics;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RectangleTest {
	
	@Test
	public void testCalculateArea() {
		
		Rectangle rect = new Rectangle(10, 20);
		
		double area = rect.calculateArea();
		
		System.out.println("The area is: " + area);
		assertEquals(200, area, 0.0001);
	}

}
