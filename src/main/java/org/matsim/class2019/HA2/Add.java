package org.matsim.class2019.HA2;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitScheduleReader;
import org.matsim.vehicles.VehicleReaderV1;

public class Add{

	private static Path inputNetwork = Paths.get("C:\\Users\\Matthias Schwarz\\Desktop\\HA2\\input\\berlin-v5-network.xml.gz");
	private static Path outputNetwork = Paths.get("C:\\Users\\Matthias Schwarz\\Desktop\\HA2\\input\\network-waßmann.xml.gz");
	
	private static final Path transitSchedule = Paths.get("C:\\Users\\Matthias Schwarz\\Desktop\\HA2\\input\\transitSchedule.xml.gz");
	private static final Path transitVehicles = Paths.get("C:\\Users\\Matthias Schwarz\\Desktop\\HA2\\input\\transitVehicles.xml.gz");

	private static final Path outputVehicles = Paths.get("C:\\Users\\Matthias Schwarz\\Desktop\\HA2\\input\\output_transitvehicles.xml.gz");
	private static final Path outputTransitSchedule = Paths.get("C:\\Users\\Matthias Schwarz\\Desktop\\HA2\\input\\output_transitSchedule.xml.gz");

			
	private Set<String> allowedModes = new HashSet<>();
	//private Set<Link2Add> links2Add = new HashSet<>();
	
	public Add()
	{
		allowedModes.add(TransportMode.car);
		allowedModes.add(TransportMode.ride);
		//TODO: are this enough modes for everything?
		
		//Verlauf: WP, Länge: GM
		//links2Add.add( new Link2Add("ID",		"STARTNODE",		"ENDNODE",	LENGTH,	LANES, CAPACITY, SPEED))
//		links2Add.add( new Link2Add("9999900", "261022410",		"287932598",	1410,	2, 2000.0,	6.944444444444445));	//2-spur "Am Treptower Park - neu stadteinwärts"
//		links2Add.add( new Link2Add("9999901", "287932598",		"20246103",		130,	4, 6000.0,	6.944444444444445));	//4-spur "Am Treptower Park - neu stadteinwärts"
//		links2Add.add( new Link2Add("9999902", "27542432",		"5332081036",	800,	3, 6000.0,	16.666666666666668));	//NK-Sonne
//		links2Add.add( new Link2Add("9999903", "560916905",		"27542414",		800,	3, 6000.0,	16.666666666666668));	//Sonne-NK
//		links2Add.add( new Link2Add("9999904", "5332081036",	"3386901041",	10,		3, 6000.0,	16.666666666666668));	//Kreuz Sonne R
//		links2Add.add( new Link2Add("9999905", "3386901047",	"560916905",	10,		3, 6000.0,	16.666666666666668));	//Kreuz Sonne L
//		links2Add.add( new Link2Add("9999906", "3386901041",	"287932598",	2300,	3, 6000.0,	16.666666666666668));	//Sonne-Trep
//		links2Add.add( new Link2Add("9999907", "287932598",		"3386901047",	2300,	3, 6000.0,	16.666666666666668));	//Trep-Sonne
//		links2Add.add( new Link2Add("9999908", "287932598",		"29786490",		1200,	3, 6000.0,	16.666666666666668));	//Trep-Ost
//		links2Add.add( new Link2Add("9999909", "29786490",		"287932598",	1200,	3, 6000.0,	16.666666666666668));	//Ost-Trep
//		links2Add.add( new Link2Add("9999910", "2856612730",	"29786490",		1700,	3, 6000.0,	16.666666666666668));	//Frank-Ost
//		links2Add.add( new Link2Add("9999911", "29786490",		"474745411",	1700,	3, 6000.0,	16.666666666666668));	//Ost-Frank
//		links2Add.add( new Link2Add("9999912", "474745411",		"99999999",		400,	3, 6000.0,	16.666666666666668));	//Frank-FrankAuf
//		links2Add.add( new Link2Add("9999913", "99999998",		"2856612730",	400,	3, 6000.0,	16.666666666666668));	//FrankAb-Frank
//		links2Add.add( new Link2Add("9999914", "99999998",		"100078730",	50,		2, 3000.0,	6.944444444444445));	//FrankAb-L
//		links2Add.add( new Link2Add("9999915", "100078730",		"99999999",		50,		2, 3000.0,	6.944444444444445));	//FrankAuf-R
//		links2Add.add( new Link2Add("9999916", "99999999",		"27195069",		850,	3, 6000.0,	16.666666666666668));	//FrankAuf-R-Stork
//		links2Add.add( new Link2Add("9999917", "27195069",		"99999998",		850,	3, 6000.0,	16.666666666666668));	//Stork-FrankAb-L
	}

