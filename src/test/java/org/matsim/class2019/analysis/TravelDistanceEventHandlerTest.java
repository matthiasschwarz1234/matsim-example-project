package org.matsim.class2019.analysis;

import org.junit.Test;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.network.NetworkUtils;
import org.matsim.vehicles.Vehicle;

import static org.junit.Assert.assertEquals;

public class TravelDistanceEventHandlerTest {

	@Test
	public void handleTrip_singleAgentSingleLink() {

		Network network = NetworkUtils.createNetwork();
		Id<Person> agentId = Id.createPersonId(0);
		Id<Vehicle> vehicleId = Id.createVehicleId(agentId);

		Node n0 = network.getFactory().createNode(Id.createNodeId(0), new Coord(0, 0));
		Node n1 = network.getFactory().createNode(Id.createNodeId(1), new Coord(100, 100));
		Link link = network.getFactory().createLink(Id.createLinkId(0), n0, n1);
		link.setCapacity(1);
		link.setLength(100);
		network.addNode(n0);
		network.addNode(n1);
		network.addLink(link);

		ActivityEndEvent endEvent = new ActivityEndEvent(1, agentId, link.getId(), null, "home");
		LinkEnterEvent enterEvent = new LinkEnterEvent(1, vehicleId, link.getId());
		LinkLeaveEvent leaveEvent = new LinkLeaveEvent(4, vehicleId, link.getId());

		TravelDistanceEventHandler handler = new TravelDistanceEventHandler(network);

		// act
		handler.handleEvent(endEvent);
		handler.handleEvent(enterEvent);
		handler.handleEvent(leaveEvent);

		// assert
		double distanceByPerson = handler.getTravelDistancesByPerson().get(agentId);
		assertEquals(link.getLength(), distanceByPerson, 0.0001);

		double totalDistance = handler.getTotalTravelDistance();
		assertEquals(link.getLength(), totalDistance, 0.0001);
	}
}