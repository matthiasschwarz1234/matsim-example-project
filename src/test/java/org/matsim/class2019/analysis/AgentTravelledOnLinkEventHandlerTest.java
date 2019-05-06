package org.matsim.class2019.analysis;

import org.junit.Test;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.network.Link;
import org.matsim.vehicles.Vehicle;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AgentTravelledOnLinkEventHandlerTest {

	@Test
	public void multipleLinkLeaveEvents() {

		Id<Vehicle> vehicle0 = Id.createVehicleId(0);
		Id<Vehicle> vehicle1 = Id.createVehicleId(1);
		Id<Vehicle> vehicle2 = Id.createVehicleId(2);
		Id<Vehicle> vehicle3 = Id.createVehicleId(3);

		Id<Link> link0 = Id.createLinkId(0);
		Id<Link> link1 = Id.createLinkId(1);
		Id<Link> link2 = Id.createLinkId(2);
		Id<Link> link3 = Id.createLinkId(3);

		Set<Id<Link>> linksToWatch = new HashSet<>();
		linksToWatch.add(link0);
		linksToWatch.add(link1);

		AgentTravelledOnLinkEventHandler handler = new AgentTravelledOnLinkEventHandler(linksToWatch);

		// vehicle 0 should be included
		handler.handleEvent(new LinkLeaveEvent(1, vehicle0, link0));
		handler.handleEvent(new LinkLeaveEvent(2, vehicle0, link3));

		// vehicle 1 should be included
		handler.handleEvent(new LinkLeaveEvent(1, vehicle1, link1));
		handler.handleEvent(new LinkLeaveEvent(2, vehicle1, link0));
		handler.handleEvent(new LinkLeaveEvent(3, vehicle1, link3));

		// vehicle 2 shouldn't be included
		handler.handleEvent(new LinkLeaveEvent(1, vehicle2, link3));
		handler.handleEvent(new LinkLeaveEvent(2, vehicle2, link2));


		assertTrue(handler.getPersonOnWatchedLinks().contains(Id.createPersonId(vehicle0)));
		assertTrue(handler.getPersonOnWatchedLinks().contains(Id.createPersonId(vehicle1)));
		assertFalse(handler.getPersonOnWatchedLinks().contains(Id.createPersonId(vehicle2)));
	}
}