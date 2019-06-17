package org.matsim.class2019.demand;

import org.junit.Ignore;
import org.junit.Test;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationWriter;

import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

public class CreateDemandTest {

	@Test
	@Ignore
	public void test() {
		CreateDemand creator = new CreateDemand(Paths.get("C:\\Users\\Janek\\Desktop\\ueberregionale-pendler.csv"),
				Paths.get("C:\\Users\\Janek\\Desktop\\regionale-pendler.csv"),
				Paths.get("C:\\Users\\Janek\\Desktop\\thueringen-kreise.shp"),
				Paths.get("C:\\Users\\Janek\\Desktop\\landcover.shp"));

		creator.create();

		Population result = creator.getPopulation();

		assertNotNull(result);
		System.out.println(result.getPersons().size());

		new PopulationWriter(result).write(Paths.get("C:\\Users\\Janek\\Desktop\\population.xml.gz").toString());
	}

}