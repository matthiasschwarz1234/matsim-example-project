package org.matsim.class2019.analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;

import java.util.HashMap;
import java.util.Map;

public class TravelDistanceEventHandler implements ActivityEndEventHandler, LinkEnterEventHandler, LinkLeaveEventHandler {

	private final Map<Id<Person>, Double> travelDistances = new HashMap<>();
	private final Map<Id<Person>, Link> currentLinks = new HashMap<>();
	private final Network network;

	TravelDistanceEventHandler(Network network) {
		this.network = network;
	}

	Map<Id<Person>, Double> getTravelDistancesByPerson() {
		return travelDistances;
	}

	double getTotalTravelDistance() {
		return travelDistances.values().stream().mapToDouble(d -> d).sum();
	}

	@Override
	public void handleEvent(ActivityEndEvent event) {
		if (isNotInteraction(event.getActType()) && !travelDistances.containsKey(event.getPersonId()))
			travelDistances.put(event.getPersonId(), 0.0);
	}

	@Override
	public void handleEvent(LinkEnterEvent event) {

		Id<Person> id = Id.createPersonId(event.getVehicleId());
		currentLinks.put(id, network.getLinks().get(event.getLinkId()));
	}

	@Override
	public void handleEvent(LinkLeaveEvent event) {

		Id<Person> id = Id.createPersonId(event.getVehicleId());
		Link travelledLink = currentLinks.remove(id);
		travelDistances.merge(id, travelledLink.getLength(), Double::sum);
	}

	private boolean isNotInteraction(String activityType) {
		return !activityType.contains(" interaction");
	}
}
