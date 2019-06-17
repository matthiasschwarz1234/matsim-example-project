package org.matsim.class2019.analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunTripAnalysis {

	public static void main(String[] args) {

		Path events = Paths.get("C:\\Users\\Janek\\Desktop\\drt-example\\output\\run02\\output_events.xml.gz");

		EventsManager manager = EventsUtils.createEventsManager();
		TripEventHandler handler = new TripEventHandler();
		manager.addHandler(handler);
		new MatsimEventsReader(manager).readFile(events.toString());

		Map<Id<Person>, List<Trip>> tripByPerson = handler.getTripToPerson();

		Map<String, Double> countByMode = new HashMap<>();
		int totalTrips = 0;

		for (List<Trip> trips : tripByPerson.values()) {
			for (Trip trip : trips) {
				/*
				The main mode of a trip is e.g. pt
				if the trip consists of legs 'access_walk' -> 'pt' -> 'pt' -> egress_walk the main mode is pt.
				Main modes are sorted as in the MiD (Mobilität in Deutschland)
				See class Trip.addLeg for more details
				 */
				String modeOfTrip = trip.getMainMode();
				countByMode.merge(modeOfTrip, 1., Double::sum);
				totalTrips++;
			}
		}

		System.out.println("The modal split is:");
		for (Map.Entry<String, Double> entry : countByMode.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + " / " + 100 * entry.getValue() / totalTrips + "%");
		}
	}
}
