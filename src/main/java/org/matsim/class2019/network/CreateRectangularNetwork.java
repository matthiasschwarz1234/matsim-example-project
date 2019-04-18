package org.matsim.class2019.network;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkUtils;

class CreateRectangularNetwork {
	
	public static void main(String[] args) {
		new CreateRectangularNetwork().create(0, 1000, 0, 1000);
	}
	
	void create(double left, double right, double top, double bottom) {
		
		// create an empty network
		Network network = NetworkUtils.createNetwork();
		
		// create nodes and add them to the network
		NetworkFactory factory = network.getFactory();
		Node n0 = factory.createNode(Id.createNodeId(0), new Coord(left, top));
		network.addNode(n0);
		Node n1 = factory.createNode(Id.createNodeId(1), new Coord(right, top));
		network.addNode(n1);
		Node n2 = factory.createNode(Id.createNodeId(2), new Coord(right, bottom));
		network.addNode(n2);
		Node n3 = factory.createNode(Id.createNodeId(3), new Coord(left, bottom));
		network.addNode(n3);
		
		// add links to the network
		Link l0 = createLink(n0, n1, 100, 8.3, factory);
		network.addLink(l0);
		Link l1 = createLink(n1, n2, 100, 8.3, factory);
		network.addLink(l1);
		Link l2 = createLink(n2, n3, 100, 8.3, factory);
		network.addLink(l2);
		Link l3 = createLink(n3, n0, 100, 8.3, factory);
		network.addLink(l3);
		
		
		
		try {
			// create an output folder if necessary
			Path outputFolder = Files.createDirectories(Paths.get("output"));
			
			// write the network
			System.out.println("Writing the network to: " + outputFolder.toAbsolutePath().toString());
			new NetworkWriter(network).write(outputFolder.resolve("rect-network.xml").toString());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Link createLink(Node from, Node to, double capacity, double freespeed, NetworkFactory factory) {
		
		Link result = factory.createLink(Id.createLinkId(UUID.randomUUID().toString()), from, to);
		result.setFreespeed(freespeed);
		result.setCapacity(capacity);
		result.setAllowedModes(getAllowedTransportModes());
		return result;
	}
	
	private Set<String> getAllowedTransportModes() {
		Set<String> result = new HashSet();
		result.add(TransportMode.car);
		result.add(TransportMode.pt);
		return result;
	}

}
