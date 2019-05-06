package org.matsim.class2019.analysis;

import org.junit.Test;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;
import org.matsim.facilities.ActivityFacility;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TravelTimeEventHandlerTest {

	@Test
	public void testEventHandling() {

		final Id<Person> person1 = Id.createPersonId(1);
		final Id<Link> linkId = Id.createLinkId(1);
		final Id<ActivityFacility> facilityId = Id.create(1, ActivityFacility.class);
		final int actEndTime = 1;
		final int actStartTime = 65;
		final int actInteraction = 33;

		ActivityEndEvent endEvent = new ActivityEndEvent(actEndTime, person1, linkId, facilityId, "some");
		ActivityStartEvent startEvent = new ActivityStartEvent(actStartTime, person1, linkId, facilityId, "some");
		ActivityStartEvent interactionStart = new ActivityStartEvent(actInteraction, person1, linkId, facilityId, "car interaction");
		ActivityEndEvent interactionEnd = new ActivityEndEvent(actInteraction, person1, linkId, facilityId, "car interaction");

		TravelTimeEventHandler handler = new TravelTimeEventHandler();

		handler.handleEvent(endEvent);
		handler.handleEvent(interactionStart);
		handler.handleEvent(interactionEnd);
		handler.handleEvent(startEvent);

		Map<Id<Person>, Double> travelTimeByAgent = handler.getTravelTimesByPerson();

		assertEquals(1, travelTimeByAgent.size());
		assertTrue(travelTimeByAgent.containsKey(person1));
		assertEquals(actStartTime - actEndTime, travelTimeByAgent.get(person1), 0.001);

		assertEquals(actStartTime - actEndTime, handler.calculateOverallTravelTime(), 0.001);
	}

	@Test
	public void testEventHandling_multiplePersons() {

		final Id<Person> person1 = Id.createPersonId(1);
		final Id<Person> person2 = Id.createPersonId(2);
		final Id<Link> linkId = Id.createLinkId(1);
		final Id<ActivityFacility> facilityId = Id.create(1, ActivityFacility.class);
		final int actEndTime = 1;
		final int actStartTime = 65;
		final int actInteraction = 33;

		ActivityEndEvent endEvent1 = new ActivityEndEvent(actEndTime, person1, linkId, facilityId, "some");
		ActivityEndEvent endEvent2 = new ActivityEndEvent(actEndTime, person2, linkId, facilityId, "some");
		ActivityStartEvent startEvent1 = new ActivityStartEvent(actStartTime, person1, linkId, facilityId, "some");
		ActivityStartEvent startEvent2 = new ActivityStartEvent(actStartTime, person2, linkId, facilityId, "some");
		ActivityStartEvent interactionStart1 = new ActivityStartEvent(actInteraction, person1, linkId, facilityId, "car interaction");
		ActivityStartEvent interactionStart2 = new ActivityStartEvent(actInteraction, person2, linkId, facilityId, "car interaction");
		ActivityEndEvent interactionEnd1 = new ActivityEndEvent(actInteraction, person1, linkId, facilityId, "car interaction");
		ActivityEndEvent interactionEnd2 = new ActivityEndEvent(actInteraction, person1, linkId, facilityId, "car interaction");

		TravelTimeEventHandler handler = new TravelTimeEventHandler();

		handler.handleEvent(endEvent1);
		handler.handleEvent(interactionStart1);
		handler.handleEvent(interactionEnd1);
		handler.handleEvent(startEvent1);

		handler.handleEvent(endEvent2);
		handler.handleEvent(interactionStart2);
		handler.handleEvent(interactionEnd2);
		handler.handleEvent(startEvent2);

		Map<Id<Person>, Double> travelTimeByAgent = handler.getTravelTimesByPerson();
		assertEquals(2, travelTimeByAgent.size());
		assertTrue(travelTimeByAgent.containsKey(person1));
		assertTrue(travelTimeByAgent.containsKey(person2));
		assertEquals(actStartTime - actEndTime, travelTimeByAgent.get(person1), 0.001);
		assertEquals(actStartTime - actEndTime, travelTimeByAgent.get(person2), 0.001);

		assertEquals((actStartTime - actEndTime) * 2, handler.calculateOverallTravelTime(), 0.001);
	}
}