	public static void main(String[] args) {
		new Add().run();
	
		}
	
	public void run() {
	
		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.createScenario(config);

		// read in existing files
		new TransitScheduleReader(scenario).readFile(transitSchedule.toString());
		new VehicleReaderV1(scenario.getTransitVehicles()).readFile(transitVehicles.toString());
		new MatsimNetworkReader(scenario.getNetwork()).readFile(networkPath.toString());
		
		Network network = NetworkUtils.createNetwork();
		new MatsimNetworkReader(network).readFile(inputNetwork.toString());
		
		NetworkFactory factory = network.getFactory();
		
		//new node for pt stop Waßmansdorf
		Node wdorf  = factory.createNode(Id.createNodeId("pt_999999999999"), new Coord(4599777.260128, 5804904.304653));
		network.addNode(wdorf);
		
		// get the existing nodes we want to connect
				Node sxf = scenario.getNetwork().getNodes().get(Id.createNodeId("pt_060260005671"));
				Node BER = scenario.getNetwork().getNodes().get(Id.createNodeId("pt_160000014391"));
				Node stop3Node = scenario.getNetwork().getNodes().get(Id.createNodeId("pt_000000167205"));
		
		// connect nodes with links
				Link link1 = createLink("wdorf-sxf", wdorf, sxf, scenario.getNetwork().getFactory());
				Link link2 = createLink("sxf-wdorf", sxf, wdorf, scenario.getNetwork().getFactory());
				Link link3 = createLink("wdorf-BER", wdorf, BER, scenario.getNetwork().getFactory());
				Link link4 = createLink("BER-wdorf", BER, wdorf, scenario.getNetwork().getFactory());
				scenario.getNetwork().addLink(link1);
				scenario.getNetwork().addLink(link2);
				scenario.getNetwork().addLink(link3);
				scenario.getNetwork().addLink(link4);
				
		
		//new capacity + numOfLanes for links at frankfurter
//		network.getLinks().get(Id.createLinkId( "3487"  )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "21867" )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "28054" )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "106815")).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "39993" )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "39993" )).setNumberOfLanes(2);
//		network.getLinks().get(Id.createLinkId( "39994" )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "39994" )).setNumberOfLanes(2);
//		network.getLinks().get(Id.createLinkId( "39995" )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "39995" )).setNumberOfLanes(2);
//		network.getLinks().get(Id.createLinkId( "39996" )).setCapacity(3200.0);
//		network.getLinks().get(Id.createLinkId( "39996" )).setNumberOfLanes(2);
//		
//		//new capacity, numberOfLanes for links at cross neukölln, minimum 6000, 3
//		network.getLinks().get(Id.createLinkId( "51377" )).setCapacity(6000.0);
//		network.getLinks().get(Id.createLinkId( "51377" )).setNumberOfLanes(3);
//		

//		for(Link2Add link2Add: links2Add) {
//			
//			Id<Link> linkId = link2Add.getId();
//			Node startNode	= network.getNodes().get( link2Add.getStartNodeId() );
//			Node endNode	= network.getNodes().get( link2Add.getEndNodeId() );
//			
//			Link link = factory.createLink( linkId, startNode, endNode );
//			addAttributes( link, link2Add );
//			link.setLength( link2Add.getLength() );
//			
//			network.addLink(link);
//		}
		
		new NetworkWriter(network).write(outputNetwork.toString());
	}
	
	
}
