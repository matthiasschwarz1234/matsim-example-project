package org.matsim.class2019.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;

public class CreateRectangularNetworkTest {

	@Test
	public void testCreateNetwork() {
		
		double left = 0;
		double right = 100;
		double top = 100;
		double bottom = 0;
		CreateRectangularNetwork testObject = new CreateRectangularNetwork();
		
		Network result = testObject.createNetwork(left, right, top, bottom);
		
		for(Link link : result.getLinks().values()) {
			assertEquals(4.5, link.getFreespeed(), 0.001);
			assertEquals(600, link.getCapacity(), 0.001);
			assertTrue(link.getAllowedModes().contains(TransportMode.ship));
		}
	}
}
